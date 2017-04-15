var suboutput;
client = new Paho.MQTT.Client("localhost",1884, "clientId");
function getNest() {

    client.onConnectionLost = onConnectionLostNestsub;
    client.onMessageArrived = onMessageArrivedNestSub;

    // connect the client
    client.connect({onSuccess:onConnectNestsub});
}

function onConnectNestsub() {
    // Once a connection has been made, make a subscription and send a message.
    console.log("Connect nest sub");
    client.subscribe("houseID123/actuator/nest/status");
}

// called when the client loses its connection
function onConnectionLostNestsub(responseObject) {
    if (responseObject.errorCode !== 0) {
        console.log("onConnectionLost:"+responseObject.errorMessage);
    }
}

// called when a message arrives
function onMessageArrivedNestSub(message) {
    var temp = message.payloadString;
    suboutput = JSON.parse(temp);
    document.getElementById("output").innerHTML= suboutput.target_temperature_c;
}
