package cs622.document.exception;

/**
 * Base class for Document parsing exceptions.
 * 
 * Extends the unchecked exception RuntimeException
 *
 */
public class DocumentParseException extends RuntimeException {

	public DocumentParseException(String message) {
		super(message);
	}
}
