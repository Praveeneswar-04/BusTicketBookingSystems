package BookingDetails;

/*
 * This class handles booking operations for buses
 * @author Praveen Eswar K
 * @since  19 FEB 2024
 */

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import Connection.Connections;
import ExceptionHandling.Exceptions;
import PersonDetails.DatabaseConnection;
import PersonDetails.Person;

public class Book {
    static Scanner sc = new Scanner(System.in);
    static Person person = new Person();
    public static ResultSet resultSet;

    // Method to book a ticket
    public static void bookTicket() {
        try {
            System.out.println("Please Select the Bus to Book ");
            displayBusAndRouteInfo(); // Display available buses
            System.out.println("Enter Bus ID to Book ");
            int number = sc.nextInt();

            String query = "SELECT b.*, r.* FROM DATABASE.Bus_Details b JOIN DATABASE.Route_Details r ON b.route_Id = r.route_Id WHERE b.Bus_Id=? ";

            PreparedStatement pstmt = Connections.getCon().prepareStatement(query);
            pstmt.setInt(1, number);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                if (number == resultSet.getInt("Bus_Id")) {
                    int busNumber = resultSet.getInt("bus_number");

                    // Confirm booking
                    System.out.println("Do you want to book Ticket? (yes/no)");
                    String confirmation = sc.next();
                    if (confirmation.equalsIgnoreCase("yes")) {
                        ConfirmBooking.details(resultSet, busNumber);
                    } else {
                        System.out.println("Booking canceled.");
                    }
                } else {
                    System.out.println("Sorry! Bus not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to display available buses and route information
    private static void displayBusAndRouteInfo() {
        String query = "SELECT b.*, r.* FROM DATABASE.Bus_Details b JOIN DATABASE.Route_Details r ON b.route_Id = r.route_Id ";
        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
        System.out.println("\tBus ID\t\t\tBus Number\t\t\tBus Name\t\t\tSeat\t\t\tPrice\t\t\tRoute ID\t\t\tDriver Name\t\t\tOrigin City\t\t\tDestination City");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
        try {
            PreparedStatement pstmt = Connections.getCon().prepareStatement(query);
            ResultSet resultSet = pstmt.executeQuery();
            System.out.println();
            while (resultSet.next()) {
                int busId = resultSet.getInt("Bus_Id");
                int busNumber = resultSet.getInt("Bus_Number");
                String busName = resultSet.getString("Bus_Name");
                int seat = resultSet.getInt("Seat");
                double price = resultSet.getDouble("Price");
                int routeId = resultSet.getInt("Route_Id");
                String driverName = resultSet.getString("Driver_Name");
                String originCity = resultSet.getString("Origin_City");
                String destinationCity = resultSet.getString("Destination_City");
                displayBusDetails(busId, busNumber, busName, seat, price, routeId, driverName, originCity,
                        destinationCity);
            }
            System.out.println("---------------------------------------------------------------------------------------------------------------------------");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to display details of a bus
    public static void displayBusDetails(int busId, int busNumber, String busName, int seat, double price, int routeId,
            String driverName, String originCity, String destinationCity) {
System.out.printf("%-8d%-24s%-16d%-20s%-8d%-8d%n", busId, busName, busNumber, driverName, routeId, seat);
}


    // Method to display booking history for a user
    public static void bookingHistory() {
        try {
            String query = "Select * from DATABASE.booking_history where User_id="
                    + DatabaseConnection.result.getInt("User_id") + "";

            PreparedStatement pstmt = Connections.getCon().prepareStatement(query);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                System.out.println(
                        "---------------------------------------------------------------------------------------------------------------------------");
                System.out.println(
                        "\tBooking ID\t\tUser ID\t\tPayment ID\t\tUser Name\t\tSeats Reserved");
                System.out.println(
                        "---------------------------------------------------------------------------------------------------------------------------");
                while (resultSet.next()) {
                    int bookingId = resultSet.getInt("Booking_Id");
                    int userId = resultSet.getInt("User_Id");
                    int paymentId = resultSet.getInt("Payment_Id");
                    String userName = resultSet.getString("User_Name");
                    int seatsReserved = resultSet.getInt("Seats_Reserved");

                    displayBookingDetails(bookingId, userId, paymentId, userName, seatsReserved);
                }
                System.out.println(
                        "---------------------------------------------------------------------------------------------------------------------------");
            } else {
                System.out.println("Oops No Booking History...");
            }
        } catch (SQLException e) {
            Exceptions.Invalid(e);
        }
    }

    // Method to display booking details
    public static void displayBookingDetails(int bookingId, int userId, int paymentId, String userName, int seatsReserved) {
        System.out.printf("%-8d%-16d%-12d%-24s%-8d%n", bookingId, userId, paymentId, userName, seatsReserved);
    }


    // Method to cancel a booking
    public static void cancelBooking() {
        System.out.println("Enter Your Booking ID to Cancel the Ticket");
        try {
            int bookingId = sc.nextInt();

            // Check if the booking ID exists in the booking details
            String checkBookingQuery = "SELECT * FROM DATABASE.BOOKING_DETAILS WHERE booking_id = ? AND User_id=?";
            PreparedStatement checkBookingStmt = Connections.getCon().prepareStatement(checkBookingQuery);
            checkBookingStmt.setInt(1, bookingId);
            checkBookingStmt.setInt(2, DatabaseConnection.result.getInt("User_Id"));

            ResultSet bookingResult = checkBookingStmt.executeQuery();

            if (bookingResult.next()) {
                // Check if the booking has already been cancelled
                String checkCancellationQuery = "SELECT * FROM DATABASE.Cancellation_Details WHERE booking_id = ? AND User_id=?";
                PreparedStatement checkCancellationStmt = Connections.getCon().prepareStatement(checkCancellationQuery);
                checkCancellationStmt.setInt(1, bookingId);
                checkCancellationStmt.setInt(2, DatabaseConnection.result.getInt("User_Id"));

                ResultSet cancellationResult = checkCancellationStmt.executeQuery();

                if (!cancellationResult.next()) {
                    // Insert into cancellation details
                    String addCancel = "INSERT INTO DATABASE.Cancellation_Details(BOOKING_ID, USER_ID, CANCELLATION_DATE, AMOUNT) VALUES (?, ?, SYSDATE, ?)";
                    PreparedStatement addCancelStmt = Connections.getCon().prepareStatement(addCancel);
                    addCancelStmt.setInt(1, bookingId);
                    addCancelStmt.setInt(2, DatabaseConnection.result.getInt("User_Id"));
                    addCancelStmt.setDouble(3, ConfirmBooking.amount);
                    addCancelStmt.executeUpdate();

                    // Delete from booking history
                    String removeHistoryQuery = "DELETE FROM DATABASE.BOOKING_HISTORY WHERE booking_id = ?";
                    PreparedStatement removeHistoryStmt = Connections.getCon().prepareStatement(removeHistoryQuery);
                    removeHistoryStmt.setInt(1, bookingId);
                    removeHistoryStmt.executeUpdate();

                    String removePHistoryQuery = "DELETE FROM DATABASE.Payment_details WHERE booking_id = ?";
                    PreparedStatement removePHistoryStmt = Connections.getCon().prepareStatement(removePHistoryQuery);
                    removePHistoryStmt.setInt(1, bookingId);
                    removePHistoryStmt.executeUpdate();

                    System.out.println("Booking with ID " + bookingId + " cancelled successfully.");
                } else {
                    System.out.println("This booking has already been cancelled.");
                }
            } else {
                System.out.println("There is no booking with this ID.");
            }
        } catch (Exception e) {
            Exceptions.handleException(e);
        }
    }
}
