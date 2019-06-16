package cs622.db;

/**
 * Result for a run of GopherJ code generator.
 */
public class ResultRecord {

	// fields

	private String id;
	private String inputFileName;
	private String outputFileName;
	private String message;
	private long time;

	// constructor

	public ResultRecord(String id, String inputFileName, String outputFileName, String message, long time) {
		this.id = id;
		this.inputFileName = inputFileName;
		this.outputFileName = outputFileName;
		this.message = message;
		this.time = time;
	}

	// setters / getters

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInputFileName() {
		return inputFileName;
	}

	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	// format the string for display
	public String toString() {
		return String.format("ID=%s, Input file=%s, Output file=%s, Message=%s, Time=%d", getId(), getInputFileName(),
				getOutputFileName(), getMessage(), getTime());
	}
}
