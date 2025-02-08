package application;

import databasePart1.DatabaseHelper;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This page displays a simple welcome message for the user.
 */

public class UserHomePage {
	
	private final DatabaseHelper databaseHelper;
	
	public UserHomePage(DatabaseHelper databaseHelper) {
		this.databaseHelper = databaseHelper;
		
	}

    public void show(Stage primaryStage) {
    	VBox layout = new VBox();
	    layout.setStyle("-fx-alignment: center; -fx-padding: 20;");
	    
	    // Label to display Hello user
	    Label userLabel = new Label("Hello, User!");
	    userLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
	    Button logOutButton = new Button("Log Out");
	    
	    
	    layout.getChildren().add(userLabel);
	    layout.getChildren().add(logOutButton);
	    
logOutButton.setOnAction(a -> { //Logs user out of account
    		
			new UserLoginPage(databaseHelper).show(primaryStage);
		});
	    
	    
	    Scene userScene = new Scene(layout, 800, 400);

	    // Set the scene to primary stage
	    primaryStage.setScene(userScene);
	    primaryStage.setTitle("User Page");
    	
    }
}