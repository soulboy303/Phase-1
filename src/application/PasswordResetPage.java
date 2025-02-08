package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;

import databasePart1.*;

public class PasswordResetPage{
	
	private final DatabaseHelper databaseHelper;
    // DatabaseHelper to handle database operations.
    public PasswordResetPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
    
    public void show(Stage primaryStage, User user) {
    	
    	TextField passwordField = new TextField();
    	passwordField.setPromptText("Enter new password");
    	passwordField.setMaxWidth(250);
        
        TextField confirmField = new TextField();
        confirmField.setPromptText("Confirm new password");
        confirmField.setMaxWidth(250);
        
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        
        
        Button NewPasswordButton = new Button("Reset Password"); //Password reset button
        
        NewPasswordButton.setOnAction(a -> {
        	String password = passwordField.getText(); //Gets information from fields
        	String confirm = confirmField.getText();
        	String passwordError = PasswordEvaluator.evaluatePassword(password); //checks to see if password is valid
        	
        	int result = password.compareTo(confirm);
        	
        	if( passwordError == "") {
        		if(result == 0) { //If the passwords in the password and confirm fields are the same, then it sets that as the new password and takes them back to login
        			databaseHelper.clearTemp(password, user);
        			new UserLoginPage(databaseHelper).show(primaryStage); 
        			errorLabel.setText("It should have worked");
        		}
        		
        		else if (result != 0){
        			errorLabel.setText("The password and confirm fields do not match"); //Error if their not equal
        		}
        		else {
        			errorLabel.setText("Error in setting up new password"); //In case something else goes wrong.
        			
        		}
        	}
        	else {
        		
        		errorLabel.setText(passwordError); //Displays password error
        		
        	}
        	
        	 });
        
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.getChildren().addAll(passwordField, confirmField, NewPasswordButton, errorLabel);

        primaryStage.setScene(new Scene(layout, 800, 400));
        primaryStage.setTitle("New Password");
        primaryStage.show();
    }
}