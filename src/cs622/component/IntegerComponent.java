package cs622.component;

public class IntegerComponent extends Component {

	public IntegerComponent(String name) {
		super(name);
	}

	@Override
	public Class getType() {
		return Integer.class;
	}

}
