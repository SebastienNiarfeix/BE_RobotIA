package test;

import lejos.nxt.Button;
import lejos.nxt.Sound;


public class Music {
	public static void main(String[] args) throws InterruptedException {
		 Button.waitForAnyPress();
		 Sound.twoBeeps();
		 Sound.twoBeeps();
		 Button.waitForAnyPress();
	}
}
