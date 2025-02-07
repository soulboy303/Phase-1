package application;

import java.util.HashMap;
import java.util.Map;

import databasePart1.DatabaseHelper;

import java.sql.SQLException;
import java.util.ArrayList;

public class ApplicationTestAutomation {
//	private static ArrayList<Integer> numPassed = new ArrayList<Integer>();
//	private static ArrayList<Integer> numFailed = new ArrayList<Integer>();
	private static HashMap<String,Integer> numPassed = new HashMap<String,Integer>();
	private static HashMap<String,Integer> numFailed = new HashMap<String,Integer>();
	private static HashMap<String, Boolean> passwordTests = new HashMap<String, Boolean>();
	private static HashMap<String, Boolean> usernameTests = new HashMap<String, Boolean>();
	private static final DatabaseHelper databaseHelper = new DatabaseHelper();

	public static void main(String[] args) {
		automatedPasswordTest();
		automatedUsernameTest();
		automatedUserTests();
		automatedInviteCodeTest();

		
		printTotalTests();
	}
	
	private static User createUser() {
		User user = new User("first", "pass", "first", "last", "rg@m", "user");
		return user;
	}
	
	
	private static void emailTest() {
		String key = "email";
		// create new list entry for each test for tracking
		numPassed.put(key,0);
		numFailed.put(key,0);
		
		
		//valid
		boolean result = EmailValidation.isEmailValid("emailTest@.com");
		if(result) {
			System.out.println("Email valid. Pass");
			addResult(true,key);
		}
		else {
			System.out.println("Email not valid. Fail");
			addResult(false,key);
		}
		
		// not valid
		result = EmailValidation.isEmailValid("emailTest.com");
		if(result) {
			System.out.println("Email valid. Should not be valid. Fail");
			addResult(false,key);
		}
		else {
			System.out.println("Email not valid. Should be invalid. Pass");
			addResult(true,key);
		}
		
		
		
		
		
		System.out.println("Password Tests:\nPassed: " + numPassed.get(key) + "\nFailed: " + numFailed.get(key));
	}
	private static void printTotalTests() {
		
		// print total passed tests
		System.out.println("\nTotal Passed: ");
		int totalPassed = 0;
		for(Map.Entry<String, Integer> entry : numPassed.entrySet()) {
			totalPassed = totalPassed + entry.getValue();
		}
		System.out.println(totalPassed);
		
		// print total failed tests
		System.out.println("total Failed:");
		int totalFailed = 0;
		for(Map.Entry<String, Integer> entry : numFailed.entrySet()) {
			totalFailed = totalFailed + entry.getValue();
		}
		
		// print total tests
		System.out.println(totalFailed);
		System.out.println("Total Tests:\n" + (totalFailed + totalPassed));
	}
	private static void addResult(boolean pass, String test) {
		// add results to counters
		if(pass) {numPassed.replace(test, numPassed.get(test)+1);}
		else {numFailed.replace(test, numFailed.get(test)+ 1);}
	}
	private static boolean startDatabase() {
		// function to start the connection to the database
        try {
            databaseHelper.connectToDatabase(); // Connect to the database
            return true;
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        	System.out.println("Error while connecting");
        	return false;
        }
	}
	private static void automatedInviteCodeTest() {
		String key = "invite";
		// create new list entry for each test for tracking
		numPassed.put(key,0);
		numFailed.put(key,0);
		
		// connect to the database
		startDatabase();
		System.out.println("Beginning Invite Tests...");
		
		System.out.println("Creating invite code...");
		String code = databaseHelper.generateInvitationCode();
		System.out.println("Received code " + code);
		
		System.out.println("Validating code...");
		boolean result = databaseHelper.validateInvitationCode(code);
		if(result) {
			System.out.println("Code was valid! Thus a pass");
			addResult(true, key);
		}
		else {
			System.out.println("Code was not valid! Thus a fail");
			addResult(false,key);
		}
		
		// check that the database does not accept non existing key
		System.out.println("Testing incorrect code \"testcodenowork\"");
		result = databaseHelper.validateInvitationCode("testcodenowork");
		if(!result) {
			System.out.println("Code was not valid! Thus a pass");
			addResult(true, key);
		}
		else {
			System.out.println("Code was valid! Thus a fail");
			addResult(false,key);
		}
		
		System.out.println("Invite Code Tests:\nPassed: " + numPassed.get(key) + "\nFailed: " + numFailed.get(key));

		
	}
	private static void automatedUsernameTest() {
		
		setUsernameTest();
		
		String key = "username";
		
		System.out.println("Beginning Username tests...");

		// create a username test class to keep things clean
		UsernameTestClass us_tst = new UsernameTestClass();
		
		// create new list entry for each test for tracking
		numPassed.put(key,0);
		numFailed.put(key,0);
		
		// Go through the list of existing tests
		for(Map.Entry<String, Boolean> entry : usernameTests.entrySet()) {
			addResult(us_tst.performTestCase(1, entry.getKey(), entry.getValue()), key);
		}
		
		System.out.println("Password Tests:\nPassed: " + numPassed.get(key) + "\nFailed: " + numFailed.get(key));
	}
	private static void automatedPasswordTest() {
	
		System.out.println("Beginning Password tests...");
		
		// set the tests for the passwords
		setPasswordTest();

		// create a password test class to keep things clean
		PasswordTestClass ps_tst = new PasswordTestClass();

		String key = "password";
		// create new list entry for each test for tracking
		numPassed.put(key,0);
		numFailed.put(key,0);
		
		// Go through the list of existing tests
		for(Map.Entry<String, Boolean> entry : passwordTests.entrySet()) {
			addResult(ps_tst.performTestCase(1, entry.getKey(), entry.getValue()), key);
		}

		
		System.out.println("Password Tests:\nPassed: " + numPassed.get(key) + "\nFailed: " + numFailed.get(key));
	}
		
	private static void automatedUserTests() {
			
			String key = "user";
			// create new list entry for each test for tracking
			numPassed.put(key,0);
			numFailed.put(key,0);
			
			System.out.println("Starting user tests...");
			
			
			// test to make sure connection to database exists
			System.out.println("Starting connection test...");
			boolean result = startDatabase();
			
			if(result) {
				System.out.println("Connected to database. Pass.");
				addResult(true,key);
			}
			else {
				System.out.println("Not connected to database. Fail.");
				addResult(false,key);
			}
			
			User user = createUser();
			
			// test to register a user
			System.out.println("Beginning register test...");
			try {
				databaseHelper.register(user);
			}
			catch(SQLException e) {
				System.out.println("SQL error while trying to register");
			}
			
			// if user exists the register works
			result = databaseHelper.doesUserExist(user.userName);
			if(result) {
				System.out.println("User successfully registered. Pass.");
				addResult(true,key);
			}
			else {
				System.out.println("User not registerd. Fail.");
				addResult(false, key);
			}
			System.out.println();
			
			
			// test to see that the user that just register can log in
			System.out.println("Beginning login test...");
			try {
			 result = databaseHelper.login(user);
			}
			catch(SQLException e) {
				System.out.println("SQL error while trying to login");
			}
			if(result) {
				System.out.println("User is able to login. Thus a pass");
				addResult(true,key);
			}
			else{
				System.out.println("User is unable to login. Thus a fail");
				addResult(false,key);
			}
			System.out.println();
			System.out.println("Starting multiuser tests..");
			
			
			User user2 = new User("first2", "pass", "first", "last", "rg@m", "user");
	
			try {
				databaseHelper.register(user2);
			}
			catch(SQLException e) {
				System.out.println("SQL error while trying to register");
			}
			
			// test having multiple users and getting all registered users
			ArrayList<String> users = databaseHelper.getAllUsernames();
			if(users.contains("first") && users.contains("first2")) {
				System.out.println("Both users listed. Pass");
				addResult(true,key);
			}
			else {
				System.out.println("Both users not listed. Fail");
				addResult(false,key);
			}
			
			System.out.println();
			System.out.println("Starting role editing tests...");
			
			// test adding roles
			user.addRole("admin");
			ArrayList<String> roles = user.getAllRoles();
			if(roles.contains("user")&& roles.contains("admin") && roles.size() == 2) {
				System.out.println("Role added. Pass.");
				addResult(true,key);
			}
			else {
				System.out.println("Role not added. Fail.");
				addResult(false,key);
			}
			
			System.out.println();
			System.out.println("Starting delete user test...");
			
			// test delete user
			databaseHelper.deleteUser(user.userName);
			if(databaseHelper.doesUserExist(user.userName)) {
				System.out.println("User not deleted. Fail");
				addResult(false, key);
			}
			else {
				System.out.println("User deleted. Pass");
				addResult(true,key);
			}
			// can the user be assigned more roles?
			
			// test to disconnect from the database
			try {
				databaseHelper.closeConnection();
				System.out.println("Connection closed. Pass");
				addResult(true, key);
			}
			catch(Exception e){
				System.out.println("Error closing connection. Fail");
				addResult(false, key);
			}
			
			System.out.println("User Tests:\nPassed: " + numPassed.get(key) + "\nFailed: " + numFailed.get(key));
	
		}
	private static void setPasswordTest() {
		// store input string with the expected pass or fail
		// positive password tests
		
		// test all together
		passwordTests.put("TestPassword22+", true);
		passwordTests.put("Test2!+Password", true);

		// negative password tests
		
		// test all together
		passwordTests.put("!", false);
		
		// test length requirement
		passwordTests.put("Aa223!", false);
		
		// test uppercase
		passwordTests.put("a-24", false);
		
		// test lowercase
		passwordTests.put("T2222333", false);
		
		// test number and special characters
		passwordTests.put("Abcdefghi", false);
		
		// test no password
		passwordTests.put("", false);
		
	}
	private static void setUsernameTest() {
		// Positive tests
		// Test general ability
		usernameTests.put("Abcdet3920_@=", true);
		
		// Negative tests
		// Test less than 6 characters
		usernameTests.put("abc22", false);
		usernameTests.put("", false);
	}
}
