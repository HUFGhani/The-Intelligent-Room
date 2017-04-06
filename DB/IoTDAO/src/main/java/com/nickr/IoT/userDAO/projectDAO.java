package com.nickr.IoT.userDAO;

import com.nickr.IoT.user.model.*;

import java.sql.*;
import java.util.ArrayList;

public class projectDAO {

	Connection conn = null;
	Statement stmt = null;
	PreparedStatement ptmt = null;
	public static final String userid = "robertsn";
	public static final String userpass = "ensteLas6";

	private void openConnection() {
		// loading jdbc driver for mysql
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			System.out.println(e);
		}

		// connecting to database
		try {

			conn = DriverManager.getConnection("jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk:3306/" + userid + "?user="
					+ userid + "&password=" + userpass);
			stmt = conn.createStatement();
		} catch (SQLException se) {
			System.out.println(se);
		}
	}

	private void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public UserPreference getUserPreference(String email, String password) {
		UserPreference preference = null;

		openConnection();
		// Create select statement and execute it
		try {
			String selectSQL = "Select u.UserID, u.Firstname, u.Lastname, u.Priority, u.HouseId, pl.Brightness, pl.Saturation, pl.Red, pl.Blue, pl.Green, pl.actionMethod as 'LightingActionMethod', pl.actionPriority as 'LightingActionPriority', pt.TargetTemp, pt.actionMethod as 'TempActionHeating', pt.actionPriority as 'TempActionPriority' "
					+ "From users u " + "inner join PrefLighting pl on u.UserID = pl.UserID "
					+ "inner join PrefTemp pt on u.UserID = pt.UserID " + "Where u.Email = '" + email
					+ "' and u.UserPassword = '" + password + "' ";
			ResultSet resultSet = stmt.executeQuery(selectSQL);
			
			while (resultSet.next()) {
				preference = new UserPreference(resultSet.getInt("UserID"), resultSet.getString("Firstname"),
						resultSet.getString("Lastname"), resultSet.getInt("Priority"),
						new LightingPreference(resultSet.getInt("Red"), resultSet.getInt("Green"), resultSet.getInt("Blue"),
								resultSet.getInt("Saturation"), resultSet.getInt("Brightness"),
								resultSet.getString("LightingActionMethod"), resultSet.getInt("LightingActionPriority")),
						new HeatingPreference(resultSet.getInt("TargetTemp"), resultSet.getString("TempActionHeating"),
								resultSet.getInt("TempActionPriority")));
			}

			// Retrieve the results

			stmt.close();
			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}

		return preference;
	}

	public LogInRegisterResponse logIn(String email, String password) {
		UserPreference userPref = getUserPreference(email, password);
		HouseConfiguration houseConfig = getHouseConfiguration(email, password);

		return new LogInRegisterResponse(houseConfig, userPref);

	}

	public HouseConfiguration getHouseConfiguration(String email, String password) {
		HouseConfiguration configuration = new HouseConfiguration();
		
		String selectSQL = "Select u.HouseID, Longitude, Latitude, NestId, ph.Id as 'philipsHueId' "
				+ "From users u "
				+ "inner join house h on u.HouseId = h.HouseId "
				+ "left join PhilipsHue ph on h.HouseId = ph.HouseId "
				+ "left join nest n on h.HouseId = n.HouseId "
				+ "Where u.Email = '" + email
				+ "' and u.UserPassword = '" + password + "' ";
		
		openConnection();
		try {
			ResultSet resultSet = stmt.executeQuery(selectSQL);
			
			while (resultSet.next()) {
				configuration.setHouseId(resultSet.getInt("HouseID"));
				Location location = new Location(
						resultSet.getDouble("Latitude"),
						resultSet.getDouble("Longitude")
						);
				configuration.setLocation(location);
				configuration.setHasLights(((resultSet.getInt("philipsHueId") > 0) ? true : false));
				configuration.setHasNest(((resultSet.getInt("NestId") > 0) ? true : false));
			}
			stmt.close();
			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}
		
		configuration.setSensors(getSensors(configuration.getHouseId()));
		
		return configuration;
	}

	public ArrayList<Sensor> getSensors(int houseId) {
		ArrayList<Sensor> allSensors = new ArrayList<>();

		String selectSQL = "Select s.SensorID, s.SensorName, s.SensorMethod, s.TimeInserted, s.PortNumber, sv.SensorValue "
				+ "from sensors s " + "left join sensorValues sv on s.SensorID = sv.SensorID " + "Where s.HouseID = "
				+ houseId;
		
		openConnection();
		try {
			ResultSet resultSet = stmt.executeQuery(selectSQL);
			
			while (resultSet.next()) {
				allSensors.add(getNextSensor(resultSet));
			}
			stmt.close();
			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}

		return allSensors;
	}

	private Sensor getNextSensor(ResultSet resultSet) {
		Sensor sensor = null;
		try {
			sensor = new Sensor(resultSet.getInt("SensorID"), resultSet.getString("SensorName"),
					resultSet.getString("SensorMethod"), resultSet.getInt("PortNumber"),
					resultSet.getDate("TimeInserted"), resultSet.getInt("SensorValue"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sensor;
	}

	public int getHouseId(String email, String password) {

		String selectSQL = "Select HouseID " + "From users u " + "Where u.Email = '" + email
				+ "' and u.UserPassword = '" + password + "' ";
		
		openConnection();
		try {
			ResultSet resultSet = stmt.executeQuery(selectSQL);	
			
			while (resultSet.next()) {
				return resultSet.getInt("HouseID");
			}
			stmt.close();
			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}
		return 0;
	}
	
	
	public LogInRegisterResponse register(RegistrationRequest request){
		try{
			String selectSQL = "Select HouseID " + "From house h " + "Where h.HouseID = '" + request.getHomeId() 
			+ "' And h.housePass = '" + request.getHomePassword() + "' ";
			 openConnection();
			 
			 ResultSet resultSet = stmt.executeQuery(selectSQL);
			 
			 if (resultSet != null){
				 String Mysql = "Insert into users(Firstname, Lastname, Email, UserPassword, UserPriority, HouseId) values (?,?,?,?,?,?);";
				   openConnection();
				   ptmt = conn.prepareStatement(Mysql);
				   ptmt.setString(1, request.getFirstName());
				   ptmt.setString(2, request.getLastName());
				   ptmt.setString(3, request.getEmail());
				   ptmt.setString(4, request.getUserPassword());
				   ptmt.setString(5, request.getUserPriority());
				   ptmt.setString(6, request.getHomeId());
				   ptmt.executeUpdate();		
			 } 
			 
		}	catch (SQLException e) {
			e.printStackTrace();	
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return logIn(request.getEmail(), request.getUserPassword());
	}
	
	
	public void InsertPhilipsHue(Hue h){
		
			try{
			   String Mysql = "Insert into PhilipsHue(Lightname, Brightness, Saturation, Red, Blue, Green, Power, AutomaticStatus, HouseId) values (?,?,?,?,?,?,?,?,?);";
			   openConnection();
			   ptmt = conn.prepareStatement(Mysql);
			   ptmt.setString(1, h.getLight().getName());
			   ptmt.setString(2, h.getLight().getBrightness());
			   ptmt.setString(3, h.getLight().getSaturation());
			   ptmt.setString(4, h.getLight().getColour().getRed());
			   ptmt.setString(5, h.getLight().getColour().getBlue());
			   ptmt.setString(6, h.getLight().getColour().getGreen());
			   ptmt.setBoolean(7, h.getLight().isOnOff());
			   ptmt.setBoolean(8, h.getLight().isAutomaticStatus());
			   ptmt.setInt(9, h.getHomeId());
			   ptmt.executeUpdate();					   
			 		   			   			   
		}	catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	}
	
	public void InsertNest(Nest n){
		
		try{
		   String Mysql = "Insert into nest(Temperature, Lastupdated, automaticStatus, HouseId) values (?,?,?,?);";
		   openConnection();
		   ptmt = conn.prepareStatement(Mysql);
		   ptmt.setInt(1, n.getTemperature());
		   ptmt.setDate(2, n.getLastUpdated());
		   ptmt.setBoolean(3, n.getAutomaticStatus());
		   ptmt.setInt(4, n.getHomeID());
		   ptmt.executeUpdate();					   
		 		   			   			   
	}	catch (SQLException e) {
		e.printStackTrace();
		
	} finally {
		try {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
	
public void insertSensors(Sensor s){
		
		try{
			
            String Mysql = String.format("Insert Into sensors (SensorId, SensorName, SensorMethod, PortNumber, TimeInserted)"
                    + "VALUES (%d, \"%s\", \"%s\", %d, \"%s\", \"%s\")"
                    + " ON DUPLICATE KEY UPDATE "
                    + " SensorName = VALUES(SensorName),"
                    + " SensorMethod = VALUES(SensorMethod),"
                    + " PortNumber = VALUES(PortNumber),"
                    + " TimeInserted = VALUES(TimeInserted)",
                    s.getSensorId(), s.getSensorName(),
                    s.getSensorMethodType(), s.getSensorPort(), s.getUpdateTimestamp());
            		ptmt.executeUpdate(Mysql);
		 
		   String Value = "Insert into sensorValue(SensorValue, SensorID) values (?,?);";
		   openConnection();
		   ptmt = conn.prepareStatement(Value);
		   ptmt.setInt(1, s.getSensorValue());
		   ptmt.setInt(2, s.getSensorId());		   
		   ptmt.executeUpdate();	
		   
		 		   			   			   
	}	catch (SQLException e) {
		e.printStackTrace();
		
	} finally {
		try {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
		

	public projectDAO() {
		// TODO Auto-generated constructor stub
	}

}
