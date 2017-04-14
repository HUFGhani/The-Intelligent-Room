
// Create a client instance
var json;
function sendHue() {
    var red = document.getElementById("red").value;
    var green = document.getElementById("green").value;
    var blue = document.getElementById("blue").value;
    var sat = document.getElementById("sat").value;
    var bri = document.getElementById("bri").value;
    var auto = document.getElementById("automationHue").value;

    json = "{\"light:{";
    json+= "\"name\":\"test\",";
    json+="\"on/off\":"+ true +",";
    json+= "\"colour\":{" ;
    json+= "\"red\":"+ red +",";
    json+= "\"green\":" + green + ",";
    json+="\"blue\":" + blue +"},";
    json+="\"saturation\":" + sat +",";
    json+= "\"brightness\":" + bri + ",";
    json+= "\"automated\":" + auto + "}}";


    client = new Paho.MQTT.Client("localhost", 1884, "clientId1");
// connect the client
    client.connect({onSuccess: onConnectHuePub});
}

// called when the client connects
function onConnectHuePub() {
    // Once a connection has been made, make a subscription and send a message.
    console.log("onConnect hue pub");
    message = new Paho.MQTT.Message(json);
    message.destinationName = "houseID123/actuator/hue";
    client.send(message);
}

$("#automationHue").on('change', function() {
                      if ($(this).is(':checked')) {
                        $(this).attr('value', 'true');
                      } else {
                        $(this).attr('value', 'false');
                      }

                    });