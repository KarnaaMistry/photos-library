package photos.app;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;

/**
 * Album is the class responsible for handling the retrieval and storage of information and photos from an album.
 * 
 * @author Fin Herbig
 * @author Karnaa Mistry
 */
public class Album implements Serializable {
	
	/**
	 * The serial version UID of the Album.
	 */
	private static final long serialVersionUID = 2083102379747324346L;
	
	/**
	 * The name of the album.
	 */
	private String name;
	
	/**
	 * A list of photos stored in the album.
	 * @see Photo
	 */
	private List<Photo> photos;
	
	/**
	 * The number of photos in the album.
	 */
	private int numPhotos;
	
	/**
	 * The date of the newest photo in the album.
	 */
	private Calendar minDate;
	
	/**
	 * The date of the oldest photo in the album.
	 */
	private Calendar maxDate;
	
	/**
	 * Constructor for new instances of Album.
	 * @param name		The name of the new Album instance.
	 */
	public Album(String name) {
		this.name = name;
		this.photos = new ArrayList<Photo>();
		this.numPhotos = 0;
		this.minDate = new GregorianCalendar(1,0,5050);
		this.maxDate = new GregorianCalendar(1,0,5050);
	}
	
	/**
	 * Returns the name of the album.
	 * @return		A <code>String</code> holding the name of the album.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns all photos in the album.
	 * @return		A <code>List</code> of every photo in the album.
	 */
	public List<Photo> getPhotos() {
		return this.photos;
	}
	
	/**
	 * Returns the number of photos.
	 * @return		Ant <code>int</code> storing the number of photos in the album.
	 */
	public int getNumPhotos() {
		return this.numPhotos;
	}
	
	/**
	 * Returns the date of the newest photo.
	 * @return		A <code>Calendar</code> instance storing the date of the newest photo.
	 */
	public Calendar getMinDate() {
		return this.minDate;
	}
	
	/**
	 * Returns the date of the oldest photo.
	 * @return		A <code>Calendar</code> instance storing the date of the oldest photo.
	 */
	public Calendar getMaxDate() {
		return this.maxDate;
	}
	
	/**
	 * Adds a new photo to the album and updates the album information.
	 * @param p		The instance of <code>Photo</code> to be added.
	 * @return		<code>false</code> if the photo already exists in the album; <code>true</code> otherwise.
	 * @see Photo
	 */
	public boolean addPhoto(Photo p) {
		if (this.photos.contains(p)) {
			return false;
		}
		this.photos.add(p);
		this.numPhotos++;
		Collections.sort(this.photos, (Photo a, Photo b) -> a.getDate().compareTo(b.getDate()));
		this.maxDate = this.photos.get(this.numPhotos-1).getDate();
		this.minDate = this.photos.get(0).getDate();
		return true;
	}
	
	/**
	 * Removes a photo from the album and updates the album information.
	 * @param p		The instance of <code>Photo</code> to be removed.
	 * @return		<code>false</code> if the photo does not exist in the album; <code>true</code> otherwise.
	 * @see Photo
	 */
	public boolean removePhoto(Photo p) {
		if (!this.photos.contains(p)) {
			return false;
		}
		this.photos.remove(p);
		this.numPhotos--;
		Collections.sort(this.photos, (Photo a, Photo b) -> a.getDate().compareTo(b.getDate()));
		if (this.numPhotos == 0) {
			this.maxDate = new GregorianCalendar(1,0,5050);
			this.minDate = new GregorianCalendar(1,0,5050);
			return true;
		}
		this.maxDate = this.photos.get(this.numPhotos-1).getDate();
		this.minDate = this.photos.get(0).getDate();
		return true;
	}
	
	/**
	 * Sets the name of the album to a given name.
	 * @param newname		A <code>String</code> containing the new name of the album.
	 * @return				<code>false</code> if the album is already named the new name; <code>true</code> otherwise.
	 */
	public boolean setName(String newname) {
		if (this.name.equals(newname)) {
			return false;
		}
		this.name = newname;
		return true;
	}
	
	/**
	 * Prints the number of photos in the album, the earliest date, and the oldest date.
	 */
	public String toString() {
		SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy");
		if (this.minDate.equals(new GregorianCalendar(1,0,5050))) {
			return this.name + "\n    (" + this.numPhotos + " photos,   no date range)"; 
		}
		if (this.numPhotos == 1) {
			return this.name + "\n    (" + this.numPhotos + " photo,   " + df.format(this.minDate.getTime()) + " - " + df.format(this.maxDate.getTime()) + ")";
		}
		return this.name + "\n    (" + this.numPhotos + " photos,   " + df.format(this.minDate.getTime()) + " - " + df.format(this.maxDate.getTime()) + ")";
	}
	
	/**
	 * Determines whether or not a passed object is the same as the current instance of album.
	 * @param o		The object to be compared against.
	 * @return		<code>true</code> if the passed object is the same as the album, <code>false</code> otherwise.
	 */
	public boolean equals(Object o) {
		if (!(o instanceof Album)) {
			return false;
		}
		Album a1 = this;
		return a1.getName().equals(((Album)o).getName());
	}

}
