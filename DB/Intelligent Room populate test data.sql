Insert into house(housePass, Latitude, Longitude, QR) values ('House123', 53.4757667, -2.2441503, 'House123');
Insert into house(housePass, Latitude, Longitude, QR) values ('House123', 53.4630589, -2.2935288, 'House234');
Insert into users(UserPassword, Lastname, Firstname, Priority, Email, HouseId) values ('User123', 'Roberts','Nicholas', 1, 'nick12345@hotmail.co.uk', 1);
Insert into users(UserPassword, Lastname, Firstname, Priority, Email, HouseId) values ('User123', 'Green','Karl', 2, 'Karl12345@hotmail.co.uk', 2);
Insert into nest(Temperature, automaticStatus, HouseId) values (30, true, 1);
Insert into nest(Temperature, automaticStatus, HouseId) values (32, true, 2);
Insert into sensors(SensorName, SensorMethod, Portnumber, SensorPriority, HouseID) values ('motionSensor', 'onChanged', 1, 1, 1);
Insert into sensors(SensorName, SensorMethod, Portnumber, SensorPriority, HouseID) values ('lightSensor', 'average', 2, 1, 2);
Insert into sensorValues(SensorValue, SensorID) values (451, 1);
Insert into sensorValues(SensorValue, SensorID) values (534, 2);
Insert into PhilipsHue(Lightname, Brightness, Saturation, Red, Blue, Green, Power, AutomaticStatus) values ('Bulb1', 234, 186, 186, 192, 157, True, True);
Insert into PhilipsHue(Lightname, Brightness, Saturation, Red, Blue, Green, Power, AutomaticStatus) values ('Bulb1', 245, 192, 224, 245, 236, True, false);

Select