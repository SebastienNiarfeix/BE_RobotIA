package graphe;

public class Arrete {
	
	private Sommet s1, s2;
	private Direction dir; 
	
	public Arrete(Sommet s1, Sommet s2, Direction dir) {
		this.s1 = s1;
		this.s2 = s2;
		this.dir = dir;
	}

	public Direction getDir() {
		return dir;
	}

	public void setDir(Direction dir) {
		this.dir = dir;
	}

	public Sommet getS1() {
		return s1;
	}

	public Sommet getS2() {
		return s2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dir == null) ? 0 : dir.hashCode());
		result = prime * result + ((s1 == null) ? 0 : s1.hashCode());
		result = prime * result + ((s2 == null) ? 0 : s2.hashCode());
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
		Arrete other = (Arrete) obj;
		if (dir != other.dir)
			return false;
		if (s1 == null) {
			if (other.s1 != null)
				return false;
		} else if (!s1.equals(other.s1))
			return false;
		if (s2 == null) {
			if (other.s2 != null)
				return false;
		} else if (!s2.equals(other.s2))
			return false;
		return true;
	}

	
	
}
