package PaymentDetails;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import BookingDetails.Book;
import Connection.Connections;
import ExceptionHandling.Exceptions;
import PersonDetails.DatabaseConnection;

/**
 * This class represents the Cash Payment method.
 * @author Praveen Eswar K
 * @since 19 FEB 2024
 */
public class Cash extends Payment {
    Scanner scanner = new Scanner(System.in);
    private static double amount;

    // Constructor for cash payment
    public Cash() {
        try {
            System.out.println("Cash payment selected.");

            System.out.println("Enter cash amount ");
            
            double amount = scanner.nextDouble();
            // Check if the entered amount is less than the total amount
            if(amount < this.amount) {
                int balance = (int) (this.amount - amount);
                System.out.printf("Balance ", balance + "");
                processCashPayment(amount);
            } else if(amount < 0) {
                System.out.println("Please Enter a valid Amount");
            } else {
                System.out.printf("Proceed");
                processCashPayment(amount);
            }
        } catch(Exception e) {
            Exceptions.InputMismatchException(e);
        }
    }

    // Constructor with amount parameter
    public Cash(double amount) {
        this.amount = amount;
    }

    // Method to process cash payment
    private static void processCashPayment(double amount) {
        String userquery = "Select max(payment_id)from DATABASE.Payment_details";
        String query = "INSERT INTO DATABASE.PAYMENT_DETAILS (PAYMENT_ID,PAYMENT_METHOD,AMOUNT,PAYMENT_DATE,USER_ID,BUS_ID) VALUES(?,?,?,SYSDATE,?,?)";
        try {
            PreparedStatement pstmt = Connections.getCon().prepareStatement(query);
            PreparedStatement uquery = Connections.getCon().prepareStatement(userquery);
            ResultSet rs = uquery.executeQuery();
            Integer id = null;
            if(rs.next()) {
                id = rs.getInt(1) + 1;
            }
            
            pstmt.setInt(1, id);
            pstmt.setString(2, "CASH");
            pstmt.setDouble(3, amount);
            pstmt.setInt(4, DatabaseConnection.result.getInt("User_id"));
            pstmt.setInt(5, Book.resultSet.getInt("Bus_Id"));
            int rowsAffected = pstmt.executeUpdate();

            if(rowsAffected < 0) {
                System.out.println("Unable to Process At the moment ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
