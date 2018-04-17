package graphe;

import java.util.ArrayList;

public class Graphe {

	private ArrayList<Sommet> liste = new ArrayList<>();
	
	public Graphe() {
		
		//creation sommets
		for(int i = 0; i < 8; i++) {
			this.ajouterSommet(new Sommet(i));
		}
		
		//creation arretes
		creerCroissement(liste.get(0), liste.get(1));
		creerIntersection(liste.get(1), liste.get(2), liste.get(3));
		creerIntersection(liste.get(3), liste.get(4), liste.get(5));
		creerIntersection(liste.get(4), liste.get(5), liste.get(6));
		creerIntersection(liste.get(2), liste.get(6), liste.get(7));	
	}
	
	public ArrayList<Sommet> getListe (){
		return liste;
	}
	
	public void ajouterSommet(Sommet s) {
		//if(!liste.contains(s))
			liste.add(s);
	}
	
	public void creerCroissement(Sommet s1, Sommet s2) {
		for(int i = 0; i < 2; i++) {
			Arrete a = new Arrete(s1, s2, "");
			s1.ajouterIntersection(a);
			s2.ajouterIntersection(a);
		}
	}
	
	public void creerIntersection(Sommet s1, Sommet s2, Sommet s3) {
			Arrete a1 = new Arrete(s1, s2, "");
			Arrete a2 = new Arrete(s1, s3, "");
			Arrete a3 = new Arrete(s2, s3, "");
			s1.ajouterIntersection(a1);
			s1.ajouterIntersection(a2);
			s2.ajouterIntersection(a1);
			s2.ajouterIntersection(a3);
			s3.ajouterIntersection(a2);
			s3.ajouterIntersection(a3);
	}
	
	
}
