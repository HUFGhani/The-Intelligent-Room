<?php

if(!isset($_SESSION)) 
    { 
        session_start(); 
    }
    else
    {
        session_destroy();
        session_start(); 
    }    

$UserID = '';

 if(isset($_SESSION['UserID'])){
     $UserID = $_SESSION['UserID'];
     $FName = $_SESSION['Firstname'];
 }   

else{
   $UserID = 'Logged Out';
}
?>

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
    <link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-slider/9.7.3/css/bootstrap-slider.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">

  </head> 
    
<?php
    if($UserID != "Logged Out"){
?>

<body>
      <nav class="navbar navbar-default">
        <div class="container-fluid">
          <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">SmartHome</a>
          </div>
          <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
              <li><a>Welcome User <?php echo $UserID; ?></a></li>
              <li><a href="#" data-toggle="modal" onclick="getNest();" data-target="#myModal">Nest</a></li>
              <li><a href="#" data-toggle="modal" onclick="getHue();" data-target="#myModal1">Hue</a></li>
              <li><a href="#" data-toggle="modal" onclick="getUser();" data-target="#userModal">User Preferences</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
             <li class="active"><a href="logout.php">Sign Out</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
      </nav>

<!-- Modal -->
  <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="myModalLabel">Nest</h4>
        </div>
        <div id="modal-body" class="modal-body">
            <div class="row">
                <h4>Current Temperature</h4><h2 id="output">°C</h2>
            </div>
            <div>
                <h4>Choose a new Temperature</h4><input id="tempNew" data-slider-id='tempSlider' type="text" data-slider-min="9" data-slider-max="32" data-slider-step="1" data-slider-value="15"/>        
            </div>
            <span id="tempNewCurrentSliderValLabel">Temperature: <label id="tempNewVal"></label></span><br>
        </div>
        <div class="checkbox">
          <label>
            <input id="automation" type="checkbox" data-toggle="toggle" value="false">
            Automation
          </label>
        </div>
    
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="button" onclick="sendNest();" data-dismiss="modal" class="btn btn-primary">Save changes</button>
        </div>
      </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Choose Colour</h4>
               
      </div>
      <div class="modal-body">
        <script type="text/javascript" src="js/colorpicker.js"></script>
        <style type="text/css">
              #picker { width: 220px; height: 200px }
              #slide { width: 60px; height: 200px }
        </style>
        <div class ="row">
            <div class ="col-xs-6" id="picker"></div>
            <div class ="col-xs-6" id="slide"></div>          
        </div>
        <script type="text/javascript">
          ColorPicker(
            document.getElementById('slide'),
            document.getElementById('picker'),
            function(hex, hsv, rgb) {
              document.getElementById('red').innerHTML = rgb.r.toFixed();
              document.getElementById('green').innerHTML = rgb.g.toFixed();
              document.getElementById('blue').innerHTML = rgb.b.toFixed();
            });
        </script>
        <div>
            <h4>Brightness</h4><input id="briNew" data-slider-id='briSlider' type="text" data-slider-min="0" data-slider-max="254" data-slider-step="5" data-slider-value="125"/>        
        </div>
          <hr>
          <div>
            <h4>Saturation</h4><input id="satNew" data-slider-id='satSlider' type="text" data-slider-min="0" data-slider-max="254" data-slider-step="5" data-slider-value="125"/>        
        </div>
          <hr>
          <h4>Current Settings</h4>
          <div class="row">
            <div class="col-md-6"></div><br>
                Red:   <label id="routput"></label><br>
                Green: <label id="goutput"></label><br>
                Blue:  <label id="boutput"></label><br>
                Brightness: <label id="brioutput"></label><br>
                Saturation: <label id="satoutput"></label>
          </div>
      <hr>
          <h4>New Settings</h4>
          <div class="row">
            <div class="col-md-6"></div><br>
                Red:   <label id="red"></label><br>
                Green: <label id="green"></label><br>
                Blue:  <label id="blue"></label><br>
                <span id="briNewCurrentSliderValLabel">Brightness: <label id="briNewVal"></label></span><br>
                <span id="satNewCurrentSliderValLabel">Saturation: <label id="satNewVal"></label></span>
          </div>
      </div>
        <div class="checkbox">
          <label>
            <input id="automationHue" type="checkbox" data-toggle="toggle" value="false">
            Automation
          </label>
        </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="sendHue();">Save changes</button>
      </div>
    </div>
  </div>
</div>
      
  <div class="modal fade" id="userModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="myModalLabel">User Preferences</h4>
        </div>
        <div id="modal-body" class="modal-body">
            First Name:   <label id="fName"></label><br>
            Last Name: <label id="lName"></label><br>
            Priority:  <label id="priority"></label><br>
            Red:   <label id="redPref"></label><br>
            Green: <label id="greenPref"></label><br>
            Blue:  <label id="bluePref"></label><br>
            Saturation:   <label id="satPref"></label><br>
            Brightness: <label id="briPref"></label><br>
            Action Method:   <label id="lightActPref"></label><br>
            Action Priority:   <label id="lightActPriorityPref"></label><br>
            Temperature:  <label id="tempPref"></label><br>
            Action Method:   <label id="tempActPref"></label><br>
            Action Priority:   <label id="tempActPriorityPref"></label><br>
        </div>
    
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="button" data-toggle="modal" href="#userEditModal" data-dismiss="modal" class="btn btn-primary">Edit Preferences</button>
        </div>
      </div>
    </div>
</div>
        
 
  <div class="modal fade" id="userEditModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="myModalLabel">User Preferences</h4>
        </div>
        <div id="modal-body" class="modal-body">
            <div>
            First Name:  <input id="fNameEdit"><br>
            Last Name:   <input id="lNameEdit"><br>
            Priority:    <select id="priorityEdit" class="form-control" name="color">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                  </select>
            </div>
        
            <div>
            <p>Red</p><input id="redPrefEdit" data-slider-id='redSlider' type="text" data-slider-min="0" data-slider-max="255" data-slider-step="5" data-slider-value="100"/>        
        </div>
            <div>
            <p>Green</p><input id="greenPrefEdit" data-slider-id='greenSlider' type="text" data-slider-min="0" data-slider-max="255" data-slider-step="5" data-slider-value="100"/>        
        </div>
            <div>
            <p>Blue</p><input id="bluePrefEdit" data-slider-id='blueSlider' type="text" data-slider-min="0" data-slider-max="255" data-slider-step="5" data-slider-value="100"/>        
        </div>
            <div>
            <p>Saturation</p><input id="satPrefEdit" data-slider-id='satEditSlider' type="text" data-slider-min="0" data-slider-max="254" data-slider-step="5" data-slider-value="100"/>        
        </div>
            <div>
            <p>Brightness</p><input id="briPrefEdit" data-slider-id='briEditSlider' type="text" data-slider-min="0" data-slider-max="254" data-slider-step="5" data-slider-value="100"/>        
        </div>
            <div>
                Light Action Method:    <select id="lightActPrefEdit" class="form-control" name="lam">
                                    <option value="light">Light</option>
                                    <option value="motion">Motion</option>
                                    <option value="location">Location</option>
                                  </select>
                Light Action Priority:  <select id="lightActPriorityPrefEdit" class="form-control" name="lap">
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                  </select>
            </div>
            <div>
            <p>Temperature</p><input id="tempPrefEdit" data-slider-id='tempEditSlider' type="text" data-slider-min="9" data-slider-max="32" data-slider-step="1" data-slider-value="15"/>        
        </div>
            <div>
                Action Method:    <select id="tempActPrefEdit" class="form-control" name="tam">
                                    <option value="motion">Motion</option>
                                    <option value="location">Location</option>
                                  </select>
                Action Priority:  <select id="tempActPriorityPrefEdit" class="form-control" name="tap">
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                  </select>
            </div>
        </div>
    
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="button" onclick="sendUser();" data-dismiss="modal" class="btn btn-primary">Save changes</button>
        </div>
      </div>
    </div>        
<?php
}
else {
?>   
<div class="wrapper">
    <form action="checkLogin.php" method="post">    
      <h2 class="form-signin-heading">Please login</h2>
      <input type="text" class="form-control" name="Email" placeholder="Email Address" required="" autofocus="" />
      <input type="password" class="form-control" name="UserPassword" placeholder="Password" required=""/>      
      <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>   
    </form>
  </div>
</div>
<?php
}
?>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="//code.jquery.com/jquery-1.10.2.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-slider/9.7.3/bootstrap-slider.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/paho-mqtt/1.0.1/mqttws31.min.js" type="text/javascript"></script>
  
    <script src="js/script.js" type="text/javascript"></script>
    <script src="js/mqttNestSub.js" type="text/javascript"></script>
    <script src="js/mqttNestPub.js" type="text/javascript"></script>
    <script src="js/mqttHueSub.js" type="text/javascript"></script>
    <script src="js/mqttHuePub.js" type="text/javascript"></script>
    <script src="js/mqttUserSub.js" type="text/javascript"></script>
    <script src="js/mqttUserPub.js" type="text/javascript"></script>
    <script src="js/colorpicker.js" type="text/javascript"></script>
    </body>
</html>
    