// Create a client instance
var nestInput;
function sendNest() {
    var temp = document.getElementById("tempNew").value;
    var auto = document.getElementById("automation").value;
    
    
    nestInput = "{\"target_temperature_c\":" + temp + ", ";
    nestInput+= "\"automated\":" + auto + "}";
  
    //client = new Paho.MQTT.Client("localhost", 1884, "clientId1");
    client = new Paho.MQTT.Client("ec2-52-56-203-226.eu-west-2.compute.amazonaws.com", 1884, "clientId1");
// connect the client
    client.connect({onSuccess: onConnectNestPub});
}

// called when the client connects
function onConnectNestPub() {
    // Once a connection has been made, make a subscription and send a message.
    console.log("onConnect nest pub");
    message = new Paho.MQTT.Message(nestInput);
    message.destinationName = "houseID123/actuator/nest";
    client.send(message);
}

$("#automation").on('change', function() {
                      if ($(this).is(':checked')) {
                        $(this).attr('value', 'true');
                      } else {
                        $(this).attr('value', 'false');
                      }

                    });