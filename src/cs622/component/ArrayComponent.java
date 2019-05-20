package cs622.component;

import java.util.ArrayList;

public class ArrayComponent extends Component {

	public ArrayComponent(String name) {
		super(name);
	}

	@Override
	public Class getType() {
		return ArrayList.class;
	}

}
