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
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;


public class PhotosController {
	
	@FXML Button testjump_tophotos;
	@FXML Button testjump_tologin;
	@FXML Button testjump_quit;
	
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
