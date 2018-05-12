package moteur;

import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;


public class Roues implements Moteur{
	
	private DifferentialPilot pilot = new DifferentialPilot(5.6f, 14.5f, Motor.B, Motor.C); 
	private float distance = 0;
	
	public Roues() {
		pilot.setRotateSpeed(100);
	}

	public DifferentialPilot getPilot() {
		return pilot;
	}

	public void setSpeed(int val) {
		pilot.setTravelSpeed(val);
	}
	
	public void avancer() {
		this.distance += pilot.getMovement().getDistanceTraveled();
		pilot.travel(80, true);
	}
	
	public void avancer(int d) {
		pilot.travel(d);
	}
	
	public void voyager(int distance) {
		pilot.travel(distance, true);
	}
	
	public void reculer() {
		pilot.travel(-80, true);
	}
	
	public void tournerAGauche() {
		this.distance += pilot.getMovement().getDistanceTraveled();
		pilot.rotateLeft();
	}
	
	public void tournerADroite() {
		this.distance += pilot.getMovement().getDistanceTraveled();
		pilot.rotateRight();
	}
	
	public void arc(int dir) {
		if(dir == 0)
			pilot.arc(-15, -90);
		else
			pilot.arc(15, 90);
	}
	
	public void demisTour() {
		pilot.rotate(180);
	}
	
	@Override
	public void arreter() {
		pilot.setAcceleration((int) (9999)); 
		pilot.stop();	
		
	}
	
	public void setDistance(float dist) {
		this.distance = dist;
	}
	
	public float getDistance() {
		return distance;
	}
	
	public void afficher() {
		LCD.drawString("" + distance, 0, 4);
	}

}
