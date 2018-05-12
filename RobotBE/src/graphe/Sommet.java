package graphe;

import java.util.ArrayList;

public class Sommet {

	private int poids;
	private int num;
	private boolean but = false;
	private ArrayList<Arrete> intersections = new ArrayList<>();
	
	public Sommet(int poids, int num, boolean but) {
		this.poids = poids;
		this.num = num;
		this.but = but;
	}
	
	public void ajouterIntersection(Arrete a) {
		//if(!this.intersections.contains(a)) 
			this.intersections.add(a);
	}
	
	public boolean getBut() {
		return this.but;
	}
	
	public int getNum() {
		return this.num;
	}
	
	public void setbut(boolean but) {
		this.but = but;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + num;
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
		if (num != other.num)
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
