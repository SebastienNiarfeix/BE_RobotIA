package test;

import lejos.nxt.*;

public class Rotation {
	public static void main(String[] args){
		Motor.A.setSpeed(600);
		while(Button.readButtons()!= Button.ID_ESCAPE) {
			Button.waitForAnyPress();
			if(Button.readButtons() == Button.ID_RIGHT) {
				Motor.A.rotateTo(80, true);
			}
			else if(Button.readButtons() == Button.ID_LEFT) {
				Motor.A.rotateTo(-80, true);
			}
		}
	}
}
