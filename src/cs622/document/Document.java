package cs622.document;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import cs622.Generatable;
import cs622.component.Component;

/**
 * Abstract class used to represent a structured document that can be
 * predictively parsed in to Component's.
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

	/**
	 * Parse the input from a file path.
	 * 
	 * @param filePath
	 *            Path to a document for processing.
	 */
	public void readInputFromFile(String filePath) {

		// Given the provided file path open it for reading and read it contents.

		Scanner scanner = null;

		try {

			File file = new File(filePath);

			// validate that the file exists
			if (!file.exists()) {
				System.out.println(System.out.format("File %s doesn't exist. Exiting.", filePath));
				return;
			}

			scanner = new Scanner(file);

			// string buffer for writing the contents of the file
			StringBuffer fileOutput = new StringBuffer();

			// read the files contents
			while (scanner.hasNextLine()) {
				fileOutput.append(scanner.nextLine());
			}

			// pass the read string input to the existing parse method using a string
			readInput(fileOutput.toString());

		} catch (IOException e) {
			System.out.println(System.out.format("Error parsing input from File %s.", filePath));
		} finally {
			// close the scanner when complete
			if (scanner != null) {
				scanner.close();
			}
		}
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
