package photos.app;

import java.io.*;
import java.util.List;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class User implements Serializable {
	
	public static final String storeDir = "data";
	
	/**
	 * yea
	 */
	private static final long serialVersionUID = 2218609123236918959L;
	private String username;
	private List<Album> albums;
	private List<String> tagTypes;
	
	//public transient List<Image> userImages;
	
	public User(String username) {
		this.username = username;
		this.albums = new ArrayList<Album>();
		this.tagTypes = new ArrayList<String>();
		this.tagTypes.add("location");
		this.tagTypes.add("person");
		//this.userImages = new ArrayList<Image>();
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public List<Album> getAlbums() {
		return this.albums;
	}
	
	public List<String> getTagTypes() {
		return this.tagTypes;
	}
	
	public boolean addAlbum(String name) {
		Album a = new Album(name);
		if (this.albums.contains(a)) {
			return false;
		}
		this.albums.add(a);
		return true;
	}
	
	public boolean delAlbum(String name) {
		Album d = new Album(name);
		if (!this.albums.contains(d)) {
			return false;
		}
		this.albums.remove(d);
		return true;
	}
	
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
	
	//public List<Image> getUserImages() {
	//	return this.userImages;
	//}
	
	public static void writeUser(User user) throws FileNotFoundException, IOException {
		File myUser = new File(user.getUsername() + ".dat");
		ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(storeDir + File.separator + myUser));
				oos.writeObject(user);
				
				oos.close();
	}
	
	public static User readUser(String filename) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(
		new FileInputStream(storeDir + File.separator + filename));
		User user = (User)ois.readObject();
		
		ois.close();
		return user;
	}

}
