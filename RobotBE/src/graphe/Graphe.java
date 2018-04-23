package graphe;

import java.util.ArrayList;
import java.util.ListIterator;

public class Graphe {

	private ArrayList<Sommet> liste = new ArrayList<>();
	
	public Graphe() {
		
		//creation sommets
		this.ajouterSommet(new Sommet(200, false));
		this.ajouterSommet(new Sommet(25, false));
		this.ajouterSommet(new Sommet(26, false));
		this.ajouterSommet(new Sommet(60, false));
		this.ajouterSommet(new Sommet(110, false));
		this.ajouterSommet(new Sommet(170, false));
		
		//creation arretes
		creerIntersection(liste.get(1), liste.get(5), liste.get(0));
		creerIntersection(liste.get(2), liste.get(0), liste.get(4));
		creerIntersection(liste.get(3), liste.get(1), liste.get(2));
		creerIntersection(liste.get(3), liste.get(4), liste.get(5));
	}
	
	public ArrayList<Sommet> getListe (){
		return liste;
	}
	
	public void ajouterSommet(Sommet s) {
		//if(!liste.contains(s))
			liste.add(s);
	}
	
	public void creerCroissement(Sommet s1, Sommet s2) {
		Arrete a = new Arrete(s1, s2, Direction.TOUT_DROIT);
		Arrete a2 = new Arrete(s1, s2, Direction.GAUCHE);
		s1.ajouterIntersection(a);
		s1.ajouterIntersection(a2);
		s2.ajouterIntersection(a);
		s2.ajouterIntersection(a2);
		
	}
	
	// Sommet 1 => gauche / droite 
	// Sommet 2 => milieu / droite
	// Sommet 3 => milieu / gauche
	public void creerIntersection(Sommet s1, Sommet s2, Sommet s3) {
			Arrete s1s2 = new Arrete(s1, s2, Direction.GAUCHE);
			Arrete s2s1 = new Arrete(s2, s1, Direction.DROITE);
			Arrete s1s3 = new Arrete(s1, s3, Direction.DROITE);
			Arrete s3s1 = new Arrete(s3, s1, Direction.GAUCHE);
			Arrete s2s3 = new Arrete(s2, s3, Direction.TOUT_DROIT);
			Arrete s3s2 = new Arrete(s2, s3, Direction.TOUT_DROIT);
			s1.ajouterIntersection(s1s2);
			s1.ajouterIntersection(s1s3);
			s2.ajouterIntersection(s2s1);
			s2.ajouterIntersection(s2s3);
			s3.ajouterIntersection(s3s1);
			s3.ajouterIntersection(s3s2);
			
	}
	
	public Sommet choix(Sommet s, Couple c) {
		ArrayList<Arrete> l = s.getIntersection();
		Arrete cible = null;
		for(ListIterator<Arrete> it = l.listIterator();it.hasNext();) {
			Arrete curr = it.next();
			if(c.getDir().equals(curr.getDir()) && c.getCible().equals(curr.getS2()))
				cible = curr;
		}
		return cible.getS2();
	}
	
	
}
