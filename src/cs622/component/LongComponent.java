package cs622.component;

public class LongComponent extends Component {

	public LongComponent(String name) {
		super(name);
	}

	@Override
	public Class getType() {
		return Long.class;
	}

}
