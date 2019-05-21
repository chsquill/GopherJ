package cs622.document;

import java.io.File;
import java.net.URL;

import cs622.Generatable;
import cs622.component.Component;

/**
 * Abstract class used to represent a structured document that can be
 * predictively parsed in Component's.
 * 
 */
public abstract class Document implements Generatable {

	protected Component[] components;

	/**
	 * Parses the input into a Component[].
	 * 
	 * @param input
	 *            Structured data such as XML or Json
	 */
	public abstract void parse(String input);

	public void readInput(String input) {
		// parse the input from a String
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
