
// Create a client instance
var json;
function sendUser() {
    var first     = document.getElementById("fNameEdit").value;
    var last   = document.getElementById("lNameEdit").value;
    var prio    = document.getElementById("priorityEdit").value;
    var red     = document.getElementById("redPrefEdit").value;
    var green     = document.getElementById("greenPrefEdit").value;
    var blue    = document.getElementById("bluePrefEdit").value;
    var satur     = document.getElementById("satPrefEdit").value;
    var bright   = document.getElementById("briPrefEdit").value;
    var lact    = document.getElementById("lightActPrefEdit").value;
    var lactp     = document.getElementById("lightActPriorityPrefEdit").value;
    var temp     = document.getElementById("tempPrefEdit").value;
    var tact    = document.getElementById("tempActPrefEdit").value;
    var tactp     = document.getElementById("tempActPriorityPrefEdit").value;
    
    
    json = "{\"userId\":\"1\",";
    json+= "\"firstName\":" + first +",";
    json+= "\"lastName\":" + last +",";
    json+= "\"priority\":" + prio +",";
    json+= "\"lightPref\":{" ;
    json+= "\"light\":{" ;
    json+="\"name\":\"Hue color lamp 1\",";
    json+= "\"colour\":{" ;
    json+= "\"red\":"+ red +",";
    json+= "\"green\":" + green + ",";
    json+= "\"blue\":" + blue +"},";
    json+= "\"saturation\":" + satur +",";
    json+= "\"brightness\":" + bright +",";
    json+= "\"actionMethod\":" + lact +",";
    json+= "\"actionPriority\":" + lactp +"},";
    json+= "\"tmpPref\":{" ;
    json+= "\"nest\":{" ;
    json+= "\"target_temperature_c\":" + temp +",";
    json+= "\"actionMethod\":" + tact +",";
    json+= "\"actionPriority\":" + tactp +"}}";
    
//    client = new Paho.MQTT.Client("localhost", 1884, "clientId1");
    client = new Paho.MQTT.Client("ec2-52-56-203-226.eu-west-2.compute.amazonaws.com", 1884, "clientId1");
// connect the client
    client.connect({onSuccess: onConnectUserPub});
}

// called when the client connects
function onConnectUserPub() {
    // Once a connection has been made, make a subscription and send a message.
    console.log("onConnect user pub");
    message = new Paho.MQTT.Message(json);
    message.destinationName = "houseID123/1/preference";
    client.send(message);
}
