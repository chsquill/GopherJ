package cs622.component;

/**
 * Integer component for Java file generation.
 * 
 * This class will map a JSON value into an Java Integer type.
 * 
 * example - INPUT { "value" : 50 } OUTPUT private Integer value
 */
public class IntegerComponent extends Component {

	public IntegerComponent(String name) {
		super(name);
	}

	@Override
	public Class getType() {
		return Integer.class;
	}

}
