package cs622.component;

/**
 * Double component for Java file generation.
 * 
 * This class will map a JSON value into a Java Double type.
 * 
 * example - INPUT { "value" : 50.0 } OUTPUT private Double value;
 */
public class DoubleComponent extends Component {

	public DoubleComponent(String name) {
		super(name);
	}

	@Override
	public Class getType() {
		return Double.class;
	}

}
