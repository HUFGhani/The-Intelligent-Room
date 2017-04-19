import model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;

public class projectDAO {

    Connection conn = null;
    Statement stmt = null;
    PreparedStatement ptmt = null;
    public static final String userid = "robertsn";
    public static final String userpass = "ensteLas6";
    Calendar calendar = Calendar.getInstance();
    Date ourJavaDateObject = new Date(calendar.getTime().getTime());

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
            String selectSQL = "Select u.UserID, u.Firstname, u.Lastname, u.Priority, u.HouseId, pl.Lightname, pl.Brightness, pl.Saturation, pl.Red, pl.Blue, pl.Green, pl.actionMethod as 'LightingActionMethod', pl.actionPriority as 'LightingActionPriority', pt.TargetTemp, pt.actionMethod as 'TempActionMethod', pt.actionPriority as 'TempActionPriority' "
                    + "From users u " + "inner join PrefLighting pl on u.UserID = pl.UserID "
                    + "inner join PrefTemp pt on u.UserID = pt.UserID " + "Where u.Email = '" + email
                    + "' and u.UserPassword = '" + password + "' ";
            ResultSet resultSet = stmt.executeQuery(selectSQL);

            while (resultSet.next()) {
                preference = new UserPreference(resultSet.getInt("UserID"), resultSet.getString("Firstname"),
                        resultSet.getString("Lastname"), resultSet.getInt("Priority"),
                        new LightPref(
                                new Light(
                                        resultSet.getString("Lightname"),
                                        new Colour(resultSet.getInt("Red"), resultSet.getInt("Green"), resultSet.getInt("Blue")),
                                        resultSet.getInt("Saturation"), resultSet.getInt("Brightness")),
                                resultSet.getString("LightingActionMethod"), resultSet.getInt("LightingActionPriority")),
                        new TmpPref(
                                new Nest(resultSet.getInt("TargetTemp")),
                                resultSet.getString("TempActionMethod"),
                                resultSet.getInt("TempActionPriority")
                        ));

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
                configuration.setHouseId(resultSet.getString("HouseID"));
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

    public ArrayList<Sensor> getSensors(String houseId) {
        ArrayList<Sensor> allSensors = new ArrayList<>();

        String selectSQL = "Select s.SensorID, s.SensorName, s.SensorMethod, s.TimeInserted, s.PortNumber, sv.SensorValue "
                + "from sensors s " + "left join sensorValues sv on s.SensorID = sv.SensorID " + "Where s.HouseID = "
                + "'" + houseId + "' GROUP BY s.sensorID";


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
                    resultSet.getLong("TimeInserted"), resultSet.getInt("SensorValue"));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sensor;
    }

    public String getHouseId(String email, String password) {
        String HouseId = "HouseID";
        String selectSQL = "Select HouseID " + "From users u " + "Where u.Email = '" + email
                + "' and u.UserPassword = '" + password + "' ";

        openConnection();
        try {
            ResultSet resultSet = stmt.executeQuery(selectSQL);

            while (resultSet.next()) {
                HouseId =  resultSet.getString("HouseID");
            }


            stmt.close();
            closeConnection();
        } catch (SQLException se) {
            System.out.println(se);
        }
        return HouseId;
    }

    public int getPriority(String houseid) {
        int maxPriority = 0;

        String selectSQL = "Select max(Priority) FROM users WHERE HouseId = " + "'" + houseid + "'";

        openConnection();
        try {
            ResultSet resultSet = stmt.executeQuery(selectSQL);

            if (resultSet.next()) {
                maxPriority = resultSet.getInt(1);
            }
            stmt.close();
            closeConnection();
        } catch (SQLException se) {
            System.out.println(se);
        }
        return maxPriority + 1;
    }

    public LogInRegisterResponse register(RegistrationRequest request) {

        try {
            String selectSQL = "Select HouseID " + "From house h " + "Where h.HouseID = '" + request.getHomeId()
                    + "' And h.housePass = '" + request.getHomePassword() + "' ";
            openConnection();

            ResultSet resultSet = stmt.executeQuery(selectSQL);

            if (resultSet != null) {
                String Mysql = "Insert into users(Firstname, Lastname, Email, UserPassword, Priority, HouseId) values (?,?,?,?,?,?);";
                openConnection();
                ptmt = conn.prepareStatement(Mysql);
                ptmt.setString(1, request.getFirstName());
                ptmt.setString(2, request.getLastName());
                ptmt.setString(3, request.getEmail());
                ptmt.setString(4, request.getUserPassword());
                ptmt.setInt(5, getPriority(request.getHomeId()));
                ptmt.setString(6, request.getHomeId());
                ptmt.executeUpdate();

                int userId = getUserID(request.getEmail(), request.getUserPassword());
                InsertUserPreference(new UserPreference(userId, ",", ",", 0, (new LightPref(new Light("Hue color lamp 1", new Colour(200, 234, 200), 15, 10), "motionSensor", 2)), (new TmpPref(new Nest(30), "Location", 1))));

            }


        } catch (SQLException e) {
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


    public int getUserID(String email, String password) {
        int UserID = 0;

        String selectSQL = "Select UserID FROM users WHERE Email = '" + email
                + "' AND UserPassword = '" + password + "' ";

        openConnection();
        try {
            ResultSet resultSet = stmt.executeQuery(selectSQL);

            while (resultSet.next()) {
                UserID = resultSet.getInt("UserID");
            }
            stmt.close();
            closeConnection();
        } catch (SQLException se) {
            System.out.println(se);
        }
        return UserID;
    }

    public void InsertPhilipsHue(Hue h) {

        try {
            String Mysql = "Insert into PhilipsHue(Lightname, Brightness, Saturation, Red, Blue, Green, Power, AutomaticStatus, HouseId) values (?,?,?,?,?,?,?,?,?);";
            openConnection();
            ptmt = conn.prepareStatement(Mysql);
            ptmt.setString(1, h.getLight().getName());
            ptmt.setInt(2, h.getLight().getBrightness());
            ptmt.setInt(3, h.getLight().getSaturation());
            ptmt.setInt(4, h.getLight().getColour().getRed());
            ptmt.setInt(5, h.getLight().getColour().getBlue());
            ptmt.setInt(6, h.getLight().getColour().getGreen());
            ptmt.setBoolean(7, h.isOnOff());
            ptmt.setBoolean(8, h.getAutomaticStatus());
            ptmt.setString(9, "houseID123");
            ptmt.executeUpdate();

        } catch (SQLException e) {
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

    public void InsertNest(Nest n) {
        LocalDateTime now = LocalDateTime.now();
        try {
            String Mysql = "Insert into nest(Temperature, Lastupdated, automaticStatus, HouseId) values (?,?,?,?);";
            openConnection();
            ptmt = conn.prepareStatement(Mysql);
            ptmt.setInt(1, n.getTargetTemperatureC());
            ptmt.setDate(2, ourJavaDateObject);
            ptmt.setBoolean(3, n.getAutomaticStatus());
            ptmt.setString(4, "houseID123");
            ptmt.executeUpdate();

        } catch (SQLException e) {
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

    public void insertSensors(Sensor s) {

        try {


            String Mysql = String.format("Insert Into sensors (SensorId, SensorName, SensorMethod, PortNumber, TimeInserted, HouseID)"
                            + "VALUES (%d, \"%s\", \"%s\", %d, \"%s\", \"%s\")"
                            + " ON DUPLICATE KEY UPDATE "
                            + " SensorName = VALUES(SensorName),"
                            + " SensorMethod = VALUES(SensorMethod),"
                            + " PortNumber = VALUES(PortNumber),"
                            + " TimeInserted = VALUES(TimeInserted),"
                            + " HouseID = VALUES(HouseID)",
                    s.getSensorId(), s.getSensorName(), s.getSensorMethodType(), s.getSensorPort(), s.getUpdateTimestamp(), "houseID123");

            openConnection();
            ptmt = conn.prepareStatement(Mysql);
            ptmt.executeUpdate();

            String Value = "Insert into sensorValues(SensorValue, SensorID) values (?,?)";
            ptmt = conn.prepareStatement(Value);
            ptmt.setInt(1, s.getSensorValue());
            ptmt.setInt(2, s.getSensorId());
            System.out.println(ptmt.toString());
            ptmt.executeUpdate();


        } catch (SQLException e) {
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

    public void InsertUserPreference(UserPreference Up) {

        try {

            String Mysql = "Insert into PrefLighting(Lightname, Brightness, Saturation, Red, Blue, Green, actionMethod, actionPriority, UserID) values (?,?,?,?,?,?,?,?,?);";
            openConnection();
            ptmt = conn.prepareStatement(Mysql);
            ptmt.setString(1, Up.getLightPref().getLight().getName());
            ptmt.setInt(2, Up.getLightPref().getLight().getBrightness());
            ptmt.setInt(3, Up.getLightPref().getLight().getSaturation());
            ptmt.setInt(4, Up.getLightPref().getLight().getColour().getRed());
            ptmt.setInt(5, Up.getLightPref().getLight().getColour().getBlue());
            ptmt.setInt(6, Up.getLightPref().getLight().getColour().getGreen());
            ptmt.setString(7, Up.getLightPref().getActionMethod());
            ptmt.setInt(8, Up.getLightPref().getActionPriority());
            ptmt.setInt(9, Up.getUserId());
            ptmt.executeUpdate();

            String HeatSql = "Insert into PrefTemp(TargetTemp, actionMethod, actionPriority, UserID) values (?,?,?,?)";
            openConnection();
            ptmt = conn.prepareStatement(HeatSql);
            ptmt.setInt(1, Up.getTmpPref().getNest().getTargetTemperatureC());
            ptmt.setString(2, Up.getTmpPref().getAutomationType());
            ptmt.setInt(3, Up.getTmpPref().getActionPriority());
            ptmt.setInt(4, Up.getUserId());
            ptmt.executeUpdate();


        } catch (SQLException e) {
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

    public void insertHouseConfiguration(HouseConfiguration s) {

        try {

            String house = "Insert Into house (HouseId) values (?)";
            ptmt = conn.prepareStatement(house);
            ptmt.setString(1, s.getHouseId());
            System.out.println(ptmt.toString());
            ptmt.executeUpdate();

            for (Sensor sensor: s.getSensors()) {

                String Mysql = String.format("Insert Into sensors (SensorId, SensorName, SensorMethod, PortNumber, TimeInserted, HouseID)"
                                + "VALUES (%d, \"%s\", \"%s\", %d, \"%s\", \"%s\")"
                                + " ON DUPLICATE KEY UPDATE "
                                + " SensorName = VALUES(SensorName),"
                                + " SensorMethod = VALUES(SensorMethod),"
                                + " PortNumber = VALUES(PortNumber),"
                                + " TimeInserted = VALUES(TimeInserted),"
                                + " HouseID = VALUES(HouseID)",
                        sensor.getSensorId(), sensor.getSensorName(), sensor.getSensorMethodType(), sensor.getSensorPort(), sensor.getUpdateTimestamp(), "houseID123");

                openConnection();
                ptmt = conn.prepareStatement(Mysql);
                ptmt.executeUpdate();

                String Value = "Insert into sensorValues(SensorValue, SensorID) values (?,?)";
                ptmt = conn.prepareStatement(Value);
                ptmt.setInt(1, sensor.getSensorValue());
                ptmt.setInt(2, sensor.getSensorId());
                System.out.println(ptmt.toString());
                ptmt.executeUpdate();
            }

        } catch (SQLException e) {
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
