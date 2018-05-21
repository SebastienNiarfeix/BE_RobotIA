package test;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

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

		 LightSensor light = new LightSensor(SensorPort.S1);
		 Calibre cal = new Calibre(0, 100);
		 cal.calibration(light);
		 Roues moteur = new Roues();
		 Bras bras = new Bras(light);
		 Robot r2d2 = new Robot(moteur, bras, cal, 30);
		 Graphe g = new Graphe();
		 
		 List<Sommet> victimes = new LinkedList<>();
		 List<Sommet> hopitaux = new LinkedList<>();
		 List<Sommet> parcours = new LinkedList<>();
		 victimes.add(g.getListe().get(1));
		 victimes.add(g.getListe().get(7));
		 victimes.add(g.getListe().get(9));
		 hopitaux.add(g.getListe().get(0));
		 hopitaux.add(g.getListe().get(10));
		 parcours = g.parcourir(g.getListe().get(0), victimes, hopitaux); //parcours
   
		 int cpt = parcours.size() - 1;
		 Sommet s_curr = parcours.get(cpt);
		 
		 //LCD.drawString("--" + s_curr.getNum(), 0, 0);
		 r2d2.setDistCible(s_curr.getPoids()/2);
		 
		 while(Button.readButtons()!= Button.ID_ESCAPE) {
			 
			if(r2d2.arrive(s_curr) && cpt == 0) {
				break;	
			}
			else if(r2d2.arrive(s_curr)){
				 // hopital ou victime
				 moteur.arreter();
				 Sommet s_avant_dernier = parcours.get(cpt+1);
				 r2d2.setDistCible(s_curr.getPoids()/2 - 13);
				 moteur.setDistance(0);
				 Thread.sleep(10000);
				 LCD.clear();
				 if(g.viensIntersect(parcours.get(cpt-1), s_avant_dernier)) {
					 r2d2.demisTour();
				 }
			}
			
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
							Thread.sleep(700);
							LCD.clear();
							Direction go = g.choix(s_curr, parcours.get(cpt-1));
							if(r2d2.chercherSignalisation()) {
								//deux lignes
								LCD.drawString("PASSING PLACE", 0, 0);
								if(go.equals(Direction.DROITE)) {
									r2d2.croissementADroite();
									r2d2.setDistCible(0);
								}
								else if(go.equals(Direction.GAUCHE)){
									r2d2.croissementAGauche();
									r2d2.setDistCible(0);
								}
								else {
									r2d2.setDistCible(20);
								}
							}
							else {
								LCD.drawString("INTERSECTION", 0, 0);
								//traitement 
								if(go.equals(Direction.DROITE)) {
									r2d2.croissementADroite();
									r2d2.setDistCible(0);
								}
								else if(go.equals(Direction.GAUCHE)){
									r2d2.croissementAGauche();
									r2d2.setDistCible(0);
								}
								else {
									r2d2.setDistCible(20);
								}
							}
						}
						else {
							cpt--;
							s_curr = parcours.get(cpt);
							r2d2.setDistCible(s_curr.getPoids());
							g.getListe().get(0).setbut(true);
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
