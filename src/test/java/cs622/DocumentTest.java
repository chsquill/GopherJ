package cs622;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;

import org.junit.Test;

import cs622.document.Document;
import cs622.document.JsonDocument;
import cs622.document.exception.JsonParseException;

/*
 * Test for the Document class.
 */
public class DocumentTest {

	@Test
	public void validJsonParseTest() {

		// validate parsing a properly formatted json string

		// valid json
		String json = "{\"firstName\" : \"Charles\"," + "\"lastName\" : \"Squillante\"}";

		Document doc = new JsonDocument();

		doc.parse(json);
	}

	@Test
	public void invalidJsonParseTest() {

		// validate that an improper formatted json string raised an exception

		// error - missing comma after first name
		String json = "{\"firstName\" : \"Charles\"" + "\"lastName\" : \"Squillante\"}";

		Document doc = new JsonDocument();

		// validate JsonParseException is thrown
		assertThrows(JsonParseException.class, () -> {
			// execute parse using invalid input
			doc.parse(json);
		});
	}

	@Test
	public void noInputFileFoundTest() {

		// validate FileNotFountException if the input file was not found

		// error - bad file name
		String fileName = "some_bad_file_name.json";

		Document doc = new JsonDocument();

		// validate FileNotFoundException is thrown
		assertThrows(FileNotFoundException.class, () -> {
			// execute parse using invalid input
			doc.readInputFromFile(fileName);
		});
	}

	@Test
	public void validateJsonTest() {

		// validate that a string is a valid JSON document

		// valid JSON
		String json = "{\"firstName\" : \"Charles\"," + "\"lastName\" : \"Squillante\"}";

		Document doc = new JsonDocument();

		// validate true is returned
		assertTrue(doc.validInput(json));

		// invalid JSON
		json = "{\"firstName\" : \"Charles\"" + "\"lastName\" : \"Squillante\"}";

		// validate false is returned
		assertTrue(!doc.validInput(json));
	}
}
