package photos.app;

import java.util.ArrayList;
import java.util.List;

public class Tag {
	
	private String tagname;
	private String tagvalue;
	
	public static List<String> tagTypes = new ArrayList<String>();
	
	public Tag(String tname, String tval) {
		this.tagname = tname;
		this.tagvalue = tval;
		
		if (!tagTypes.contains(tname)) {
			tagTypes.add(tname);
		}
	}
	
	public String getTagname() {
		return this.tagname;
	}
	
	public String getTagvalue() {
		return this.tagvalue;
	}
	
	public boolean equals(Tag t2) {
		Tag t1 = this;
		return t1.getTagname().equals(t2.getTagname()) && t1.getTagvalue().equals(t2.getTagvalue());
		
	}
	
	

}
