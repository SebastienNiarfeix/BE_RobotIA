package moteur;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import sensor.Calibre;

public class Bras implements Moteur{
	
	private LightSensor light;
	
	public Bras(LightSensor light) {
		this.light = light;
	}

	@Override
	public void setSpeed(int val) {
		Motor.A.setSpeed(val);
	}

	@Override
	public void tournerAGauche() {
		Motor.A.rotateTo(45);
		
	}
	
	public void tournerAGauche(int deg) {
		Motor.A.rotateTo(deg);
		
	}

	@Override
	public void tournerADroite() {
		Motor.A.rotateTo(-47);
		
	}
	
	public void tournerADroite(int deg) {
		Motor.A.rotateTo(-deg);
		
	}
	
	public void centrer() {
		Motor.A.rotateTo(0);
	}

	@Override
	public void arreter() {
		Motor.A.stop();
	}
	
	public boolean getMesure(Calibre cal) {
		return cal.estBon(light.getNormalizedLightValue());
	}
	
	public boolean voirSignalisation(DifferentialPilot p, Calibre cal) throws InterruptedException {
		boolean a_gauche = false;
		Motor.A.rotateTo(90, true);
		while(!a_gauche && Motor.A.isMoving()) {
			a_gauche = !getMesure(cal);
			Thread.sleep(10);
		}
		a_gauche = balayerAGauche(cal);
		this.centrer();
		boolean a_droite = false;
		if(a_gauche){
			//System.out.println("G?EIKr?GIEF");
			p.stop();
			Motor.A.rotateTo(-90, true);
			while(!a_droite && Motor.A.isMoving()) {
				a_droite = !getMesure(cal);
				Thread.sleep(10);
			}
			a_droite = balayerADroite(cal);
		}
		return a_droite && a_gauche;
	}
	
	
	public boolean balayerAGauche(Calibre cal) throws InterruptedException {
		boolean a_gauche = false;
		Motor.A.rotateTo(90, true);
		while(!a_gauche && Motor.A.isMoving()) {
			a_gauche = getMesure(cal);
			Thread.sleep(10);
		}
		this.centrer();
		return a_gauche;
	}
	
	public boolean balayerADroite(Calibre cal) throws InterruptedException {
		boolean a_droite = false;
		Motor.A.rotateTo(-90, true);
		while(!a_droite && Motor.A.isMoving()) {
			a_droite = getMesure(cal);
			Thread.sleep(10);
		}
		this.centrer();
		return a_droite;
	}
	
	
}
