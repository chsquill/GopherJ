package cs622;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.Test;

import cs622.document.Document;
import cs622.document.DocumentManager;
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

	@Test
	void noInputFileFoundTest() {

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
	void findValidJsonFromDirectoryTest() {

		// validate a list of json files was returned

		String dirName = "/home/chuck/git/GopherJ/json_files";

		DocumentManager doc = new DocumentManager();

		// list of validated and sorted files
		List<String> sortedValidDocuments = doc.readValidFiles(dirName);

		// assert that more than one was returned
		assertTrue(sortedValidDocuments.size() > 0);
	}

	@Test
	void validateJsonTest() {

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
