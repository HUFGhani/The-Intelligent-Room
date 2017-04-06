<?php
require("phpMQTT.php");
// echo "<br> mqtt starting..";

$mqtt = new phpMQTT("localhost", 1883, "MQTT Subscriber Client");
// echo "<br> mqtt created..";
if(!$mqtt->connect()){
	    // echo "<br> mqtt not connected..";
	        exit(1);

}
$topics['houseID123/actuator/nest/status'] = array("qos"=>2, "function"=>"receiveMessage");

$mqtt->subscribe($topics,2);
// echo "<br> mqtt Subscribed..";
$mqtt->proc();

$mqtt->close();
// echo "<br> mqtt closed..";
function receiveMessage($topic,$msg){

	        // echo "<b>";
		      //   echo "<br> Msg Recieved at ".date("r");
		      //   echo "<br> Topic   : {$topic}";
			         echo $msg;
			        // echo "</b>";
}


?>
