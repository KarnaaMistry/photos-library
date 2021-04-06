package photos.app;

import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

public class Photo {
	
	private Calendar date;
	private String caption;
	private ArrayList<Tag> tags;
	private String filepath;
	
	public Photo() {
		
		this.caption = "";
		this.tags = new ArrayList<Tag>();
		
		
	}
	
	public Photo(String caption) {
		this();
		this.caption = caption;
	}
	
	
	public boolean addTag(String tname, String tvalue) {
		String name = tname.trim();
		String value = tvalue.trim();
		
		if (tname.equalsIgnoreCase("location")) {
			for (Tag t : this.tags) {
				if (t.getTagname().equalsIgnoreCase("location")) { return false; }
			}
		}
		
		Tag x = new Tag(name, value, 0);
		if (this.tags.contains(x)) { return false; }
		
		this.tags.add(x);
		return true;
	
	}
	
	public boolean deleteTag(String tname, String tvalue) {
		String name = tname.trim();
		String value = tvalue.trim();
		
		Tag temp = new Tag(name, value, 0);
		if (!this.tags.contains(temp)) { return false; }
		
		this.tags.remove(temp);
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
	
	

}
