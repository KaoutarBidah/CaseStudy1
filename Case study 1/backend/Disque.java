package backend;

import java.util.HashMap;

public class Disque{
	private String nomDisque = "C";
	private HashMap<String, String> listUsers = new HashMap<String, String>();
	private HashMap<String, String> listAdmins = new HashMap<String, String>();
	
	
	public Disque(String nom) {
		nomDisque = nom;
	}
	
	public String getNomDisque() {
		return nomDisque;
	}
	public HashMap<String, String> getUsers() {
		return listUsers;
	}
	public HashMap<String, String> getAdmins() {
		return listAdmins;
	}
	
	public boolean addAdmin(String name, String password) {
		if(this.getAdmins().get(name) == null) {
			listAdmins.put(name, password);
			return true;
		}else {
			return false;
		}
	}
	public boolean addUser(String name,String password) {
		if(this.getUsers().get(name) == null) {
			listUsers.put(name, password);
			return true;
		}else {
			return false;
		}
	}
}
