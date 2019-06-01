package cs622.component;

/**
 * Supports any field types for Java file generation.
 * 
 * This class will map JSON value into a Java type - some examples.
 * 
 * example - INPUT { "value" : 50.0 } OUTPUT private Double value;
 * 
 * example - INPUT { "value" : "" } OUTPUT private String value
 * 
 * example - INPUT { "value" : 50 } OUTPUT private Integer value
 * 
 * example - INPUT { "value" : 3000000000 } OUTPUT private Long value
 * 
 * example - INPUT { "values" : [] } OUTPUT private ArrayList values;
 * 
 */
public class GenericComponent<T> extends Component<T> {

	private T type;

	public GenericComponent(String name, T type) {
		super(name);
		this.type = type;
	}

	public Class<T> getType() {
		return (Class<T>) type.getClass();
	}

}
