package test;

import graphe.Graphe;
import graphe.Sommet;
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
		 boolean sommet = true;

		 LightSensor light = new LightSensor(SensorPort.S1);
		 Calibre cal = new Calibre(0, 50);
		 cal.calibration(light);
		 Roues moteur = new Roues();
		 Bras bras = new Bras(light);
		 Robot r2d2 = new Robot(moteur, bras, cal, 200);
		 //Graphe g = new Graphe();
		 
		 //Sommet s = g.getListe().get(0);
		 
		 while(Button.readButtons()!= Button.ID_ESCAPE) {
			
			int curr = light.getNormalizedLightValue();
			//System.out.println("" + curr);
			
			if(cal.estBon(curr)) {
				// on est tjrs centré sur la ligne
				if(!sommet) {
					r2d2.suivreLigne();
				}
				else {
					if(r2d2.chercherSignalisation()) {
						
						LCD.drawString("LIGNE VUE", 0, 0);
						moteur.avancer();
						Thread.sleep(1500);
						LCD.clear();
						if(r2d2.chercherSignalisation()) {
							//deux lignes
							LCD.drawString("CROISSEMENT", 0, 0);
							sommet = false;
							r2d2.croissementAGauche();
						}
						else {
							LCD.drawString("INTERSECTION", 0, 0);
							sommet = false;
							//traitement 
							r2d2.croissementAGauche();
						}
					}
				}				
			}
			else if (!cal.estBon(curr)) {
				r2d2.revenirSurLigne();
			}
			Thread.sleep(10); 
		 }
	 }
}
