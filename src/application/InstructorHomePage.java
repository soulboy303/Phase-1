package application;

import databasePart1.DatabaseHelper;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InstructorHomePage {
	
	public final DatabaseHelper databaseHelper;

    public InstructorHomePage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
    
    public void show(Stage primaryStage, User user) {
    	
    	
    	VBox layout = new VBox();
    	
	    layout.setStyle("-fx-alignment: center; -fx-padding: 20;");
    	Scene instructorScene = new Scene(layout, 800, 400);
    	
    	primaryStage.setScene(instructorScene);
	    primaryStage.setTitle("Instructor Page");
    }
}