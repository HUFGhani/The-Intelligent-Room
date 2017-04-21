<?php

// 1. Create a database connection
$dbhost = "ec2-52-56-203-226.eu-west-2.compute.amazonaws.com";
$dbuser = "root";
$dbpass = "TwN20nDwuJGB";
$dbname = "iot";

$conn = new mysqli($dbhost, $dbuser, $dbpass, $dbname,3306);


if(mysqli_connect_errno()) {
    die("Database connection failed: " .
     	mysqli_connect_error() .
   	  " (" . mysqli_connect_errno() . ")"
       );
}

?>
