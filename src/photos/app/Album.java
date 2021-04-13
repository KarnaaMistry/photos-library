package photos.app;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;

public class Album implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2083102379747324346L;
	private String name;
	private List<Photo> photos;
	private int numPhotos;
	private Calendar minDate;
	private Calendar maxDate;
	
	public Album(String name) {
		this.name = name;
		this.photos = new ArrayList<Photo>();
		this.numPhotos = 0;
		this.minDate = new GregorianCalendar(1,0,5050);
		this.maxDate = new GregorianCalendar(1,0,5050);
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<Photo> getPhotos() {
		return this.photos;
	}
	
	public int getNumPhotos() {
		return this.numPhotos;
	}
	
	public Calendar getMinDate() {
		return this.minDate;
	}
	
	public Calendar getMaxDate() {
		return this.maxDate;
	}
	
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
	
	public boolean setName(String newname) {
		if (this.name.equals(newname)) {
			return false;
		}
		this.name = newname;
		return true;
	}
	
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
	
	public boolean equals(Object o) {
		if (!(o instanceof Album)) {
			return false;
		}
		Album a1 = this;
		return a1.getName().equals(((Album)o).getName());
	}

}
