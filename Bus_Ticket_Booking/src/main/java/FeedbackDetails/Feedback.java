package FeedbackDetails;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import Connection.Connections;
import ExceptionHandling.Exceptions;
import PersonDetails.DatabaseConnection;

/**
 * This class handles user feedback related operations.
 * @author Praveen Eswar k
 * @since 19 FEB 2024
 */
public class Feedback {
    static Scanner sc = new Scanner(System.in);

    /**
     * Method to get user feedback.
     */
    public static void getUserFeedback() {
        System.out.println("Please provide your feedback:");
        String feedback = sc.nextLine();
        updateFeedbackInDatabase(feedback);
    }

    /**
     * Method to update user feedback in the database.
     */
    public static void updateFeedbackInDatabase(String feedback) {
        String userquery = "SELECT MAX(Feedback_id) FROM DATABASE.Feedback_details";

        String updateQuery = "INSERT INTO DATABASE.Feedback_details (feedback_id, user_id, user_name, feedback, feedback_date) VALUES (?, ?, ?, ?, SYSDATE)";
        try (PreparedStatement preparedStatement = Connections.getCon().prepareStatement(updateQuery)) {
            PreparedStatement uquery = Connections.getCon().prepareStatement(userquery);
            ResultSet rs = uquery.executeQuery();
            Integer id = null;
            if (rs.next()) {
                id = rs.getInt(1) + 1;
            }
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, DatabaseConnection.result.getInt("user_id"));
            preparedStatement.setString(3, DatabaseConnection.result.getString("user_name"));
            preparedStatement.setString(4, feedback);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Feedback updated successfully.");
            } else {
                System.out.println("Failed to update feedback.");
            }
        } catch (Exception e) {
Exceptions.Invalid(e);        }
    }
}
