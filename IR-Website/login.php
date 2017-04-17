<?php

include 'Connection.php';
//include 'checkLogin.php';

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

?>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>SmartHome</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
  </head> 

<div class="wrapper">
    <form action="checkLogin.php" method="post">    
      <h2 class="form-signin-heading">Please login</h2>
      <input type="text" class="form-control" name="Email" placeholder="Email Address" required="" autofocus="" />
      <input type="password" class="form-control" name="UserPassword" placeholder="Password" required=""/>      
      <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>   
    </form>
  </div>

    
    