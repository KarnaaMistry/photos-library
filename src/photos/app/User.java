package photos.app;

import java.io.*;
import java.util.List;

import java.util.ArrayList;

/**
 * User is the class responsible for handling the retrieval and storage of a users username, tag types, and albums.
 * 
 * @author Fin Herbig
 * @author Karnaa Mistry
 */
public class User implements Serializable {
	
	/**
	 * The data storage directory.
	 */
	public static final String storeDir = "data";
	
	/**
	 * The serial version UID of the tag.
	 */
	private static final long serialVersionUID = 2218609123236918959L;
	
	/**
	 * The username of the user.
	 */
	private String username;
	
	/**
	 * A <code>List</code> containing the albums of the user.
	 * @see Album
	 */
	private List<Album> albums;
	
	/**
	 * A <code>List</code> containing the tag types associated with a user.
	 */
	private List<String> tagTypes;

	/**
	 * Constructor class for new instances of users which sets the username along with predefined tag types.
	 * @param username		the username
	 */
	public User(String username) {
		this.username = username;
		this.albums = new ArrayList<Album>();
		this.tagTypes = new ArrayList<String>();
		this.tagTypes.add("location");
		this.tagTypes.add("person");
	}
	
	/**
	 * Returns the username of a user.
	 * @return		A <code>String</code> containing the username of the user.
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * Returns all the albums of the user.
	 * @return The <code>List</code> containing every album associated with the user.
	 */
	public List<Album> getAlbums() {
		return this.albums;
	}
	
	/**
	 * Returns a list of every album associated with the user.
	 * @return The <code>List</code> containing every album associated with the user.
	 */
	public List<String> getTagTypes() {
		return this.tagTypes;
	}
	
	/**
	 * Adds an album to the users albums list.
	 * @param name		A <code>String</code> containing the name of the album to be added.
	 * @return			<code>false</code> if the album is already stored with the user; <code>true</code> otherwise.
	 */
	public boolean addAlbum(String name) {
		Album a = new Album(name);
		if (this.albums.contains(a)) {
			return false;
		}
		this.albums.add(a);
		return true;
	}
	
	/**
	 * Removes an album from the users albums list.
	 * @param name		A <code>String</code> containing the name of the album to be removed.
	 * @return			<code>false</code> if the album is not stored with the user; <code>true</code> otherwise.
	 */
	public boolean delAlbum(String name) {
		Album d = new Album(name);
		if (!this.albums.contains(d)) {
			return false;
		}
		this.albums.remove(d);
		return true;
	}
	
	/**
	 * Renames an album stored in the users albums list.
	 * @param oldname		A <code>String</code> containing the name of the album to be renamed.
	 * @param newname		A <code>String</code> containing the new name of the album.
	 * @return				<code>false</code> if the album to be renamed is the same as the new name; <code>true</code> otherwise.
	 */
	public boolean renAlbum(String oldname, String newname) {
		if (oldname.equals(newname)) {
			return false;
		}
		Album old = null;
		for (Album a : this.albums) {
			if (a.getName().equals(oldname)) {
				old = a;
				break;
			}
		} 
		if (old == null) { return false; }
		old.setName(newname);
		return true;
	}
	

	/**
	 * Writes the user data to a file.
	 * @param user							User whose data will be stored.
	 * @throws FileNotFoundException		If the file cannot be found
	 * @throws IOException					If there is an IOExeption
	 */
	public static void writeUser(User user) throws FileNotFoundException, IOException {
		File myUser = new File(user.getUsername() + ".dat");
		ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(storeDir + File.separator + myUser));
				oos.writeObject(user);
				
				oos.close();
	}
	
	/**
	 * Reads the user data to a user instance
	 * @param filename						A <code>String</code> containing the filename of the user data.
	 * @throws ClassNotFoundException		If the class cannot be found
	 * @throws IOException					If there is an IOExeption
	 * @return								The user that was read
	 */
	public static User readUser(String filename) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(
		new FileInputStream(storeDir + File.separator + filename));
		User user = (User)ois.readObject();
		
		ois.close();
		return user;
	}

}
