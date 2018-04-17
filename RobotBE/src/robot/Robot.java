package robot;

import moteur.Bras;
import moteur.Roues;
import sensor.Calibre;

public class Robot {
	
	private Roues moteur;
	private Bras bras;
	private Calibre cal;
	private int speed;
	
	public Robot(Roues moteur, Bras bras, Calibre cal, int speed) {
		this.moteur = moteur;
		this.bras = bras;
		this.cal = cal;
		this.speed = speed;
		this.moteur.setSpeed(speed);
		this.bras.setSpeed(600);
	}

	public void suivreLigne() {
		moteur.setSpeed(speed);
		moteur.avancer();
	}
	
	public boolean chercherSignalisation() {
		moteur.setSpeed(50);
		moteur.avancer();
		bras.tournerADroite();
		boolean droite = bras.getMesure(cal);
		boolean gauche = false;
		if(droite) {
			moteur.arreter();
			bras.tournerAGauche();
			gauche = bras.getMesure(cal);
		}
		bras.centrer();
		return gauche && droite;
	}

}
