package com.pcs.analytics.flinktests.kafka.source.bean;

import java.io.Serializable;

/**
 * @author pcseg171
 * This class supports extensions to be configured to a point. Configurations
 * could be defined as a name and type pair.
 * A point could have only one extension with identical name and type combination.
 *
 */
public class PointExtension implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1055469495661921645L;

	private String extensionName;
	private String extensionType;

	
	public PointExtension(){
		
	}
	
	public PointExtension(String extensionName,String extensionType){
		this.extensionName = extensionName;
		this.extensionType = extensionType;
	}
	
	/**
	 * Sets the extension name
	 * @param extensionName
	 */
	public void setExtensionName(String extensionName) {
		this.extensionName = extensionName;
	}
	public void setExtensionType(String extensionType) {
		this.extensionType = extensionType;
	}
	public String getExtensionType(){
		return extensionType;
	};
	public String getExtensionName(){
		return extensionName;
	};

	
	@Override
	public final boolean equals(Object obj) {
		if(obj != null){
			if (obj instanceof PointExtension) {
				PointExtension _newObj = (PointExtension) obj;
				
				if(_newObj.getExtensionName() != null && _newObj.getExtensionType() != null){
					if(getExtensionName().equalsIgnoreCase(_newObj.getExtensionName()) && 
							(getExtensionType().equalsIgnoreCase(_newObj.getExtensionType()))){
						return true;
					}else{
						return false;
					}
				}else{
					return false;
				}
			}else{
				return false;
			}

		}else{
			return false;
		}
	}

	@Override
	public int hashCode() {
		//TODO
		return super.hashCode();
	}
}
