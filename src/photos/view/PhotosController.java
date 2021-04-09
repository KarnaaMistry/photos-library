package photos.view;

import photos.app.*;

import java.io.*;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class PhotosController {
	
	public static User currUser;
	
	@FXML Button logout;
	@FXML Button quit;
	@FXML Text username_display;
	@FXML ImageView bigboi;
	
	public void initialize() throws FileNotFoundException {
		
		InputStream stream = new FileInputStream("data/stockphotos/en_croissant.png");
		Image image = new Image(stream);
		if (image != null) {
            double w = 0;
            double h = 0;

            double ratioX = bigboi.getFitWidth() / image.getWidth();
            double ratioY = bigboi.getFitHeight() / image.getHeight();

            double reducCoeff = 0;
            if(ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = image.getWidth() * reducCoeff;
            h = image.getHeight() * reducCoeff;

            bigboi.setX((bigboi.getFitWidth() - w) / 2);
            bigboi.setY((bigboi.getFitHeight() - h) / 2);

        }
		bigboi.setImage(image);
		
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
		 Stage stage = (Stage) quit.getScene().getWindow();
		 stage.close();
	}
	
	public void start(Stage mainStage) { 
		
		
		
	}


}
