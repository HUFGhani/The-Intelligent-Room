<?php
// Start the session
  if(!isset($_SESSION)) 
    { 
        session_start(); 
    }
    else
    {
        session_destroy();
        session_start(); 
    }

$Email = isset($_POST['Email']) ? $_POST['Email'] : '';
$UserPassword = isset($_POST['UserPassword']) ? $_POST['UserPassword'] : '';

include 'Connection.php';


if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
//
$stmt = $conn->prepare("select * from users where Email = ? and UserPassword = ?");
//
$stmt->bind_param("ss", $Email, $UserPassword);

$stmt->execute();

$result = $stmt->get_result();

while ($row = $result->fetch_assoc()) {
    $_SESSION['Email'] = $row["Email"];
    $_SESSION['Firstname'] = $row["Firstname"];
    $_SESSION['Lastname'] = $row["Lastname"];
    
    if ($row >0){
        header('Location: index.php');  
    } 
}

//echo $Email;
//echo $UserPassword;



$stmt->close();

?>