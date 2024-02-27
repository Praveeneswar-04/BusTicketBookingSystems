package PersonDetails;

import java.util.Scanner;
import BusDetails.BusOperations;
import ExceptionHandling.Exceptions;
import SearchDetails.Searching;

/**
 * This class represents a bus operator and provides functionality
 * for managing buses, routes, stops, and performing search operations.
 * @author Praveen eswar K
 * @since 20 FEB 2024
 */
public class BusOperator extends Person {
    static Scanner sc = new Scanner(System.in);

    // Default constructor
    public BusOperator() {
    }

    // Constructor with parameters
    public BusOperator(int user_Id, String user_Name, String password, String phone_number, String email_Id,
            String address) {
        super(user_Name, password, phone_number, email_Id, address);
    }

    // Method to display the bus operator menu
    public static void Menu() {
        boolean flag = true;
        while (flag) {
            System.out.println("\t===============================================");
            System.out.println("\t|          Welcome Bus Operator                |");
            System.out.println("\t|=============================================|");
            System.out.println("\t| 1. Add Bus                                   |");
            System.out.println("\t| 2. Update Bus                                |");
            System.out.println("\t| 3. View Bus                                  |");
            System.out.println("\t| 4. Search                                    |");
            System.out.println("\t| 5. Add Route                                 |");
            System.out.println("\t| 6. Add Stop                                  |");
            System.out.println("\t| 7. Exit                                      |");
            System.out.println("\t===============================================");
            System.out.println("Enter Your Option:");
            
            try {
                int option = sc.nextInt();
                switch (option) {
                case 1:
                    BusOperations.Add();
                    break;
                case 2:
                    BusOperations.Update();
                    break;
                case 3:
                    BusOperations.View();
                    break;
                case 4:
                    Searching.Search();
                    break;
                case 5:
                    BusOperations.addRoute();
                    break;
                case 6:
                    BusOperations.addStop();
                    break;
                case 7:
                    System.out.println("Exiting...");
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
