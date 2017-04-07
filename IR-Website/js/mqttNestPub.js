/**
 * Created by hamzaghani on 07/04/2017.
 */

// Create a client instance
var nestInput;
function sendNest() {
    nestInput = document.getElementById("temperature").value;
    client = new Paho.MQTT.Client("localhost", 1884, "clientId1");
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
