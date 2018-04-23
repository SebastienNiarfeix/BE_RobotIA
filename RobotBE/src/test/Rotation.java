package test;

import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;

public class Rotation {
	
	public static void main(String[] args){
		
		Motor.A.setSpeed(600);
		DifferentialPilot pilot = new DifferentialPilot(2.1f, 5.7f, Motor.B, Motor.C); 
		while(Button.readButtons()!= Button.ID_ESCAPE) {
			Button.waitForAnyPress();
			if(Button.readButtons() == Button.ID_RIGHT) {
				pilot.rotateRight();
			}
			else if(Button.readButtons() == Button.ID_LEFT) {
				pilot.rotateLeft();
			}
		}
		
	}
	
}
