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
        
        
        Button NewPasswordButton = new Button("Create Temporary Password");
        
        NewPasswordButton.setOnAction(a -> {
        	String password = passwordField.getText();
        	String confirm = confirmField.getText();
        	String passwordError = PasswordEvaluator.evaluatePassword(password);
        	
        	if( passwordError != "") {
        		if(password.equals(confirm)) {
        			databaseHelper.clearTemp(password, user);
        			new UserLoginPage(databaseHelper).show(primaryStage);
        		}
        		
        		else if (!password.equals(confirm)){
        			errorLabel.setText("The password and confirm fields do not match");
        		}
        		else {
        			errorLabel.setText("Error in setting up new password");
        			
        		}
        	}
        	else {
        		
        		errorLabel.setText(passwordError);
        		
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