<?php

require("phpMQTT.php");


$mqtt = new phpMQTT("localhost", 1883, "phpMQTT Pub Example"); //Change client name to something unique

if ($mqtt->connect()) {
	$mqtt->publish("test",$_REQUEST['message'],0, 1);
	$mqtt->close();
}

?>
