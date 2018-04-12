package graphe;

public class Arrete {
	
	private Sommet s1, s2;
	private String nom; 
	
	public Arrete(Sommet s1, Sommet s2, String nom) {
		this.s1 = s1;
		this.s2 = s2;
		this.nom = nom;
	}

	public String getnom() {
		return nom;
	}

	public void setnom(String nom) {
		this.nom = nom;
	}

	public Sommet getS1() {
		return s1;
	}

	public Sommet getS2() {
		return s2;
	}
	
	
}
