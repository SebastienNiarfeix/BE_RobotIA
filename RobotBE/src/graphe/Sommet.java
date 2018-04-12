package graphe;

import java.util.ArrayList;

public class Sommet {

	private int poids;
	private ArrayList<Arrete> routes;
	
	public Sommet(int poids) {
		this.poids = poids;
	}
	
	public void ajouterChemin(Arrete a) {
		if(!this.routes.contains(a)) 
			this.routes.add(a);
	}
	
	public int getDegres() {
		return this.routes.size();
	}

	public int getPoids() {
		return poids;
	}

	public ArrayList<Arrete> getRoutes() {
		return routes;
	}
	
}
