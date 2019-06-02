package cs622.generator;

import cs622.component.Component;

/**
 * Base interface for a class that can be used to generate a Java file.
 */
public interface Generatable {

	/**
	 * The components that will be used to generate the Java file.
	 * 
	 * @return Java file components.
	 */
	public Component[] getComponents();

}
