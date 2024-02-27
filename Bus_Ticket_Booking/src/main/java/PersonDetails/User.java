package PersonDetails;

import java.util.Scanner;

import BookingDetails.*;
import ExceptionHandling.Exceptions;
import FeedbackDetails.Feedback;
import Validator.Validate;
import SearchDetails.*;

/**
 * This class represents a user and contains methods related to user registration and user menu options.
 * It extends the Person class.
 * @author Praveen Eswar K
 * @since 19 FEB 2024
 */
public class User extends Person {
    private static Scanner sc = new Scanner(System.in);
    static Person person = new Person();
    static Account account = new Account();

    /**
     * Constructor to initialize a user object with provided details.
    
     */
    public User(String user_name, String Password, String phone_number, String gender, String email_Id, String address) {
        super(user_name, Password, phone_number, gender, email_Id, address);
    }

    /**
     * Default constructor.
     */
    public User() {
        super();
    }

    /**
     * Method to register a new user.
     */
    public static void Register() {
        try {
            System.out.println("Enter your details to Register");
            System.out.println("Enter User Name");
            String user_Name = Validate.validateUsername();

            System.out.println("Enter your Password");
            String password = Validate.validatePassword();

            System.out.println("Enter your Email-ID");
            String email_Id = Validate.validateEmail();

            System.out.println("Enter Your Gender[Male/Female/Others]");
            String gender = Validate.validateGender().toUpperCase();

            System.out.println("Enter Phone Number");
            String phone_number = Validate.validateMobileNumber();

            System.out.println("Enter Your Address");
            String address = sc.nextLine();

            System.out.println("Welcome " + user_Name);

            User user = new User(user_Name, password, phone_number, gender, email_Id, address);
            DatabaseConnection.AddUser(user);
        } catch (Exception e) {
            Exceptions.InputMismatchException(e);
        }
    }

    /**
     * Method to display the user menu and handle user choices.
     */
    public static void menu() {
        boolean flag = true;
        while (flag) {
            System.out.println("\t===============================================");
            System.out.println("\t|                 Main Menu                   |");
            System.out.println("\t|=============================================|");
            System.out.println("\t| 1. Search                                   |");
            System.out.println("\t| 2. Book Ticket                              |");
            System.out.println("\t| 3. Cancel Booking                           |");
            System.out.println("\t| 4. Booking History                          |");
            System.out.println("\t| 5. Feedback                                 |");
            System.out.println("\t| 6. Go Back                                  |");
            System.out.println("\t===============================================");
            System.out.println("Enter your choice");

            try {
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        Searching.Search();
                        break;
                    case 2:
                        Book.bookTicket();
                        break;
                    case 3:
                        Book.cancelBooking();
                        break;
                    case 4:
                        Book.bookingHistory();
                        break;
                    case 5:
                        Feedback.getUserFeedback();
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        flag = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number from 1 to 6.");
                }
            } catch (Exception e) {
                Exceptions.InputMismatchException(e);
                sc.next();
            }
        }
    }

}
