package robot;

import moteur.Bras;
import moteur.Roues;
import sensor.Calibre;

public class Robot {
	
	private Roues moteur;
	private Bras bras;
	private Calibre cal;
	private int speed;
	private boolean perduD = false;
	private boolean perduG = false;
	
	public Robot(Roues moteur, Bras bras, Calibre cal, int speed) {
		this.moteur = moteur;
		this.bras = bras;
		this.cal = cal;
		this.speed = speed;
		this.moteur.setSpeed(speed);
		this.bras.setSpeed(600);
	}

	public void setPerduD(boolean perduD) {
		this.perduD = perduD;
	}

	public void setPerduG(boolean perduG) {
		this.perduG = perduG;
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
	
	public void revenirSurLigne() throws InterruptedException {
		if(perduD) {
			moteur.tournerAGauche();
		}
		else if(perduG) {
			moteur.tournerADroite();
		}
		else {
			// perdu
			if(bras.balayerAGauche(cal)) {
				setPerduD(true);
			}	
			else if(bras.balayerADroite(cal)) {
				setPerduG(true);
			}
			else {
				//totalement perdu
				moteur.reculer();
			}
		}
	}
	
	public void croissementAGauche() throws InterruptedException {
		moteur.setSpeed(50);
		moteur.avancer();
		do {
			bras.tournerAGauche();
			Thread.sleep(10);
		}while(!bras.getMesure(cal));
		bras.centrer();
		moteur.tournerAGauche();
	}
	
	public void croissementAuMilieu() {
		
	}
	
	public void IntersectionAGauche() {
		
	}
	
	public void IntersectionADroite() {
		
	}
	
	public void IntersectionToutDroit() {
		
	}

}
