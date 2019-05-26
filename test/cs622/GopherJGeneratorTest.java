package cs622;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import cs622.document.Document;
import cs622.document.JsonDocument;
import cs622.document.exception.JsonParseException;
import cs622.generator.GopherJGenerator;

/*
 * Test for the GopherJGenerator.
 */
class GopherJGeneratorTest {

	// @Test
	void packageStatementCreatedTest() {

		GopherJGenerator generator = new GopherJGenerator();

		Document doc = new JsonDocument();

		// TODO hard coded
		String json = "{\"firstName\" : \"Charles\"," + "\"lastName\" : \"Squillante\"," + "\"age\" : 50,"
				+ "\"hobbies\" : [\"School\", \"Hiking\", \"Astronomy\"]}";

		doc.parse(json);

		String output = generator.generate(doc);

		// check if first line of generated file starts with 'package'
		assertTrue(output.startsWith("package"));
	}

	// @Test
	void inputFromAFileAndPackageStatementCreatedTest() {

		GopherJGenerator generator = new GopherJGenerator();

		Document doc = new JsonDocument();

		String filePath = "json.input";

		doc.readInputFromFile(filePath);

		String output = generator.generate(doc);

		// check if first line of generated file starts with 'package'
		assertTrue(output.startsWith("package"));
	}

	@Test
	void invalidJsonStructureTest() {

		// error - missing comma after first name
		String json = "{\"firstName\" : \"Charles\"" + "\"lastName\" : \"Squillante\"}";

		Document doc = new JsonDocument();

		assertThrows(JsonParseException.class, () -> {
			// execute parse using invalid input
			doc.parse(json);
		});
	}

}
