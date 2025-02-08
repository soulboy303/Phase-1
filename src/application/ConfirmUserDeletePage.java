package application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

import databasePart1.DatabaseHelper;
import javafx.application.Platform;

public class ConfirmUserDeletePage {

private final DatabaseHelper databaseHelper;
private final String userName;

    public ConfirmUserDeletePage(String userName, DatabaseHelper databaseHelper) {
this.databaseHelper = databaseHelper;
this.userName = userName;
    }
    
    public void show(Stage primaryStage, User user) {
    
    // Buttons and layout boxes for text and buttons
Button yesButton = new Button("Yes");
Button noButton = new Button("No");
HBox buttonLayout = new HBox(20);
VBox textLayout = new VBox(20);
// Setting the "Are you sure" text box and font
Label confirmText = new Label("Are You sure you want to delete "+ userName +"?");
textLayout.setStyle("-fx-alignment: center; -fx-padding: 20;");
    confirmText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
    textLayout.getChildren().add(confirmText);
// Adding both buttons and the position of the buttons to the button layout
    buttonLayout.setStyle("-fx-alignment: center; -fx-padding: 20;");
    buttonLayout.getChildren().addAll(yesButton, noButton);
    
    // Adding the button layout to the text layout
    textLayout.getChildren().add(buttonLayout);
    
    // When the "Yes" button is pressed
    yesButton.setOnAction(a -> {
    
    databaseHelper.deleteUser(userName); // Delete user with matching string username
    new ManageUsersPage(databaseHelper).show(primaryStage,user); // Go back to manage users page
    
    });
    
    // When the "No" button is pressed
    noButton.setOnAction(a -> {
    
    new ManageUsersPage(databaseHelper).show(primaryStage,user); // Go back to manage users page
    
    });
    
Scene manageUsersScene = new Scene(textLayout, 800, 400);

primaryStage.setScene(manageUsersScene);
    primaryStage.setTitle("Manage Users");
    
    }

}