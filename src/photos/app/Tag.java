package photos.app;

import java.io.Serializable;

/**
 * Tag is the class responsible for handling the retrieval and storage of a tag type value pair.
 * 
 * @author Fin Herbig
 * @author Karnaa Mistry
 */
public class Tag implements Serializable {
	
	/**
	 * The serial version UID of the tag.
	 */
	private static final long serialVersionUID = 5683892459737750416L;
	
	/**
	 * The tag type.
	 */
	private String tagname;
	
	/**
	 * The value of the tag.
	 */
	private String tagvalue;
	
	
	/**
	 * Constructor which stores the passed tag type and tag value 
	 * @param tname		<code>String</code> containing the tag type.
	 * @param tval		<code>String</code> containing the tag value.
	 */
	public Tag(String tname, String tval) {
		this.tagname = tname;
		this.tagvalue = tval;
	}
	
	/**
	 * Returns the type of the tag. 
	 * @return A <code>String</code> containing the tag type.
	 */
	public String getTagname() {
		return this.tagname;
	}
	
	/**
	 * Returns the type of the tag. 
	 * @return A <code>String</code> containing the tag value.
	 */
	public String getTagvalue() {
		return this.tagvalue;
	}
	
	/**
	 * Prints the tag type and the tag value of the tag instance.
	 */
	public String toString() {
		return this.tagname + ":  " + this.tagvalue;
	}
	
	/**
	 * Determines whether or not a passed object is the same as the current instance of tag.
	 * @param o		The object to be compared against.
	 * @return		<code>true</code> if the passed object is the same as the tag, <code>false</code> otherwise.
	 */
	public boolean equals(Object o) {
		if (!(o instanceof Tag)) {
			return false;
		}
		Tag t1 = this;
		return t1.getTagname().equals(((Tag)o).getTagname()) && t1.getTagvalue().equals(((Tag)o).getTagvalue());
		
	}
	
	

}
