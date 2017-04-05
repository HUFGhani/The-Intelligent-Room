Select u.UserID, u.Firstname, u.Lastname, u.Priority, u.HouseId, pl.Brightness, pl.Saturation, pl.Red, pl.Blue, pl.Green, pl.actionMethod as 'LightingActionMethod', pl.actionPriority, pt.TargetTemp, pt.actionMethod as 'TempActionHeating', pt.actionPriority
From users u 
inner join PrefLighting pl on u.UserID = pl.UserID
inner join PrefTemp pt on u.UserID = pt.UserID
Where u.Email = 'nick12345@hotmail.co.uk' and u.UserPassword = 'User123';

Select s.SensorID, s.SensorName, s.SensorMethod, s.TimeInserted, s.PortNumber, s.HouseID, sv.SensorValue
from sensors s
inner join sensorValues sv on s.SensorID = sv.SensorID
inner join users u on s.HouseID = u.HouseId
Where u.Email = 'nick12345@hotmail.co.uk' and u.UserPassword = 'User123';

Select HouseID 
From users u
Where u.Email = 'nick12345@hotmail.co.uk' and u.UserPassword = 'User123';

Select u.HouseID, Longitude, Latitude, NestId, ph.Id as 'philipsHueId'
From users u
inner join house h on u.HouseId = h.HouseId
left join PhilipsHue ph on h.HouseId = ph.HouseId
left join nest n on h.HouseId = n.HouseId
Where u.Email = 'nick12345@hotmail.co.uk' and u.UserPassword = 'User123';

