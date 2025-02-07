package application;

import databasePart1.DatabaseHelper;
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
	public final DatabaseHelper databaseHelper;

    public AdminHomePage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
	
    public void show(Stage primaryStage, User user) {
    	
    	// taken from welcomeloginpage since it should be here on the admin page
    			// takes user to invite code generation screen
    			Button inviteButton = new Button("Invite");
    			inviteButton.setOnAction(a -> {
    				new InvitationPage().show(databaseHelper, primaryStage);
    			});
    	
    	// Button for entering the admin database
    	Button databaseButton = new Button("Manage Users");
    	Button tempPasswordButton = new Button("Create temporary password");
    	VBox layout = new VBox(-60, databaseButton, inviteButton);
    	
	    layout.setStyle("-fx-alignment: center; -fx-padding: 20;");
	    
	    // label to display the welcome message for the admin
	    // Label adminLabel = new Label("Hello, Admin!");
	    // adminLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

	    // layout.getChildren().add(adminLabel);
	    Scene adminScene = new Scene(layout, 800, 400);
	    
	    // When button is pressed show admin database GUI
	    databaseButton.setOnAction(a -> {
	    	
	    	new ManageUsersPage(databaseHelper).show(primaryStage,user);
	    });
	    
	        

	    // Set the scene to primary stage
	    primaryStage.setScene(adminScene);
	    primaryStage.setTitle("Admin Page");
    }
}