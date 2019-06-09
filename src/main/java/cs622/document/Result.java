package cs622.document;

import java.io.Serializable;

public class Result implements Serializable {

	private static final long serialVersionUID = 1L;

	private String jsonInput;
	private String javaOutput;

	public Result(String jsonInput, String javaOutput) {
		this.jsonInput = jsonInput;
		this.javaOutput = javaOutput;
	}

	public String getJsonInput() {
		return jsonInput;
	}

	public void setJsonInput(String jsonInput) {
		this.jsonInput = jsonInput;
	}

	public String getJavaOutput() {
		return javaOutput;
	}

	public void setJavaOutput(String javaOutput) {
		this.javaOutput = javaOutput;
	}

}