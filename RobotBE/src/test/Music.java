package test;

import java.util.ArrayList;

import graphe.Graphe;
import graphe.Sommet;
import lejos.nxt.Button;


public class Music {
	public static void main(String[] args) throws InterruptedException {
		 Button.waitForAnyPress();
		 Graphe g = new Graphe();
		 ArrayList<Sommet> maListe = g.getListe();
		 for(int i = 0; i < maListe.size(); i++) {
			 System.out.println(maListe.get(i));
			 Button.waitForAnyPress();
		 }
	}
}
