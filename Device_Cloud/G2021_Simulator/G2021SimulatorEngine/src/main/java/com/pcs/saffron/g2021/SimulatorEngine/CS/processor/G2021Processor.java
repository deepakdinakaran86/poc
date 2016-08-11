package com.pcs.saffron.g2021.SimulatorEngine.CS.processor;

import com.pcs.saffron.g2021.SimulatorEngine.CS.exceptions.MessageProcessException;
import com.pcs.saffron.g2021.SimulatorEngine.CS.message.ServerMessage;

public abstract class G2021Processor {
	
	public abstract ServerMessage processG2021Message(Object G2021Data) throws MessageProcessException ;
}
