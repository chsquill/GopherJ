package cs622.document;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.json.JSONObject;

import cs622.generator.GopherJGenerator;

/**
 * Abstract class used to represent a structured document that can be
 * predictively parsed in to Component's.
 * 
 */
public class DocumentManager {

	// generator for code generation
	private GopherJGenerator generator = new GopherJGenerator();

	public DocumentManager() {

		// create generator
		generator = new GopherJGenerator();
		generator.setWriteOutputToDisk(false);
	}

	/**
	 * Finds all files in a directory of file type '.json'
	 * 
	 * @param directory
	 *            of files
	 * @return List of file paths for json files.
	 */
	public List<String> process(String filePath) {

		// CopyOnWriteArrayList is thread safe
		List<String> results = new CopyOnWriteArrayList<>();

		try {

			File file = new File(filePath);

			if (!file.isDirectory()) {
				Document doc = new JsonDocument();
				doc.readInputFromFile(filePath);
				results.add(generator.generate(doc));
				return results;
			}

			// gather all the valid json files alphabetically
			List<String> jsonFiles = readValidFiles(filePath);

			// loop thru the json files
			for (int i = 0; i < jsonFiles.size(); i++) {

				String jsonFile = jsonFiles.get(i);

				// expect more than one file so name appropriately
				final String fileName = "GopherJDTO_" + i;

				// create a new thread per file
				Thread documentThread = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							// generate the Document
							Document doc = new JsonDocument();
							doc.setJavaClassName(fileName);
							doc.readInputFromFile(jsonFile);
							// results is thread-safe
							results.add(generator.generate(doc));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});

				documentThread.start(); // start thread
				documentThread.join(); // join current thread
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return results;
	}

	/**
	 * Finds all files in a directory of file type '.json'
	 * 
	 * @param directory
	 *            of files
	 * @return List of file paths for json files.
	 */
	private List<String> readValidFiles(String directory) {

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
	private String readContentsFromFile(String filePath) {
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
