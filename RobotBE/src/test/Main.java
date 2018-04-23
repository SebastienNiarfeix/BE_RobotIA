package test;

import graphe.Couple;
import graphe.Direction;
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
		 boolean inter = false;
		 int cpt = 0;

		 LightSensor light = new LightSensor(SensorPort.S1);
		 Calibre cal = new Calibre(0, 100);
		 cal.calibration(light);
		 Roues moteur = new Roues();
		 Bras bras = new Bras(light);
		 Robot r2d2 = new Robot(moteur, bras, cal, 20);
		 Graphe g = new Graphe();
		 
		 Couple parcours[] = new Couple[4];
		 parcours[0] = new Couple(Direction.DROITE, g.getListe().get(2));
		 parcours[1] = new Couple(Direction.GAUCHE, g.getListe().get(3));
		 parcours[2] = new Couple(Direction.DROITE, g.getListe().get(5));
		 parcours[3] = new Couple(Direction.TOUT_DROIT, g.getListe().get(0));
		 
		 Sommet s_curr = g.getListe().get(0); // sommet depart
		// g.getListe().get(3).setbut(true); //sommet arrivé
		 
		 
		 r2d2.setDistCible(s_curr.getPoids());
		 
		 while(Button.readButtons()!= Button.ID_ESCAPE && !r2d2.arrive(s_curr)) {
			
			int curr = light.getNormalizedLightValue();
			//System.out.println("" + curr);
			LCD.clear();
			r2d2.afficherDistance();
			
			if(cal.estBon(curr)) {
				r2d2.setPerduD(false);
				r2d2.setPerduG(false);
				// on est tjrs centré sur la ligne
				if(r2d2.estSurSommet()) {
					//LCD.drawString("JE SUIS LA LIGNE", 0, 0);
					r2d2.suivreLigne();
				}
				else {
					
					if(r2d2.chercherSignalisation()) {
						inter = !inter;
						LCD.drawString("LIGNE VUE", 0, 0);
						moteur.setDistance(0);
						if(inter) {
							moteur.avancer();
							Thread.sleep(1000);
							LCD.clear();
							if(r2d2.chercherSignalisation()) {
								//deux lignes
								LCD.drawString("CROISSEMENT", 0, 0);
								//r2d2.croissementADroite();
								r2d2.setDistCible(0);
							}
							else {
								LCD.drawString("INTERSECTION", 0, 0);
								//traitement 
								if(parcours[cpt].getDir().equals(Direction.DROITE)) {
									r2d2.croissementADroite();
									r2d2.setDistCible(0);
								}
								else if(parcours[cpt].getDir().equals(Direction.GAUCHE)){
									r2d2.croissementAGauche();
									r2d2.setDistCible(0);
								}
								else {
									r2d2.setDistCible(25);
								}
							}
						}
						else {
							s_curr = parcours[cpt++].getCible();
							r2d2.setDistCible(s_curr.getPoids());
						}
					}
				}				
			}
			else if (!cal.estBon(curr)) {
				r2d2.revenirSurLigne();
			}
			Thread.sleep(10); 
		 }
		 LCD.clear();
		 LCD.drawString("LIGNE D'ARRIVEE", 0, 0);
		 moteur.arreter();
		 Button.waitForAnyPress();
	 }
}
