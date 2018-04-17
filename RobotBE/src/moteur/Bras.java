package moteur;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import sensor.Calibre;

public class Bras implements Moteur{
	
	private LightSensor light;
	
	public Bras(LightSensor light) {
		this.light = light;
	}

	@Override
	public void setSpeed(int val) {
		Motor.B.setSpeed(val);
	}

	@Override
	public void tournerAGauche() {
		Motor.A.rotateTo(60);
		
	}

	@Override
	public void tournerADroite() {
		Motor.A.rotateTo(-60);
		
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
	
	public boolean balayerAGauche(Calibre cal) throws InterruptedException {
		boolean a_gauche = false;
		Motor.A.rotateTo(80, true);
		while(!a_gauche && Motor.A.isMoving()) {
			a_gauche = getMesure(cal);
			Thread.sleep(10);
		}
		this.centrer();
		return a_gauche;
	}
	
	public boolean balayerADroite(Calibre cal) throws InterruptedException {
		boolean a_droite = false;
		Motor.A.rotateTo(-80, true);
		while(!a_droite && Motor.A.isMoving()) {
			a_droite = getMesure(cal);
			Thread.sleep(10);
		}
		this.centrer();
		return a_droite;
	}
	
	
}
