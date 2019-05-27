package cs622;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import cs622.document.Document;
import cs622.document.JsonDocument;
import cs622.generator.GopherJGenerator;

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

		GopherJGenerator generator = new GopherJGenerator();

		Document doc = new JsonDocument();

		String filePath = "json.input";

		doc.readInputFromFile(filePath);

		String output = generator.generate(doc);

		// check if first line of generated file starts with 'package'
		assertTrue(output.startsWith("package"));
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
}
