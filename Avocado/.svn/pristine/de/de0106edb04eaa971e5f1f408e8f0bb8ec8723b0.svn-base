package com.pcs.cache.hazelcast;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.hazelcast.core.IMap;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;
import com.pcs.cache.Cache;

/**
 * @author pcseg310 (Rakesh)
 *
 */
public class HazelCastCache implements Cache {

	private static final DataSerializable NULL = new NULL();

	private final IMap<Object, Object> map;

	public HazelCastCache(final IMap<Object, Object> map) {
		this.map = map;
	}

	@Override
	public String getName() {
		return map.getName();
	}

	@Override 
	public <T> T get(Object key, Class<T> type) {		
		Object value = fromStoreValue(this.map.get(key));
		if (type != null && value != null && !type.isInstance(value)) {
			throw new IllegalStateException(
					"Cached value is not of required type [" + type.getName()
							+ "]: " + value);
		}
		return (T) value;
	}

	@Override
	public void put(final Object key, final Object value) {
		if (key != null) {
			map.set(key, toStoreValue(value));
		}
	}

	@Override
	public Object putIfAbsent(Object key, Object value) {
		Object ret = null;
		if (key != null) {
			ret = map.putIfAbsent(key, toStoreValue(value));
		}
		return ret;
	}
	
	@Override
	public void evict(final Object key) {
		if (key != null) {
			map.delete(key);
		}
	}

	@Override
	public void clear() {
		map.clear();
	}

	protected Object toStoreValue(final Object value) {
		if (value == null) {
			return NULL;
		}
		return value;
	}

	protected Object fromStoreValue(final Object value) {
		if (NULL.equals(value)) {
			return null;
		}
		return value;
	}

	static final class NULL implements DataSerializable {
		public void writeData(final ObjectDataOutput out) throws IOException {
		}

		public void readData(final ObjectDataInput in) throws IOException {
		}

		@Override
		public boolean equals(Object obj) {
			return obj != null && obj.getClass() == getClass();
		}

		@Override
		public int hashCode() {
			return 0;
		}
	}

	@Override
    public void put(Object key, Object value, long ttl, TimeUnit timeunit) {
		if (key != null) {
			map.set(key, toStoreValue(value),ttl,timeunit);
		}
    }


}
