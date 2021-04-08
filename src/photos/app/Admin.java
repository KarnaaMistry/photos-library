package photos.app;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;


public class Admin {
	
	/**
	 * 
	 */
	private List<User> userlist;
	
	public Admin() {
		this.userlist = new ArrayList<User>();
	}
	
	public List<User> getUserlist() {
		return this.userlist;
	}
	
	public void loadUser(User u) {
		this.userlist.add(u);
	}
	
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
	
	public String toString() {
		String str = "";
		for (User u : this.userlist) {
			str = str + u.getUsername() + "\n";
		}
		return str; 
	}
	
	
	
	
	
	

}
