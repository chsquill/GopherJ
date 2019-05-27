package cs622;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

import cs622.document.Document;
import cs622.document.JsonDocument;
import cs622.generator.GopherJGenerator;

/*
 * Test for the GopherJGenerator class.
 */
class GopherJGeneratorTest {

	@Test
	void packageStatementCreatedTest() {

		// validate that that a string representing a java file was created

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

	@Test
	void inputFromAFileAndPackageStatementCreatedTest() {

		// validate that that a string representing a java file was created from an
		// input file

		GopherJGenerator generator = new GopherJGenerator();

		Document doc = new JsonDocument();

		String filePath = "json.input";

		doc.readInputFromFile(filePath);

		String output = generator.generate(doc);

		// check if first line of generated file starts with 'package'
		assertTrue(output.startsWith("package"));

		File outputFile = new File("GopherJDto.java");

		// validate that the file was written to disk
		assertTrue(outputFile.exists());

	}

}
