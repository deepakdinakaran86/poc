package com.pcs.saffron.g2021.SimulatorEngine.DS.generator;

public interface RandomGenerator {
	
	public Object getRandomGeneratedValue();
	
	public Object getRandomGeneratedValue(Object minVal , Object maxVal);
	
}
