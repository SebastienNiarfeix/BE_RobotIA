package sensor;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;

public class Calibre {
	
	public int val;
	public int diff;

	public Calibre(int val, int diff) {
		this.val = val; 
		this.diff = diff;
	}
	
	public int getValeur() {
		return this.val;
	}
	
	public int getHigh() {
		return val + diff;
	}
	
	public int getLow() {
		return val - diff;
	}
	
	public void calibration(LightSensor l) throws InterruptedException {
		int calibrate = 0;
		l.setFloodlight(true);
		System.out.println("Pour Calibrer : -> entree.");
		Button.waitForAnyPress();
		while(Button.readButtons() != Button.ID_RIGHT) {
			LCD.clear();
			calibrate = l.getNormalizedLightValue();
			LCD.drawString("Val curr = " + calibrate, 0, 0);
			Thread.sleep(100);
		 }
		 Button.waitForAnyPress();
		 this.val = calibrate;
		 //LCD.clear();
	 }
	
	public boolean estBon(int n) {
		return (this.getLow() < n && n < this.getHigh());
	}
}
