package SearchDetails;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import Connection.Connections;
import ExceptionHandling.Exceptions;

/**
 * This class implements various search operations on a list of buses.
 * It implements the Search interface.
 * @author Praveen Eswar K
 * @since  19 FEB 2024
 */
interface Search {
    void sortByName(List<Bus> values);
    void sortByPrice(List<Bus> values);
    void filterByPriceRange(List<Bus> values, double minPrice, double maxPrice);
    void filterBySeatsAvailable(List<Bus> values, int minSeatsAvailable);
    void view(List<Bus> values);
}

public class Bus_List implements Search {

    static Scanner sc = new Scanner(System.in);

    /**
     * Sorts the list of buses by price.
     */
    @Override
    public void sortByPrice(List<Bus> values) {
        Collections.sort(values, new Comparator<Bus>() {
            @Override
            public int compare(Bus bus1, Bus bus2) {
                return Double.compare(bus1.getPrice(), bus2.getPrice());
            }
        });
        System.out.println("Sorted buses by price:");
        System.out.println("Bus ID\tBus Name\t\tBus Number\tDriver Name\tRoute ID    Seat\n");

        for (Bus bus : values) {
            System.out.printf("%-8d%-24s%-16d%-20s%-8d%-8d%n", bus.getBus_Id(), bus.getBus_Name(), bus.getBus_Number(), bus.getDriver_Name(), bus.getRoute_id(), bus.getSeat());
        }
    }

    /**
     * Sorts the list of buses by name.
     */
    @Override
    public void sortByName(List<Bus> values) {
        List<Bus> sort = values.stream().sorted(Comparator.comparing(Bus::getBus_Name)).collect(Collectors.toList());
        System.out.println("Bus ID\tBus Name\t\tBus Number\tDriver Name\tRoute ID    Seat\n");

        for (Bus bus : sort) {
            System.out.printf("%-8d%-24s%-16d%-20s%-8d%-8d%n", bus.getBus_Id(), bus.getBus_Name(), bus.getBus_Number(), bus.getDriver_Name(), bus.getRoute_id(), bus.getSeat());
        }
    }

    /**
     * Filters the list of buses by price range.
    
     */
    public void filterByPriceRange(List<Bus> values, double minPrice, double maxPrice) {
        List<Bus> filter = new ArrayList<>();
        for (Bus bus : values) {
            if (bus.getPrice() >= minPrice && bus.getPrice() <= maxPrice) {
                filter.add(bus);
            }
        }
        System.out.println("Bus ID\tBus Name\t\tBus Number\tDriver Name\tRoute ID    Seat\n");
        filter.forEach(System.out::println);
    }

    /**
     * Filters the list of buses by available seats.
     */
    public void filterBySeatsAvailable(List<Bus> buses, int minSeatsAvailable) {
        List<Bus> filter = new ArrayList<>();
        for (Bus bus : buses) {
            if (bus.getSeat() >= minSeatsAvailable) {
                filter.add(bus);
            }
        }
        System.out.println("Bus ID\tBus Name\t\tBus Number\tDriver Name\tRoute ID    Seat\n");
        filter.forEach(System.out::println);
    }

    /**
     * Searches for buses by name.
     */
    public void searchByName(List<Bus> values, String name) {
        System.out.println("Bus ID\tBus Name\t\tBus Number\tDriver Name\tRoute ID    Seat\n");

        values.stream()
                .filter(bus -> bus.getBus_Name().equalsIgnoreCase(name))
                .map(Bus::toString) // Assuming Bus class has a meaningful toString method
                .forEach(busString -> System.out.print(busString + " "));
        System.out.println();
    }

    /**
     * Searches for buses by place.
    
     */
    public void searchByPlace(List<Bus> values, String name) {
        String query = "Select * from DATABASE.Route_Details";
        int route_id;

        try {
            PreparedStatement pstmt = Connections.getCon().prepareStatement(query);
            ResultSet resultSet = pstmt.executeQuery();
            int a = 0;
            while (resultSet.next()) {
                if (resultSet.getString("Destination_city").equalsIgnoreCase(name)) {
                    System.out.println("present");
                    a = resultSet.getInt("Route_Id");
                }

            }
            route_id = a;
            System.out.println("Bus ID\tBus Name\t\tBus Number\tDriver Name\tRoute ID    Seat\n");

            values.stream().filter(Bus -> Bus.getRoute_id() == route_id).forEach(System.out::println);
        } catch (Exception e) {
            Exceptions.handleException(e);
        }
    }

    /**
     * Displays the list of buses.
     */
    public void view(List<Bus> values) {

    }
}
