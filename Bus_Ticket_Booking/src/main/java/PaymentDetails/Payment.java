package PaymentDetails;

import java.util.Scanner;
import ExceptionHandling.Exceptions;

/**
 * This class represents the payment options available to users.
 * Users can choose between different payment methods such as cash, card, or mobile wallet.
 * @author Praveen Eswar K
 * @since  19 FEB 2024
 */
public class Payment {
    static Scanner scanner = new Scanner(System.in);
    
    /**
     * Method to prompt the user for payment method selection.
     */
    public static int paymentmethod(double amount) {
        boolean flag = true;
        int choice = 0;
        System.out.println("\t===============================================");
        System.out.println("\t|                 Payment Menu                 |");
        System.out.println("\t|=============================================|");
        System.out.println("\t| 1. Cash                                     |");
        System.out.println("\t| 2. Card                                     |");
        System.out.println("\t| 3. Mobile Wallet                            |");
        System.out.println("\t| 4. Go Back                                  |");
        System.out.println("\t===============================================");
        System.out.println("\nEnter Your Option:");

        try {
            while (flag) {
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        Cash cash = new Cash(amount);
                        new Cash();
                        flag = false;
                        break;
                    case 2:
                        Card card = new Card(amount);
                        new Card();
                        flag = false;
                        break;
                    case 3:
                        MobileWallet wallet = new MobileWallet(amount);
                        new MobileWallet();
                        flag = false;
                        break;
                    case 4:
                        flag = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            }
        } catch (Exception e) {
            Exceptions.InputMismatchException(e);
            scanner.next();
        }
        return choice;
    }
}
