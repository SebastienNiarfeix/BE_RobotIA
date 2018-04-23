package graphe;

import java.util.ArrayList;

public class Sommet {

	private int poids;
	private boolean but = false;
	private ArrayList<Arrete> intersections = new ArrayList<>();
	
	public Sommet(int poids, boolean but) {
		this.poids = poids;
		this.but = but;
	}
	
	public void ajouterIntersection(Arrete a) {
		//if(!this.intersections.contains(a)) 
			this.intersections.add(a);
	}
	
	public boolean getBut() {
		return this.but;
	}
	
	public void setbut(boolean but) {
		this.but = but;
	}

	@Override
	public String toString() {
		return "Sommet [but=" + but + ", intersections=" + intersections + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (but ? 1231 : 1237);
		result = prime * result + ((intersections == null) ? 0 : intersections.hashCode());
		result = prime * result + poids;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sommet other = (Sommet) obj;
		if (but != other.but)
			return false;
		if (intersections == null) {
			if (other.intersections != null)
				return false;
		} else if (!intersections.equals(other.intersections))
			return false;
		if (poids != other.poids)
			return false;
		return true;
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
