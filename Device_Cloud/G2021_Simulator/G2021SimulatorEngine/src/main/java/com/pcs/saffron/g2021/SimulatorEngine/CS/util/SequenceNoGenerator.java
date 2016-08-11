package com.pcs.saffron.g2021.SimulatorEngine.CS.util;
/**
 * 
 * @author Santhosh
 *
 */

public class SequenceNoGenerator {
	
	private static Integer currentSeqNo=0;
	
	public static Integer sequenceNoIncreamentor(){
		if(currentSeqNo < 256)
			return ++currentSeqNo;
		else
			return 1;
	}
	
	public static Integer getSequenceNo(){
		return currentSeqNo;
	}
	
}
