package photos.app;

import java.util.List;
import java.util.ArrayList;


public class Admin {
	
	public static List<User> userlist = new ArrayList<User>();
	
	public static boolean addUser(String username) {
		
		if (username.equals("admin")) { return false; }
		for (User u : userlist) {
			if (u.getUsername().equals(username)) {
				return false;
			}
		}
		userlist.add(new User(username));
		return true;
	}
	
	public static boolean delUser(String username) {
		User u = null;
		for (User v : userlist) {
			if (v.getUsername().equals(username)) {
				u = v;
				break;
			}
		}
		if (u == null) { return false; }
		userlist.remove(u);
		return true;
	}

}
