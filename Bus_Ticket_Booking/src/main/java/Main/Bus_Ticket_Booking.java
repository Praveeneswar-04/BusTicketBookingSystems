package Main;

import java.util.Scanner;
import ExceptionHandling.Exceptions;
import GuestDetails.Guest;
import PersonDetails.Account;
import PersonDetails.User;

/**
 * This class represents the main entry point for the bus ticket booking application.
 * @author Praveen Eswar k
 * @since 19 FEB 2024
 */
public class Bus_Ticket_Booking {
    static Scanner sc = new Scanner(System.in);
    static boolean flag0 = true;

    public static void main(String[] args) {
        System.out.println("\n\tWelcome to XYZ Bus Ticket Booking!");

        // Main menu loop
        while (flag0) {
            System.out.println("\t===============================================");
            System.out.println("\t|                 Main Menu                   |");
            System.out.println("\t|=============================================|");
            System.out.println("\t| 1. Login                                    |");
            System.out.println("\t| 2. Register                                 |");
            System.out.println("\t| 3. Guest                                    |");
            System.out.println("\t| 4. Exit                                     |");
            System.out.println("\t===============================================");
            System.out.println("\nEnter Your Option");

            try {
                int Option = sc.nextInt();

                switch (Option) {
                    case 1:
                        login(); // Proceed to login
                        break;
                    case 2:
                        User.Register(); // Register a new user
                        break;
                    case 3:
                        Guest.Guest_User(); // Enter as a guest
                        break;
                    case 4:
                        flag0 = false;
                        System.out.println("Thank You Visit Again !");
                        break;
                    default:
                        System.out.println("Choose a Valid Option");
                        break;
                }
            } catch (Exception e) {
                Exceptions.InputMismatchException(e); // Handle input mismatch exception
                sc.next();
            }
        }
    }

    /**
     * Method to handle the login process.
     */
    public static void login() {
        boolean flag = true;
        while (flag) {
            System.out.println("\t===============================================");
            System.out.println("\t|                 Login As                    |");
            System.out.println("\t|=============================================|");
            System.out.println("\t| 1. User                                     |");
            System.out.println("\t| 2. Admin                                    |");
            System.out.println("\t| 3. Bus Operator                             |");
            System.out.println("\t| 4. Go Back                                  |");
            System.out.println("\t| 5. Exit                                     |");
            System.out.println("\t===============================================");
            System.out.println("\nEnter your Option");
            try {
                int option = sc.nextInt();

                switch (option) {
                    case 1:
                        String User = "User";
                        Account acc = new Account(User);
                        acc.login(); // Login as a user
                        break;
                    case 2:
                        String Admin = "Admin";
                        Account acc1 = new Account(Admin);
                        acc1.login(); // Login as an admin
                        break;
                    case 3:
                        String Operator = "BusOperator";
                        Account acc2 = new Account(Operator);
                        acc2.login(); // Login as a bus operator
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        return; // Go back
                    case 5:
                        System.out.println("Exiting...");
                        flag = false; // Exit the program
                        break;
                    default:
                        System.out.println("Choose a Valid Option");
                        break;
                }
            } catch (Exception e) {
                Exceptions.InputMismatchException(e); // Handle input mismatch exception
                sc.next();
            }

        }
    }

   
}
