package moteur;

import lejos.nxt.Motor;

public class Roues implements Moteur{
	
	public void setSpeed(int val) {
		Motor.B.setSpeed(val);
		Motor.C.setSpeed(val);
	}
	
	public void avancer() {
		Motor.C.forward();
		Motor.B.forward();
	}
	
	public void avancer(int speed) {
		setSpeed(speed);
		avancer();
	}
	
	public void reculer() {
		Motor.C.backward();
		Motor.B.backward();
	}
	
	public void tournerAGauche() {
		Motor.B.setSpeed(Motor.B.getSpeed() - 100);
		Motor.B.forward();
		Motor.C.forward();
	}
	
	public void tournerADroite() {
		Motor.B.setSpeed(Motor.B.getSpeed() - 100);
		Motor.B.forward();
		Motor.C.forward();
	}

	@Override
	public void arreter() {
		Motor.B.stop();
		Motor.C.stop();		
	}

}
