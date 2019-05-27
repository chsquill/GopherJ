package cs622;

import cs622.document.Document;
import cs622.document.JsonDocument;
import cs622.document.exception.JsonParseException;
import cs622.generator.GopherJGenerator;

/**
 * Main class - entry into GopherJ.
 * 
 */
public class Main {

	public static void main(String[] args) {

		// current run of invalid content - JsonParseException expected

		GopherJGenerator gen = new GopherJGenerator();

		// hard coded invalid JSON content
		String json = "{\"firstName\" : \"Charles\"" + "\"lastName\" : \"Squillante\"";

		Document doc = new JsonDocument();

		try {
			doc.parse(json);
		} catch (JsonParseException e) {
			e.printStackTrace();
			System.exit(1);
		}

		gen.generate(doc);
	}

}
