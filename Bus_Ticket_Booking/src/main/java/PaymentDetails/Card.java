package PaymentDetails;

import java.sql.*;
import java.util.*;

import BookingDetails.Book;
import Connection.Connections;
import ExceptionHandling.Exceptions;
import PersonDetails.DatabaseConnection;
/*
 * This class represents the card Payment
 * @author Praveen Eswar K
 * @since 19 FEB 2024
 */
public class Card extends Payment {
    private static double amount;
    static Scanner sc = new Scanner(System.in);

    // Constructor for card payment
    public Card() {
        try {
            System.out.println("Card payment selected.");

            // Input card number
            System.out.print("Enter card number: ");
            String cardNumber = scanner.next();

            // Validate card number length
            while (cardNumber.length() != 10) {
                System.out.println("Enter a valid number");
                cardNumber = null;
            }

            // Input PIN
            System.out.println("Enter Your Pin Number");
            int pin = sc.nextInt();

            // Process card payment
            processCardPayment(cardNumber);
        } catch (Exception e) {
            Exceptions.InputMismatchException(e);
        }
    }

    // Constructor with amount parameter
    public Card(double amount) {
        this.amount = amount;
    }

    // Method to process card payment
    @SuppressWarnings("static-access")
    private void processCardPayment(String cardNumber) {
        String userquery = "Select max(payment_id)from DATABASE.Payment_details";
        String query = "INSERT INTO DATABASE.PAYMENT_DETAILS (PAYMENT_ID,PAYMENT_METHOD,AMOUNT,PAYMENT_DATE,USER_ID,BUS_ID) VALUES(?,?,?,SYSDATE,?,?)";
        try {
            PreparedStatement uquery = Connections.getCon().prepareStatement(userquery);
            ResultSet rs = uquery.executeQuery();

            Integer id = null;
            if (rs.next()) {
                id = rs.getInt(1) + 1;
            }

            PreparedStatement pstmt = Connections.getCon().prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.setString(2, "CARD");
            pstmt.setDouble(3, this.amount);
            pstmt.setInt(4, DatabaseConnection.result.getInt("User_id"));
            pstmt.setInt(5, Book.resultSet.getInt("Bus_Id"));
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected < 0) {
                System.out.println("Unable to Process At the moment ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
