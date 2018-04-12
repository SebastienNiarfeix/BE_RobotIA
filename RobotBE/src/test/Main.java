package test;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import moteur.Bras;
import moteur.Roues;
import sensor.Calibre;

public class Main {
	
	 public static void main(String[] args) throws Exception{
		 
		 LCD.clear();
		 int speed = 250;
		 boolean droite = false;
		 boolean gauche = false;
		 boolean cumDroite = false;
		 boolean cumGauche = false;
		 
		 LightSensor light = new LightSensor(SensorPort.S1);
		 Calibre cal = new Calibre(0, 50);
		 cal.calibration(light);

		 Roues moteur = new Roues();
		 Bras bras = new Bras(light);
		
		 moteur.setSpeed(speed);
		 bras.setSpeed(600);
		 
		 while(Button.readButtons()!= Button.ID_ESCAPE) {
			
			LCD.clear();
			int curr = light.getNormalizedLightValue();
			System.out.println("" + curr);
			
			if(cal.estBon(curr)) {
				// on est tjrs centré sur la ligne
				if(cumDroite && cumGauche) {
					cumDroite = false;
					cumGauche = false;
				}
				moteur.setSpeed(speed);
				moteur.avancer();
		
				bras.tournerAGauche();
			    gauche = cal.estBon(light.getNormalizedLightValue());
			    cumGauche = gauche || cumGauche;
			    
				LCD.clear();
				LCD.drawString("" + gauche, 0, 0);
				
				bras.tournerADroite();
				droite = cal.estBon(light.getNormalizedLightValue());
				cumDroite = droite || cumDroite;
				
				LCD.clear();
				LCD.drawString("" + droite, 0, 0);
				
				if(droite && gauche) {
					//signalisation
					moteur.arreter();
					break;
				}
				bras.centrer();
			}
			else if (!cal.estBon(curr)) {
				// est perdu
				moteur.arreter();
				// balayage
				if(bras.balayerAGauche(cal)) {
					moteur.tournerAGauche();
				}	
				else if(bras.balayerADroite(cal)) {
					moteur.tournerADroite();
				}
				else {
					//totalement perdu
					//moteur.reculer();
				}
			}
			Thread.sleep(10); 
		 }
	 }
}
