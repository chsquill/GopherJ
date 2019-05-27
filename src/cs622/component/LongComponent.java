package cs622.component;

/**
 * Long component for Java file generation.
 * 
 * This class will map a JSON value into an Java Long type.
 * 
 * example - INPUT { "value" : 3000000000 } OUTPUT private Long value
 */
public class LongComponent extends Component {

	public LongComponent(String name) {
		super(name);
	}

	@Override
	public Class getType() {
		return Long.class;
	}

}
