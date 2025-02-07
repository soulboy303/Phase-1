package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;

import databasePart1.*;

public class SetRolePage{
	
	private final DatabaseHelper databaseHelper;
    // DatabaseHelper to handle database operations.
    public SetRolePage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
    
    public void show(Stage primaryStage, User user) {
    	
    	TextField userNameField = new TextField();
    	userNameField.setPromptText("Enter username to change role ");
    	userNameField.setMaxWidth(250);
    	
    	TextField roleField = new TextField();
    	roleField.setPromptText("Enter role");
    	roleField.setMaxWidth(250);
        
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        
        Button setRoleButton = new Button("Set Role ");
        Button backButton = new Button("Back");
        
        setRoleButton.setOnAction(a -> {
          String userName = userNameField.getText();
          String role = roleField.getText();
      	System.out.println("role = " + user.getRole(0));  	

          if (user.getRole(0).toString() == "admin") {
        	System.out.println("Not allowed to change role for an admin"); // need to check for user == admin 
          } else {
        	if(databaseHelper.doesUserExist(userName)) {
        	  user.setUserName(userName);
        	  user.setRole(0, role);
        	     
        	  try {
				 databaseHelper.setRole(user);
			  } catch (SQLException e) {
						// TODO Auto-generated catch block
				e.printStackTrace();
			  }        		
            } else {
        			errorLabel.setText("User Does Not Exist: Enter Another Username to Delete");
            }
          }
        });
        
        backButton.setOnAction(a -> {
        	new ManageUsersPage(databaseHelper).show(primaryStage, user);
        });
        
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.getChildren().addAll(userNameField, roleField, setRoleButton, backButton, errorLabel);

        primaryStage.setScene(new Scene(layout, 800, 400));
        primaryStage.setTitle("User Delete");
        primaryStage.show();
    }
}