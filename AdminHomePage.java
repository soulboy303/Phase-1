package application;

import java.sql.SQLException;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * AdminPage class represents the user interface for the admin user.
 * This page displays a simple welcome message for the admin.
 */

public class AdminHomePage {
	
	
	/**
     * Displays the admin page in the provided primary stage.
     * @param primaryStage The primary stage where the scene will be displayed.
     */
    public void show(Stage primaryStage) {
    	VBox layout = new VBox();
    	
	    layout.setStyle("-fx-alignment: center; -fx-padding: 20;");
	    
	    // label to display the welcome message for the admin
	    Label adminLabel = new Label("Hello, Admin!");
	    Label passwordResetLabel = new Label("Password Reset:");

	 // Input field for the user's userName, password
        TextField userNameField = new TextField();
        userNameField.setPromptText("Enter User Name");
        userNameField.setMaxWidth(250);

        PasswordField tempPasswordField = new PasswordField();
        tempPasswordField.setPromptText("Enter Temporary Password");
        tempPasswordField.setMaxWidth(250);
        
        Button resetPasswordButton = new Button("Reset Password");
        Label userPasswordLabel = new Label("");
        Label sqlLabel = new Label("");

	    adminLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
	    
	    resetPasswordButton.setOnAction(a -> {
        	// Retrieve user inputs
            String userName = userNameField.getText();
            String password = tempPasswordField.getText();
            
         
            // Display temporary password
            userPasswordLabel.setText("userName = " + userName + ", password = " + password); 
            
            // save temporary password to user table
            String tableName = "cse360users";
            String sql = "UPDATE " + tableName + 
                         " SET password = '" + password + "'" +
                         " WHERE userName = '" + userName + "';";
            sqlLabel.setText(sql); 

    		// need to figure out how to use this command: statement.execute(sql);

        	
            // need to figure out how to mark the password as temporary so that user can only use it once 
            // for example we could add
            // 1. set a class variable called is_temp_password and have in login function check it 
            // 2. a field in the user table called is_temp_password which is true or false
           
        });
	    
	    layout.getChildren().addAll(adminLabel, passwordResetLabel, userNameField, tempPasswordField, resetPasswordButton, userPasswordLabel, sqlLabel);
	    Scene adminScene = new Scene(layout, 800, 400);

	    // Set the scene to primary stage
	    primaryStage.setScene(adminScene);
	    primaryStage.setTitle("Admin Page");
	    
	    
	   
	    
	    
    }
}