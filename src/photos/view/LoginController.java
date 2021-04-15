package photos.view;

import photos.app.*;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * LoginController is the class which handles the javaFX functions and display for the login page.
 * 
 * @author Fin Herbig
 * @author Karnaa Mistry
 */
public class LoginController {
	
	@FXML Button login_button;
	@FXML Button quit_button;
	@FXML TextField login_username;
	@FXML Text login_error;
	
	/**
	 * Initializes the login page by adding all user data.
	 */
	public void initialize() {
		login_error.setText("");
		Photos.saveAdmin();
	}
	
	/**
	 * Brings the user or admin to their respective page.
	 * @param event			Action event representing the login button being pushed.
	 * @throws IOException	Thrown if login path is not present.
	 * @see User
	 */
	@FXML void login(ActionEvent event) throws IOException {

		login_username.setText(login_username.getText().trim());
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
	
	/**
	 * Quits the photos program
	 * @param event		Action event representing the quit button being pressed.
	 */
	@FXML void quit(ActionEvent event) {
		 Stage stage = (Stage) quit_button.getScene().getWindow();
		 stage.close();
	}
	
	/**
	 * Called from <code>Photos</code>, left as placeholder, initialization handled in <code>initialize()</code>.
	 * @param mainStage		primary JavaFX stage
	 */
	public void start(Stage mainStage) { 
		
		
		
	}


}
