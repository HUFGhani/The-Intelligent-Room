Drop Table if exists PrefTemp; 
Drop Table if exists PrefLighting; 
Drop Table if exists PhilipsHue;
Drop Table if exists sensorValues;
Drop Table if exists sensors;
Drop Table if exists nest;
Drop Table if exists users;
Drop Table if exists house;

CREATE TABLE house(
HouseId int NOT NULL auto_increment, 
housePass varchar(255),
Latitude double,
Longitude double,
QR varchar(30),
PRIMARY KEY (HouseID)
);


CREATE TABLE users(
UserID Int NOT NULL auto_increment, 
UserPassword varchar(255),
Lastname varchar(30),
Firstname varchar(30),
Priority integer(2),
Email varchar(80) unique,
HouseId Int,
PRIMARY KEY (UserID),
FOREIGN KEY (HouseId) REFERENCES House(HouseId)	
);


CREATE TABLE nest(
NestId Int NOT NULL auto_increment,
Temperature integer(2), 
Lastupdated timeStamp default current_timestamp,
automaticStatus boolean, 
HouseId Int, 
PRIMARY KEY (NestId), 
FOREIGN KEY (HouseId) REFERENCES House(HouseId)
);


CREATE TABLE sensors(
SensorID Int NOT NULL, 
SensorName varchar(30),
SensorMethod varchar(20),
PortNumber integer(2),
SensorPriority integer(2),
TimeInserted TimeStamp default current_timestamp,
HouseID Int NOT NULL,
PRIMARY KEY (SensorID, HouseID),
FOREIGN KEY (HouseID) REFERENCES House(HouseID)
);

CREATE TABLE sensorValues(
SensorValueID Int auto_increment,
SensorValue Integer(3),
TimeInserted TimeStamp default current_timestamp,
SensorID Int, 
PRIMARY KEY (SensorValueID),
FOREIGN KEY (SensorID) REFERENCES sensors(SensorID)
);
		

CREATE TABLE PhilipsHue(
Id Int NOT NULL auto_increment, 
Lightname varchar(30), 
Brightness integer(3),
Saturation integer(3), 
Red integer(3), 
Blue integer(3), 
Green integer(3), 
Power boolean, 
AutomaticStatus boolean, 
HouseId Int, 
PRIMARY KEY (Id),
FOREIGN KEY (HouseId) REFERENCES house(HouseId)
);

CREATE TABLE PrefLighting(
Brightness integer(3),
Saturation integer(3), 
Red integer(3), 
Blue integer(3), 
Green integer(3), 
actionMethod varchar(10), 
actionPriority integer(3),
UserID Int, 
FOREIGN KEY (UserID) REFERENCES users(UserID)
);


CREATE TABLE PrefTemp(
TargetTemp integer(2),
actionMethod varchar(10),
actionPriority integer(3), 
UserID Int, 
FOREIGN KEY (UserID) REFERENCES users(UserID)
);





