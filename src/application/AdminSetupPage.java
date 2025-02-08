package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;

import databasePart1.*;

/**
 * The SetupAdmin class handles the setup process for creating an administrator account.
 * This is intended to be used by the first user to initialize the system with admin credentials.
 */
public class AdminSetupPage {
	
    private final DatabaseHelper databaseHelper;

    public AdminSetupPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public void show(Stage primaryStage) {
    	// Input fields for userName and password
        TextField userNameField = new TextField();
        userNameField.setPromptText("Enter Admin userName");
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
        emailField.setPromptText("Enter Email");
        emailField.setMaxWidth(250);

        Button setupButton = new Button("Setup");
        
     // Label to display error messages for invalid input or registration issues
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        
        setupButton.setOnAction(a -> {
        	// Retrieve user input
            String userName = userNameField.getText();
            String password = passwordField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String emailAddress = emailField.getText();
            
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
            			
            			// Create a new User object with admin role and register in the database
            			User user=new User(userName, password, firstName, lastName, emailAddress, "admin", false);
            			databaseHelper.register(user);
            			System.out.println("Administrator setup completed.");
                
            			// Navigate to the Welcome Login Page
            			new UserLoginPage(databaseHelper).show(primaryStage);
            		}
            			else { errorLabel.setText("This email is invalid.");
            			}
            			}
            		else {
            			errorLabel.setText(passwordError);
            		}
            	}
            	else { 
            		errorLabel.setText(usernameError);
            	}
            } 
            catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
                e.printStackTrace();
            }
        });

        VBox layout = new VBox(10, userNameField, passwordField, firstNameField, lastNameField, emailField, setupButton, errorLabel);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        primaryStage.setScene(new Scene(layout, 800, 400));
        primaryStage.setTitle("Administrator Setup");
        primaryStage.show();
    }
}
