package test;


import lejos.nxt.*;

public class test2 {
	 public static void main(String[] args) throws Exception{
		 LCD.clear();
		 int speed = 100;
		 Motor.B.setSpeed(speed);
		 Motor.C.setSpeed(speed);
		 LightSensor light = new LightSensor(SensorPort.S1);
		 light.setFloodlight(true);
		 Button.waitForAnyPress();
		 while(Button.readButtons()!= Button.ID_ESCAPE) {
			LCD.clear();
			Motor.B.forward();
			Motor.C.forward();
			LCD.drawString(""+ light.readValue(),0,0);
			if(light.readValue() > 49) {
				//Motor.C.setSpeed(360);
				Thread.sleep(2000);
				double nombreAleatoire = Math.random();
				if(nombreAleatoire > 0.5) {
					Motor.B.stop();
					Motor.C.rotate(360, false);
				}else {
					Motor.C.stop();
					Motor.B.rotate(360, false);
				}
			}
			Thread.sleep(100); 
		 }
		 
//		 while(Button.readButtons()!= Button.ID_ENTER) {
//			 LCD.drawString(""+ light.readValue(),0,0);
//			 Thread.sleep(20);
//		 }
		 //Button.waitForAnyPress();
	  }
}