package PaymentDetails;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import BookingDetails.Book;
import Connection.Connections;
import ExceptionHandling.Exceptions;
import NotifcationDetails.Email;
import PersonDetails.DatabaseConnection;

/**
 * This class represents the Mobile Wallet Payment method.
 * It allows users to make payments using mobile wallets.
 * @author Prvaeen Eswar K
 * @since 19 FEB 2024
 */
public class MobileWallet extends Payment {
    static Scanner scanner = new Scanner(System.in);
    private static double amount;
    
    // Constructor for mobile wallet payment
    public MobileWallet() {
        System.out.println("Mobile wallet payment selected.");

        System.out.print("Enter Wallet number: ");
        String Wallet = scanner.next();

        processCardPayment(Wallet);
    }

    // Constructor with amount parameter
    public MobileWallet(double amount) {
        this.amount = amount;
    }

    // Method to process mobile wallet payment
    private void processCardPayment(String Wallet) {
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
            pstmt.setString(2, "CARD");
            pstmt.setDouble(3, this.amount);
            pstmt.setInt(4, DatabaseConnection.result.getInt("User_id"));
            pstmt.setInt(5, Book.resultSet.getInt("Bus_Id"));
            int rowsAffected = pstmt.executeUpdate();

            if(rowsAffected < 0) {
                System.out.println("Unable to Process At the moment ");
            }
        } catch (Exception e) {
            Exceptions.Invalid(e);
        }   
    }
}
