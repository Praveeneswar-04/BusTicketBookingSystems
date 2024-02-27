package PersonDetails;

import java.util.Scanner;

import ExceptionHandling.Exceptions;

/**
 * This class represents user accounts and handles login functionality.
 * It allows users to log in with a username and password.
 * @author Praveen Eswar K
 * @since  20 FEB 2024
 */
public class Account {
    private String User_name;
    private String password;
    private String person;
    static Scanner sc=new Scanner(System.in);

    // Constructor with username and password parameters
    public Account(String user_name, String password) {
        this.User_name = user_name;
        this.password = password;
    }

    // Constructor with person parameter
    public Account(String name) {
        this.person=name;
    }

    // Default constructor
    public Account() {
    }

    // Method to prompt user for login
    public void login () {
        try {
            System.out.println("Enter the User Name ");
            String u_name = sc.next();
            System.out.println("Enter the Password ");
            String pwd = sc.next();
            System.out.println();
            Account acc=new Account(u_name,pwd);
            DatabaseConnection.Validatelogin(acc.User_name, acc.password, person);
        } catch(Exception e) {
            Exceptions.handleException(e);
        }
    }

    public String getUser_name() {
        return this.User_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
