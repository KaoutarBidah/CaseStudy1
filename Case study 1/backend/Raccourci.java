package backend;

public class Raccourci extends Element{
	private Dossier destination;
	
	public Raccourci(Dossier destination, String nom) {
		this.setNom(nom);
		this.destination = destination;
	}
	
	public Dossier ouvrir() {
		return destination;
	}
	
	public void afficherElement(int indent) {
		//Affiche la structure du dossier et de ses sous-dossiers 
		System.out.println(this.getNom() + " " + this.getTaille() + "Kb <shortcut>");
	}
}
