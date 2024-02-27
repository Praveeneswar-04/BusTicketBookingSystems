package PersonDetails;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Connection.Connections;
import ExceptionHandling.Exceptions;
import SearchDetails.Bus;

/**
 * This class manages the database connections and performs operations related to user authentication and registration.
 * It also retrieves and manipulates data from the database.
 * @author Praveen Eswar K
 * @since 19 FEB 2024
 */
public class DatabaseConnection {
    
    static ArrayList<User> values = new ArrayList<>();
    public static ResultSet result;
    
    /**
     * Validates user login credentials based on the provided username, password, and user type.
     */
    public static void Validatelogin(String User_Name, String User_Password, String name) {
        String Name = name;
        String User_name = User_Name;
        String User_password = User_Password;

        Connections.invoke();
        try {
            int count = -1;

            if (Name.equals("Admin")) {
                // Validate admin login
                String validate_Admin = "SELECT * from DATABASE.Admin_Details ";
                PreparedStatement stmt = Connections.getCon().prepareStatement(validate_Admin);
                ResultSet values = stmt.executeQuery();
                while (values.next()) {
                    String db_name = values.getString("Admin_User_Name");
                    String db_pwd = values.getString("Password");
                    if (User_name.equals(db_name) && User_password.equals(db_pwd)) {
                        System.out.println("Login Successful!");
                        adminResult(User_name, User_password);
                        Administrator.Menu();
                        count++;
                    }
                }
                if (count < 0) {
                    System.out.println("Invalid Credentials.");
                }
            }
            
            if (Name.equals("User")) {
                // Validate user login
                String validate_User = "SELECT * from DATABASE.User_Details ";
                PreparedStatement stmt = Connections.getCon().prepareStatement(validate_User);
                ResultSet values = stmt.executeQuery();
                while (values.next()) {
                    String db_name = values.getString("User_Name");
                    String db_pwd = values.getString("Password");
                    if (User_name.equals(db_name) && User_password.equals(db_pwd)) {
                        System.out.println("Login Successful!");
                        count++;
                        userResult(User_name, User_password);
                        User.menu();
                    }
                }
                if (count < 0) {
                    System.out.println("Invalid Credentials.");
                }
            }

            if (Name.equals("BusOperator")) {
                // Validate bus operator login
                String validate_BusOperator = "SELECT * from DATABASE.Bus_Operator_Details ";
                PreparedStatement stmt = Connections.getCon().prepareStatement(validate_BusOperator);
                ResultSet values = stmt.executeQuery();
                while (values.next()) {
                    String db_name = values.getString("Operator_Name");
                    String db_pwd = values.getString("Password");
                    if (User_name.equals(db_name) && User_password.equals(db_pwd)) {
                        System.out.println("Login Successful!");
                        count++;
                        operatorResult(User_name, User_password);
                        BusOperator.Menu();
                    }
                }
                if (count < 0) {
                    System.out.println("Invalid Credentials.");
                }
            }
        } catch (Exception e) {
            Exceptions.handleException(e);
        }
    }

    /**
     * Adds a new user to the database.
     */
    public static void AddUser(User user) {
        Connections.invoke();
        String userquery = "Select max(User_id)from DATABASE.User_details";
        String query = "INSERT INTO DATABASE.User_Details (USER_ID,USER_NAME,PASSWORD,PHONE_NUMBER,GENDER,EMAIL_ID,ADDRESS)  VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = Connections.getCon().prepareStatement(query);
            PreparedStatement uquery = Connections.getCon().prepareStatement(userquery);
            ResultSet rs = uquery.executeQuery();
            Integer id = null;
            if (rs.next()) {
                id = rs.getInt(1) + 1;
            }

            pstmt.setInt(1, id);
            pstmt.setString(2, user.getUser_Name());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getPhone_number());
            pstmt.setString(5, user.getGender());
            pstmt.setString(6, user.getEmail_Id());
            pstmt.setString(7, user.getAddress());

            pstmt.executeUpdate();
            System.out.println("User registered successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Retrieves a list of all buses from the database.
     */
    public List<Bus> getAllBus() {
        List<Bus> buses = new ArrayList<>();
        try {
            String query = "SELECT * FROM DATABASE.Bus_Details";
            Statement s = Connections.getCon().createStatement();
            ResultSet rs = s.executeQuery(query);
            while (rs.next()) {
                Bus bus = new Bus(rs.getInt("Bus_Id"), rs.getInt("Bus_Number"), rs.getString("Bus_Name"),
                        rs.getInt("Seat"), rs.getDouble("Price"), rs.getInt("Route_Id"), rs.getString("Driver_Name"));
                buses.add(bus);
            }
        } catch (Exception e) {
            System.out.println();
        }
        return buses;
    }

    /**
     * Retrieves user details from the database based on the provided username and password.
     */
    public static void userResult(String User_name, String User_password) {
        String fetchdetails = "SELECT * FROM DATABASE.USER_DETAILS WHERE USER_NAME=? AND PASSWORD=?";
        try {
            PreparedStatement fetchStatement = Connections.getCon().prepareStatement(fetchdetails);
            fetchStatement.setString(1, User_name);
            fetchStatement.setString(2, User_password);
            result = fetchStatement.executeQuery();
            result.next();

        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * Retrieves admin details from the database based on the provided username and password.
     */
    public static void adminResult(String User_name, String User_password) {
        String fetchdetails = "SELECT * FROM DATABASE.Admin_DETAILS WHERE admin_USER_NAME=? AND PASSWORD=?";
        try {
            PreparedStatement fetchStatement = Connections.getCon().prepareStatement(fetchdetails);
            fetchStatement.setString(1, User_name);
            fetchStatement.setString(2, User_password);
            result = fetchStatement.executeQuery();
            result.next();

        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * Retrieves bus operator details from the database based on the provided username and password.
     */
    public static void operatorResult(String User_name, String User_password) {

        try {
            String fetchdetails = "SELECT * FROM DATABASE.Bus_Operator_DETAILS WHERE Operator_NAME=? AND PASSWORD=?";

            PreparedStatement fetchStatement = Connections.getCon().prepareStatement(fetchdetails);
            fetchStatement.setString(1, User_name);
            fetchStatement.setString(2, User_password);
            result = fetchStatement.executeQuery();
            result.next();

        } catch (Exception e) {
            e.getMessage();
        }
    }

}
