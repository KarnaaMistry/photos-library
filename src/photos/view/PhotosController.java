package photos.view;

import photos.app.*;

import java.io.*;
import java.util.Optional;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.ListCell;


/**
 * PhotosController is the class which handles the javaFX functions and display for the main photos page.
 * 
 * @author Fin Herbig
 * @author Karnaa Mistry
 */
public class PhotosController {
	
	/**
	 * Represents the user currently logged in.
	 * @see User
	 */
	public static User currUser;
	/**
	 * Represents the currently selected album.
	 * @see Album
	 */
	public static Album currAlbum;
	/**
	 * A <code>Map</code> hashing the filepath to the <code>Image</code>.
	 * @see Image
	 */
	public static Map<String, Image> userImages;
	/**
	 * A <code>boolean</code> indicating that the images list should display the search result.
	 */
	public static boolean searchResultMode;
	
	@FXML Button logout_but;
	@FXML Button quit_but;
	@FXML Text username_display;
	@FXML ImageView photo_display;
	@FXML ListView<String> albumListView;
	@FXML ListView<Photo> photoListView;
	@FXML ObservableList<Photo> photoList;
	@FXML Button deleteAlb_but;
	@FXML Button createAlb_but;
	@FXML Button renameAlb_but;
	@FXML TextField albumField;
	@FXML Text error_album;
	@FXML Text alb_numphot;
	@FXML Text alb_daterng;
	@FXML Button alb_clearname;
	@FXML Button addpho_but;
	@FXML Button rempho_but;
	@FXML Button nextpho_but;
	@FXML Button prevpho_but;
	@FXML TextArea applyCap;
	@FXML Button applyCap_but;
	@FXML Button clearCap_but;
	@FXML TextField toAlbum;
	@FXML Button clearToAlbum;
	@FXML Button copy_but;
	@FXML Button move_but;
	@FXML Text error_copymove;
	@FXML Text date_taken;
	@FXML ListView<Tag> tagListView;
	@FXML ObservableList<String> userTagTypes;
	@FXML Button addTag_but;
	@FXML Button delTag_but;
	@FXML ComboBox<String> tagTypesBox;
	@FXML TextField newTag_text;
	@FXML TextField newTagType_text;
	@FXML Button newTagType_but;
	@FXML Text tag_error;
	@FXML Text tagType_error;
	@FXML Button createAlbum_searchres;
	@FXML TextField createAlbum_searchres_name;
	@FXML DatePicker search_date1;
	@FXML DatePicker search_date2;
	@FXML TextField searchbytag;
	@FXML TextField searchbytag2;
	@FXML Button searchDate_but;
	@FXML Button searchTag_but;
	@FXML Text error_search;
	@FXML ToggleGroup search_radio;
	@FXML RadioButton search_or;
	@FXML RadioButton search_and;
	@FXML RadioButton search_single;
	@FXML ComboBox<String> tagTypesBox1;
	@FXML ComboBox<String> tagTypesBox2;
	@FXML Text error_photo;

	/**
	 * Updates <code>currAlbum</code> with the album selected by the user.
	 */
	void selectAlbum() {
		if (albumListView.getSelectionModel().getSelectedIndex() < 0) { photoListView.getItems().clear(); currAlbum = null; return; }
		String name = albumListView.getSelectionModel().getSelectedItem().substring(0,albumListView.getSelectionModel().getSelectedItem().indexOf('\n'));
		for (Album a : currUser.getAlbums()) {
			if (a.getName().equals(name)) {
				currAlbum = a;
				break;
			}
		}
		searchResultMode = false;
		clearAllErrors();
		

		refreshPhotoListView();
		refreshTagListView(photoListView.getSelectionModel().getSelectedItem());
		
		albumField.setText(name);

	}
	
	/**
	 * Updates the image display with the selected photo along with all respective information including date, tags, and captions.
	 * @see Photo
	 */
	void selectPhoto() {
		if (searchResultMode) {
			if (photoListView.getSelectionModel().getSelectedIndex() < 0) {
				clearAllErrors();
				applyCap.setDisable(true);
				photo_display.setImage(null);
				return;
			}
			applyCap.setDisable(false);
			Photo p = photoListView.getSelectionModel().getSelectedItem();
			Image pic = userImages.get(p.getFilepath().toString());

			double width = pic.getWidth();
			double height = pic.getHeight();
			
			if ((double)(width / height) < ((double)(4.0/3.0))) {  
				photo_display.setX((double)(200.0 - width*(150.0/height)));  //height is big, make Y 0 -> center X
				photo_display.setY(0);
			} else {
				photo_display.setX(0);
			    photo_display.setY((double)(150.0 - height*(200.0/width)));  //width is big, make X 0 -> center Y
			}

			photo_display.setImage(pic);
			applyCap.setText(p.getCaption());
			
			SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy HH:mm:ss");
			date_taken.setText(df.format(p.getDate().getTime()));
			
			tagListView.getItems().clear();
			for (Tag t : p.getTags()) {
				tagListView.getItems().add(t);
			}
			return;
		}
		
		if (currAlbum == null || photoListView.getSelectionModel().getSelectedIndex() < 0 || albumListView.getSelectionModel().getSelectedIndex() < 0) {
			clearAllErrors();
			applyCap.setDisable(true);
			photo_display.setImage(null);
			return;
		}
		applyCap.setDisable(false);
		Photo p = photoListView.getSelectionModel().getSelectedItem();
		Image pic = userImages.get(p.getFilepath().toString());

		double width = pic.getWidth();
		double height = pic.getHeight();
		
		if ((double)(width / height) < ((double)(4.0/3.0))) {  
			photo_display.setX((double)(200.0 - width*(150.0/height)));  //height is big, make Y 0 -> center X
			photo_display.setY(0);
		} else {
			photo_display.setX(0);
		    photo_display.setY((double)(150.0 - height*(200.0/width)));  //width is big, make X 0 -> center Y
		}

		photo_display.setImage(pic);
		applyCap.setText(p.getCaption());
		
		SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy HH:mm:ss");
		date_taken.setText(df.format(p.getDate().getTime()));
		
		tagListView.getItems().clear();
		for (Tag t : p.getTags()) {
			tagListView.getItems().add(t);
		}
		//tagListView.setItems(FXCollections.observableList(p.getTags()));
		
	}
	
	/**
	 * Resets the tagType_error field to a blank string.
	 */
	void selectTag() {
		tagType_error.setText("");
	}
	
	/**
	 * Using the userImages map, displays the photos in an album in a list.
	 * @see Photo
	 * @see Album
	 */
	void loadImages() {
		userImages = new HashMap<String, Image>();
		for (Album x : currUser.getAlbums()) {
			for (Photo y : x.getPhotos()) {
				FileInputStream input = null;
		    	try {
					input = new FileInputStream(y.getFilepath());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	userImages.put(y.getFilepath(), new Image(input));
			}
		}
	}
	
	/**
	 * Loads every tag type the user has initialized and places them in drop down menus.
	 * @see User
	 */
	void loadTagTypes() {
		clearAllErrors();
		userTagTypes = FXCollections.observableList(currUser.getTagTypes());
		FXCollections.sort(userTagTypes);
		tagTypesBox.setItems(userTagTypes);
		tagTypesBox1.setItems(userTagTypes);
		tagTypesBox2.setItems(userTagTypes);
	}
	
	/**
	 * Initializes all of the displays on the page.
	 * @throws FileNotFoundException	when an image cannot be located on the local machine.
	 */
	public void initialize() throws FileNotFoundException {
		
		searchResultMode = false;
		loadImages();
		loadTagTypes();
		applyCap.setDisable(true);
		
		refreshSearchRadio();

		albumListViewCell();
		photoListViewCell();
		
		albumListView
		.getSelectionModel()
		.selectedIndexProperty()
		.addListener(
				(albumListView, oldVal, newVal) -> 
				selectAlbum());

		
		photoListView
		.getSelectionModel()
		.selectedIndexProperty()
		.addListener( 
				(photoListView, oldVal1, newVal1) -> 
				selectPhoto());
		
		
		tagListView
		.getSelectionModel()
		.selectedIndexProperty()
		.addListener( 
				(tagListView, oldVal2, newVal2) -> 
				selectTag());
		

		
		username_display.setText(currUser.getUsername());
		refreshAlbumListView();
		
		

	}
	
	/**
	 * Finds all images located within a date range and updates the image list with the results.
	 * @param event		Action event related to the user pressing the search by date button.
	 * @see Album
	 * @see Photo
	 */
	@FXML void handleSearchByDate(ActionEvent event) {
		if (search_date1.getValue() == null || search_date2.getValue() == null) {
			error_search.setText("error: invalid date");
			return;
		}
		LocalDate d1 = search_date1.getValue();
		LocalDate d2 = search_date2.getValue();
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.clear();
		cal2.clear();
		cal1.set(d1.getYear(), d1.getMonthValue()-1, d1.getDayOfMonth());
		cal2.set(d2.getYear(), d2.getMonthValue()-1, d2.getDayOfMonth());
		
		searchResultMode = true;
		
		List<Photo> allPhotos = new ArrayList<Photo>();
		
		for (Album a : currUser.getAlbums()) {
			for (Photo p : a.getPhotos()) {
				if (!allPhotos.contains(p)) {
					allPhotos.add(p);
				}
			}
		}
		
		List<Photo> results = new ArrayList<Photo>();
		
		for (Photo p : allPhotos) {
			Calendar date = p.getDate();
			Calendar myDate = (Calendar)date.clone();
			myDate.set(Calendar.HOUR_OF_DAY, 0);
			myDate.set(Calendar.MINUTE, 0);
			myDate.set(Calendar.SECOND, 0);
			myDate.set(Calendar.MILLISECOND, 0);
			if (myDate.compareTo(cal1) >= 0 && myDate.compareTo(cal2) <= 0) {
				results.add(p);
			}
		}
		
		clearAllErrors();
		
		tagListView.getSelectionModel().clearSelection();
		refreshTagListView(null);
		photoListView.getSelectionModel().clearSelection();
		albumListView.getSelectionModel().clearSelection();
		
		
		
		photoListView.getItems().clear();
		for (Photo r : results) {
			photoListView.getItems().add(r);
		}
		
		photoListView.getSelectionModel().select(-1);
		
	}

	/**
	 * Finds all images with the given tag and logical operations for multiple tags.
	 * @param event		Action event related to the user pressing the search by date button.
	 * @see Album
	 * @see Photo
	 * @see Tag
	 */
	@FXML void handleSearchByTag(ActionEvent event) {
		
		searchbytag.setText(searchbytag.getText().trim());
		searchbytag2.setText(searchbytag2.getText().trim());
		String str = searchbytag.getText();
		
		if (tagTypesBox1.getSelectionModel().getSelectedIndex() < 0) {
			error_search.setText("error: need a type and value");
			return;
		}
		
		RadioButton selected = (RadioButton) search_radio.getSelectedToggle();
		String radiotype = selected.getText();
		
		if (radiotype.equals("Single Tag")) {
			

			
			String name = tagTypesBox1.getSelectionModel().getSelectedItem();
			String value = str;
			if (str.length() < 1) {
				error_search.setText("error: missing tag value");
				return;
			}
			Tag tester = new Tag(name, value);
			
			List<Photo> allPhotos = new ArrayList<Photo>();
			
			for (Album a : currUser.getAlbums()) {
				for (Photo p : a.getPhotos()) {
					if (!allPhotos.contains(p)) {
						allPhotos.add(p);
					}
				}
			}
			
			List<Photo> results = new ArrayList<Photo>();
			
			for (Photo p : allPhotos) {
				for (Tag t : p.getTags()) {
					if (t.equals(tester)) {
						if (!results.contains(p)) { results.add(p); }
					}
				}
			}
			
			tagListView.getSelectionModel().clearSelection();
			refreshTagListView(null);
			photoListView.getSelectionModel().clearSelection();
			albumListView.getSelectionModel().clearSelection();
			
			clearAllErrors();
			
			photoListView.getItems().clear();
			for (Photo r : results) {
				photoListView.getItems().add(r);
			}
			
			photoListView.getSelectionModel().select(-1);
			searchResultMode = true;
			
			return;
			
		}
		
		if (radiotype.equals("AND") || radiotype.equals("OR")) {
			
			if (tagTypesBox1.getSelectionModel().getSelectedIndex() < 0 || tagTypesBox2.getSelectionModel().getSelectedIndex() < 0
					|| searchbytag2.getText().length() < 1 || searchbytag.getText().length() < 1) {
				error_search.setText("error: need pair of type-value tags");
				return;
			}
			
			List<Photo> results = new ArrayList<Photo>();
			
			
			if (radiotype.equals("OR")) {
				
				String name1 = tagTypesBox1.getSelectionModel().getSelectedItem();
				String value1 = searchbytag.getText();
				String name2 = tagTypesBox2.getSelectionModel().getSelectedItem();
				String value2 = searchbytag2.getText();
				Tag tester1 = new Tag(name1, value1);
				Tag tester2 = new Tag(name2, value2);
				
				List<Photo> allPhotos = new ArrayList<Photo>();
				
				for (Album a : currUser.getAlbums()) {
					for (Photo p : a.getPhotos()) {
						if (!allPhotos.contains(p)) {
							allPhotos.add(p);
						}
					}
				}
				
				for (Photo p : allPhotos) {
					for (Tag t : p.getTags()) {
						if (t.equals(tester1) || t.equals(tester2)) {
							if (!results.contains(p)) { results.add(p); }
						}
					}
				}
				
			}
			
			if (radiotype.equals("AND")) {
				
				String name1 = tagTypesBox1.getSelectionModel().getSelectedItem();
				String value1 = searchbytag.getText();
				String name2 = tagTypesBox2.getSelectionModel().getSelectedItem();
				String value2 = searchbytag2.getText();
				Tag tester1 = new Tag(name1, value1);
				Tag tester2 = new Tag(name2, value2);
				
				List<Photo> allPhotos = new ArrayList<Photo>();
				
				for (Album a : currUser.getAlbums()) {
					for (Photo p : a.getPhotos()) {
						if (!allPhotos.contains(p)) {
							allPhotos.add(p);
						}
					}
				}
				
				for (Photo p : allPhotos) {
					for (int i = 0; i < p.getTags().size(); i++) {
						for (int j = 0; j < p.getTags().size(); j++) {
							if (p.getTags().get(i).equals(tester1) && (p.getTags().get(j).equals(tester2))) {
								if (!results.contains(p)) { results.add(p); }
							}
						}
					}
				}
				
			}

			tagListView.getSelectionModel().clearSelection();
			refreshTagListView(null);
			photoListView.getSelectionModel().clearSelection();
			albumListView.getSelectionModel().clearSelection();
			
			clearAllErrors();
			
			photoListView.getItems().clear();
			for (Photo r : results) {
				photoListView.getItems().add(r);
			}
			
			photoListView.getSelectionModel().select(-1);
			searchResultMode = true;
		}

	}
	
	/*
	 * Creates a new album from the search results.
	 * @see Album
	 * @see Photo
	 */
	@FXML void handleCreateAlbumSearchRes(ActionEvent event) {
		
		if (!searchResultMode) {
			error_search.setText("error: no active search results");
			return; 
		}
		
		if (photoListView.getItems().isEmpty()) {
			error_search.setText("error: no photos in search results");
			return; 
		}
		createAlbum_searchres_name.setText(createAlbum_searchres_name.getText().trim());
		String name = createAlbum_searchres_name.getText();
		
		if (name.length() < 1) { 
			error_search.setText("error: album needs a name");
			return; 
		}
		
		for (Album a : currUser.getAlbums()) {
			if (a.getName().equals(name)) {
				error_search.setText("error: album name already exists");
				return; 
			}
		}
		
		currUser.addAlbum(name);
		Album b = null;
		for (Album a : currUser.getAlbums()) {
			if (a.getName().equals(name)) {
				b = a;
				break;
			}
		}
		for (Photo p : photoListView.getItems()) {
			b.addPhoto(p);
		}
		searchResultMode = false;
		refreshAlbumListView(name);
		createAlbum_searchres_name.setText("");

	}
	
	/**
	 * Handles the addition of a new tag type to the user.
	 * @param event		Action event corresponding to the create tag type button.
	 */
	@FXML void handleNewTagType(ActionEvent event) {
		newTagType_text.setText(newTagType_text.getText().trim().toLowerCase());
		String newtag = newTagType_text.getText();
		if (newtag.length() < 1) {
			tagType_error.setText("error: type needs a name");
			return;
		}
		
		if (photoListView.getSelectionModel().getSelectedIndex() < 0 || albumListView.getSelectionModel().getSelectedIndex() < 0) {
			tagType_error.setText("error: no photo selected");
			return;
		}
		for (String str : userTagTypes) {
			if (str.equals(newtag)) {
				tagType_error.setText("error: type already exists");
				return;
			}
		}
		currUser.getTagTypes().add(newtag);
		loadTagTypes();
		newTagType_text.setText("");
	}
	
	/**
	 * Handles the addition of a new tag to a specific photo.
	 * @param event		Action event corresponding to the add tag button.
	 * @see Photo
	 * @see Tag
	 */
	@FXML void handleAddTag(ActionEvent event) {
		newTag_text.setText(newTag_text.getText().trim());
		String val = newTag_text.getText();
		if (val.length() < 1 || tagTypesBox.getSelectionModel().getSelectedIndex() < 0) {
			tag_error.setText("error: tag needs type and value");
			return;
		}
		if (photoListView.getSelectionModel().getSelectedIndex() < 0 || (!searchResultMode && albumListView.getSelectionModel().getSelectedIndex() < 0)) {
			tag_error.setText("error: no photo selected");
			return;
		}
		Photo ph = photoListView.getSelectionModel().getSelectedItem();
		Tag tag = new Tag(tagTypesBox.getSelectionModel().getSelectedItem(), val);
		for (Tag t : ph.getTags()) {
			if (t.equals(tag)) {
				tag_error.setText("error: tag already exists");
				return;
			}
		}

		if (!ph.addTag(tag) && tagTypesBox.getSelectionModel().getSelectedItem().equalsIgnoreCase("location")) {
			tag_error.setText("error: already has location");
			return;
		}
		
		newTag_text.setText("");

		refreshTagListView(ph, tag);
	}
	
	/**
	 * Handles the deletion of a tag attached to a selected photo.
	 * @param event Action event corresponding to the delete tag button.
	 * @see Photo
	 * @see Tag
	 */
	@FXML void handleDelTag(ActionEvent event) {
		if (photoListView.getSelectionModel().getSelectedIndex() < 0 || (!searchResultMode && albumListView.getSelectionModel().getSelectedIndex() < 0)) {
			tag_error.setText("error: no photo selected");
			return;
		}
		if (tagListView.getItems().isEmpty() || tagListView.getSelectionModel().getSelectedIndex() < 0) {
			tag_error.setText("error: no tag selected");
			return;
		}
		Tag rm = tagListView.getSelectionModel().getSelectedItem();
		Photo ph = photoListView.getSelectionModel().getSelectedItem();
		if (!confirm("DeletionTag")) {
			return;
		}
		ph.getTags().remove(rm);
		
		refreshTagListView(ph);
	}
	
	/**
	 * Handles the copying of one photo to another specified album.
	 * @param event		Action event corresponding to the copy button.
	 * @see Photo
	 * @see Album
	 */
	@FXML void handleCopy(ActionEvent event) {
		toAlbum.setText(toAlbum.getText().trim());
		if (searchResultMode) {
			String alb = toAlbum.getText();
			if (photoListView.getSelectionModel().getSelectedIndex() < 0) { 
				error_copymove.setText("error copying: no photo selected");
				return; 
			}
			Photo p = photoListView.getSelectionModel().getSelectedItem();
			Album dest = null;
			for (Album a : currUser.getAlbums()) {
				if (a.getName().equals(alb)) {
					dest = a; break;
				}
			}
			if (dest == null || alb.length() < 1) {
				error_copymove.setText("error copying: that album does not exist");
				return;
			}
			dest.addPhoto(p);
			refreshAlbumListView();
			refreshPhotoListView(p); 

			return;
		}
		if (albumListView.getSelectionModel().getSelectedIndex() < 0 || albumListView.getItems().isEmpty()) {
			error_copymove.setText("error copying: no photo selected");
			return;
		}
		String alb = toAlbum.getText();
		String curr = currAlbum.getName();
		if (photoListView.getSelectionModel().getSelectedIndex() < 0) { 
			error_copymove.setText("error copying: no photo selected");
			return; 
		}
		if (alb.equals(currAlbum.getName())) {
			return;
		}
		Photo p = photoListView.getSelectionModel().getSelectedItem();
		Album dest = null;
		for (Album a : currUser.getAlbums()) {
			if (a.getName().equals(alb)) {
				dest = a; break;
			}
		}
		if (dest == null || alb.length() < 1) {
			error_copymove.setText("error copying: that album does not exist");
			return;
		}
		dest.addPhoto(p);
		refreshAlbumListView(curr);
		refreshPhotoListView(p);
		clearAllErrors();
		
	}
	
	/**
	 * Handles the transfer of a photo in one album to another specified album.
	 * @param event		Action event corresponding to the move button.
	 */
	@FXML void handleMove(ActionEvent event) {
		if (searchResultMode) {
			error_copymove.setText("error moving: select an initial album");
			return;
		}
		if (albumListView.getSelectionModel().getSelectedIndex() < 0 || albumListView.getItems().isEmpty()) {
			error_copymove.setText("error copying: no photo selected");
			return;
		}
		toAlbum.setText(toAlbum.getText().trim());
		String alb = toAlbum.getText();
		String curr = currAlbum.getName();
		if (photoListView.getSelectionModel().getSelectedIndex() < 0) { 
			error_copymove.setText("error moving: no photo selected");
			return; 
		}
		Photo p = photoListView.getSelectionModel().getSelectedItem();
		Album dest = null;
		for (Album a : currUser.getAlbums()) {
			if (a.getName().equals(alb)) {
				dest = a; break;
			}
		}
		if (dest == null || alb.length() < 1) {
			clearAllErrors();
			return;
		}
		dest.addPhoto(p);
		if (!currAlbum.getName().equals(dest.getName())) {
			currAlbum.removePhoto(p);
		}
		refreshAlbumListView(curr);
		refreshPhotoListView();
	}
	
	/**
	 * Handles the update of a photos caption.
	 * @param event		Action event corresponding to the update caption button.
	 */
	@FXML void handleApplyCaption(ActionEvent event) {
		if (photoListView.getSelectionModel().getSelectedIndex() < 0) { return; }
		applyCap.setText(applyCap.getText().trim());
		String newcap = applyCap.getText();
		Photo p = photoListView.getSelectionModel().getSelectedItem();
		p.setCaption(newcap);
		refreshPhotoListView(p);
	}
	
	/**
	 * Handles the clearing of the photos caption text field.
	 * @param event		Action event corresponding to the clear button.
	 */
	@FXML void handleClearCap(ActionEvent event) { applyCap.setText(""); }
	
	/**
	 * Handles the selection the photo below the currently selected photo.
	 * @param event		Action event corresponding to the next button.
	 */
	@FXML void handleNextPhoto(ActionEvent event) {
		int index = photoListView.getSelectionModel().getSelectedIndex();
		if (index < 0) { 
			if (photoListView.getItems().isEmpty()) { return; }
			else { photoListView.getSelectionModel().select(0); }
		}
		if (index == photoListView.getItems().size()-1) {
			photoListView.getSelectionModel().select(0);
		} else {
			photoListView.getSelectionModel().select(index+1);
		}
	}
	
	/**
	 * Handles the selection the photo above the currently selected photo.
	 * @param event		Action event corresponding to the prev button.
	 */
	@FXML void handlePrevPhoto(ActionEvent event) {
		int index = photoListView.getSelectionModel().getSelectedIndex();
		if (index < 0) { 
			if (photoListView.getItems().isEmpty()) { return; }
			else { photoListView.getSelectionModel().select(0); }
		}
		if (index == 0) {
			photoListView.getSelectionModel().select(photoListView.getItems().size()-1);
		} else {
			photoListView.getSelectionModel().select(index-1);
		}
	}
	
	/**
	 * Handles the creation of a new album with a specified name.
	 * @param event		Action event corresponding to the create album button.
	 * @see Album
	 */
	@FXML void handleCreateAlbum(ActionEvent event) {
		albumField.setText(albumField.getText().trim());
		String newalb = albumField.getText();
		if (newalb.length() < 1) {
			error_album.setText("error creating: album must have a name");
			return;
		}
		Album temp = new Album(newalb);
		if (currUser.getAlbums().contains(temp)) {
			error_album.setText("error creating: album with that name already exists");
			return;
		}
		currUser.addAlbum(newalb);
		refreshAlbumListView(newalb);
	}
	
	/**
	 * Handles the deletion of a selected album without deleting the photos from other albums.
	 * @param event		Action event corresponding to the delete album button.
	 * @see Album
	 */
	@FXML void handleDeleteAlbum(ActionEvent event) {
		if (searchResultMode) {
			error_album.setText("error deleting: must have an album selected");
			return;
		}
		
		if (albumListView.getSelectionModel().getSelectedIndex() < 0) {
			error_album.setText("error deleting: must have an album selected");
			return;
		}
		if (!confirm("DeletionAlbum")) {
			return;
		}
		albumField.setText("");
		String delalb = albumListView.getSelectionModel().getSelectedItem().substring(0,albumListView.getSelectionModel().getSelectedItem().indexOf('\n'));
		currUser.delAlbum(delalb);
		refreshAlbumListView();
		refreshTagListView(null);
	}
	
	/**
	 * Handles the renaming of an album.
	 * @param event		Action event corresponding to the rename album button.
	 * @see Album
	 */
	@FXML void handleRenameAlbum(ActionEvent event) {
		albumField.setText(albumField.getText().trim());
		String renalb = albumField.getText();
		if (albumListView.getSelectionModel().getSelectedIndex() < 0) {
			error_album.setText("error renaming: must have an album selected");
			return;
		}
		if (renalb.length() < 1) {
			error_album.setText("error renaming: album must have a name");
			return;
		}
		for (int i = 0; i < albumListView.getItems().size(); i++) {
			if (i == albumListView.getSelectionModel().getSelectedIndex()) {
				continue;
			}
			if (albumListView.getItems().get(i).substring(0,albumListView.getItems().get(i).indexOf('\n')).equals(renalb)) {
				error_album.setText("error renaming: album with that name already exists");
				return;
			}
		}
		Photo x = photoListView.getSelectionModel().getSelectedItem();
		currUser.renAlbum(albumListView.getSelectionModel().getSelectedItem().substring(0,albumListView.getSelectionModel().getSelectedItem().indexOf('\n')), renalb);
		refreshAlbumListView(renalb);
		photoListView.getSelectionModel().select(x);
	}
	
	/**
	 * Handles the adding of a photo to an album while checking if that photo already exists in another album.
	 * @param event		Action event corresponding to the add photo button.
	 * @see Album
	 * @see Photo
	 */
	@FXML void handleAddPhoto(ActionEvent event) {
		if (searchResultMode) {
			error_photo.setText("error adding: must have an album selected");
			return;
		}
		if (albumListView.getSelectionModel().getSelectedIndex() < 0) { 
			error_photo.setText("error adding: must have an album selected");
			return;
		}

		FileChooser choose = new FileChooser();
		choose.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.bmp;*.dib;*.png;*.gif;*.jpe;*.jpeg;*jpg;*.jpe;*.rle"));
		File newphoto = choose.showOpenDialog((Stage) addpho_but.getScene().getWindow());
		if (newphoto == null) {
			return;
		}
		Photo p = null;
		for (Album g : currUser.getAlbums()) {
			for (Photo h : g.getPhotos()) {
				if (h.getFilepath().equals(newphoto.getPath().toString())) {
					p = h; break;
				}
			}
		}
		if (p == null) {
			p = new Photo(newphoto.getPath().toString());
		}
		

		currAlbum.addPhoto(p);

		refreshAlbumListView(currAlbum.getName());

		refreshPhotoListView(p);
	}
	
	/**
	 * Handles removing a photo from an album
	 * @param event		Action event corresponding to the remove photo button.
	 * @see Album
	 * @see Photo
	 */
	@FXML void handleRemovePhoto(ActionEvent event) {
		if (searchResultMode) {
			error_photo.setText("error removing: must have an album selected");
			return;
		}
		if (albumListView.getSelectionModel().getSelectedIndex() < 0) { 
			error_photo.setText("error removing: must have an album selected");
			return;
		}
		
		if (photoListView.getSelectionModel().getSelectedIndex() < 0) {
			error_photo.setText("error removing: must have a photo selected");
			return;
		}
		
		if (!confirm("Removal")) {
			return;
		}

		Photo del = photoListView.getSelectionModel().getSelectedItem();
		currAlbum.removePhoto(del);
		
		refreshAlbumListView(currAlbum.getName());
		refreshPhotoListView();
		
	}
	
	/**
	 * Returns the user to the login screen.
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
		 Stage stage = (Stage) quit_but.getScene().getWindow();
		 stage.close();
	}
	
	/**
	 * Handles the clearing of the album name text field.
	 * @param event		Action event representing the clear button being pressed next to the album text field.
	 */
	@FXML void handleAlbumNClear(ActionEvent event) {
		albumField.setText("");
	}
	
	/**
	 * Handles the clearing of the move/copy album text field.
	 * @param event		Action event representing the clear being pressed next to the move/copy album text field.
	 */
	@FXML void handleToAlbumClear(ActionEvent event) {
		toAlbum.setText("");
	}
	
	/**
	 * Starts this state of the application, Left as placeholder, initialization handled in <code>initialize()</code>.
	 * @param mainStage		primary JavaFX stage
	 */
	public void start(Stage mainStage) { 

		
	}
	
	/**
	 * Enables the second tag text field and drop down if the AND or OR radio buttons are selected.
	 */
	@FXML void refreshSearchRadio() {
		RadioButton selected = (RadioButton) search_radio.getSelectedToggle();
		String str = selected.getText();
		
		if (str.equals("OR") || str.equals("AND")) {
			tagTypesBox2.setDisable(false);
			searchbytag2.setDisable(false);
			return;
		}
		if (str.equals("Single Tag")) {
			tagTypesBox2.setDisable(true);
			searchbytag2.setDisable(true);
		}
	}
	
	/**
	 * Refreshes the album list with the current contents of the users albums.
	 * @see User
	 */
	void refreshAlbumListView() {
		albumListView.getItems().clear();
		for (Album alb : currUser.getAlbums()) {
			albumListView.getItems().add(alb.toString());
		}
		FXCollections.sort(albumListView.getItems());
		clearAllErrors();
	}
	
	/**
	 * Refreshes the album list with the current contents of the users albums and select the specified album.
	 * @param desiredAlb	Album to be selected after refresh.
	 * @see User
	 */
	void refreshAlbumListView(String desiredAlb) {
		albumListView.getItems().clear();
		for (Album alb : currUser.getAlbums()) {
			albumListView.getItems().add(alb.toString());
		}
		FXCollections.sort(albumListView.getItems());
		for (String str : albumListView.getItems()) {
			if (str.substring(0,str.indexOf('\n')).equals(desiredAlb)) {
				albumListView.getSelectionModel().select(str);
				
				break;
			}
		}
		clearAllErrors();
	}
	
	/**
	 * Refreshes the photo list with the current contents of the selected album.
	 * @see Album
	 * @see Photo
	 */
	void refreshPhotoListView() {
		if (searchResultMode) {
			List<Photo> temp = new ArrayList<Photo>();
			for (Photo p : photoListView.getItems()) {
				temp.add(p);
			}
			photoListView.getItems().clear();
			for (Photo p : temp) {
				photoListView.getItems().add(p);
			}
			refreshTagListView(null);
			return;
		}
		photoListView.getItems().clear();
		if (currAlbum == null) {  return; }
		if (currAlbum.getPhotos().isEmpty()) {
			return;
		}

		for (Photo p : currAlbum.getPhotos()) {
			photoListView.getItems().add(p);
		}
		refreshTagListView(null);
	}
	
	/**
	 * Refreshes the photo list view and selects the specified photo.
	 * @param ph		The photo to be selected.
	 * @see Album
	 * @see Photo
	 */
	void refreshPhotoListView(Photo ph) {
		if (searchResultMode) {
			List<Photo> temp = new ArrayList<Photo>();
			for (Photo p : photoListView.getItems()) {
				temp.add(p);
			}
			photoListView.getItems().clear();
			for (Photo p : temp) {
				photoListView.getItems().add(p);
			}
			photoListView.getSelectionModel().select(ph);
			refreshTagListView(ph);
			return;
		}
		photoListView.getItems().clear();
		if (currAlbum == null) { return; }
		if (currAlbum.getPhotos().isEmpty()) {
			return;
		}

		for (Photo p : currAlbum.getPhotos()) {
			photoListView.getItems().add(p);
		}
		photoListView.getSelectionModel().select(ph);
		refreshTagListView(ph);
	}
	
	/**
	 * Refreshes the tag list of a specific photo.
	 * @param ph		The photo whose tag list is being refreshed.
	 * @see Photo
	 * @see Tag
	 */
	void refreshTagListView(Photo ph) {

		
		tag_error.setText("");
		tagListView.getItems().clear();
		if ((!searchResultMode && (currAlbum == null || currAlbum.getPhotos().isEmpty() || albumListView.getSelectionModel().getSelectedIndex() < 0))
				|| photoListView.getSelectionModel().getSelectedIndex() < 0 || ph == null) { return; }
		for (Tag t : ph.getTags()) {
			tagListView.getItems().add(t);
		}

	}
	
	/**
	 * Refreshes the tag list of a specific photo and selects the specified tag.
	 * @param ph		The photo whose tag list is being refreshed.
	 * @param ta		The tag to be selected after refresh.
	 * @see Photo
	 * @see Tag
	 */
	void refreshTagListView(Photo ph, Tag ta) {
		tag_error.setText("");
		tagListView.getItems().clear();
		if ((!searchResultMode && (currAlbum == null || currAlbum.getPhotos().isEmpty() || albumListView.getSelectionModel().getSelectedIndex() < 0))
				|| photoListView.getSelectionModel().getSelectedIndex() < 0 || ph == null) { return; }
		for (Tag t : ph.getTags()) {
			tagListView.getItems().add(t);
		}
		
		tagListView.getSelectionModel().select(ta);

	}
	
	/**
	 * Manages the cell factory for the album list view.
	 */
	void albumListViewCell() {
		albumListView.setCellFactory(param -> new ListCell<String>(){
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setPrefHeight(40);
                    setFont(Font.font(11));
                    setText(item);
                }
            }
        });
	}
	
	/**
	 * Manages the cell factory for the photo list view.
	 */
	void photoListViewCell() {
		photoListView.setCellFactory(param -> new ListCell<Photo>(){

            @Override
            protected void updateItem(Photo item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                	setMinWidth(0);
                	setPrefWidth(1);
                	setPrefHeight(50);
                	setMaxHeight(50);
                   
                    setFont(Font.font(11));
                    setWrapText(true);

                    Image img = userImages.get(item.getFilepath());
            		ImageView thumb = new ImageView(img);
            		thumb.setFitHeight(45);
            		thumb.setFitWidth(60);
            		thumb.setPreserveRatio(true);
                    setText(item.getCaption());

                    setGraphic(thumb);
                }
            }
        });
        
	}
	
	/**
	 * Manages the cell factory for the tag list view.
	 */
	void tagListViewCell() {
		tagListView.setCellFactory(param -> new ListCell<Tag>(){
            @Override
            protected void updateItem(Tag item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setPrefHeight(25);
                    setFont(Font.font(10));
                    setWrapText(true);
                    setText(item.toString());
                }
            }
        });
	}
	
	/**
	 * Clears any error messages which may be currently displayed.
	 */
	void clearAllErrors() {
		error_album.setText("");
		error_copymove.setText("");
		tag_error.setText("");
		tagType_error.setText("");
		error_search.setText("");
		error_photo.setText("");
	}
	
	/**
	 * A popup confirmation for the deletion of a photo, tag, or album.
	 * @return		A flag indicating if the button has functioned correctly.
	 */
	boolean confirm(String type) {

		boolean flag = false;
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm");
		if (type.equals("Removal")) {
			alert.setHeaderText("Are you sure you want to remove this photo from \"" 
					+ albumListView.getSelectionModel().getSelectedItem().substring(0,albumListView.getSelectionModel().getSelectedItem().indexOf('\n'))+ "\"?");
		}
		if (type.equals("DeletionTag")) {
			alert.setHeaderText("Are you sure you want to delete the tag \"" + tagListView.getSelectionModel().getSelectedItem().toString() + "\"?");
		}
		if (type.equals("DeletionAlbum")) {
			alert.setHeaderText("Are you sure you want to delete \"" + albumListView.getSelectionModel().getSelectedItem().
					substring(0,albumListView.getSelectionModel().getSelectedItem().indexOf('\n'))+ "\"?");
		}
		
		//alert.setContentText("");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
		    flag = true;
		}
		return flag;
		
	}
	


}
