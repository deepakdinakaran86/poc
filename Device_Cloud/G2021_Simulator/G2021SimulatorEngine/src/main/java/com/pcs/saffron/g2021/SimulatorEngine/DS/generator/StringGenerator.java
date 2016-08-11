package com.pcs.saffron.g2021.SimulatorEngine.DS.generator;

import org.apache.commons.lang.RandomStringUtils;

public class StringGenerator extends AdapterGenerator {
	
	public String getRandomGeneratedValue() {		
		return RandomStringUtils.randomAlphabetic(5);
	}
	
	
}
