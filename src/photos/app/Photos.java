package photos.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import photos.view.PhotosController;

public class Photos extends Application {
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();   
		
		loader.setLocation(
				getClass().getResource("/photos/view/Login.fxml"));
		AnchorPane root = (AnchorPane)loader.load();

		PhotosController photosController = 
				loader.getController();
		photosController.start(primaryStage);

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Photos Login");
		primaryStage.setResizable(false);
		primaryStage.show(); 

	}

	public static void main(String[] args) {
		
		//Code for testing the admin / user / login environment
		
		if (Admin.addUser("karnaa123"))
			System.out.println("success");
		else
			System.out.println("failure");
		
		if (Admin.addUser("fin123"))
			System.out.println("success");
		else
			System.out.println("failure");
		
		if (Admin.addUser("karnaa123"))
			System.out.println("success");
		else
			System.out.println("failure");
		
		launch(args);

	}

}
