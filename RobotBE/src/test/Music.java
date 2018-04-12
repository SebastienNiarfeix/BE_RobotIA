package test;

import lejos.nxt.*;


public class Music {
	public static void main(String[] args) throws InterruptedException{
		 LightSensor light = new LightSensor(SensorPort.S1);
		 while(Button.readButtons()!= Button.ID_ENTER) {
			 LCD.drawString(""+ light.readValue(),0,0);
			 Thread.sleep(20);
		 }
	}
}
