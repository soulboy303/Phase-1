package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;

import databasePart1.*;

public class InputTypeUserNamePage{
	
	private final DatabaseHelper databaseHelper;
    // DatabaseHelper to handle database operations.
    public InputTypeUserNamePage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
    
    public void show(Stage primaryStage, User user) {
    	
    	TextField deleteUserNameField = new TextField();
        deleteUserNameField.setPromptText("Enter userName to delete");
        deleteUserNameField.setMaxWidth(250);
        
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        
        Button deleteUserButton = new Button("Delete User");
        Button backButton = new Button("Back");
        
        deleteUserButton.setOnAction(a -> {
        	String userToDelete = deleteUserNameField.getText();
        		
        		if(databaseHelper.doesUserExist(userToDelete)) {
        			new ConfirmUserDeletePage(userToDelete, databaseHelper).show(primaryStage, user);
        		}
        		
        		else {
        			errorLabel.setText("User Does Not Exist: Enter Another Username to Delete");
        		}
        	
        	 });
        
        backButton.setOnAction(a -> {
        	new ManageUsersPage(databaseHelper).show(primaryStage, user);
        });
        
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.getChildren().addAll(deleteUserNameField, deleteUserButton, backButton, errorLabel);

        primaryStage.setScene(new Scene(layout, 800, 400));
        primaryStage.setTitle("User Delete");
        primaryStage.show();
    }
}