package GuestDetails;

import PersonDetails.User;
import SearchDetails.Searching;
import java.util.Scanner;
import Connection.Connections;

/**
 * This class provides functionality for guest users.
 * @author Praveen Eswar k
 * @since 19 FEB 2024
 */
public class Guest {
    static Scanner sc = new Scanner(System.in);

    /**
     * Method to handle guest user operations.
     */
    public static void Guest_User() {
        Connections.invoke(); // Establish database connection

        boolean flag = true;

        while (flag) {
            System.out.println("\t\t===============================================");
            System.out.println("\t\t|                 Main Menu                   |");
            System.out.println("\t\t|=============================================|");
            System.out.println("\t\t| 1. Register                                 |");
            System.out.println("\t\t| 2. Search Bus                               |");
            System.out.println("\t\t| 3. Exit                                     |");
            System.out.println("\t\t===============================================");
            System.out.println("\nEnter Your Option:");

            int Option = sc.nextInt();

            switch (Option) {
                case 1:
                    User.Register(); // Register a new user
                    flag = false; // Set flag to exit loop after registration
                    break;
                case 2:
                    Searching.Search(); // Search for buses
                    break;
                case 3:
                    System.out.println("Exiting..."); // Exit the program
                    flag = false; // Set flag to exit loop
                    break;
            }
        }
    }
}
