package graphe;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Dijkstra {

	private Graphe g;
	private int[] distance;
	private Sommet[] pred;
	private Sommet sdeb;
	private ArrayList<Sommet> q = new ArrayList<>();
	private LinkedList<Sommet> parcours = new LinkedList<>();
	
	public Dijkstra(Graphe g, Sommet s) {
		this.sdeb = s;
		this.g = g;
		distance = new int[g.getListe().size()];
		pred = new Sommet[g.getListe().size()];
		for(int i = 0; i < g.getListe().size(); i++) {
			distance[i] = Integer.MAX_VALUE;
		}
		distance[s.getNum()] = s.getPoids();
		this.q.addAll(g.getListe());
		this.run();
	}
	
	/*public Sommet minDistance() {
		int mini = Integer.MAX_VALUE; 
		Sommet ret = null;
		for(ListIterator<Sommet> it = q.listIterator();it.hasNext();) {
			Sommet curr = it.next();
			if(distance[curr.getNum()] < mini) {
				mini = distance[curr.getNum()];
				ret = curr;
				
			}
		}
		return ret;
	}*/
	
	public Sommet miniDistance(){
		int mini = Integer.MAX_VALUE; 
		//System.out.println("OY");
		Sommet ret = null;
		for(Sommet s : q){
			//System.out.println("" +s.getNum()+" = " + s.getPoids() + " /// "+ distance[s.getNum()]);
			if(distance[s.getNum()] < mini){
				mini = distance[s.getNum()];
				ret = s;
			}
		}
		return ret;
	}
	
	private void majDistance(Sommet s1, Sommet next) {
		int ind = next.getNum();
		if(distance[ind] > distance[s1.getNum()] + next.getPoids()) {
			distance[ind] = distance[s1.getNum()] + next.getPoids();
			pred[ind] = s1; 
			//sSystem.out.println(pred[ind].getNum());
			
		}
	}
	
	public void run() {
		Sommet s1;
		while(!q.isEmpty()) {
			s1 = miniDistance();
			if(s1 == null){
				break;
			}
			q.remove(s1);
			for(Sommet s2 : this.g.getVoisins(s1)) {
			//for(ListIterator<Sommet> it = this.g.getVoisins(s1).listIterator();it.hasNext();) {
			//	Sommet curr = it.next();
				//System.out.println("--"+s2.getNum());
				majDistance(s1, s2);
			}
		}
	}
	
	public LinkedList<Sommet> getParcours(Sommet but){
		Sommet curr = but;		
		//curr.setbut(true);
		while(this.sdeb.getNum() != curr.getNum()) {
			parcours.add(curr);
			curr = pred[curr.getNum()];
		}
		parcours.add(curr);
		return parcours;
	}
	
	public static void main(String[]args){
		Graphe g = new Graphe();
		List<Sommet> victimes = new LinkedList<>();
		List<Sommet> hopitaux = new LinkedList<>();
		List<Sommet> parcours = new LinkedList<>();
		victimes.add(g.getListe().get(1));
		victimes.add(g.getListe().get(7));
		victimes.add(g.getListe().get(9));
		hopitaux.add(g.getListe().get(0));
		hopitaux.add(g.getListe().get(10));
		parcours = g.parcourir(g.getListe().get(0), victimes, hopitaux); 
		
		for(Sommet s : parcours)
			System.out.println(s.getNum());
   
	}
}