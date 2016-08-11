package com.pcs.saffron.g2021.SimulatorEngine.DS.generator;

import java.util.Random;

public class NumberGenerator extends AdapterGenerator {
	
	private static Random random = null;
	
	public Short getRandomGeneratedValue() {	
		random = new Random();
		return (short) random.nextInt(100);
	}
}
