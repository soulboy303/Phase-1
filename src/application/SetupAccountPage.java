package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;

import databasePart1.*;

/**
 * SetupAccountPage class handles the account setup process for new users.
 * Users provide their userName, password, and a valid invitation code to register.
 */
public class SetupAccountPage {
	
    private final DatabaseHelper databaseHelper;
    // DatabaseHelper to handle database operations.
    public SetupAccountPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    /**
     * Displays the Setup Account page in the provided stage.
     * @param primaryStage The primary stage where the scene will be displayed.
     */
    public void show(Stage primaryStage) {
    	// Input fields for userName, password, and invitation code
        TextField userNameField = new TextField();
        userNameField.setPromptText("Enter userName");
        userNameField.setMaxWidth(250);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");
        passwordField.setMaxWidth(250);
        
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("Enter First Name");
        firstNameField.setMaxWidth(250);
        
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Enter Last Name");
        lastNameField.setMaxWidth(250);
        
        TextField emailField = new TextField();
        emailField.setPromptText("Enter Email Address");
        emailField.setMaxWidth(250);
        
        TextField inviteCodeField = new TextField();
        inviteCodeField.setPromptText("Enter InvitationCode");
        inviteCodeField.setMaxWidth(250);
        
        // Label to display error messages for invalid input or registration issues
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        

        Button setupButton = new Button("Setup");
        
        setupButton.setOnAction(a -> {
        	// Retrieve user input
            String userName = userNameField.getText();
            String password = passwordField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String emailAddress = emailField.getText();
            String code = inviteCodeField.getText();
            
            // Calls user name and password evaluators to see if they give any error messages
            String usernameError = UserNameRecognizer.checkForValidUserName(userName);
            String passwordError = PasswordEvaluator.evaluatePassword(password);
            boolean emailValid = EmailValidator.isEmailValid(emailAddress);
            
            try {
            	// Checks to see if user name is valid
            	if(usernameError == "") {
            	
            		// Checks to see if password is valid
            		if(passwordError == "") {
            			
            			if(emailValid) {
            			
            			// Check if the user already exists
            			if(!databaseHelper.doesUserExist(userName)) {
            		
            				// Validate the invitation code
            				if(databaseHelper.validateInvitationCode(code)) {
            			
            					// Create a new user and register them in the database
            					User user=new User(userName, password, firstName, lastName, emailAddress, "user", false);
            					databaseHelper.register(user);
		                
            					// Navigate to the Welcome Login Page
            					new WelcomeLoginPage(databaseHelper).show(primaryStage,user);
            				}
            				else {
            					errorLabel.setText("Please enter a valid invitation code");
            				}
            			}
            			else {
            				errorLabel.setText("This useruserName is taken!!.. Please use another to setup an account");
            			}
            			}
            			
            			else {
            				errorLabel.setText("This email is invalid");
            			}
            	
            		}
            		else {
            			errorLabel.setText(passwordError);
            		}
            	}
            	else { 
            		errorLabel.setText(usernameError);
            	}
            	
            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
                e.printStackTrace();
            }
        });

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.getChildren().addAll(userNameField, passwordField, firstNameField, lastNameField, emailField, inviteCodeField, setupButton, errorLabel);

        primaryStage.setScene(new Scene(layout, 800, 400));
        primaryStage.setTitle("Account Setup");
        primaryStage.show();
    }
}
