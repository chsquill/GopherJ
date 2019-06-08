package cs622;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.Test;

import cs622.document.Document;
import cs622.document.JsonDocument;
import cs622.document.Result;
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

		// validate FileNotFountException if the input file was not found

		String dirName = "/home/chuck/git/GopherJ/json_files";

		Document doc = new JsonDocument();

		List<String> sortedValidDocuments = doc.readInputDirectory(dirName);

		assertTrue(sortedValidDocuments.size() > 0);
	}

	@Test
	void testStoreAndReadResult() {

		Document doc = new JsonDocument();

		try {

			String filePath = doc.storeParseResult("this is json", "this is java");

			assertTrue(new File(filePath).exists());

			Result result = doc.readParseResult(filePath);

			assertEquals(result.getJsonInput(), "this is json");

		} catch (Exception e) {

			fail();
		}

	}

}
