package backend;

public class Fichier extends Element{
	int taille;
	
	public Fichier(String nom) {
		this.setNom(nom);
		this.taille = (int)Math.floor(Math.random()*100);
	}
	
	public int getTaille() {
		return taille;
	}
}
