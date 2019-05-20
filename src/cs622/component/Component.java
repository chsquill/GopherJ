package cs622.component;

public abstract class Component {

	public abstract Class getType();

	private String name;

	public Component(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
