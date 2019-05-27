package cs622.document.exception;

/**
 * JSON parsing exception to signal an improper formatted JSON file encountered
 * during parsing.
 */
public class JsonParseException extends DocumentParseException {

	public JsonParseException(String message) {
		super(message);
	}
}
