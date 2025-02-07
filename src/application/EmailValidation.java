package application;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidation {
	
	private static final String emailChars = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
	
	public static boolean isEmailValid(String emailAddress) {
		
		Pattern emailPattern = Pattern.compile(emailChars);
		Matcher emailMatch = emailPattern.matcher(emailAddress);
		
		return emailMatch.matches();
	}
}