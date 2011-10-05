package patterns.memoization;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Memoization is a technique for adding caching transparently to a class.
 * It remembers results from method invocations and returns the cached
 * value on subsequent invocations.
 * <p>
 * One requirement with this is that classes to be memoized implement an
 * interface so that a proxy can be generated.
 * <p>
 * Things to keep in mind with this:
 * 1. Recursive calls won't benefit from memoization
 * 2. Method calls should return consistent results
 * 3. Method calls should not have side effects
 * 4. Method calls should not have mutable arguments
 *
 * @author rayvanderborght
 */
public class Memoizer<T> implements InvocationHandler {

    private T object;
    private Map<Method, Map<List<Object>, Object>> caches = new HashMap<Method, Map<List<Object>,Object>>();

    public Memoizer() {
    }

    @SuppressWarnings("unchecked")
    public T build(T object) {
    	this.object = object;
        return (T) Proxy.newProxyInstance(
                this.object.getClass().getClassLoader(),
                this.object.getClass().getInterfaces(),
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // always invoke methods that return void, there is nothing to cache!
        if (Void.TYPE.equals(method.getReturnType())) {
            return this.invoke(method, args);
        }

        Map<List<Object>, Object> cache = this.getCache(method);
        List<Object> key = Arrays.asList(args);
        Object value = cache.get(key);

        if (value == null && !cache.containsKey(key)) {
            value = this.invoke(method, args);
            cache.put(key, value);
        }
        return value;
    }

    private Object invoke(Method method, Object[] args) throws Throwable {
        try {
            return method.invoke(this.object, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    /**
     * Each method invocation caches the results keyed by the argument list
     * that was used to invoke it.
     *
     * NOTE: a natural improvement here is to replace this with an LRU cache impl
     */
    private synchronized Map<List<Object>, Object> getCache(Method method) {
        Map<List<Object>, Object> cache = this.caches.get(method);
        if (cache == null) {
            cache = Collections.synchronizedMap(new HashMap<List<Object>, Object>());
            this.caches.put(method, cache);
        }
        return cache;
    }
}
