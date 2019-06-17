package cs622.document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import cs622.component.Component;
import cs622.db.DataStore;
import cs622.generator.Generatable;
import cs622.generator.Result;

/**
 * Abstract class used to represent a structured document that can be
 * predictively parsed in to Component's.
 * 
 */
public abstract class Document implements Generatable {

	/** Components to be included in the generated Java file. */
	protected Component[] components;

	/** Input types - local, file path, url */
	protected String inputPath;

	/**
	 * Parses the input into a Component[].
	 * 
	 * @param input
	 *            Structured data such as XML or Json
	 */
	public abstract Result parse(String input);

	/**
	 * Validates the input document.
	 * 
	 * @param input
	 *            Structured data such as XML or Json
	 * @return If document is valid.
	 */
	public abstract boolean validInput(String input);

	/**
	 * Reads the string input of a document to be parsed.
	 * 
	 * @param input
	 *            Input to be parsed.
	 */
	public Result readInput(String input) {
		// parse the input from a String
		return parse(input);
	}

	/**
	 * Reads and parses the input from a file path.
	 * 
	 * @param filePath
	 *            Path to a document for processing.
	 * @throws IOException
	 */
	public void readInputFromFile(String filePath) throws IOException {

		// Given the provided file path open it for reading and read it
		// contents.

		// record input path
		inputPath = filePath;

		Scanner scanner = null;

		try {

			File file = new File(filePath);

			// validate that the file exists
			if (!file.exists() || file.isDirectory()) {
				throw new FileNotFoundException(String.format("File %s doesn't exist. Exiting.", filePath));
			}

			scanner = new Scanner(file);

			// string writer for writing the contents of the file
			StringWriter fileOutput = new StringWriter();

			// read the files contents
			while (scanner.hasNextLine()) {
				fileOutput.append(scanner.nextLine());
			}

			// pass the read string input to the existing parse method using a
			// string
			readInput(fileOutput.toString());

		} catch (Exception e) {
			// record error of parse
			if (DataStore.getInstance() != null) {
				DataStore.getInstance().recordResults(filePath, "ERROR", e.getMessage());
			}
			throw e;
		} finally {
			// close the scanner when complete
			if (scanner != null) {
				scanner.close();
			}
		}
	}

	/**
	 * Finds all files in a directory of file type '.json'
	 * 
	 * @param directory
	 *            of files
	 * @return List of file paths for json files.
	 */
	public List<String> readValidFiles(String directory) {

		List<String> sortedFilePaths = null;

		try {

			File dir = new File(directory);

			if (!dir.isDirectory()) {
				return sortedFilePaths;
			}

			// use streams to find a sorted list of valid files, this stream
			// validates
			// each file against validInput(...)
			sortedFilePaths = Arrays
					.stream(dir.listFiles(
							file -> (file.isFile() && validInput(readContentsFromFile(file.getAbsolutePath())))))
					.map(file -> file.getAbsolutePath()).sorted().collect(Collectors.toList());

		} catch (Exception e) {
			// nothing to do here - will return null
		}

		return sortedFilePaths;
	}

	/*
	 * Utility method to read contents from file
	 */
	public String readContentsFromFile(String filePath) {
		try {
			return new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
		} catch (IOException e) {
			return "not_valid";
		}
	}

	public void readInput(URL input) {
		// record input path
		inputPath = input.toString();
		// TODO for future feature
	}

	public Component[] getComponents() {
		return components;
	}

	public void setComponents(Component[] components) {
		this.components = components;
	}

	@Override
	public String getInputPath() {
		return inputPath;
	}
}
