package cs622.component;

/**
 * A Component used to represent an item in a Java source file that will be
 * generated.
 * 
 */
public abstract class Component {

	/**
	 * Gets the class type of the component.
	 * 
	 * @return
	 */
	public abstract Class getType();

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
