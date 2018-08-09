package Storage;

import EncryptionTools.Encryption;
import basics.Location;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    //create an Connection-object to initiate the connection of the applicstion with the database 
    protected static Connection conn;

    //method that checks if the current connection is open or close and returns true or false respectively
    public static boolean isConnected() {
        try {
            return conn != null && !conn.isClosed();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //this method creates the actual connection with the database with encrypted the user name and the password
    public static void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        String url = "jdbc:mysql://localhost:3306/go_swiss";//using oracle database 
        Encryption.setCredentials("Login.encrypted");//takes the credentials from a encrypted file and pass it to two strings
        String pass = Encryption.getPassword();
        String user = Encryption.getUsername();
        try {
            conn = DriverManager.getConnection(url, user, pass);//make the connection
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //checks if the database's connection is connected and if it is, disconnects it. 
    public static void disconnect() {
        try {
            if (conn != null && !conn.isClosed()) {
                //conn.commit();
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //method which writes the location data into the DB and throws custom-made exception 
    public static void writeCitiesToDB(HashMap<String, Location> map) throws DBHasDataException {
        try ( //creating a prepared statement to insert values into the JLOCATION table using SQL commands
                PreparedStatement pst = conn.prepareStatement("INSERT INTO JLOCATION VALUES(?,?,?,?,?,?)")) {
            //Checking of the database is initiated 
            Statement st = conn.createStatement();
            ResultSet check = st.executeQuery("SELECT * FROM JLOCATION");//checks if the JLOCATION table in the DB has data 
            if (check.next() == true) {//and if its true throws the DBexception
                throw new DBHasDataException("ERROR::The db contains data");
            }
            //Checking completed
            //a for each loop is used to set the values to insert and adds to a batch the necessary data to be stored in the db
            for (String cityName : map.keySet()) {
                pst.setString(1, cityName);
                pst.setString(2, map.get(cityName).getName());
                pst.setInt(3, map.get(cityName).getid());
                pst.setString(4, map.get(cityName).getCoordsType());
                pst.setDouble(5, map.get(cityName).getCoordsX());
                pst.setDouble(6, map.get(cityName).getCoordsY());
                pst.addBatch();
            }
            pst.executeBatch();//executes the SQL commands and places the batch into the db
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    //method that resets the DB which basically means that deletes all stored data from the table
    //The sql parameter is the command used in sql in order to delete a specific table
    public static void resetDatabase(String sql) {
        try (Statement st = conn.createStatement()) {
            st.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //method that reads the location data from the DB
    public static HashMap<String, Location> readCitiesFromDB() {
        HashMap<String, Location> all = new HashMap();//creates a HashMap and puts all data there
        try (Statement st = conn.createStatement()) {
            String sql = "SELECT * FROM JLOCATION";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {//stores the data as Location objects 
                Location loc = new Location(rs.getString(2), rs.getInt(3), rs.getString(4), rs.getDouble(5), rs.getDouble(6));
                all.put(rs.getString(1), loc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        return all;
    }

    /**
     * Reads information of a specific city (station) from the database.
     * @param cityName String
     * @return HashMap
     */
    public static HashMap<String, Location> readSpecCityFromDB(String cityName) {
        HashMap<String, Location> all = new HashMap();//creates a HashMap and puts all data there
        try (Statement st = conn.createStatement()) {
            String sql = "SELECT * FROM JLOCATION WHERE JKEY = '" + cityName + "'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {//stores the data as Location objects 
                Location loc = new Location(rs.getString(2), rs.getInt(3), rs.getString(4), rs.getDouble(5), rs.getDouble(6));
                all.put(rs.getString(1), loc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        return all;
    }

    //method which writes the location data into the DB and throws custom-made exception 
    //basics.Connection is used because there is also the sql.Connection imported and used
    public static void writeConnectionsToDB(HashMap<String, basics.Connection> conMap) throws DBHasDataException {
        try ( //creating a prepared statement to insert values into the JCONNECTION table using SQL commands
                PreparedStatement pst = conn.prepareStatement("INSERT INTO JCONNECTION VALUES(?,?,?,?,?)")) {
            //Checking if the database is initiated 
            Statement st = conn.createStatement();
            ResultSet check = st.executeQuery("SELECT * FROM JCONNECTION");///checks if the JCONNECTION table in the DB has data 
            if (check.next() == true) {//and if its true throws the DBexception
                throw new DBHasDataException("ERROR::The db contains data");
            }
            //Checking completed
            //a for each loop is used to set the values to insert and adds to a batch the necessary data to be stored in the db
            for (Map.Entry<String, basics.Connection> con : conMap.entrySet()) {
                pst.setString(1, con.getKey());
                pst.setString(2, con.getValue().getFrom().getStation().getName().replaceAll(",", " "));
                pst.setInt(3, con.getValue().getFrom().getStation().getid());
                pst.setString(4, con.getValue().getTo().getStation().getName().replaceAll(",", " "));
                pst.setInt(5, con.getValue().getTo().getStation().getid());
                pst.addBatch();
            }
            pst.executeBatch();//executes the SQL commands and places the batch into the db
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    //method that reads the connection data from the DB
    public static HashMap<String, basics.Connection> readConnectionsFromDB() {
        HashMap<String, basics.Connection> all = new HashMap();//creates a HashMap and puts all data there
        try (Statement st = conn.createStatement()) {
            String sql = "SELECT * FROM JCONNECTION";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {//stores the data as Connection objects 
                basics.Connection con = new basics.Connection(new Location(rs.getString(2), rs.getInt(3)), new Location(rs.getString(4), rs.getInt(5)));
                all.put(rs.getString(1), con);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        return all;
    }

    //method that reads the specific connection data from the DB
    public static HashMap<String, basics.Connection> readSpecConnectionsFromDB(String fromCity,String toCity) {
        HashMap<String, basics.Connection> all = new HashMap();//creates a HashMap and puts all data there
        try (Statement st = conn.createStatement()) {
            String sql = "SELECT * FROM JCONNECTION WHERE COMPKEY='"+fromCity+":"+toCity+"'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {//stores the data as Connection objects 
                basics.Connection con = new basics.Connection(new Location(rs.getString(2), rs.getInt(3)), new Location(rs.getString(4), rs.getInt(5)));
                all.put(rs.getString(1), con);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        return all;
    }

    //print the possible db errors
    public static void DBError(String sql) {
        System.err.println("FATAL:>> Database will reset");
        Database.resetDatabase(sql);
        System.out.println("Database has been reset.Try again now.");
    }

}
