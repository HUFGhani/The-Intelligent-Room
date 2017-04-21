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

$FName = isset($_POST['Firstname']) ? $_POST['Firstname'] : '';
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
    $_SESSION['Firstname'] = $row["Firstname"];
    
    if ($row = 1){
        header('Location: index.php');  
    } 
    else {
        
    }
}

echo "Sorry, we can't seem to find you...";
?>

<html>
<head>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
    <link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-slider/9.7.3/css/bootstrap-slider.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
<script>
function goBack() {
    window.history.back()
}
</script>
</head>
<body>
<br>

    <div class="wrapper">
    <form action="checkLogin.php" method="post">    
      <h2 class="form-signin-heading">Please login</h2>
      <input type="text" class="form-control" name="Email" placeholder="Email Address" required="" autofocus="" />
      <input type="password" class="form-control" name="UserPassword" placeholder="Password" required=""/>      
      <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>   
    </form>
  </div>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="//code.jquery.com/jquery-1.10.2.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-slider/9.7.3/bootstrap-slider.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/paho-mqtt/1.0.1/mqttws31.min.js" type="text/javascript"></script>
</html>

<?php
$stmt->close();
?>
