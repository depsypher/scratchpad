package patterns.builder;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Demonstrates usage of the BuilderObject
 *
 * @author rayvanderborght
 */
public class BuilderObjectTest {

	@Test
	public void testBuilder() throws Exception {
		BuilderObject bo = new BuilderObject.Builder("Foo").description("A foo object").build();
		assertEquals("Foo", bo.getName());
		assertEquals("A foo object", bo.getDescription());
	}
}
