package photos.app;

import java.util.List;
import java.util.ArrayList;
import java.io.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import photos.view.LoginController;

public class Photos extends Application {
	
	public static Admin admin;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();   
		
		loader.setLocation(
				getClass().getResource("/photos/view/Login.fxml"));
		AnchorPane root = (AnchorPane)loader.load();

		LoginController home = 
				loader.getController();
		home.start(primaryStage);

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Photos Login");
		primaryStage.setResizable(false);
		primaryStage.show(); 

	}
	
	public static Admin loadAdmin() {
		List<String> dataFiles = new ArrayList<String>();
		File folder = new File("data");
		File[] list = null;
		list = folder.listFiles();
		if (list != null) {
			for (File file : list) {
			    if (file.isFile()) {
			        dataFiles.add(file.getName());
			    }
			}
		}

		admin = new Admin();
		for (String str : dataFiles) {
			User u = null;
			try {
				u = User.readUser(str);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
			admin.loadUser(u);
		}
		
		return admin;
	}
	
	public static void saveAdmin() {
		List<User> users = admin.getUserlist();
		List<String> usernames = new ArrayList<String>();
		for (User u : users) {
			usernames.add(u.getUsername());
		}
		File folder = new File("data");
		File[] list = null;
		list = folder.listFiles();
		
		if (list != null) {
			for (File file : list) {
				if (!usernames.contains(file.getName().substring(0, file.getName().length()-4))) {
					file.delete();
				}
			}
		}
		for (User u : users) {
			try {
				User.writeUser(u);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * The main method that handles the Photos application.
	 * @param args		command line arguments
	 */
	public static void main(String[] args) {
		
		admin = loadAdmin();
	

		
		launch(args);
		
		
		saveAdmin();
		

	}

}
