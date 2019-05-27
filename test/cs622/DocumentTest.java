package cs622;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import cs622.document.Document;
import cs622.document.JsonDocument;
import cs622.document.exception.JsonParseException;

/*
 * Test for the Document class.
 */
class DocumentTest {

	@Test
	void validJsonParseTest() {

		// validate parsing a properly formatted json string

		// valid json
		String json = "{\"firstName\" : \"Charles\"," + "\"lastName\" : \"Squillante\"}";

		Document doc = new JsonDocument();

		doc.parse(json);
	}

	@Test
	void invalidJsonParseTest() {

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

}
