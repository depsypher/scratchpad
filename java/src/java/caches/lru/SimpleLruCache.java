package caches.lru;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Just about the simplest lru cache you can make in java. It inherits just
 * about all of its functionality from LinkedHashMap.
 *
 * @author rayvanderborght
 */
public class SimpleLruCache<K, V> extends LinkedHashMap<K, V> {

	private static final long serialVersionUID = 1L;

	private final int capacity;

	/**
	 * Create a cache that inherits most of its functionality from LinkedHashMap.
	 *
	 * Some things to note:
	 *
	 * 1. Put happens before remove (aka eviction), so we make capacity one
	 *    bigger than max because otherwise we can overrun capacity.
	 *
	 * 2. Using a threshold greater than 1 makes sense because it prevents
	 *    rehashing from happening. Otherwise when the number of items in the
	 *    cache exceeds the threshold a rehash will occur.
	 *
	 * 3. We use access ordering instead of the default insertion ordering so
	 *    that the oldest item is at the head of the list of entries. This
	 *    makes lru eviction possible.
	 *
	 * @param capacity The max number of items in the cache
	 */
	public SimpleLruCache(int capacity) {
		super(capacity + 1, 1.1F, true);
		this.capacity = capacity;
	}

	/**
	 * The condition on which the oldest entry should be removed. The default
	 * impl never returns true; this is meant to be overriden exactly for the
	 * purpose of creating a cache.
	 *
	 * {@inheritDoc}
	 */
	@Override
	protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
		return this.size() > this.capacity;
	}
}
