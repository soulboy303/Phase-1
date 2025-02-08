package application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.application.Platform;
import java.util.ArrayList;
import databasePart1.*;

public class ManageUsersPage {

private final DatabaseHelper databaseHelper;
private ArrayList<String> allUsernames;

    public ManageUsersPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
        allUsernames = databaseHelper.getAllUsernames();
    }

public void show(Stage primaryStage, User user) {
// Buttons for admin functions
Button deleteButton = new Button("Delete a User");
Button setRoleButton = new Button("Set Roles");
Button tempPasswordButton = new Button("Create temporary password");
Button backButton = new Button("Back");
HBox adminFunctions = new HBox(20);
adminFunctions.setStyle("-fx-alignment: bottom-right; -fx-padding: 20;");
adminFunctions.getChildren().add(deleteButton);
adminFunctions.getChildren().add(setRoleButton);
adminFunctions.getChildren().add(tempPasswordButton);
adminFunctions.getChildren().add(backButton);


// Setting up the VBox for all users
        VBox userNames = new VBox(7); 
        userNames.setStyle("-fx-alignment: top-left; -fx-padding: 20;");
        
        // Display top left message in bold to admin
        Label usernamesLabel = new Label("Information displayed below:  username, role, ***more info to be added here later***");
        usernamesLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        userNames.getChildren().add(usernamesLabel);
        
        // The main layout of the page that will be displayed
        HBox mainLayout = new HBox(15);
        mainLayout.setStyle("-fx-alignment: top-left; -fx-padding: 20;");

        // Display all users and their information
        for (int i = 0; i < allUsernames.size(); ++i) {
            // Create a VBox for each user to hold the username and role
            HBox userHBox = new HBox(20);
            
            // Display username
            Label usersLabel = new Label(allUsernames.get(i));
            usersLabel.setStyle("-fx-font-size: 16px;");
            usersLabel.setMinWidth(100);
            
            // Display user's role
            Label rolesLabel = new Label(databaseHelper.getUserRole(allUsernames.get(i)));
            rolesLabel.setStyle("-fx-font-size: 16px;");
            rolesLabel.setMinWidth(100);
            
            // Display user's first name
            Label firstNameLabel = new Label(databaseHelper.getUserFirstName(allUsernames.get(i)));
            firstNameLabel.setStyle("-fx-font-size: 16px;");
            firstNameLabel.setMinWidth(75);
            
            // Display user's last name
            Label lastNameLabel = new Label(databaseHelper.getUserLastName(allUsernames.get(i)));
            lastNameLabel.setStyle("-fx-font-size: 16px;");
            lastNameLabel.setMinWidth(75);
            // Display user's email
            Label emailLabel = new Label(databaseHelper.getUserEmail(allUsernames.get(i)));
            emailLabel.setStyle("-fx-font-size: 16px;");
            emailLabel.setMinWidth(100);
            
            // Add username and role to the HBox
            userHBox.getChildren().addAll(usersLabel, rolesLabel, firstNameLabel, lastNameLabel, emailLabel);            
            // Add the userHBox to the userNames HBox
            userNames.getChildren().addAll(userHBox);
            
        }
        
        // Adding all the user info to the admin function buttons
        mainLayout.getChildren().addAll(userNames, adminFunctions);
        
        
        deleteButton.setOnAction(a -> {
        
        new InputTypeUserNamePage(databaseHelper).show(primaryStage,user);
        });
        deleteButton.setMinWidth(100); //Button for deleting users
        setRoleButton.setOnAction(a -> {
            new SetRolePage(databaseHelper).show(primaryStage,user);
         });
        setRoleButton.setMinWidth(100); //Button for adding/removing roles
        tempPasswordButton.setOnAction(a -> {
	    	
	    	new TempPasswordPage(databaseHelper).show(primaryStage,user);
	    });
        tempPasswordButton.setMinWidth(200); //Button for generating temporary passwords
        
        backButton.setOnAction(a -> { //Button for returning to the home page
            new AdminHomePage(databaseHelper).show(primaryStage, user);
            });

// Set scene to Manage Users page GUI
Scene manageUsersScene = new Scene(mainLayout, 800, 400);

primaryStage.setScene(manageUsersScene);
    primaryStage.setTitle("Manage Users");

}

}