package backend;

public abstract class Element {
	private String nom;
	
	
	public int getTaille() {
		return 0;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public void afficherElement(int indent) {
		System.out.println(nom + " " + this.getTaille() + "Kb");
	}
}

