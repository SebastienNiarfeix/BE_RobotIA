package graphe;


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
	
}
