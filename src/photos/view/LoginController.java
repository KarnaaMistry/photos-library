package photos.view;

import photos.app.*;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class LoginController {
	
	@FXML Button login_button;
	@FXML Button quit_button;
	@FXML TextField login_username;
	@FXML Text login_error;
	
	public void initialize() {
		login_error.setText("");
	}
	
	@FXML void login(ActionEvent event) throws IOException {
		String name = login_username.getText();
		String loginpath;
		String title;
		User v = null;
		if (name.equals("admin")) {
			loginpath = "/photos/view/Admin.fxml";
			title = "Admin";
		} else {
			for (User u : Photos.admin.getUserlist()) {
				if (u.getUsername().equals(name)) { v = u; break; }
			} 
			if (v == null) { 
				login_error.setText("error: username does not exist");
				return; 
			}
			loginpath = "/photos/view/Photos.fxml";
			title = "Photos";
		}
		Parent p = null;
	    PhotosController.currUser = v;
        try {
			p = FXMLLoader.load(getClass().getResource(loginpath));
		} catch (IOException e) {
			e.printStackTrace();
		}
        Scene sc = new Scene(p);
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainStage.setScene(sc);
        mainStage.setTitle(title);
        mainStage.show();
	}
	
	@FXML void quit(ActionEvent event) {
		 Stage stage = (Stage) quit_button.getScene().getWindow();
		 stage.close();
	}
	
	public void start(Stage mainStage) { 
		
		
		
	}


}
