package photos.app;

import java.util.Calendar;
import java.util.ArrayList;

import javafx.scene.image.Image;
import photos.view.PhotosController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

/**
 * Photo is the class responsible for handling the retrieval and storage of information on a photo.
 * 
 * @author Fin Herbig
 * @author Karnaa Mistry
 */
public class Photo implements Serializable {
	
	/**
	 * The serial version UID of the photo.
	 */
	private static final long serialVersionUID = 788663239606276282L;
	
	/**
	 * The last date of modification of the photo.
	 */
	private Calendar date;
	
	/**
	 * The caption of the photo.
	 */
	private String caption;
	
	/**
	 * An <code>ArrayList</code> of tags associated with the photo
	 * @see Tag
	 */
	private ArrayList<Tag> tags;
	
	/**
	 * The filepath leading to the photo.
	 */
	private String filepath;
	
	/**
	 * Constructor of the instance using the filepath to retrieve information on the date of the photo.
	 * @param filepath		A <code>String</code> containing the filepath of the photo.
	 */
	public Photo(String filepath) {
		
		this.caption = "";
		this.tags = new ArrayList<Tag>();
		File f = new File(filepath);
		
		date = Calendar.getInstance();
		date.setTimeInMillis(f.lastModified());
		date.set(Calendar.MILLISECOND,0);
		
		this.filepath = filepath;
		
		FileInputStream input = null;
    	try {
			input = new FileInputStream(filepath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	PhotosController.userImages.put(filepath, new Image(input));
		
	}
	
	/**
	 * Constructor of the instance using the filepath to retrieve information on the date of the photo as well as storing a given caption.
	 * @param filepath		A <code>String</code> containing the filepath of the photo.
	 * @param caption		A <code>String</code> containing the caption to be added to the photo.
	 */
	public Photo(String filepath, String caption) {
		this(filepath);
		this.caption = caption;

	}

	/**
	 * Add a given tag to the list of tags associated with a photo.
	 * @param t		The instance of tag to be added to the photo.
	 * @return		<code>false</code> if the photo already has the given tag; <code>true</code> otherwise.
	 * @see Tag
	 */
	public boolean addTag(Tag t) {
		String name = t.getTagname();
		
		if (name.equalsIgnoreCase("location")) {
			for (Tag u : this.tags) {
				if (u.getTagname().equalsIgnoreCase("location")) { return false; }
			} 
		}
		
		if (this.tags.contains(t)) { return false; }

		this.tags.add(t);
		return true;
	}
	
	/**
	 * Removes a given tag to the list of tags associated with a photo.
	 * @param tname		<code>String</code> containing the tag type of the tag to be removed.
	 * @param tvalue	<code>String</code> containing the tag value of the tag to be removed.
	 * @return			<code>false</code> if the photo does not posess the tag; <code>true</code> otherwise.
	 * @see Tag
	 */
	public boolean deleteTag(String tname, String tvalue) {
		String name = tname.trim();
		String value = tvalue.trim();
		
		Tag temp = new Tag(name, value);
		if (!this.tags.contains(temp)) { return false; }
		
		this.tags.remove(temp);
		return true;
	}
	
	/**
	 * Updates the caption of a photo with a given caption.
	 * @param newcaption	A <code>String</code> containing the caption to be added to the photo. 
	 * @return				<code>true</code>
	 */
	public boolean setCaption(String newcaption) {
		newcaption = newcaption.trim();
		this.caption = newcaption;
		return true;
	}
	
	/**
	 * Returns the date the photo was last modified.
	 * @return The date this photo was last modified.
	 */
	public Calendar getDate() {
		return this.date;
	}
	
	/**
	 * Returns the caption of the photo.
	 * @return The caption of the photo.
	 */
	public String getCaption() {
		return this.caption;
	}
	
	/**
	 * Returns the list of tags associated with the photo.
	 * @return The tags <code>ArrayList</code> of the photo.
	 */
	public ArrayList<Tag> getTags() {
		return this.tags;
	}
	
	/**
	 * Returns the filepath of the photo.
	 * @return <code>String</code> contiaing the filepath to the photo.
	 */
	public String getFilepath() {
		return this.filepath;
	}

	/**
	 * Determines whether or not a passed object is the same as the current instance of photo.
	 * @param o		The object to be compared against.
	 * @return		<code>true</code> if the passed object is the same as the photo, <code>false</code> otherwise.
	 */
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Photo)) {
			return false;
		}
		Photo p1 = this;
		return p1.getFilepath().equals(((Photo)o).getFilepath());
	}
	
	

}
