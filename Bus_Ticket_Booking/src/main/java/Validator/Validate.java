package Validator;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ExceptionHandling.Exceptions;

public class Validate {
	static Scanner sc = new Scanner(System.in);
	private static final String Mobile_Pattern = "^[6-9]\\d{9}$";
	private static final String EMAIL_Pattern = "[a-zA-Z0-9_-]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}";
	private static final String PASSWORD_Pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,15}$";
	private static final String USERNAME_Pattern = "^[a-zA-Z0-9_]{3,15}$";

	public static String validateMobileNumber() {

		String phno ;
		do {
			phno=sc.next();
		
		if (phno.isEmpty()) {
			System.out.println("Mobile Number Cannot be  Empty");
		}
		else {
		Pattern pattern = Pattern.compile(Mobile_Pattern);
		Matcher matcher = pattern.matcher(phno);

		if (!matcher.matches()){
			System.out.println("Please Enter valid Phone Number ");
			phno = null;		}}}while(phno==null);
		return phno;
	}

	public static String validateEmail() {
		String email;
		do {
			email=sc.next();
		if (email.isEmpty()) {
			Exceptions.handleException(new Exception("Email Cannot be Empty"));
		}
		else {
		Pattern pattern = Pattern.compile(EMAIL_Pattern);
		Matcher matcher = pattern.matcher(email);

		if (!(matcher.matches())) {
			System.out.println("Please Enter valid Email as ['Sanjai@gmail.com']");
			email = null;		}}
		}while(email==null);
		return email;
	}

	public static String validatePassword() {
		String password;
		do {
			password = sc.next();
			if (password == null || password.isEmpty()) {
				System.out.println("Cannot Be Empty");
			} else {
				Pattern pattern = Pattern.compile(PASSWORD_Pattern);
				Matcher matcher = pattern.matcher(password);

				if (!matcher.matches()) {
					System.out.println("Please Enter Strong Password as ['Praveen@123']");
					password = null;
				}
			}
		} while (password == null);
		return password;
	}

	public static String validateUsername() {
		String username;
		do {
			username = sc.next();
			if (username == null || username.isEmpty()) {
				System.out.println("Username cannot be empty");
			} else {
				Pattern pattern = Pattern.compile(USERNAME_Pattern);
				Matcher matcher = pattern.matcher(username);

				if (!matcher.matches()) {
					System.out.println("Please Enter valid User Name as ['Kisshore_123']");
					username = null;
				}
			}
		} while (username == null);

		return username;
	}

	public static String validateGender() {
		String gender;
				do {
					gender = sc.next();
					if (gender == null || gender.isEmpty()) {
						System.out.println("Gender cannot be empty");
					} else {
						if(!(gender.equalsIgnoreCase("Male")||gender.equalsIgnoreCase("Female")||gender.equalsIgnoreCase("Others"))){
							System.out.println("Please Enter a valid Gender");
							gender = null;
						}
					}
				} while (gender == null);

				return gender;
	}
	


}
