package test;

import lejos.nxt.*;

public class Autre {
	public static void main(String[] args){
		 LCD.drawString("Program 2", 0, 0);
	     Button.waitForAnyPress();
	     LCD.clear();
	     int speed = 60;
	     Motor.B.setSpeed(speed);
	     Motor.C.setSpeed(speed);
	     Sound.beep();
	     Motor.B.forward();
	     Motor.C.forward();
	     LCD.drawString("FORWARD AT " + Motor.B.getSpeed(),0,0);
	     Button.waitForAnyPress();
	     LCD.drawString("BACKWARD AT " + Motor.B.getSpeed(),0,0);
	     Sound.twoBeeps();
	     Motor.B.backward();
	     Motor.C.backward();
	     Button.waitForAnyPress();
	     Motor.B.stop();     
	     Motor.C.stop();
	  }
}
