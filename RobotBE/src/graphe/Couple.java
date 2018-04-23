package graphe;

public class Couple {
	
	private Direction dir;
	private Sommet cible;
	
	public Couple(Direction dir, Sommet cible) {
		super();
		this.dir = dir;
		this.cible = cible;
	}

	public Direction getDir() {
		return dir;
	}

	public Sommet getCible() {
		return cible;
	}
	

}
