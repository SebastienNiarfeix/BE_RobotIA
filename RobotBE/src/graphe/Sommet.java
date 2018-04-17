package graphe;

import java.util.ArrayList;

public class Sommet {

	private int poids;
	private ArrayList<Arrete> intersections;
	
	public Sommet(int poids) {
		this.poids = poids;
	}
	
	public void ajouterIntersection(Arrete a) {
		//if(!this.intersections.contains(a)) 
			this.intersections.add(a);
	}
	
	public int getDegres() {
		return this.intersections.size();
	}

	public int getPoids() {
		return poids;
	}
	
	public void setPoids(int poids) {
		this.poids = poids;
	}

	public ArrayList<Arrete> getIntersection() {
		return intersections;
	}
	
}
