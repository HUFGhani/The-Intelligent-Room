Drop Table PhilipsHue;
Drop Table LocationAutomation;
Drop Table AutomationActionLighting;
Drop Table LocationChangeType;
Drop Table actionType;
Drop Table actionHeating;
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
QR varbinary(30),
PRIMARY KEY (HouseID)
);

CREATE TABLE users(
UserID Int NOT NULL auto_increment, 
UserPassword varchar(255) unique,
Lastname varbinary(30),
Firstname varbinary(30),
Priority integer(2),
Email varbinary(80) unique,
HouseId Int,
PRIMARY KEY (UserID),
FOREIGN KEY (HouseId) REFERENCES House(HouseId)	
);

CREATE TABLE nest(
NestId Int NOT NULL auto_increment,
Temperature integer(2), 
Lastupdated timeStamp,
automaticStatus boolean, 
HouseId Int, 
PRIMARY KEY (NestId), 
FOREIGN KEY (HouseId) REFERENCES House(HouseId)
);

CREATE TABLE sensors(
SensorID Int NOT NULL auto_increment, 
SensorName varbinary(45),
SensorMethod varbinary(45),
PortNumber integer(2),
SensorPriority integer(10),
TimeInserted TimeStamp,
HouseID Int,
PRIMARY KEY (SensorID),
FOREIGN KEY (HouseID) REFERENCES House(HouseID)
);

CREATE TABLE sensorValues(
SensorValue Integer(15),
TimeInserted TimeStamp,
SensorID Int, 
FOREIGN KEY (SensorID) REFERENCES sensors(SensorID)
);

CREATE TABLE actionHeating(
AutomationActionId Int NOT NULL auto_increment,
State boolean, 
Temperature integer(2),
NestId Int, 
PRIMARY KEY (AutomationActionId),
FOREIGN KEY (NestId) REFERENCES nest(NestId)
);

CREATE TABLE actionType(
AutomationActionTypeId Int NOT NULL auto_increment,
ActionType varbinary(20),
PRIMARY KEY (AutomationActionTypeId)
);

CREATE TABLE LocationChangeType(
LocationChangeTypeID Int NOT NULL auto_increment, 
Description varbinary(10),
PRIMARY KEY (LocationChangeTypeID)
);

CREATE TABLE AutomationActionLighting(
AutomationActionId Int NOT NULL auto_increment, 
BulbID integer(10) unique, 
Red integer(3), 
Green integer(3), 
Blue integer(3),
Hue integer(10), 
Brightness integer(10),
PRIMARY KEY (AutomationActionId)
);

CREATE TABLE LocationAutomation(
LocationAutoId Int NOT NULL auto_increment, 
Distance integer(15), 
NeedsRemovingFromApp varchar(5), 
Description varchar(5), 
Latitude double, 
Longitude double, 
LocationChangeTypeID Int,
AutomationActionTypeId Int,
AutomationActionId Int, 
UserID Int,
PRIMARY KEY (LocationAutoId), 
FOREIGN KEY (LocationChangeTypeID) REFERENCES LocationChangeType(LocationChangeTypeID),
FOREIGN KEY (AutomationActionTypeId) REFERENCES actionType(AutomationActionTypeId),
FOREIGN KEY (AutomationActionId) REFERENCES AutomationActionLighting(AutomationActionId),
FOREIGN KEY (UserID) REFERENCES users(UserID)
);

CREATE TABLE PhilipsHue(
Id Int NOT NULL auto_increment, 
Lightname varbinary(30) unique, 
Brightness integer(10),
Saturation integer(10), 
Red integer(3), 
Blue integer(3), 
Green integer(3), 
Power boolean, 
AutomaticStatus boolean, 
PRIMARY KEY (Id)
);







