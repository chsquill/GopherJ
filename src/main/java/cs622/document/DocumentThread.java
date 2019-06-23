package cs622.document;

import java.io.IOException;
import java.util.List;

import cs622.generator.GopherJGenerator;

/**
 * Thread class to represent a single process parsing an input file.
 */
public class DocumentThread extends Thread {

	private String fileName; // output Java class name

	private String jsonFile; // input file name

	private List<String> results; // aggregated results

	/**
	 * Constructor
	 */
	public DocumentThread(String fileName, String jsonFile, List<String> results) {
		this.fileName = fileName;
		this.jsonFile = jsonFile;
		this.results = results;
	}

	/**
	 * Parses an input and generates Java source.
	 */
	public void run() {

		try {
			// assume JSON for now
			Document doc = new JsonDocument();
			doc.setJavaClassName(fileName);
			doc.readInputFromFile(jsonFile);
			// generate Java source text and add to the results
			results.add(GopherJGenerator.getInstance(false).generate(doc));
		} catch (IOException e) {
			results.add("Error generating for file " + jsonFile);
		}
	}
}
