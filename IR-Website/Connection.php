<?php

// 1. Create a database connection
$dbhost = "mudfoot.doc.stu.mmu.ac.uk";
$dbuser = "robertsn";
$dbpass = "ensteLas6";
$dbname = "robertsn";

$conn = new mysqli($dbhost, $dbuser, $dbpass, $dbname);


if(mysqli_connect_errno()) {
    die("Database connection failed: " . 
     	mysqli_connect_error() . 
   	  " (" . mysqli_connect_errno() . ")"
       );
}

?>