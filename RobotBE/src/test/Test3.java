package test;

import lejos.nxt.*;
import sensor.Calibre;

public class Test3 {
	 public static void main(String[] args) throws Exception{
		 LCD.clear();
		 int speed = 200;
		 boolean droite = false;
		 boolean gauche = false;
		 boolean cumDroite = false;
		 boolean cumGauche = false;
		 Motor.A.setSpeed(400);
		 Motor.B.setSpeed(speed);
		 Motor.C.setSpeed(speed);
		 LightSensor light = new LightSensor(SensorPort.S1);
		 Calibre cal = new Calibre(0, 50);
		 cal.calibration(light);
		 
		 while(Button.readButtons()!= Button.ID_ESCAPE) {
			LCD.clear();
			int curr = light.getNormalizedLightValue();
			System.out.println("" + curr);
			if(cal.estBon(curr)) {
				if(cumDroite && cumGauche) {
					cumDroite = false;
					cumGauche = false;
				}
				Motor.C.setSpeed(speed);
				Motor.B.setSpeed(speed);
				Motor.C.forward();
				Motor.B.forward();
				//regarde à droite si blanc
				Motor.A.rotate(40, false);
			    gauche = cal.estBon(light.getNormalizedLightValue());
			    cumGauche = droite || cumDroite;
				LCD.clear();
				LCD.drawString("" + droite, 0, 0);
				//regarde à gauche si blanc
				Motor.A.rotate(-80, false);
				droite = cal.estBon(light.getNormalizedLightValue());
				cumDroite = gauche || cumGauche;
				LCD.clear();
				LCD.drawString("" + gauche, 0, 0);
				if(droite && gauche) {
					//signalisation
					Motor.B.stop();
					Motor.C.stop();
					break;
				}
				Motor.A.rotate(40, false);
			}
			else if (!cal.estBon(curr)) {
				if(cumGauche) {
					//c'est qu'il est a droite de la ligne
					Motor.C.setSpeed(300);
					Motor.C.forward();
					Motor.B.backward();
				}
				else if(cumDroite) {
					//c'est qu'il est a gauche de la ligne
					Motor.B.setSpeed(300);
					Motor.B.forward();
					Motor.C.backward();
				}
				else {
					// est perdu
					Motor.C.stop();
					Motor.B.stop();
					boolean a_gauche = false;
					Motor.A.rotateTo(80, true);
					while(!a_gauche && Motor.A.isMoving()) {
						a_gauche = cal.estBon(light.getNormalizedLightValue());
						Thread.sleep(10);
					}
					Motor.A.rotateTo(0);
					if(!a_gauche) {
						boolean a_droite = false;
						Motor.A.rotateTo(-80, true);
						while(!a_droite && Motor.A.isMoving()) {
							a_droite = cal.estBon(light.getNormalizedLightValue());
							Thread.sleep(10);
						}
						Motor.A.rotateTo(0);
						if(a_droite) {
							cumDroite = true;
						}
					}
					else {
						cumGauche = true;
					}
					
				}
			}
			Thread.sleep(10); 
		 }
	  }
}
