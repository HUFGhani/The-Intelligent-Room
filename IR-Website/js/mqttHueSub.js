
function getHue() {

        client = new Paho.MQTT.Client("localhost", 1884, "clientId");
        client.onConnectionLost = onConnectionLostHuesub;
        client.onMessageArrived = onMessageArrivedHueSub;

        // connect the client
        client.connect({onSuccess: onConnectHuesub});
    }

    function onConnectHuesub() {
        // Once a connection has been made, make a subscription and send a message.
        console.log("Connect hue sub");
        client.subscribe("houseID123/actuator/hue/status");
    }

// called when the client loses its connection
    function onConnectionLostHuesub(responseObject) {
        if (responseObject.errorCode !== 0) {
            console.log("onConnectionLost:" + responseObject.errorMessage);
        }
    }

// called when a message arrives
    function onMessageArrivedHueSub(message) {
        var temp = message.payloadString;
        suboutput = JSON.parse(temp);
        document.getElementById("routput").innerHTML= suboutput.light.colour.red;
        document.getElementById("goutput").innerHTML= suboutput.light.colour.green;
        document.getElementById("boutput").innerHTML= suboutput.light.colour.blue;
        document.getElementById("broutput").innerHTML= suboutput.light.brightness;
        document.getElementById("satoutput").innerHTML= suboutput.light.saturation;
}