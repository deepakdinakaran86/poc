/**
 * 
 */
package com.pcs.data.analyzer.cache;

/**
 * @author pcseg310
 *
 */
public enum CacheProviderType {

	HZ("HazelCast"), InMemory("InMemory");

	private String name;

	CacheProviderType(String name) {
		this.setName(name);
	}

	public static CacheProviderType valueOfName(String name) {
		CacheProviderType ret = null;
		for (CacheProviderType type : values()) {
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
