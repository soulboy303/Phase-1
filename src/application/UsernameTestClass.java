package application;

import java.util.Scanner;

public class UsernameTestClass {
	
	static String inputLine;
	public static boolean performTestCase(int testNum, String input, boolean expectedOutput){
	System.out.println("Welcome to the UserName Recognizer Testbed\n");

	// Input has been provided, let's see if it is a valid date or not
	String errMessage = UserNameRecognizer.checkForValidUserName(input);
	
	// If the returned String is not empty, it is an error message
	if (errMessage != "") {
		// Display the error message
		System.out.println(errMessage);
		// Display the input line so the user can see what was entered		
		//System.out.println(input);
		// Display the line up to the error and the display an up arrow
		System.out.println(input.substring(0,UserNameRecognizer.userNameRecognizerIndexofError) + "\u21EB");
		if(expectedOutput) {
			System.out.println("Expected to pass. Came out invalid. Test failed\n");
			return false;
		}
		else {
			System.out.println("Expected to fail. Came out invalid. Test passed\n");
			return true;
		}
	}
	else {
		// The returned String is empty, it, so there is no error in the input.
		System.out.println("Success! The UserName is valid.");
		if(expectedOutput) {
			System.out.println("Expected to pass. Came out valid. Test passed\n");
			return true;
		}
		else {
			System.out.println("Expected to fail. But came out as valid. Test failed\n");
			return false;
		}
	}
	}

}

