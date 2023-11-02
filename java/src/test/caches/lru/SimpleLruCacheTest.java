package caches.lru;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

/**
 * Demonstrates usage of SimpleLru cache
 *
 * @author rayvanderborght
 */
public class SimpleLruCacheTest {

	@Test
	public void testSimpleLruCache() {
		Map<String, String> cache = new SimpleLruCache<String, String>(2);
		cache.put("a", "1");
		cache.put("b", "2");
		cache.put("c", "3");

		assertEquals(null, cache.get("a"));	// was oldest and therefore evicted
		assertEquals("2", cache.get("b"));
		assertEquals("3", cache.get("c"));
	}
}
