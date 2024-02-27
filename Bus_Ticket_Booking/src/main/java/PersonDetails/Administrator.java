package PersonDetails;

import java.util.Scanner;
import SearchDetails.*;
import BusDetails.*;
import ExceptionHandling.Exceptions;

/**
 * This class represents an administrator and provides functionality
 * for managing buses and performing search operations.
 * @author Praveen Eswar K
 * @since  20 FEB 2024
 */
public class Administrator extends Person {
    static Scanner sc = new Scanner(System.in);

    // Constructor with parameters
    public Administrator(int user_Id, String user_Name, String password, String phone_number, String email_Id,
            String address) {
        super(user_Name, password, phone_number, email_Id, address);
    }

    // Default constructor
    public Administrator() {
    }

    // Method to display the administrator menu
    public static void Menu() {
        boolean flag = true;
        while (flag) {
            System.out.println("\t===============================================");
            System.out.println("\t|                    Main Menu                |");
            System.out.println("\t|=============================================|");
            System.out.println("\t| 1. Add Bus                                   |");
            System.out.println("\t| 2. Remove Bus                                |");
            System.out.println("\t| 3. Update Bus                                |");
            System.out.println("\t| 4. View Bus                                  |");
            System.out.println("\t| 5. Search                                    |");
            System.out.println("\t| 6. Exit                                      |");
            System.out.println("\t===============================================");
            System.out.println("Enter Your Option");
            try {
                int option = sc.nextInt();
                switch (option) {
                case 1:
                    BusOperations.Add();
                    break;
                case 2:
                    BusOperations.Delete();
                    break;
                case 3:
                    BusOperations.Update();
                    break;
                case 4:
                    BusOperations.View();
                    break;
                case 5:
                    Searching.Search();
                    break;
                case 6:
                    flag = false;
                    break;
                default:
                    System.out.println("Enter a valid Option");
                    break;
                }
            } catch (Exception e) {
                Exceptions.InputMismatchException(e);
                sc.next();
            }
        }
    }
}
