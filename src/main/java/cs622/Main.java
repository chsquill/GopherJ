package cs622;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Scanner;

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

		StringWriter writer = new StringWriter();

		writer.append("GopherJ Main Menu\n").append("-----------------\n").append("1. Generate Java from JSON Input\n")
				.append("2. Generate Java from XML Input\n").append("3. Generate JUnit from Java\n")
				.append("4. Exit\n");

		System.out.println(writer.toString());

		Scanner sc = new Scanner(System.in);

		boolean running = true;

		while (running) { // look to prompt the user for values until 'exit'

			System.out.println("\nPlease enter menu item #:");

			Integer option = null;

			try {
				option = Integer.valueOf(sc.nextLine()); // read users input
			} catch (NumberFormatException e) {
				System.out.println("\nInvalid entry\n");
				System.out.println(writer.toString());
				continue;
			}

			boolean processed = false;

			switch (option) {
			case 1:
				processed = process(new JsonDocument(), sc);
				break;
			case 2:
				System.out.println("XML generation not implemented yet.");
				break;
			case 3:
				System.out.println("Jnit generation not implemented yet.");
				break;
			case 4:
				System.out.println("\nBye!");
				System.exit(0);
				break;
			default:
				System.out.println("\nInvalid entry\n");
				System.out.println(writer.toString());
				continue;
			}

			System.out.format("\nProcessing was %s successful.\n", processed ? "" : "not");

		}

		sc.close();
	}

	private static boolean process(Document document, Scanner sc) {

		GopherJGenerator gen = GopherJGenerator.getInstance(true);

		try {
			document.readInputFromFile(promptForFilePath(sc));
		} catch (JsonParseException e) {
			return false;
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			return false;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return false;
		}

		gen.generate(document);

		return true;
	}

	private static String promptForFilePath(Scanner sc) throws FileNotFoundException {

		System.out.println("\nPlease enter input file path:");

		String path = sc.nextLine();

		File file = new File(path);

		if (!file.exists() || file.isDirectory()) {
			throw new FileNotFoundException("File not found.");
		}

		return path;
	}

}
