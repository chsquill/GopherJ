package cs622.component;

public class DoubleComponent extends Component {

	public DoubleComponent(String name) {
		super(name);
	}

	@Override
	public Class getType() {
		return Double.class;
	}

}
