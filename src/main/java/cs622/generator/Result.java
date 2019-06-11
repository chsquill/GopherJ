package cs622.generator;

import java.io.Serializable;

/**
 * Results DTO for a parsing operation.
 * 
 */
public class Result implements Serializable {

	private static final long serialVersionUID = 1L;

	private String documentInput;
	private String javaOutput;

	public Result(String documentInput, String javaOutput) {
		this.documentInput = documentInput;
		this.javaOutput = javaOutput;
	}

	public String getDocumentInput() {
		return documentInput;
	}

	public void setDocumentInput(String documentInput) {
		this.documentInput = documentInput;
	}

	public String getJavaOutput() {
		return javaOutput;
	}

	public void setJavaOutput(String javaOutput) {
		this.javaOutput = javaOutput;
	}

}