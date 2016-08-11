/**
 * 
 */
package com.pcs.saffron.cipher.data.point;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.saffron.cipher.data.point.types.PointDataTypes;

/**
 * Interface to be implemented by all CAN points.
 * @author PCSEG171
 *
 */
public class CAN extends Point {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 79626850700092963L;
	
	private PointDataTypes TYPE = PointDataTypes.CAN;
	private static final Logger LOGGER = LoggerFactory.getLogger(CAN.class);
	
	public CAN(){
		super.setType(TYPE.name());
	}
	public CAN(String name) throws Exception{
		try {
			setPointName(name);
			super.setType(TYPE.name());
		} catch (Exception exec) {
			LOGGER.error("Failed to intialize a CAN point under name : "+name,exec);
			throw new Exception("Failed to intialize a CAN point under name : "+name,exec);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.pcs.driver.core.points.IPoint#getCalculatedValue(java.lang.String)
	 */
	@Override
	public Object getCalculatedValue(String formula)throws Exception {
		// TODO Auto-generated method stub
		return super.getCalculatedValue(formula);
	}

}
