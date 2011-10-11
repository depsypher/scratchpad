package patterns.builder;

/**
 * The builder pattern is useful for creating immutable objects. Alternatives
 * include using so called "telescoping constructors" pattern or using setters.
 * Telescoping constructors (multiple ctors with different number of args)
 * works but is hard to read and easy for clients to get wrong. Using setters
 * works, but then the class isn't immutable anymore, and also can be left
 * semi-initialized state. The builder pattern addresses these drawbacks.
 *
 * @author rayvanderborght
 */
public class BuilderObject {

	private String name;
	private String description;

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	/**
	 * The private ctor ensures only the Builder can create BuilderObjects.
	 * Also, this is an atomic operation in terms of thread-safety, which
	 * ensures that the object is fully initialized.
	 */
	private BuilderObject(Builder builder) {
		this.name = builder.name;
		this.description = builder.description;
	}

	/**
	 * The Builder inner class
	 *
	 * @author rayvanderborght
	 */
	public static class Builder {

		/** Since name is mandatory mark it final */
		private final String name;

		/** Description is not required, will default to null */
		private String description;

		/** Name is mandatory, all others get defaults */
		public Builder(String name) {
			this.name = name;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public BuilderObject build() {
			return new BuilderObject(this);
		}
	}
}
