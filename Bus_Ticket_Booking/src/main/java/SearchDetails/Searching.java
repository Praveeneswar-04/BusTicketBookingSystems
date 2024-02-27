package SearchDetails;

import java.util.List;
import java.util.Scanner;

import BookingDetails.Book;
import BusDetails.BusOperations;
import ExceptionHandling.Exceptions;
import PersonDetails.DatabaseConnection;
import PersonDetails.Person;

/**
 * This class provides functionality for searching buses, sorting them, and filtering them based on various criteria.
 * It interacts with the Bus_List class to perform these operations.
 * @author Praveen Eswar K
 * @since 19 FEB 2024
 */
public class Searching {

	static Scanner sc = new Scanner(System.in);
	static List<Bus> values = BusOperations.DbtoList();
	static Person person = new Person();
	static Bus_List object = new Bus_List();

	/**
	 * Displays the search menu and handles user input for various search operations.
	 */
	public static void Search() {

		boolean flag=true;
		
		while(flag) {
		
		System.out.println("Buses ");
//		object.view(values);
		System.out.println("\t===============================================");
		System.out.println("\t|                Search Menu                  |");
		System.out.println("\t|=============================================|");
		System.out.println("\t| 1. Search By Bus Name                        |");
		System.out.println("\t| 2. Search by Place                           |");
		System.out.println("\t| 3. Sort By Price                             |");
		System.out.println("\t| 4. Sort By Name                              |");
		System.out.println("\t| 5. Filter By Price                           |");
		System.out.println("\t| 6. Filter By Seat                            |");
		System.out.println("\t| 7. Book Ticket                               |");
		System.out.println("\t| 8. Exit                                      |");
		System.out.println("\t===============================================");
		System.out.println("Enter your choice:");
		try {
			int option = sc.nextInt();

			switch (option) {
			case 1:
				System.out.println("Enter the Name of the Bus:");
				sc.nextLine();
				String name = sc.nextLine();
				object.searchByName(values, name);
				break;
			case 2:
				System.out.println("Enter the Place name:");
				sc.nextLine();
				String Place = sc.nextLine();
				object.searchByPlace(values, Place);
				break;
			case 3:
				object.sortByPrice(values);
				// Implement sort by price
				break;
			case 4:
				object.sortByName(values);
				// Implement sort by name
				break;
			case 5:
				System.out.println("Enter Your price Range:\nMin:");
				try {
					double min = sc.nextDouble();
					System.out.println("Max");
					double max = sc.nextDouble();
					object.filterByPriceRange(values, min, max);
				} catch (Exception e) {
					Exceptions.InputMismatchException(e);
					sc.next();
				}
				// Implement filter by price
				break;
			case 6:
				System.out.println("Enter the Number of Seats");
				try {
					int seats = sc.nextInt();
					object.filterBySeatsAvailable(values, seats);
				} catch (Exception e) {
					Exceptions.InputMismatchException(e);
					sc.next();
				}
				break;
			case 7:
				if (!(DatabaseConnection.result.getString("User_name").isEmpty())||!(DatabaseConnection.result.getString("Admin_User_name").isEmpty())||!(DatabaseConnection.result.getString("Operator_name").isEmpty())) {
					Book.bookTicket();
				} else {
					System.out.println("You Need to Register first...");
				}
				break;
			case 8:
				flag=false;
				System.out.println("Exiting...");
				break;
			default:
				System.out.println("Invalid choice. Please enter a number between 1 and 7.");
			}
		} catch (Exception e) {
			Exceptions.InputMismatchException(e);
			sc.next();
		}
	}}

}
