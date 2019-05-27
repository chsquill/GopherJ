package cs622.component;

import java.util.ArrayList;

/**
 * ArrayList component for Java file generation.
 * 
 * This class will map a JSON array into a Java ArrayList.
 * 
 * example - INPUT { "animals" : [] } OUTPUT private ArrayList animals;
 */
public class ArrayComponent extends Component {

	public ArrayComponent(String name) {
		super(name);
	}

	@Override
	public Class getType() {
		return ArrayList.class;
	}

}
