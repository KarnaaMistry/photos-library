package photos.app;

import java.io.Serializable;

public class Tag implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5683892459737750416L;
	private String tagname;
	private String tagvalue;
	
	public Tag(String tname, String tval) {
		this.tagname = tname;
		this.tagvalue = tval;
	}
	
	public String getTagname() {
		return this.tagname;
	}
	
	public String getTagvalue() {
		return this.tagvalue;
	}
	
	public String toString() {
		return this.tagname + ":  " + this.tagvalue;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof Tag)) {
			return false;
		}
		Tag t1 = this;
		return t1.getTagname().equals(((Tag)o).getTagname()) && t1.getTagvalue().equals(((Tag)o).getTagvalue());
		
	}
	
	

}
