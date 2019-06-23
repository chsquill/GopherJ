package cs622;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.Test;

import cs622.document.DocumentManager;

/*
 * Test for the DocumentManager class.
 */
public class DocumentManagerTest {

	/*
	 * Test to covers read of a directory and concurrent parse and generate.
	 */
	@Test
	public void validateProcessTest() {

		try {

			// create temp directory
			Path path = Files.createTempDirectory("temp_dir");

			// create temp file 1
			Path file1 = Files.createTempFile(path, "temp_file_1", "json");

			// create temp file 2
			Path file2 = Files.createTempFile(path, "temp_file_2", "json");

			String json = "{\"firstName\" : \"Charles\"," + "\"lastName\" : \"Squillante\"}";

			// write file 1
			FileWriter fileWriter = new FileWriter(file1.toFile());
			fileWriter.write(json);
			fileWriter.close();

			// write file 2
			fileWriter = new FileWriter(file2.toFile());
			fileWriter.write(json);
			fileWriter.close();

			DocumentManager manager = new DocumentManager();

			// process and get results
			List<String> results = manager.process(path.toAbsolutePath().toString());

			// expect two results back
			assertTrue(results.size() == 2);

		} catch (Exception e) {
			fail("Expected 2 files");
		}
	}
}
