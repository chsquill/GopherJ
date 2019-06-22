package cs622.document;

import java.io.IOException;
import java.util.List;

import cs622.generator.GopherJGenerator;

/**
 * Abstract class used to represent a structured document that can be
 * predictively parsed in to Component's.
 * 
 */
public class DocumentThread extends Thread {

	private String fileName;

	private String jsonFile;

	private List<String> results;

	/**
	 * Constructor
	 */
	public DocumentThread(String fileName, String jsonFile, List<String> results) {

		this.fileName = fileName;
		this.jsonFile = jsonFile;
		this.results = results;
	}

	public void run() {

		try {
			Document doc = new JsonDocument();
			doc.setJavaClassName(fileName);
			doc.readInputFromFile(jsonFile);
			results.add(GopherJGenerator.getInstance(false).generate(doc));
		} catch (IOException e) {
			results.add("Error generating for file " + jsonFile);
		}
	}
}
