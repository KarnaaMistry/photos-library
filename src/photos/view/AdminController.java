package photos.view;

import photos.app.*;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
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



public class AdminController {

	@FXML Button logout_button;
	@FXML Button quit_button;
	@FXML Button delete_user;
	@FXML Button create_user;
	@FXML ListView<String> userList;
	@FXML Text error_create;
	@FXML Text error_delete;
	@FXML TextField new_username;
	
	public void initialize() {
		
		refreshUserList();
	}
	
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
	
	@FXML void quit(ActionEvent event) {
		//Photos.saveAdmin();
		Stage stage = (Stage) quit_button.getScene().getWindow();
		stage.close();
	}
	
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
		Photos.admin.delUser(userList.getSelectionModel().getSelectedItem());
		refreshUserList();
	}
	
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
	
	void refreshUserList() {
		error_create.setText("");
		error_delete.setText("");
		userList.getItems().clear();
		for (User u : Photos.admin.getUserlist()) {
			userList.getItems().add(u.getUsername());
		}
		FXCollections.sort(userList.getItems());
	}


}
