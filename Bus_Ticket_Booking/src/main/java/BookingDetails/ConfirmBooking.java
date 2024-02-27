package BookingDetails;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Connection.Connections;
import ExceptionHandling.Exceptions;
import NotifcationDetails.SMS;
import PaymentDetails.Payment;
import PersonDetails.*;
import java.util.*;

/*
 * This class represents the confirmation of the booking 
 * @author Praveen Eswar k
 * @since 19 FEB 2024
 */
public class ConfirmBooking {
    Account acc = new Account();
    Person per = new Person();
    static Scanner sc = new Scanner(System.in);
    static double amount = 0;

    // Method to gather booking details and confirm booking
    
    public static void details(ResultSet resultSet, int BusNumber) {
        try {
            int seats = validateSeats(BusNumber); // Validate and get the number of seats
            sc.nextLine();
            System.out.println("Enter the Date to be booked [YYYY-MM-DD]");
            String traveldate = validatedate(); // Validate the travel date

            amount = seats * resultSet.getDouble("Price"); // Calculate the total price
            System.out.println("Total Price :" + amount);

            ConfirmBooking book = new ConfirmBooking();
            int payment_id = Payment.paymentmethod(amount); // Process payment and get payment ID
            book.makeBooking(resultSet, BusNumber, payment_id, seats, traveldate, amount); // Make booking
        } catch (Exception e) {
            Exceptions.handleException(e);
        }

    }

    // Method to make a booking
    public void makeBooking(ResultSet resultSet, int bus_number, int paymentId, int seatsReserved, String traveldate, double Price) {
        try {
            // Prepare queries
            String bookingquery = "Select max(Booking_id)from DATABASE.booking_details";
            String tickectquery = "Select max(Ticket_number)from DATABASE.booking_details";
            String updateQuery = "INSERT INTO DATABASE.Booking_Details ( BOOKING_ID,USER_ID,TICKET_NUMBER,TRAVEL_DATE,PRICE,PAYMENT_ID,ROUTE_ID,SEATS_BOOKED,BUS_DETAILS_BUS_ID) values(?,?,?,to_date(?,'yyyy-mm-dd'),?,?,?,?,?)";
            String historyQuery = "INSERT INTO DATABASE.BOOKING_HISTORY(BOOKING_ID,USER_ID,ROUTE_ID,SEATS_RESERVED,PAYMENT_ID,USER_NAME) VALUES(?,?,?,?,?,?)";

            PreparedStatement preparedStatement = Connections.getCon().prepareStatement(updateQuery);
            PreparedStatement bookingStatement = Connections.getCon().prepareStatement(bookingquery);
            PreparedStatement ticketStatement = Connections.getCon().prepareStatement(tickectquery);

            ResultSet rs = ticketStatement.executeQuery(tickectquery);
            ResultSet rs1 = bookingStatement.executeQuery(bookingquery);

            Integer bookingId = null;
            Integer tickectnumber = null;

            // Get booking and ticket numbers
            if (rs1.next()) {
                bookingId = rs1.getInt(1) + 1;
            }
            if (rs.next()) {
                tickectnumber = rs.getInt(1) + 1;

            }

            // Set parameters for the PreparedStatement
            preparedStatement.setInt(1, bookingId);
            preparedStatement.setInt(2, DatabaseConnection.result.getInt("User_Id"));
            preparedStatement.setInt(3, tickectnumber);
            preparedStatement.setString(4, traveldate);
            preparedStatement.setDouble(5, Price);
            preparedStatement.setInt(6, paymentId);
            preparedStatement.setInt(7, resultSet.getInt("Route_id"));
            preparedStatement.setInt(8, seatsReserved);
            preparedStatement.setInt(9, resultSet.getInt("Bus_id"));

            // Display booking details and confirm
            System.out.println("\nBooking Details");
            System.out.println("Booking ID \n" + bookingId);
            System.out.println("User ID \n" + DatabaseConnection.result.getInt("User_Id"));
            System.out.println("Ticket Number \n" + tickectnumber);
            System.out.println("Travel Date \n" + traveldate);
            System.out.println("Price \n" + Price);
            System.out.println("Payment ID \n " + paymentId);
            System.out.println("Route ID \n " + resultSet.getInt("Route_id"));
            System.out.println("Seats Reserved \n " + seatsReserved);
            System.out.println("Bus ID \n" + resultSet.getInt("Bus_id"));
            System.out.println("Please Ensure The details ");
            System.out.println("Do you Want to Continue[Yes/No]");
            String option = sc.next();

            // If user confirms, execute the update query
            if (option.equalsIgnoreCase("yes")) {
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    // Insert into booking history
                    PreparedStatement historyStatement = Connections.getCon().prepareStatement(historyQuery);
                    historyStatement.setInt(1, bookingId);
                    historyStatement.setInt(2, DatabaseConnection.result.getInt("User_Id"));
                    historyStatement.setInt(3, resultSet.getInt("Route_id"));
                    historyStatement.setInt(4, seatsReserved);
                    historyStatement.setInt(5, paymentId);
                    historyStatement.setString(6, DatabaseConnection.result.getString("User_Name"));
                    historyStatement.executeUpdate();
                    System.out.println("Booking  successfully.");
                    SMS.sendTicketConfirmation();
                } else {
                    System.out.println("Booking update failed.");
                }
            } else {
                Book.bookTicket(); // If user cancels, return to booking
            }

            // Close PreparedStatement and Connection
            preparedStatement.close();
        } catch (Exception e) {
            Exceptions.handleException(e);
        }
    }

    // Method to validate and get the travel date
    public static String validatedate() {
        String travelDateStr = sc.nextLine();

        // Validate the date
        LocalDate travelDate = null;
        do {
            try {
                travelDate = LocalDate.parse(travelDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
                LocalDate currentDate = LocalDate.now();
                LocalDate previousDay = currentDate.minusDays(1);

                if (travelDate.isBefore(currentDate) || travelDate.equals(previousDay)) {
                    System.out.println("Invalid date. Date cannot be the previous day of the present day.");
                    travelDateStr = null;
                } else {
                    return travelDateStr;
                }
            } catch (Exception e) {
                System.out.println("Invalid date format. Please enter date in YYYY-MM-DD format.");
                return null;
            }
        } while (true);
    }

    // Method to validate the number of seats
    public static int validateSeats(int busNumber) {
        try {
            String query = "SELECT * FROM DATABASE.BUS_DETAILS WHERE BUS_NUMBER=?";
            PreparedStatement pstmt = Connections.getCon().prepareStatement(query);
            pstmt.setInt(1, busNumber);
            ResultSet rs = pstmt.executeQuery();
            rs.next();

            int availableSeats = rs.getInt("seat");

            if (availableSeats == 0) {
                System.out.println("No seats available for the bus.");
                return 0;
            }

            System.out.println("Available seats on bus " + busNumber + ": " + availableSeats);
            int seatsRequired = 0;
            do {
                // Get number of seats required from the user
                System.out.println("Enter Seats Required: ");
                seatsRequired = sc.nextInt();

                // Validate the user-required seats
                if (validateSeats(seatsRequired, availableSeats)) {
                    System.out.println("Seats available. Proceed with booking.");
                    return seatsRequired;

                    // Further processing...
                } else {
                    System.out.println("Invalid number of seats. Please enter a valid number.");
                }
            } while (true);
        } catch (Exception e) {
            Exceptions.Invalid(e);
            return 0;
        }
    }

    // Method to validate the number of seats
    public static boolean validateSeats(int requiredSeats, int availableSeats) {
        return requiredSeats <= availableSeats && requiredSeats > 0;
    }
}
