package photos.view;

import photos.app.*;
import java.io.IOException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * AdminController is the class which handles the javaFX functions and display for the admin page.
 * 
 * @author Fin Herbig
 * @author Karnaa Mistry
 */
public class AdminController {

	@FXML Button logout_button;
	@FXML Button quit_button;
	@FXML Button delete_user;
	@FXML Button create_user;
	@FXML ListView<String> userList;
	@FXML Text error_create;
	@FXML Text error_delete;
	@FXML TextField new_username;
	
	/**
	 * Initializes the user list from which the admin can modify users.
	 */
	public void initialize() {
		
		refreshUserList();
	}
	
	/**
	 * Returns the admin to the login screen.
	 * @param event		Action event representing the logout button being pressed.
	 * @see LoginController
	 */
	@FXML void logout(ActionEvent event) {
		Parent p = null;
        try {
			p = FXMLLoader.load(getClass().getResource("/photos/view/Login.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        Scene sc = new Scene(p);
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainStage.setScene(sc);
        mainStage.setTitle("Photos Login");
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
	 * Removes a selected user from the userList.
	 * @param event		Action event representing the remove button.
	 */
	@FXML void handleDelete(ActionEvent event) {
		error_create.setText("");
		if (userList.getItems().isEmpty()) {
			error_delete.setText("error: no users to delete");
			return;
		}
		if (userList.getSelectionModel().getSelectedIndex() < 0) {
			error_delete.setText("error: must have a user selected");
			return;
		}
		if (!confirm()) {
			return;
		}
		Photos.admin.delUser(userList.getSelectionModel().getSelectedItem());
		refreshUserList();
	}
	
	/**
	 * Creates a new user and adds them to the user list.
	 * @param event		Action event representing the add button.
	 * @see User
	 */
	@FXML void handleCreate(ActionEvent event) {
		error_delete.setText("");
		if (new_username.getText().length() < 1) {
			error_create.setText("error: must type a username");
			return;
		}
		if (new_username.getText().equals("admin")) {
			error_create.setText("error: that username already exists");
			return;
		}
		for (User u : Photos.admin.getUserlist()) {
			if (u.getUsername().equals(new_username.getText())) {
				error_create.setText("error: that username already exists");
				return;
			}
		}
		Photos.admin.addUser(new_username.getText());
		new_username.setText("");
		refreshUserList();
	}
	
	/**
	 * Refreshes the userList display with the current userList.
	 * @see User
	 */
	void refreshUserList() {
		error_create.setText("");
		error_delete.setText("");
		userList.getItems().clear();
		for (User u : Photos.admin.getUserlist()) {
			userList.getItems().add(u.getUsername());
		}
		FXCollections.sort(userList.getItems());
	}
	
	/**
	 * A popup confirmation for the deletion of a user.
	 * @return		A flag indicating if the button has functioned correctly.
	 */
	boolean confirm() {

		boolean flag = false;
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm");
		alert.setHeaderText("Are you sure you want to delete \"" + userList.getSelectionModel().getSelectedItem() + "\"?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
		    flag = true;
		}
		return flag;
		
	}


}
