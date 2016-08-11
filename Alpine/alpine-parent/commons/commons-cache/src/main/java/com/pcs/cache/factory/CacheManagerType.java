/**
 * 
 */
package com.pcs.cache.factory;

/**
 * @author pcseg310
 *
 */
public enum CacheManagerType {

	HZ("HazelCast"), InMemory("InMemory");

	private String name;

	CacheManagerType(String name) {
		this.setName(name);
	}

	public static CacheManagerType valueOfName(String name) {
		CacheManagerType ret = null;
		for (CacheManagerType type : values()) {
			if (type.getName().equals(name)) {
				ret = type;
				break;
			}
		}
		return ret;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
