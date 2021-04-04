package backend;

import java.util.*;

public class Dossier extends Element {
	private Vector<Element> listeElements = new Vector<Element>(0);
	private Dossier parent;
	
	public Dossier(String nom,Dossier parent) {
		this.setNom(nom);
		this.parent = parent;
	}
	
	//----------Getters------------
	public Vector<Element> getElements() {
		return listeElements;
	}
	
	public Dossier getParent() {
		return parent;
	}
	
	public int getTaille() {
		int taille = 0;
		for (Element elt:listeElements) {
			taille += elt.getTaille();
		}
		return taille;
	}
	
	
	//-----------------Creation d'elements----------------------
	public void créer(Fichier fichier) {
		for (Element element:listeElements) {
			if ((element instanceof Fichier) && element.getNom().equals(fichier.getNom())) {
				System.out.println("A file with this name already exists!");
				return;
			}
		}
		listeElements.add(fichier);
	}
	
	public void créer(Dossier dossier) {
		for (Element element:listeElements) {
			if ((element instanceof Dossier) && element.getNom().equals(dossier.getNom())) {
				System.out.println("A folder with this name already exists!");
				return;
			}
		}
		listeElements.add(dossier);
	}
	
	public void créer(Raccourci raccourci) {
		for (Element element:listeElements) {
			if ((element instanceof Dossier) && element.getNom().equals(raccourci.getNom())) {
				System.out.println("A shortcut with this name already exists!");
				return;
			}
		}
		listeElements.add(raccourci);
	}
	
	
	//-----------------------Deplacement d'elements---------------------------------------------
	public void déplacer(Dossier destination, String nom,String type) {
		if(type.toLowerCase().equals("folder")) {
			for (Element element:destination.listeElements) {
				if ((element instanceof Dossier) && element.getNom().equals(nom)) {
					System.out.println("A folder with this name already exists in the target directory!");
					return;
				}
			}
			for (Element element:this.listeElements) {
				if ((element instanceof Dossier) && element.getNom().equals(nom)) {
					destination.créer((Dossier) element);
					this.supprimer(element);
					return;
				}
			}
		}else if(type.toLowerCase().equals("file")) {
			for (Element element:destination.listeElements) {
				if ((element instanceof Fichier) && element.getNom().equals(nom)) {
					System.out.println("A file with this name already exists in the target directory!");
					return;
				}
			}
			for (Element element:this.listeElements) {
				if ((element instanceof Fichier) && element.getNom().equals(nom)) {
					destination.créer((Fichier) element);
					this.supprimer(element);
					return;
				}
			}
		}else if(type.toLowerCase().equals("shortcut")) {
			for (Element element:destination.listeElements) {
				if ((element instanceof Raccourci) && element.getNom().equals(nom)) {
					System.out.println("A file with this name already exists in the target directory!");
					return;
				}
			}
			for (Element element:this.listeElements) {
				if ((element instanceof Raccourci) && element.getNom().equals(nom)) {
					destination.créer((Raccourci) element);
					this.supprimer(element);
					return;
				}
			}
			System.out.println("Invalid name!");
		}
	}
	
	public void supprimer(Element elt) {
		listeElements.remove(elt);
	}
	
	public void afficherElement(int indent) {
		//Affiche la structure du dossier et de ses sous-dossiers 
		System.out.println(this.getNom() + " " + this.getTaille() + "Kb <dir>");
		for (Element element:listeElements) {
			for(int i = 0;i<indent;i++) {
				System.out.print("  ");
			}
			System.out.print("|_");
			element.afficherElement(indent+1);
		}
	}
}
