package photos.app;

import java.util.List;
import java.util.ArrayList;

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
		
		//test code
		
		Photo myPhoto = new Photo("I took this in new brunswick lol");
		
		if (myPhoto.addTag("location", "New Brunswick")) { System.out.println("1 Success"); }
		if (!myPhoto.addTag("location", "New York")) { System.out.println("2 Failure"); }
		if (myPhoto.addTag("person", "Karnaa")) { System.out.println("3 Success"); }
		if (myPhoto.addTag("person", "Fin")) { System.out.println("4 Success"); }
		
		List<Tag> taglist = myPhoto.getTags();
		
		for (Tag t : taglist) {
			System.out.println(t.getTagname() + ", " + t.getTagvalue());
		}
		
		System.out.println("``");
		
		for (String str : Tag.tagTypes) {
			System.out.println(str);
		}
		
		launch(args);

	}

}
