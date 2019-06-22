package cs622.generator;

import cs622.component.Component;

/**
 * Base interface for a class that can be used to generate a Java file.
 */
public interface Generatable {

	/**
	 * Output Java class name for the generated class.
	 * 
	 * @return Java class name.
	 */
	public String getJavaClassName();

	/**
	 * FileName
	 */
	public String getInputPath();

	/**
	 * The components that will be used to generate the Java file.
	 * 
	 * @return Java file components.
	 */
	public Component[] getComponents();

}
