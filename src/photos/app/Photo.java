package photos.app;

import java.util.Calendar;
import java.util.ArrayList;

import javafx.scene.image.Image;
import photos.view.PhotosController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;


public class Photo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 788663239606276282L;
	private Calendar date;
	private String caption;
	private ArrayList<Tag> tags;
	private String filepath;
	
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
	
	public Photo(String filepath, String caption) {
		this(filepath);
		this.caption = caption;

	}

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
	
	public boolean deleteTag(String tname, String tvalue) {
		String name = tname.trim();
		String value = tvalue.trim();
		
		Tag temp = new Tag(name, value);
		if (!this.tags.contains(temp)) { return false; }
		
		this.tags.remove(temp);
		return true;
	}
	
	public boolean setCaption(String newcaption) {
		newcaption = newcaption.trim();
		this.caption = newcaption;
		return true;
	}
	
	public Calendar getDate() {
		return this.date;
	}
	
	public String getCaption() {
		return this.caption;
	}
	
	public ArrayList<Tag> getTags() {
		return this.tags;
	}
	
	public String getFilepath() {
		return this.filepath;
	}

	
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Photo)) {
			return false;
		}
		Photo p1 = this;
		return p1.getFilepath().equals(((Photo)o).getFilepath());
	}
	
	

}
