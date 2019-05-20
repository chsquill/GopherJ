package cs622.document;

import java.io.File;
import java.net.URL;

import cs622.Generatable;
import cs622.component.Component;

public abstract class Document implements Generatable {

	protected Component[] components;

	public abstract void parse(String input);

	public void readInput(String input) {
		parse(input);
	}

	public void readInput(File input) {
		// TODO read input from file
	}

	public void readInput(URL input) {
		// TODO read input from URL
	}

	public Component[] getComponents() {
		return components;
	}

	public void setComponents(Component[] components) {
		this.components = components;
	}

}
