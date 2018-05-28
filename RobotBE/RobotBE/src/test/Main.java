package test;

import java.util.LinkedList;
import java.util.List;
import graphe.Direction;
import graphe.Graphe;
import graphe.Sommet;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import moteur.Bras;
import moteur.Roues;
import robot.Donnees;
import robot.Robot;
import sensor.Calibre;

public class Main {
	
	 public static void main(String[] args) throws Exception{
		 
		 LCD.clear();
		 boolean inter = false;
		 String nom = "Gary"; // Le robot que l'on utilise

		 LightSensor light = new LightSensor(SensorPort.S1);
		 Calibre cal = new Calibre(0, 100);
		 cal.calibration(light);
		 Roues moteur = new Roues();
		 Bras bras = new Bras(light);
		 Robot r2d2 = new Robot(moteur, bras, cal, 30);
		 Graphe g = new Graphe();
		 Donnees d = new Donnees();
		 
		 List<Sommet> victimes = new LinkedList<>();
		 List<Sommet> hopitaux = new LinkedList<>();
		 List<Sommet> parcours = new LinkedList<>();
		
		 hopitaux.add(g.getListe().get(2));
		 //hopitaux.add(g.getListe().get(11));
		 hopitaux.add(g.getListe().get(6));
		 
		 if(nom.equals("Gary2")) {
			 victimes.add(g.getListe().get(4));
			 victimes.add(g.getListe().get(3));
			 victimes.add(g.getListe().get(0));
			 parcours = g.parcourir(g.getListe().get(7), victimes, hopitaux); //parcours
			 r2d2.connecterBluetouth("Gary", d);
		 }
		 else {
			 victimes.add(g.getListe().get(8));
			 victimes.add(g.getListe().get(5));
			 parcours = g.parcourir(g.getListe().get(9), victimes, hopitaux); //parcours
			 r2d2.attendreBluetouth("Gary2", d);
		 }
   
		 int cpt = parcours.size() - 1;
		 r2d2.setS_curr(parcours.get(cpt));
		 
		 //LCD.drawString("--" + s_curr.getNum(), 0, 0);
		 r2d2.setDistCible(r2d2.getS_curr().getPoids()/2);
		 
		 while(Button.readButtons()!= Button.ID_ESCAPE) {
			 
			if(r2d2.arrive(r2d2.getS_curr()) && cpt == 0) {
				Sound.twoBeeps();
				if(victimes.size()%2 == 0) {
					Sound.twoBeeps();
				}
				break;	
			}
			else if(r2d2.arrive(r2d2.getS_curr())){
				 moteur.arreter();
				 // hopital ou victime
				 if(victimes.contains(r2d2.getS_curr())) {
					 r2d2.getS_curr().setbut(false);
					 Sound.beep();
					 for(Sommet s : hopitaux)
						 s.setbut(true);
				 }
				 else {
					 r2d2.getS_curr().setbut(false);
					 Sound.twoBeeps();
					 Sound.twoBeeps();
				 }
				 Sommet s_avant_dernier = parcours.get(cpt+1);
				 r2d2.setDistCible(r2d2.getS_curr().getPoids()/2 - 13);
				 moteur.setDistance(0);
				 Thread.sleep(2000);
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
						// -1 fini circuit
						if(d.getPos_robot2() == -1)
						{
							//verifierCollision
							if(nom.equals("Gary2")) {
								while(g.viensIntersect(g.getListe().get(d.getPos_robot2()), parcours.get(cpt-1))) {
									moteur.arreter();
									Thread.sleep(3000);
								}
							}
							else if(parcours.get(cpt-2).equals(g.getListe().get(d.getPos_robot2()))) {
								//changer le parcours
								while(true) {
									moteur.arreter();
									Thread.sleep(3000);
								}
							}
						}
						moteur.avancer();
						if(inter) {
							moteur.avancer();
							Thread.sleep(700);
							LCD.clear();
							Direction go = g.choix(r2d2.getS_curr(), parcours.get(cpt-1));
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
							r2d2.setS_curr(parcours.get(cpt));
							r2d2.setDistCible(r2d2.getS_curr().getPoids());
							//g.getListe().get(0).setbut(true);
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
		 r2d2.setS_curr(new Sommet(0, -1, false));
		 moteur.arreter();
		 Button.waitForAnyPress();
	 }
}
