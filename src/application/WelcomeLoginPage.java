package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Platform;
import databasePart1.*;
import java.util.ArrayList;
/**
 * The WelcomeLoginPage class displays a welcome screen for authenticated users.
 * It allows users to navigate to their respective pages based on their role or quit the application.
 */
public class WelcomeLoginPage {
	
	private final DatabaseHelper databaseHelper;

    public WelcomeLoginPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
    public void show( Stage primaryStage, User user) {
    	
    	VBox layout = new VBox(5);
	    layout.setStyle("-fx-alignment: center; -fx-padding: 20;");
	    
	    Label welcomeLabel = new Label("Welcome!!");
	    welcomeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
	    
		layout.getChildren().add(welcomeLabel);
	    // Buttons to navigate to the user's respective page based on their role

		////FIX ME//////
		/// 
		// if user only has zero roles error out
		// should not happen but who knows
		if(user.getAllRoles().size() == 0){
			//error out
		}
		// if user is only one role upon entering check and then display the homepage for the role
		// code for using continue button just commented out
		//else if(user.getAllRoles().size() == 1){
		//Edit once new HomePages are required
			 Button continueButton = new Button("Continue to your Page");
			continueButton.setOnAction(a -> {
				String role = databaseHelper.getUserRole(user.getUserName());
			 	System.out.println(role);
				
				if(role.contains("admin")) {
					new AdminHomePage(databaseHelper).show(primaryStage, user);
				}
				else if(role.contains("user")) {
					new UserHomePage(databaseHelper).show(primaryStage);
				}
			/*	else if(role.equals("student")){
					new StudentHomePage().show(primaryStage);
				}
				else if(role.equals("instructor")){
					new InstructorHomePage().show(primaryStage);
				}
				else if(role.equals("staff")){
					new StaffHomePage().show(primaryStage);
				}
				else if(role.equals("reviewer")){
					new ReviewerHomePage().show(primaryStage); 
				} */
			 });
			 layout.getChildren().add(continueButton);
		//}
		// if more than one role, check all possible roles and create buttons for each that user has
		// buttons take user to role home
		/*else if(user.getAllRoles().size() > 1){
			if(user.getAllRoles().contains("admin")){
				Button adminButton = new Button("Admin");
				adminButton.setOnAction(a -> {
					new AdminHomePage(databaseHelper).show(primaryStage, user);
				});
				layout.getChildren().add(adminButton);
			}
			if(user.getAllRoles().contains("user")){
				Button userButton = new Button("User");
				userButton.setOnAction(a -> {
					new UserHomePage().show(primaryStage);
				});
				layout.getChildren().add(userButton);
			}
			if(user.getAllRoles().contains("student")){
				Button studentButton = new Button("Student");
				studentButton.setOnAction(a -> {
					new StudentHomePage().show(primaryStage, user);
				});
				layout.getChildren().add(studentButton);
			}
			if(user.getAllRoles().contains("instructor")){
				Button instructorButton = new Button("Instructor");
				instructorButtonButton.setOnAction(a -> {
					new InstructorHomePage().show(primaryStage, user);
				});
				layout.getChildren().add(instructorButton);
			}
			if(user.getAllRoles().contains("staff")){
				Button staffButton = new Button("Staff");
				staffButton.setOnAction(a -> {
					new StaffHomePage().show(primaryStage, user);
				});
				layout.getChildren().add(staffButton);
			}
			if(user.getAllRoles().contains("reviewer")){
				Button reviewerButton = new Button("Reviewer");
				reviewerButton.setOnAction(a -> {
					new ReviewerHomePage().show(primaryStage, user);
				}); 
				layout.getChildren().add(reviewerButton);
			} 
		}*/
		

	    
	    // Button to quit the application
	    Button quitButton = new Button("Quit");
	    quitButton.setOnAction(a -> {
	    	databaseHelper.closeConnection();
	    	Platform.exit(); // Exit the JavaFX application
	    });
	    
		// put in adminhomepage since only admins can do it and only on the admins home page
	    // // "Invite" button for admin to generate invitation codes
	    // if ("admin".equals(user.getRole(0))) {
			// Button inviteButton = new Button("Invite");
			// inviteButton.setOnAction(a -> {
			// 	new InvitationPage().show(databaseHelper, primaryStage);
			// });
        // }

	    // layout.getChildren().addAll(welcomeLabel,quitButton);
		layout.getChildren().add(quitButton);
	    Scene welcomeScene = new Scene(layout, 800, 400);

	    // Set the scene to primary stage
	    primaryStage.setScene(welcomeScene);
	    primaryStage.setTitle("Welcome Page");
    }
}