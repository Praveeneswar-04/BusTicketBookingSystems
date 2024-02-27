package BusDetails;

/**
 * This class handles various operations related to bus management.
 * @author Praveen Eswar K
 * @since 19 FEB 2024
 */

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Connection.Connections;
import ExceptionHandling.Exceptions;
import PersonDetails.DatabaseConnection;
import SearchDetails.Bus;


public class BusOperations {
    static Scanner sc = new Scanner(System.in);
    static int found = 0;
    public static List<Bus> values = new ArrayList<>();

    /**
     * Adds a new bus to the database.
     */
    public static void Add() {
        // Load bus details from the database to a list
        DbtoList();
        try {
            // Gather input for the new bus
            System.out.println("Enter Bus Number");
            int busNumber = sc.nextInt();
            sc.nextLine(); // Consume newline
            System.out.println("Enter Bus Name");
            String busName = sc.nextLine();
            System.out.println("Enter Seat Count");
            int seatCount = sc.nextInt();
            System.out.println("Enter Price");
            double price = sc.nextDouble();
            System.out.println("Enter Route ID");
            int routeId = sc.nextInt();
            sc.nextLine(); // Consume newline
            System.out.println("Enter Driver Name ");
            String driverName = sc.nextLine();
            System.out.println("Enter Operator ID");
            int operatorid = sc.nextInt();
            sc.nextLine(); // Consume newline
            System.out.println("Enter Destination City");
            String dCity = sc.nextLine();

            // Get the maximum bus ID from the database
            String idquery = "Select max(bus_id)from DATABASE.bus_details";
            PreparedStatement idstmt = Connections.getCon().prepareStatement(idquery);
            ResultSet rs = idstmt.executeQuery();
            
            Integer id = null;

            if (rs.next()) {
                id = rs.getInt(1) + 1;
            }

            // Insert new bus details into the database
            String query = "INSERT INTO DATABASE.Bus_Details (Bus_id,Bus_Number, Bus_Name, Seat, Price, Route_Id, Driver_Name,Operator_id,Destination_city) VALUES (?, ?, ?, ?, ?, ?,?,?,?)";
            PreparedStatement pstmt = Connections.getCon().prepareStatement(query);
            
            pstmt.setInt(1, id);

            pstmt.setInt(2, busNumber);
            pstmt.setString(3, busName);
            pstmt.setInt(4, seatCount);
            pstmt.setDouble(5, price);
            pstmt.setInt(6, routeId);
            pstmt.setString(7, driverName);
            pstmt.setInt(8, operatorid);
            pstmt.setString(9, dCity);
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Bus details added successfully!");
            } else {
                System.out.println("Cannot add bus details. Please Verify Details");
            }
        } catch (Exception e) {
            Exceptions.handleException(e);
        }

    }

    /**
     * Updates an existing bus record in the database.
     */
    public static void Update() {
        // Load bus details from the database to a list
        DbtoList();

        System.out.println("Enter the Bus Id To update :");
        int id = sc.nextInt();
        try {
            // Gather updated input for the bus
            System.out.println("Enter New Bus Number:");
            int busNumber = sc.nextInt();
            sc.nextLine(); // Consume newline
            System.out.println("Enter New Bus Name:");
            String busName = sc.nextLine();
            System.out.println("Enter New Seat Count:");
            int seatCount = sc.nextInt();
            System.out.println("Enter New Price:");
            double price = sc.nextDouble();
            System.out.println("Enter New Route ID:");
            int routeId = sc.nextInt();
            sc.nextLine(); // Consume newline
            System.out.println("Enter New Driver Name:");
            String driverName = sc.nextLine();
            System.out.println("Enter Operator ID");
            int operatorid = sc.nextInt();
            sc.nextLine(); // Consume newline
            System.out.println("Enter Destination City");
            String dCity = sc.nextLine();
            
            // Update the bus details in the database
            String query = "UPDATE DATABASE.Bus_Details SET Bus_Number = ?, Bus_Name = ?, Seat = ?, Price = ?, Route_Id = ?, Driver_Name = ? ,Operator _id = ? ,Destination_city = ? WHERE Bus_Id = ?";
            PreparedStatement pstmt = Connections.getCon().prepareStatement(query);

            pstmt.setInt(1, busNumber);
            pstmt.setString(2, busName);
            pstmt.setInt(3, seatCount);
            pstmt.setDouble(4, price);
            pstmt.setInt(5, routeId);
            pstmt.setString(6, driverName);
            pstmt.setInt(7, operatorid);
            pstmt.setString(8, dCity);
            pstmt.setInt(9, id);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Bus details updated successfully!");
            } else {
                System.out.println("Failed to update bus details.");
            }
        } catch (SQLException e) {
            Exceptions.handleException(e);
        }
    }

    /**
     * Deletes an existing bus record from the database.
     */
    public static void Delete() {
        // Load bus details from the database to a list
        DbtoList();

        System.out.println("Enter the Bus Id to Remove :");
        int id = sc.nextInt();
        try {
            // Delete the bus details from the database
            String query = "DELETE FROM DATABASE.Bus_Details WHERE Bus_Id = ?";
            PreparedStatement pstmt = Connections.getCon().prepareStatement(query);
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Bus details deleted successfully!");
            } else {
                System.out.println("Cannot add bus details. Please Verify Details");
            }
        } catch (Exception e) {
            Exceptions.handleException(e);
        }
    }

    /**
     * Displays all bus details from the database.
     */
    public static void View() {
        // Load bus details from the database to a list
        DbtoList();
        String query = "SELECT * FROM DATABASE.Bus_Details";
        try {
            PreparedStatement pstmt = Connections.getCon().prepareStatement(query);
            ResultSet rs = pstmt.executeQuery(query);
            System.out.println("Bus_Id\tBus_Number\tBus_Name\t  Seat\t   Price\tRoute_Id\tDriver_Name\n");
            while (rs.next()) {
                System.out.printf("%-8d%-12d%-24s%-8d%-12.2f%-12d%-16s%n",
                    rs.getInt("Bus_Id"),
                    rs.getInt("Bus_Number"),
                    rs.getString("Bus_Name"),
                    rs.getInt("Seat"),
                    rs.getDouble("Price"),
                    rs.getInt("Route_Id"),
                    rs.getString("Driver_Name")
                );
            }}
        
        catch (SQLException e) {
            Exceptions.handleException(e);
        }
    }

    /**
     * Retrieves bus details from the database and stores them in a list.
     * @return List of bus details
     */
    public static List<Bus> DbtoList() {
        try {
            String query = "SELECT * FROM DATABASE.Bus_Details ";
            PreparedStatement pstmt = Connections.getCon().prepareStatement(query);
            ResultSet rs = pstmt.executeQuery(query);
            while (rs.next()) {    
                values.add(new Bus(rs.getInt("Bus_Id"), rs.getInt("Bus_Number"), rs.getString("Bus_Name"),
                        rs.getInt("Seat"), rs.getDouble("Price"), rs.getInt("Route_Id"), rs.getString("Driver_Name")));
            }
            
        } catch (Exception e) {
            Exceptions.handleException(e);
        }
        return values;
    }

    /**
     * Inserts bus details from the list into the database.
     */
    public static void ListtoDB() {
        try {
            String query = "INSERT INTO DATABASE.Bus_Details (Bus_Id,Bus_Number, Bus_Name, Seat, Price, Route_Id, Driver_Name) VALUES (?,?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = Connections.getCon().prepareStatement(query);
            for (Bus bus : values) {
                pstmt.setInt(1, bus.getBus_Id());
                pstmt.setInt(2, bus.getBus_Number());
                pstmt.setString(3, bus.getBus_Name());
                pstmt.setInt(4, bus.getSeat());
                pstmt.setDouble(5, bus.getPrice());
                pstmt.setInt(6, bus.getRoute_id());
                pstmt.setString(7, bus.getDriver_Name());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println("Data updated in the database successfully!");
        } catch (Exception e) {
            Exceptions.handleException(e);
        }
    }
    



    /**
     * Adds a new route to the database.
     */
    public static void addRoute() {
        try {
            System.out.println("Enter Route ID");
            int routeId = sc.nextInt();
            sc.nextLine(); // Consume newline
            System.out.println("Enter Origin City['Chennai']");
            String originCity = sc.nextLine();
            System.out.println("Enter Destination City['Salem']");
            String destinationCity = sc.nextLine();
            System.out.println("Enter Departure Date[YYYY-MM-DD]");
            String departuredate = sc.nextLine();
            System.out.println("Enter Travel Duration\n");
            String travelDuration = sc.nextLine();
            System.out.println("Enter Route Name['Chennai-Coimbatore']");
            String routename = sc.nextLine();

            // Call the method to insert route details into the database
            insertRouteDetails(routeId, originCity, destinationCity, departuredate, travelDuration, routename);
        } catch (Exception e) {
            Exceptions.Invalid(e);;
        }
    }
    /**
     * Inserts route details into the database.
     */
    
    public static void insertRouteDetails(int routeId, String originCity, String destinationCity, String departuredate, String travelDuration, String routename) {
        try {
            // If bus ID is valid, insert route details into the database
            String query = "INSERT INTO DATABASE.route_details (route_id, origin_city, destination_city, departure_date, travel_duration,Operator_id,route_name) VALUES (?, ?, ?,to_date( ?,'yyyy-mm-dd'), ?, ?,?)";
            PreparedStatement pstmt = Connections.getCon().prepareStatement(query);
            pstmt.setInt(1, routeId);
            pstmt.setString(2, originCity);
            pstmt.setString(3, destinationCity);
            pstmt.setString(4, departuredate);
            pstmt.setString(5, travelDuration);
            pstmt.setInt(6, DatabaseConnection.result.getInt("Operator_id"));
            pstmt.setString(7, routename);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Route details inserted successfully.");
            } else {
                System.out.println("Failed to insert route details.");
            }
        } catch (Exception e) {
        	System.out.println("The route Already Exsist");
//        	Exceptions.Invalid(e);
        }
    }
    
//    public static boolean isvalid(int busId) {
//    	boolean valid =false;
//    	 try {
//    	        // Prepare the SQL query
//    	        String query = "SELECT COUNT(*) FROM DATABASE.bus_details WHERE bus_id = ?";
//    	        PreparedStatement pstmt = Connections.getCon().prepareStatement(query);
//    	        pstmt.setInt(1, busId);
//    	        ResultSet rs=pstmt.executeQuery();
//    	        // Execute the query
//
//    	        // Check if any row is returned
//    	        if (rs.next()) {
//    	            int count = rs.getInt(1); // Get the count of rows returned
//    	            valid = count > 0; // If count > 0, bus ID exists
//    	        }
//    	    }
//    	 catch(Exception e) {Exceptions.handleException(e);}
//    	 return valid;
//    }
    
    
    /**
     * Adds a new stop to the database.
     */
    public static void addStop() {
        try {
            System.out.println("Enter stop id:");
            int stopId = sc.nextInt();
            System.out.println("Enter route id:");
            int routeId = sc.nextInt();
            sc.nextLine(); // Consume newline
            System.out.println("Enter stop name:");
            String stopName = sc.nextLine();
            System.out.println("Enter arrival time['2024-03-02 09.00 AM']:");
            String arrivalTime = sc.nextLine();
            System.out.println("Enter operator id:");
            int operatorId = sc.nextInt();
            sc.nextLine(); // Consume newline
            System.out.println("Enter bus id:");
            int busId = sc.nextInt();

            // Call the method to insert stop details into the database
            insertStopdetails(stopId, routeId, stopName, arrivalTime, operatorId, busId);

        } catch (Exception e) {
            Exceptions.Invalid(e);
        }}
    /**
     * Inserts stop details into the database.
     */

    
    public static void insertStopdetails(int stopId, int routeId, String stopName, String arrivalTime, int operatorId, int busId) {
        try {
            String query = "INSERT INTO DATABASE.stop_details (stop_id, route_id, stop_name, arrival_time, operator_id,  bus_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = Connections.getCon().prepareStatement(query);

            // Set values for parameters
            pstmt.setInt(1, stopId);
            pstmt.setInt(2, routeId);
            pstmt.setString(3, stopName);
            pstmt.setString(4, arrivalTime);
            pstmt.setInt(5, operatorId);
            pstmt.setInt(6, busId);

            // Execute the query
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Stop details added successfully.");
            } else {
                System.out.println("Failed to add stop details.");
            }
        } catch (Exception e) {}
    }

    /**
     * Checks if a given route ID is valid.
     */
    
    public static boolean isvalid1(int routeId) {
        boolean valid = false;
        String query = "SELECT COUNT(*) FROM DATABASE.route_details WHERE route_id = ?";
        try {
            PreparedStatement preparedStatement = Connections.getCon().prepareStatement(query);
            preparedStatement.setInt(1, routeId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    valid = count > 0;
                }
            }
        } catch (Exception e) {
            Exceptions.handleException(e);
        }
        return valid;
    }
}