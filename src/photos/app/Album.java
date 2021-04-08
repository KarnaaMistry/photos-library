package photos.app;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2083102379747324346L;
	private String name;
	private List<Photo> photos;
	
	public Album(String name) {
		this.name = name;
		this.photos = new ArrayList<Photo>();
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<Photo> getPhotos() {
		return this.photos;
	}
	
	public boolean addPhoto(Photo p) {
		if (this.photos.contains(p)) {
			return false;
		}
		this.photos.add(p);
		return true;
	}
	
	public boolean removePhoto(Photo p) {
		if (!this.photos.contains(p)) {
			return false;
		}
		this.photos.remove(p);
		return true;
	}

}
