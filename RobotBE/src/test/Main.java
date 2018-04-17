package test;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import moteur.Bras;
import moteur.Roues;
import robot.Robot;
import sensor.Calibre;

public class Main {
	
	 public static void main(String[] args) throws Exception{
		 
		 LCD.clear();
		 boolean perduD = false;
		 boolean perduG = false;
		 boolean sommet = true;
		 
		 LightSensor light = new LightSensor(SensorPort.S1);
		 Calibre cal = new Calibre(0, 50);
		 cal.calibration(light);
		 Roues moteur = new Roues();
		 Bras bras = new Bras(light);
		 Robot r2d2 = new Robot(moteur, bras, cal, 200);
		 
		 while(Button.readButtons()!= Button.ID_ESCAPE) {
			
			int curr = light.getNormalizedLightValue();
			//System.out.println("" + curr);
			
			if(cal.estBon(curr)) {
				perduD = false;
				perduG = false;
				// on est tjrs centré sur la ligne
				if(!sommet) {
					r2d2.suivreLigne();
				}
				else {
					if(r2d2.chercherSignalisation()) {
						moteur.avancer();
						Thread.sleep(1500);
						if(r2d2.chercherSignalisation()) {
							//deux lignes
							LCD.drawString("RENCONTRE", 0, 0);
							sommet = false;
							//traitement 
						}
						else {
							//traitement 
							LCD.drawString("INTERSECTION", 0, 0);
							sommet = false;
						}
					}
				}				
			}
			else if (!cal.estBon(curr)) {
				if(perduD) {
					moteur.tournerAGauche();
				}
				else if(perduG) {
					moteur.tournerADroite();
				}
				else {
					// perdu
					if(bras.balayerAGauche(cal)) {
						perduD = true;
					}	
					else if(bras.balayerADroite(cal)) {
						perduG = true;
					}
					else {
						//totalement perdu
						moteur.reculer();
					}
				}
			}
			Thread.sleep(10); 
		 }
	 }
}
