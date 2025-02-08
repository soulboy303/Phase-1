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
        
        Button addRoleButton = new Button("Add Role ");
        Button removeRoleButton = new Button("Remove Role ");
        Button backButton = new Button("Back");
        
        addRoleButton.setOnAction(a -> {
            String userName = userNameField.getText();
            String role = roleField.getText();
        System.out.println("role = " + user.getRole(0));  

            if (databaseHelper.getUserRole(userName) == "admin") {
          System.out.println("Not allowed to change role for an admin"); // need to check for user == admin 
            } else {
          if(databaseHelper.doesUserExist(userName)) {      
          	
          		databaseHelper.addRole(userName, role);
          		new ManageUsersPage(databaseHelper).show(primaryStage, user); //Adds role to user
          		
          		
              } else {
          errorLabel.setText("User Does Not Exist: Enter Another Username to Delete");
              }
            }
          });
        
        removeRoleButton.setOnAction(a -> {
            String userName = userNameField.getText();
            String role = roleField.getText();
        System.out.println("role = " + user.getRole(0));  

            if (databaseHelper.getUserRole(userName) == "admin") {
          System.out.println("Not allowed to change role for an admin"); // need to check for user == admin 
            } else {
          if(databaseHelper.doesUserExist(userName)) {      
          	
          		String potenitalErr = databaseHelper.removeRole(userName, role); // Removes a role from user, take you back to manageuserpage is sucessful
          		if(potenitalErr != null) {
          			
          			errorLabel.setText(potenitalErr);
          		}
          		
          		else{new ManageUsersPage(databaseHelper).show(primaryStage, user);} 
          		
          		
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
        layout.getChildren().addAll(userNameField, roleField, addRoleButton, removeRoleButton, backButton, errorLabel);

        primaryStage.setScene(new Scene(layout, 800, 400));
        primaryStage.setTitle("User Delete");
        primaryStage.show();
    }
}