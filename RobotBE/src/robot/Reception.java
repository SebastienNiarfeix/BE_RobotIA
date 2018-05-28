package robot;

import java.io.DataInputStream;
import java.io.IOException;

import lejos.nxt.LCD;

public class Reception extends Thread{
	
	private Robot r2d2;
	private Donnees d;
	
	public Reception(Robot r2d2, Donnees d) {
		this.r2d2 = r2d2;
		this.d = d;
	}

	@Override
	public void run() {
		
		LCD.clear();
		while(true) {
			DataInputStream dis = r2d2.getBtc().openDataInputStream();
			try {
				int val = dis.read();
				d.setPos_robot2(val);
				LCD.drawString("Je recois :" + val, 0, 4);
				LCD.refresh();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
