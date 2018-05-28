package robot;

import java.util.List;

public class Donnees {
	
	private int pos_robot2 = -1;
	private List<Integer> victimes;
	
	
	public int getPos_robot2() {
		return pos_robot2;
	}
	
	public void setPos_robot2(int pos_robot2) {
		this.pos_robot2 = pos_robot2;
	}
	
	public List<Integer> getVictimes() {
		return victimes;
	}
	
	public void setVictimes(Integer ... victimes) {
		this.victimes.clear();
		for(int v : victimes) {
			this.victimes.add(v);
		}
	}
	
	public void setVictimes(List<Integer> victimes) {
		this.victimes.clear();
		this.victimes = victimes;
	}

}
