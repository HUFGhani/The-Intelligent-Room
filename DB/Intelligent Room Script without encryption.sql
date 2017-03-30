Drop Table PhilipsHue;
Drop Table sensorValues;
Drop Table sensors;
Drop Table nest;
Drop Table users;
Drop Table house;

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
SensorID Int NOT NULL auto_increment, 
SensorName varchar(45),
SensorMethod varchar(45),
PortNumber integer(2),
SensorPriority integer(10),
TimeInserted TimeStamp default current_timestamp,
HouseID Int,
PRIMARY KEY (SensorID),
FOREIGN KEY (HouseID) REFERENCES House(HouseID)
);


CREATE TABLE sensorValues(
SensorValue Integer(15),
TimeInserted TimeStamp default current_timestamp,
SensorID Int, 
FOREIGN KEY (SensorID) REFERENCES sensors(SensorID)
);


CREATE TABLE PhilipsHue(
Id Int NOT NULL auto_increment, 
Lightname varchar(30), 
Brightness integer(10),
Saturation integer(10), 
Red integer(3), 
Blue integer(3), 
Green integer(3), 
Power boolean, 
AutomaticStatus boolean, 
PRIMARY KEY (Id)
);






