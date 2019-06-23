package cs622.document;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.json.JSONObject;

import cs622.generator.GopherJGenerator;

/**
 * Document manager class. Handles concurrent processing of input files.
 */
public class DocumentManager {

	// generator for code generation
	private GopherJGenerator generator;

	/**
	 * Constructor
	 */
	public DocumentManager() {
		generator = GopherJGenerator.getInstance(false);
	}

	/**
	 * Processes json file(s). If the filePath is a directory all the valid json
	 * files in the directory will be parsed. Otherwise a single file will be
	 * parsed.
	 * 
	 * @param filePath
	 * @return Generated Java source for one or more files.
	 * @throws Exception
	 */
	public List<String> process(String filePath) throws Exception {

		// CopyOnWriteArrayList is thread safe
		List<String> results = new CopyOnWriteArrayList<>();

		File file = new File(filePath);

		// if the file not a directory then process singel file
		if (!file.isDirectory()) {

			// assume JsonDocument for now
			Document doc = new JsonDocument();

			doc.readInputFromFile(filePath);

			// gernare and add results
			results.add(generator.generate(doc));

		} else {

			// gather all the valid json files alphabetically
			List<String> jsonFiles = readValidFiles(filePath);

			List<DocumentThread> threads = new ArrayList<>();

			// loop thru the json files
			for (int i = 0; i < jsonFiles.size(); i++) {

				String jsonFile = jsonFiles.get(i);

				// expect more than one file so name sequentially
				final String fileName = "GopherJDTO_" + i;

				// create a new thread per file
				DocumentThread documentThread = new DocumentThread(fileName, jsonFile, results);

				// save the threads
				threads.add(documentThread);

				// start thread
				documentThread.start();
			}

			// join all the threads to current thread
			for (Thread thread : threads) {
				thread.join();
			}
		}

		return results; // return all the results
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

	/**
	 * Validates JSON input.
	 * 
	 * @param JSON
	 *            input.
	 * @return Whether input is valid.
	 */
	private boolean validInput(String input) {
		try {
			new JSONObject(input);
			return true;
		} catch (JSONException e) {
			return false;
		}
	}

}
