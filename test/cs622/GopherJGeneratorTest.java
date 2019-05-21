package cs622;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import cs622.document.Document;
import cs622.document.JsonDocument;
import cs622.generator.GopherJGenerator;

/*
 * Test for the GopherJGenerator.
 */
class GopherJGeneratorTest {

	@Test
	void testPackageStatementCreated() {

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

}
