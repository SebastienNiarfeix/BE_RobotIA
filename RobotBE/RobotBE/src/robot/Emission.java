package robot;

import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.LCD;

public class Emission extends Thread{
	
	private Robot r2d2;
	
	public Emission(Robot r2d2) {
		this.r2d2 = r2d2;
	}

	@Override
	public void run() {
		
		while(true) {
			DataOutputStream dis = r2d2.getBtc().openDataOutputStream();
			try {
				dis.writeInt(r2d2.getS_curr().getNum());
				dis.flush();
				Thread.sleep(2000);
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}
			LCD.drawString("J'envoie : " + r2d2.getS_curr().getNum(), 0, 3);
			LCD.refresh();
		}
		
	}

}
