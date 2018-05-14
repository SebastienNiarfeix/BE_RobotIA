package graphe;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Graphe {

	private ArrayList<Sommet> liste = new ArrayList<>();
	
	public Graphe() {
		
//		this.ajouterSommet(new Sommet(200, 0, false));
//		this.ajouterSommet(new Sommet(25, 1, false));
//		this.ajouterSommet(new Sommet(23, 2, false));
//		this.ajouterSommet(new Sommet(54, 3, false));
//		this.ajouterSommet(new Sommet(110, 4, false));
//		this.ajouterSommet(new Sommet(150, 5, false));
//		creerIntersection(liste.get(1), liste.get(5), liste.get(0));
//		creerIntersection(liste.get(2), liste.get(0), liste.get(4));
//		creerIntersection(liste.get(3), liste.get(1), liste.get(2));
//		creerIntersection(liste.get(3), liste.get(4), liste.get(5));
		
		this.ajouterSommet(new Sommet(100, 0, false));
		this.ajouterSommet(new Sommet(60, 1, false));
		this.ajouterSommet(new Sommet(60, 2, false));
		this.ajouterSommet(new Sommet(160, 3, false));
		this.ajouterSommet(new Sommet(60, 4, false));
		this.ajouterSommet(new Sommet(15, 5, false));
		this.ajouterSommet(new Sommet(15, 6, false));
		this.ajouterSommet(new Sommet(15, 7, false));
		this.ajouterSommet(new Sommet(15, 8, false));
		this.ajouterSommet(new Sommet(60, 9, false));
		this.ajouterSommet(new Sommet(160, 10, false));
		this.ajouterSommet(new Sommet(15, 11, false));
		
		creerIntersection(liste.get(1), liste.get(2), liste.get(0));
		creerIntersection(liste.get(2), liste.get(1), liste.get(3));
		creerIntersection(liste.get(4), liste.get(3), liste.get(10));
		creerIntersection(liste.get(7), liste.get(5), liste.get(4));
		creerIntersection(liste.get(6), liste.get(0), liste.get(5));
		creerIntersection(liste.get(8), liste.get(9), liste.get(6));
		creerIntersection(liste.get(8), liste.get(7), liste.get(11));
		creerIntersection(liste.get(11), liste.get(10), liste.get(9));
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
			Arrete s3s2 = new Arrete(s3, s2, Direction.TOUT_DROIT);
			s1.ajouterIntersection(s1s2);
			s1.ajouterIntersection(s1s3);
			s2.ajouterIntersection(s2s1);
			s2.ajouterIntersection(s2s3);
			s3.ajouterIntersection(s3s1);
			s3.ajouterIntersection(s3s2);
			
	}
	
	public ArrayList<Sommet> getVoisins(Sommet s){
		ArrayList<Sommet> v = new ArrayList<>();
		for(ListIterator<Arrete> it = s.getIntersection().listIterator();it.hasNext();) {
			v.add(it.next().getS2());
		}
		return v;
	}
	
	public Direction choix(Sommet s1, Sommet s2) {
		ArrayList<Arrete> l = s1.getIntersection();
		Arrete cible = null;
		for(ListIterator<Arrete> it = l.listIterator();it.hasNext();) {
			Arrete curr = it.next();
			if(curr.getS2().equals(s2)) {
				cible = curr;
				break;
			}
		}
		return cible.getDir();
	}
	
	public boolean viensIntersect(Sommet cible, Sommet passe) {
		ArrayList<Arrete> l = passe.getIntersection();
		for(ListIterator<Arrete> it = l.listIterator();it.hasNext();) {
			Arrete curr = it.next();
			if(curr.getS2().equals(cible)) {
				return true;
			}
		}
		return cible.equals(passe);
	}
	
	
	public int getLongueur(List<Sommet> p) {
		int l = 0;
		for(ListIterator<Sommet> it = p.listIterator(p.size()); it.hasPrevious();){
			l += it.previous().getPoids();
		}
		return l - p.get(0).getPoids()/2;
	}
	
	public List<Sommet> parcourir (Sommet depart, List<Sommet> victimes, List<Sommet> hopitaux) {
		List<Sommet> parcours = new LinkedList<>();
		if(victimes.isEmpty()) {
			parcours.add(depart);
			return parcours;
		}
		Graphe g = new Graphe();
		int min = Integer.MAX_VALUE;
		Dijkstra dj = new Dijkstra(g, depart);
		List<Sommet> parcoursCheckMinimum = new LinkedList<>();
		List<Sommet> parcoursList = new LinkedList<>();
		List<Sommet> parcoursCheck = new LinkedList<>();
		List<Sommet> parcoursFinal = new LinkedList<>();
		List<Sommet> retour = new LinkedList<>();
		List<Sommet> victimesSauvees = new LinkedList<>();
		Sommet victime = null;
		victimesSauvees.addAll(victimes);
		for(int i =0; i<victimes.size(); i++) {
			parcoursList = new LinkedList<>();
			dj = new Dijkstra(g, depart);
			parcours = dj.getParcours(victimes.get(i));
			for(int j=0; j<victimes.size(); j++) {
				if(!victimes.get(i).equals(victimes.get(j))){
					parcoursCheckMinimum = new LinkedList<>();
					dj = new Dijkstra(g, victimes.get(i));
					parcoursCheck = dj.getParcours(victimes.get(j));
					parcoursCheck.remove(victimes.get(i));
					int longueur = this.getLongueur(parcoursCheck);
					if(longueur < min) {
						victime = victimes.get(i);
						min = longueur;
						parcoursCheckMinimum.addAll(parcoursCheck);		
						//parcoursCheckMinimum.remove(victimes.get(i));
					}
				}
			}
			min = Integer.MAX_VALUE;
			parcoursCheckMinimum.addAll(parcours);
			int longueur = this.getLongueur(parcoursCheckMinimum);
			if(longueur < min) {
				min = longueur;
				parcoursList.addAll(parcoursCheckMinimum);	
				
			}
		}
		victimesSauvees.remove(victime);
		victimesSauvees.remove(parcoursList.get(0));
		min = Integer.MAX_VALUE;
		for(int k=0; k<hopitaux.size(); k++) {
			dj = new Dijkstra(g, parcoursList.get(0));
			parcours = dj.getParcours(hopitaux.get(k));
			parcours.remove(parcoursList.get(0));
			int longueur = this.getLongueur(parcours);
			if(longueur < min) {
				parcoursFinal = new LinkedList<>();
				min = longueur;
				parcoursFinal.addAll(parcours);
				victime = hopitaux.get(k);
			}
		}
		parcoursFinal.addAll(parcoursList);
		parcoursFinal.remove(victime);
		retour.addAll(this.parcourir(victime, victimesSauvees, hopitaux));
		retour.addAll(parcoursFinal);
		return retour;
	}
	
}
