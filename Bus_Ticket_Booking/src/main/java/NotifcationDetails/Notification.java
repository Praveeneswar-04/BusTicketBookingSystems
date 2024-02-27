package NotifcationDetails;

import java.sql.SQLException;
import ExceptionHandling.Exceptions;
import PersonDetails.DatabaseConnection;
import PersonDetails.Person;

/**
 * This class represents notification details such as email and phone number.
 * @author Praveen Eswar k
 * @since 19 FEB 2024
 */
public class Notification {
    protected String email;
    protected String phoneNumber;
    static Person person = new Person();

    /**
     * Constructs a notification object with the provided email and phone number.
     */
    public Notification(String email, String phoneNumber) {
        try {
            // Initialize the email and phone number from the database
            this.email = DatabaseConnection.result.getString("Email_id");
            this.phoneNumber = DatabaseConnection.result.getString("phone_number");
        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace();
        }
    }

    /**
     * Default constructor to initialize notification details from the database.
     */
    public Notification() {
        try {
            // Initialize notification details from the database
            new Notification(DatabaseConnection.result.getString("Email_id"), DatabaseConnection.result.getString("phone_number"));
        } catch (Exception e) {
            // Handle any exceptions
            Exceptions.Invalid(e);
        }
    }
}
