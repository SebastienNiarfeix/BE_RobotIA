package test;

import java.util.LinkedList;
import java.util.ListIterator;

import graphe.Dijkstra;
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
		 
		 int cpt = 0;
		 int[] liste = {4, 9, 1, 9};
		 
		 Sommet s_curr = g.getListe().get(0); // sommet départ
		 Dijkstra dj = new Dijkstra(g, s_curr);
		 Sommet s_arr = g.getListe().get(liste[cpt++]); // sommet arrivée
		 LinkedList<Sommet> parcours = dj.getParcours(s_arr); 
		 ListIterator<Sommet> it = parcours.listIterator(parcours.size()-1);
		 
		 r2d2.setDistCible(s_curr.getPoids()/2);
		 
		 while(Button.readButtons()!= Button.ID_ESCAPE) {
			 
			if(r2d2.arrive(s_curr) && cpt == 4) {
				break;	
			}
			else if(r2d2.arrive(s_curr)){
				// X-ieme dijkstra
				 moteur.arreter();
				 Sommet s_avant_dernier = it.next();
				 s_curr.setbut(false);
				 s_curr = s_arr;
				 s_arr = g.getListe().get(liste[cpt++]);
				 dj = new Dijkstra(g, s_curr);
				 parcours = dj.getParcours(s_arr); // sommet arrivée
				 it = parcours.listIterator(parcours.size()-1);
				 r2d2.setDistCible(s_curr.getPoids()/2 - 13);
				 moteur.setDistance(0);
				 LCD.drawString("SAUVETAGE", 0, 0);
				 Thread.sleep(2000);
				 LCD.clear();
				 if(g.viensIntersect(it.previous(), s_avant_dernier)) {
					 r2d2.demisTour();
				 }
				 it.next();
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
							Direction go = g.choix(s_curr, it.previous());
							it.next();
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
							s_curr = it.previous();
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
