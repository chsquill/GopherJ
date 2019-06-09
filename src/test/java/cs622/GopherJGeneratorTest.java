package cs622;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import cs622.document.Document;
import cs622.document.JsonDocument;
import cs622.generator.GopherJGenerator;
import cs622.generator.Result;

/*
 * Test for the GopherJGenerator class.
 */
class GopherJGeneratorTest {

	/*
	 * Validate the first line of the generated string starts with "package".
	 */
	@Test
	void packageStatementCreatedTest() {

		GopherJGenerator generator = new GopherJGenerator();

		Document doc = new JsonDocument();

		// valid JSON
		String json = "{\"firstName\" : \"Charles\"," + "\"lastName\" : \"Squillante\"," + "\"age\" : 50,"
				+ "\"hobbies\" : [\"School\", \"Hiking\", \"Astronomy\"]}";

		doc.parse(json);

		String output = generator.generate(doc);

		// check if first line of generated file starts with 'package'
		assertTrue(output.startsWith("package"));
	}

	/*
	 * Validate the first line of the generated string starts with "package" once
	 * read from a sample input file.
	 */
	@Test
	void inputFromAFileAndPackageStatementCreatedTest() {

		try {

			GopherJGenerator generator = new GopherJGenerator();

			Document doc = new JsonDocument();

			String filePath = "input.json";

			doc.readInputFromFile(filePath);

			String output = generator.generate(doc);

			// check if first line of generated file starts with 'package'
			assertTrue(output.startsWith("package"));

		} catch (Exception e) {
			fail("Unable to find package statement.");
		}

	}

	/*
	 * Validate that a file is persisted to disk and once read contains the expected
	 * contents.
	 */
	@Test
	void fileWrittenToDiskTest() {

		final String SAMPLE_TEXT = "SOME TEXT";

		GopherJGenerator generator = new GopherJGenerator();

		// write a file with some text to disk
		generator.writeFileToDisk(SAMPLE_TEXT);

		// find file created - **Note - file always created as GopherJDto.java for now
		File outputFile = new File(GopherJGenerator.GENERATED_FILE_NAME);

		// validate that the file was written to disk
		assertTrue(outputFile.exists());

		Scanner scanner = null;

		try {
			scanner = new Scanner(outputFile);
			if (scanner.hasNextLine()) {
				assertEquals(SAMPLE_TEXT, scanner.nextLine());
			}
		} catch (FileNotFoundException e) {
			fail("Unable to find file created.");
		} finally {
			scanner.close();
		}
	}

	/*
	 * Validate that the results of generation (input source, output code) can be
	 * stored and retrieved in Result object.
	 */
	@Test
	void storeAndReadResultTest() {

		GopherJGenerator generator = new GopherJGenerator();

		try {

			// create temp file
			File file = File.createTempFile("Result", "dat");

			// store the file receiving the file path back
			String filePath = generator.storeParseResult("this is json", "this is java", file.getAbsolutePath());

			// validate the file exists
			assertTrue(new File(filePath).exists());

			// read the result back from disk
			Result result = generator.readParseResult(filePath);

			// validate the input of the read document has the expected text
			assertEquals(result.getDocumentInput(), "this is json");

		} catch (Exception e) {

			fail("Error reading/writing Result object to disk.");
		}

	}
}
