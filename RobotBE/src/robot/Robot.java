package robot;

import graphe.Sommet;
import moteur.Bras;
import moteur.Roues;
import sensor.Calibre;

public class Robot {
	
	private Roues moteur;
	private Bras bras;
	private Calibre cal;
	private int speed;
	private float distCible = 0;
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

	public float getDistCible() {
		return distCible;
	}

	public void setDistCible(float distCible) {
		this.distCible = distCible;
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
	
	public boolean chercherSignalisation() throws InterruptedException {
		moteur.setSpeed(2);
		moteur.avancer();
		return bras.voirSignalisation(moteur.getPilot(), cal);
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
			moteur.setSpeed(3);;
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
		moteur.setSpeed(speed);
		moteur.arc(1);
		moteur.setSpeed(2);
		moteur.reculer();
		do {
			bras.tournerAGauche(90);
			Thread.sleep(10);
		}while(!bras.getMesure(cal));
		bras.centrer();
		moteur.reculer();
		Thread.sleep(100);
	}
	
	public void croissementADroite() throws InterruptedException {
		moteur.setSpeed(speed);
		moteur.arc(0);
		moteur.setSpeed(2);
		moteur.reculer();
		do {
			bras.tournerADroite(90);
			Thread.sleep(10);
		}while(!bras.getMesure(cal));
		bras.centrer();
		moteur.reculer();
		Thread.sleep(100);
	}
	
	public void passingPlaceGauche() throws InterruptedException {
		do {
			bras.tournerAGauche(90);
			Thread.sleep(10);
		}while(!bras.getMesure(cal));
		bras.centrer();
	}
	
	public void afficherDistance() {
		moteur.afficher();
	}
	
	public boolean estSurSommet() {
		return distCible > moteur.getDistance();
	}

	public boolean arrive(Sommet s) {
		return s.getBut() && (s.getPoids()/2 < moteur.getDistance());
	}

	public void demisTour() throws InterruptedException {
		moteur.demisTour();
	}

}
