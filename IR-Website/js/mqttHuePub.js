
// Create a client instance
var json;
function sendHue() {
    var red = document.getElementById('red').innerHTML;
    var green = document.getElementById("green").innerHTML;
    var blue = document.getElementById("blue").innerHTML;
    var bri = document.getElementById("briNew").value;
    var sat = document.getElementById("satNew").value;
    var auto = document.getElementById("automationHue").value;

    json = "{\"light\":{ ";
    json+= "\"name\":\"Hue color lamp 1\",";
    json+="\"on/off\":"+ true +",";
    json+= "\"colour\":{" ;
    json+= "\"red\":"+ red +",";
    json+= "\"green\":" + green + ",";
    json+="\"blue\":" + blue +"},";
    json+= "\"brightness\":" + bri + ",";
    json+="\"saturation\":" + sat +",";
    json+= "\"automated\":" + auto + "}}";


    client = new Paho.MQTT.Client("localhost", 1884, "clientId1");
//    client = new Paho.MQTT.Client("ec2-52-56-203-226.eu-west-2.compute.amazonaws.com", 1884, "clientIdHuePub");
// connect the client
    client.connect({onSuccess: onConnectHuePub});
}

// called when the client connects
function onConnectHuePub() {
    // Once a connection has been made, make a subscription and send a message.
    console.log("onConnect hue pub");
    message = new Paho.MQTT.Message(json);
    message.qos = 2;
    message.destinationName = "houseID123/actuator/hue";
    message.retained = true;
    client.send(message);
}

$("#automationHue").on('change', function() {
                      if ($(this).is(':checked')) {
                        $(this).attr('value', 'true');
                      } else {
                        $(this).attr('value', 'false');
                      }

                    });