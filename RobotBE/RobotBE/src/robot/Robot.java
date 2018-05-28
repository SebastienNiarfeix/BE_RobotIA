package robot;

import java.util.List;

import javax.bluetooth.RemoteDevice;

import graphe.Graphe;
import graphe.Sommet;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
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
	private Sommet s_curr;
	private BTConnection btc;

	public Robot(Roues moteur, Bras bras, Calibre cal, int speed) {
		this.moteur = moteur;
		this.bras = bras;
		this.cal = cal;
		this.speed = speed;
		this.moteur.setSpeed(speed);
		this.bras.setSpeed(600);
	}
	
	public BTConnection getBtc() {
		return btc;
	}

	public Sommet getS_curr() {
		return s_curr;
	}

	public void setS_curr(Sommet s_curr) {
		this.s_curr = s_curr;
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
	
	public void attendreBluetouth(String name, Donnees d) {
		 LCD.drawString("Attente Connection...", 0, 0);
    	 btc = Bluetooth.waitForConnection();
		 if (btc == null) {
			 LCD.clear();
			 LCD.drawString("Connect fail", 0, 0);
			 Button.waitForAnyPress();
			 System.exit(1);
		 }
		 LCD.clear();
		 LCD.drawString("connecté", 0, 0);
		 Emission em = new Emission(this);
		 em.start();
		 Reception rec = new Reception(this, d);
		 rec.start();
	
	}
	
	public void connecterBluetouth(String name, Donnees d) {
		 LCD.drawString("Connection...", 0, 0);
		 RemoteDevice btrd = Bluetooth.getKnownDevice(name);
		 if (btrd == null) {
			 LCD.clear();
			 LCD.drawString("No such device", 0, 0);
		 	Button.waitForAnyPress();
		 	System.exit(1);
		 }
		 btc = Bluetooth.connect(btrd);
		 if (btc == null) {
			 LCD.clear();
			 LCD.drawString("Connect fail", 0, 0);
			 Button.waitForAnyPress();
			 System.exit(1);
		 }
		 LCD.clear();
		 LCD.drawString("connecté", 0, 0);
		 Reception rec = new Reception(this, d);
		 rec.start();
		 Emission em = new Emission(this);
		 em.start();
		 
	}
	
	public void gererConflit(List<Sommet> parcours, List<Sommet> parcours2, Graphe g, Donnees d){
		
	}

}
