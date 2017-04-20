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

$UserID = isset($_POST['UserID']) ? $_POST['UserID'] : '';
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
    $_SESSION['UserID'] = $row["UserID"];
    
    if ($row = 1){
        header('Location: index.php');  
    } 
    else {
        
    }
}

echo "Incorrect Login Details, please go back and try again...";
?>
<!DOCTYPE html>
<html>
<head>
<script>
function goBack() {
    window.history.back()
}
</script>
</head>
<body>
<br>
<button onclick="goBack()">Go Back</button>
</body>
</html>

<?
$stmt->close();
?>
