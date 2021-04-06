package photos.app;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class User implements Serializable {
	
	public static final String storeDir = "data";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2218609123236918959L;
	private String username;
	private List<Album> albums;
	
	public User(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public List<Album> getAlbums() {
		return this.albums;
	}
	
	public static void writeUser(User user) throws FileNotFoundException, IOException {
		File myUser = new File(user.getUsername() + ".dat");
		myUser.createNewFile();
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
