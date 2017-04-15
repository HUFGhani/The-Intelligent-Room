
function getUser() {

        //client = new Paho.MQTT.Client("localhost", 1884, "clientId");
        client = new Paho.MQTT.Client("ec2-52-56-203-226.eu-west-2.compute.amazonaws.com", 1884, "clientIdUserSub");
        client.onConnectionLost = onConnectionLostUsersub;
        client.onMessageArrived = onMessageArrivedUserSub;

        // connect the client
        client.connect({onSuccess: onConnectUsersub});
    }

    function onConnectUsersub() {
        // Once a connection has been made, make a subscription and send a message.
        console.log("Connect user sub");
        client.subscribe("houseID123/1/preference");
    }

// called when the client loses its connection
  function onConnectionLostUsersub(responseObject) {
        if (responseObject.errorCode !== 0) {
            console.log("onConnectionLost:" + responseObject.errorMessage);
        }
    }


// called when a message arrives
    function onMessageArrivedUserSub(message) {
        var pref = message.payloadString;
        suboutput = JSON.parse(pref);
        document.getElementById("fName").innerHTML= suboutput.firstName;
        document.getElementById("lName").innerHTML= suboutput.lastName;
        document.getElementById("priority").innerHTML= suboutput.priority;
        document.getElementById("redPref").innerHTML= suboutput.lightPref.colour.red;
        document.getElementById("greenPref").innerHTML= suboutput.lightPref.colour.green;
        document.getElementById("bluePref").innerHTML= suboutput.lightPref.colour.blue;
        document.getElementById("satPref").innerHTML= suboutput.lightPref.saturation;
        document.getElementById("briPref").innerHTML= suboutput.lightPref.brightness;
        document.getElementById("lightActPref").innerHTML= suboutput.lightPref.actionMethod;
        document.getElementById("lightActPriorityPref").innerHTML= suboutput.lightPref.actionPriority;
        document.getElementById("tempPref").innerHTML= suboutput.tempPref.target_temperature_c;
        document.getElementById("tempActPref").innerHTML= suboutput.tempPref.actionMethod;
        document.getElementById("tempActPriorityPref").innerHTML= suboutput.tempPref.actionPriority;
}