package photos.app;

import java.util.List;
import java.util.ArrayList;

/**
 * Admin is the class responsible for handling admin functions and storing relavant user information.
 * 
 * @author Fin Herbig
 * @author Karnaa Mistry
 */
public class Admin {
	
	/**
	 * The list of all registered users.
	 * @see User
	 */
	private List<User> userlist;
	
	/**
	 * Constructor for new instances of Admin.
	 */
	public Admin() {
		this.userlist = new ArrayList<User>();
	}
	
	/**
	 * A function to return every user registered with the admin.
	 * @return		A list of every registered user.
	 */
	public List<User> getUserlist() {
		return this.userlist;
	}
	
	/**
	 * Adds an already existing user to the userList.
	 * @param u		The user to be added.
	 * @see User
	 */
	public void loadUser(User u) {
		this.userlist.add(u);
	}
	
	/**
	 * Adds a new user using a username to the userList given that 
	 * @param username		A <code>String</code> representing the username of the user to be added.
	 * @return				<code>false</code> if this user already exists in userList; <code>true</code> otherwise.
	 * @see User
	 */
	public boolean addUser(String username) {
		
		if (username.length() < 1) { return false; } 
		if (username.equals("admin")) { return false; }
		for (User u : this.userlist) {
			if (u.getUsername().equals(username)) {
				return false;
			}
		}
		this.userlist.add(new User(username));
		return true;
	}
	
	/**
	 * Deletes a user using a given username.
	 * @param username		A <code>String</code> representing the username of the user to be deleted.
	 * @return				<code>false</code> if this user does not exist in userList; <code>true</code> otherwise.
	 * @see User
	 */
	public boolean delUser(String username) {
		User u = null;
		for (User v : this.userlist) {
			if (v.getUsername().equals(username)) {
				u = v;
				break;
			}
		}
		if (u == null) { return false; }
		this.userlist.remove(u);
		return true;
	}
	
	/**
	 * Prints the username of every user in the userList.
	 */
	public String toString() {
		String str = "";
		for (User u : this.userlist) {
			str = str + u.getUsername() + "\n";
		}
		return str; 
	}
	
	
	
	
	
	

}
