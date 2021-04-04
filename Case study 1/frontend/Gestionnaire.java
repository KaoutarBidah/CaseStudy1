package frontend;

import java.util.Scanner;
import backend.*;

public class Gestionnaire {

	public static void main(String[] args) {
		
		Disque disque = new Disque("C");
		disque.addAdmin("admin", "admin");
		
		
		Personne currentUser = null, attempt;
		
		Dossier root = new Dossier("root", null);
		Dossier current = root, temp;
		String username,password;
		String choice,type;
		String[] parts;
		boolean error;
		
		Scanner input = new Scanner(System.in);
		
		do {
			choice = "";
			//----------------Authentification-----------------------
			while(currentUser == null) {
				System.out.println("Welcome! To access your files in disk "+disque.getNomDisque()+", please login");
				System.out.println("Login as admin? (y/n)");
				choice = input.nextLine();
				if(choice.toLowerCase().equals("y")) {
					System.out.println("Username: ");
					username = input.nextLine();
					System.out.println("Password: ");
					password = input.nextLine();
					attempt = new Admin(username, password);
					if(attempt.authentifier(disque, true)) {
						currentUser = attempt;
					}else {
						System.out.println("Login failed!");
					}
				}else if(choice.toLowerCase().equals("n")) {
					System.out.println("Username: ");
					username = input.nextLine();
					System.out.println("Password: ");
					password = input.nextLine();
					attempt = new User(username, password);
					if(attempt.authentifier(disque, false)) {
						currentUser = attempt;
					}else {
						System.out.println("Login failed!");
					}
				}
			}
			//--------------------------------------------------------
			System.out.print(">");
			choice = input.nextLine();
			
			if(choice.toLowerCase().equals("dir")) {
				//Afficher les elements du dossier courant
				current.afficherElement(0);
				continue;
			}
			if(choice.toLowerCase().equals("file")) {
				//Creer un fichier
				System.out.print("File name: ");
				choice = input.nextLine();
				current.créer(new Fichier(choice));
				continue;
			}
			if(choice.toLowerCase().equals("folder")) {
				//Creer un dossier
				System.out.print("Folder name: ");
				choice = input.nextLine();
				current.créer(new Dossier(choice,current));
				continue;
			}
			if(choice.toLowerCase().equals("shortcut")) {
				//Creer un raccourci
				System.out.print("Which folder in this directory would you like to create a shortcut to? ");
				choice = input.nextLine();
				error = true;
				for(Element elt: current.getElements()) {
					if ((elt instanceof Dossier) && elt.getNom().equals(choice)) {
						System.out.print("Shortcut name: ");
						choice = input.nextLine();
						current.créer(new Raccourci((Dossier)elt, choice));
						error = false;
						break;
					}
				}
				if(error) {
					System.out.print("No such folder!\n");
				}
				continue;
			}
			if(choice.toLowerCase().equals("goto")) {
				System.out.print("Which shortcut would you like to open? ");
				choice = input.nextLine();
				error = true;
				for(Element elt: current.getElements()) {
					if ((elt instanceof Raccourci) && elt.getNom().equals(choice)) {
						current = ((Raccourci)elt).ouvrir();
						error = false;
						break;
					}
				}
				if(error) {
					System.out.print("No such shortcut!");
				}
				continue;
			}
			if(choice.toLowerCase().equals("mv")) {
				//Deplacer les elements
				System.out.print("File, folder or shortcut? ");
				type = input.nextLine();
				if(!(type.toLowerCase().equals("file") || type.toLowerCase().equals("folder") || type.toLowerCase().equals("shortcut"))) {
					System.out.print("Invalid input!");
				}else {
					System.out.print("Destination folder (type '..' to go to parent dir): ");
					choice = input.nextLine();
					parts = choice.split("/");
					
					temp = current;
					error = true;
					
					for(String part: parts){
						error = true;
						if(part.equals(".")) {
							error = false;
							continue;
						}
						if(part.equals("..")) {
							error = false;
							if(temp.getParent() != null) {
								temp = temp.getParent();
							}
							continue;
						}
						for(Element elt: temp.getElements()) {
							if ((elt instanceof Dossier) && elt.getNom().equals(part)) {
								temp = (Dossier)elt;
								error = false;
								break;
							}
						}
						if(error) {
							break;
						}
					}
					
					if(error) {
						System.out.print("Something went wrong!\n");
					}else {
						//Si le type est correct et on trouve le dossier
						System.out.print("Name of element to move: ");
						choice = input.nextLine();
						current.déplacer(temp, choice, type);
					}	
				}
				continue;
			}
			if(choice.toLowerCase().equals("cd")) {
				//Changer le dossier courant
				System.out.print("Destination folder (type '..' to go to parent dir): ");
				choice = input.nextLine();
				parts = choice.split("/");
				
				temp = current;
				error = true;
				
				for(String part: parts){
					error = true;
					if(part.equals(".")) {
						error = false;
						continue;
					}
					if(part.equals("..")) {
						error = false;
						if(temp.getParent() != null) {
							temp = temp.getParent();
						}
						continue;
					}
					for(Element elt: temp.getElements()) {
						if ((elt instanceof Dossier) && elt.getNom().equals(part)) {
							temp = (Dossier)elt;
							error = false;
							break;
						}
					}
					if(error) {
						break;
					}
				}
				
				if(error) {
					System.out.print("No such folder!\n");
				}else {
					current = temp;
				}
				continue;
			}
			else if(choice.toLowerCase().equals("delete")) {
				System.out.print("File, folder or shortcut? ");
				type = input.nextLine();
				if(!(type.toLowerCase().equals("file") || type.toLowerCase().equals("folder") || type.toLowerCase().equals("shortcut"))) {
					System.out.println("Invalid input!");
				}else {
					System.out.print("Name of element to delete: ");
					choice = input.nextLine();
					error = true;
					if(type.toLowerCase().equals("file")) {
						for(Element elt: current.getElements()) {
							if ((elt instanceof Fichier) && elt.getNom().equals(choice)) {
								System.out.print("Are you sure? (y/n) ");
								choice = input.nextLine();
								if(choice.toLowerCase().equals("y")) {	
									current.supprimer(elt);
								}
								error = false;
								break;
							}
						}
					}else if (type.toLowerCase().equals("folder")) {
						for(Element elt: current.getElements()) {
							if ((elt instanceof Dossier) && elt.getNom().equals(choice)) {
								System.out.print("Are you sure? (y/n) ");
								choice = input.nextLine();
								if(choice.toLowerCase().equals("y")) {	
									current.supprimer(elt);
								}
								error = false;
								break;
							}
						}
					}else if (type.toLowerCase().equals("shortcut")) {
						for(Element elt: current.getElements()) {
							if ((elt instanceof Raccourci) && elt.getNom().equals(choice)) {
								System.out.print("Are you sure? (y/n) ");
								choice = input.nextLine();
								if(choice.toLowerCase().equals("y")) {	
									current.supprimer(elt);
								}
								error = false;
								break;
							}
						}
					}
					if(error) {
						System.out.print("Invalid name!");
					}
				}
			}
			else if(choice.toLowerCase().equals("disconnect")) {
				currentUser = null;
			}
			else if(choice.toLowerCase().equals("adduser")) {
				if(currentUser instanceof Admin) {
					System.out.println("Username: ");
					username = input.nextLine();
					System.out.println("Password: ");
					password = input.nextLine();
					((Admin)currentUser).inscrireUser(disque,username, password);
				}else {
					System.out.println("Not logged in as administrator!");
				}
			}
			else if(!choice.toLowerCase().equals("exit") && !choice.toLowerCase().equals("")) {
				System.out.println("Invalid command!");
			}
		}while(!choice.toLowerCase().equals("exit"));
		
		input.close();
	}

}
