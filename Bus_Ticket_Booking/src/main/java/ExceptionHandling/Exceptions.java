package ExceptionHandling;

/**
 * This class provides methods to handle exceptions in the bus management system.
 * @author Praven Eswar K
 * @since 19 FEB 2024
 */
public class Exceptions {
    
    /**
     * Handles exceptions by printing the error message to the console.
     */
    public static void handleException(Exception e) {
        System.out.println(e.getMessage());
    }
    
    /**
     * Handles InputMismatchException by displaying a specific error message.
     */
    public static void InputMismatchException(Exception e) {
        System.out.println("An Error Occurred Enter a Valid Option");
    }
    
    /**
     * Handles invalid operations by displaying a specific error message.
     */
    public static void Invalid(Exception e) {
        System.out.println("An Error Occurred Please Try Again ");
    }
}
