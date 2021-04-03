package photos.view;

import java.io.IOException;
import java.util.Optional;

import com.sun.javafx.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;


public class PhotosController {
	
	@FXML Button testjump_tophotos;
	@FXML Button testjump_tologin;
	@FXML Button testjump_quit;
	@FXML TextField login_username;
	
	@FXML void toPhotos(ActionEvent event) {
		Parent p = null;
        try {
			p = FXMLLoader.load(getClass().getResource("/photos/view/Photos.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        Scene sc = new Scene(p);
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainStage.setScene(sc);
        mainStage.show();
	}
	
	@FXML void login(ActionEvent event) {
		Parent p = null;
		String loginpath;
		/*
		 * TO DO:
		 * - ADMIN CLASS: CHECK IF USERNAME EXISTS IN ADMIN'S LIST OF USERS
		 * - if not, THEN ERROR MESSAGE.
		 */
		if (login_username.getText().equals("admin")) {
			loginpath = "/photos/view/Admin.fxml";
		} else {
			loginpath = "/photos/view/Photos.fxml";
		}
		
        try {
			p = FXMLLoader.load(getClass().getResource(loginpath));
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        Scene sc = new Scene(p);
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainStage.setScene(sc);
        mainStage.show();
	}
	
	@FXML void toLogin(ActionEvent event) {
		Parent p = null;
        try {
			p = FXMLLoader.load(getClass().getResource("/photos/view/Login.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        Scene sc = new Scene(p);
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainStage.setScene(sc);
        mainStage.show();
		
	}
	
	@FXML void quit(ActionEvent event) {
		 Stage stage = (Stage) testjump_quit.getScene().getWindow();
		 stage.close();
	}
	
	public void start(Stage mainStage) { 
		
		
		
	}


}
