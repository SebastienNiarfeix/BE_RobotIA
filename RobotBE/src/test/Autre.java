package test;

import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;

public class Autre {
	public static void main(String[] args) throws InterruptedException{
		 LCD.drawString("O", 0, 0);
		 float d = 0;
	     Button.waitForAnyPress();
	     DifferentialPilot pilot = new DifferentialPilot(5.6f, 14.5f, Motor.B, Motor.C, false);
	     pilot.setTravelSpeed(20);
	     pilot.travel(20, true);
	     while(pilot.isMoving()) {
	    	 d += pilot.getMovement().getDistanceTraveled();
	    	 pilot.travel(20, true);
	    	 LCD.clear();
	    	 LCD.drawString("" + d, 0, 0);
	    	 Thread.sleep(100);
	     }
	     Button.waitForAnyPress();
	  }
}
