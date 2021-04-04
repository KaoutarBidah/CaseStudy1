package frontend;

import backend.Disque;

public class Personne {
	private String username;
	private String password;
	
	public void setCredentials(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public boolean authentifier(Disque disque, boolean asAdmin) {
		if(asAdmin) {
			if(disque.getAdmins().get(username) == null || !disque.getAdmins().get(username).equals(password)) {
				return false;
			}else {
				return true;
			}
		}else {
			if(disque.getUsers().get(username) == null || !disque.getUsers().get(username).equals(password)) {
				return false;
			}else {
				return true;
			}
		}
	}
}
