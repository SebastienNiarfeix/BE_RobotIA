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
		 int cpt = 0;
		 boolean droite = false;
		 boolean gauche = false;
		 boolean perduD = false;
		 boolean perduG = false;
		 
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
				perduD = false;
				perduG = false;
				// on est tjrs centré sur la ligne
				cpt = (cpt+1)%2;
				moteur.setSpeed(speed);
				moteur.avancer();
				
				if(cpt == 0) {
					//droite = false;
					bras.tournerAGauche();
				    gauche = cal.estBon(light.getNormalizedLightValue());
				    LCD.clear();
					LCD.drawString("" + gauche, 0, 0);
				}
				else {
					//gauche = false;
					bras.tournerADroite();
					droite = cal.estBon(light.getNormalizedLightValue());
					LCD.clear();
					LCD.drawString("" + droite, 0, 0);
				}
				
				if(droite && gauche) {
					//signalisation
					moteur.arreter();
					break;
				}
				bras.centrer();
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
						if(droite)
							perduD = true;
						else if(gauche)
							perduG = true;
						else { 
							moteur.reculer();
						}
					}
				}
			}
			Thread.sleep(10); 
		 }
	 }
}
