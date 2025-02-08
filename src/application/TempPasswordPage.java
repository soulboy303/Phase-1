package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;

import databasePart1.*;

public class TempPasswordPage{
	
	private final DatabaseHelper databaseHelper;
    // DatabaseHelper to handle database operations.
    public TempPasswordPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
    
    public void show(Stage primaryStage, User user) {
    	
    	TextField createUserNameField = new TextField();
        createUserNameField.setPromptText("Enter userName");
        createUserNameField.setMaxWidth(250);
        
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        
        Label tempLabel = new Label();
        tempLabel.setStyle("-fx-font-size: 14px; -fx-font-style: italic;");
        
        Button TempPassButton = new Button("Create Temporary Password");
        Button backButton = new Button("Back");
        
        TempPassButton.setOnAction(a -> {
        	String userToMake = createUserNameField.getText();
        		
        		if(databaseHelper.doesUserExist(userToMake)) {
        			String tempPassword = databaseHelper.generateTempPassword(userToMake);
        			tempLabel.setText(tempPassword);
        		}
        		
        		else {
        			errorLabel.setText("User Does Not Exist: Enter Another Username to Create Password for.");
        		}
        	
        	 });
        
        backButton.setOnAction(a -> {
        	new AdminHomePage(databaseHelper).show(primaryStage, user);
        });
        
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.getChildren().addAll(createUserNameField, TempPassButton, tempLabel, backButton, errorLabel);

        primaryStage.setScene(new Scene(layout, 800, 400));
        primaryStage.setTitle("Create Temp Password");
        primaryStage.show();
    }
}