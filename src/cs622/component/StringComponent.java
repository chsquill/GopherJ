package cs622.component;

/**
 * String component for Java file generation.
 * 
 * This class will map a JSON string into an Java String type.
 * 
 * example - INPUT { "value" : "" } OUTPUT private String value
 */
public class StringComponent extends Component {

	public StringComponent(String name) {
		super(name);
	}

	@Override
	public Class getType() {
		return String.class;
	}

}
