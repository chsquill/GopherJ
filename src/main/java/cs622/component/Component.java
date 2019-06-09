package cs622.component;

/**
 * A Component used to represent an item in a Java source file that will be
 * generated. This will class will be sub-classed providing the data type to
 * create.
 * 
 */
public abstract class Component<T> {

	/**
	 * Gets the class type of the component.
	 * 
	 * @return The type of the Java type to create.
	 * 
	 */
	public abstract Class<T> getType();

	/** Name of the component */
	private String name;

	public Component(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
