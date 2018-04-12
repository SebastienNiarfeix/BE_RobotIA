package test;

import lejos.nxt.*;
import sensor.Calibre;

public class Test {
	 public static void main(String[] args) throws Exception{
		 LCD.clear();
		 int speed = 100;
		 Motor.B.setSpeed(speed);
		 Motor.C.setSpeed(speed);
		 LightSensor light = new LightSensor(SensorPort.S1);
		 LightSensor light2 = new LightSensor(SensorPort.S2);
		 Calibre cal = new Calibre(0, 100);
		 cal.calibration(light);
		 while(Button.readButtons()!= Button.ID_ESCAPE) {
			LCD.clear();
			int curr = light.getNormalizedLightValue();
			int curr2 = light2.getNormalizedLightValue();
			System.out.println("gauche = " + curr + "    droite = " + curr2);
			if(cal.estBon(curr) && cal.estBon(curr2)) {
				Motor.B.forward();
				Motor.C.forward();
			}
			else if(!cal.estBon(curr)) {
				Motor.B.backward();
			}
			else{
				Motor.C.backward();
			}
			Thread.sleep(100); 
		 }
	  }
}
