package cs622.generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import cs622.component.Component;
import cs622.db.DataStore;
import cs622.document.JsonDocument;
import cs622.document.XmlDocument;

/**
 * Generator that creates the Java source file. It will base the generation on
 * the Generatable's components passed into generate().
 * 
 * This generator implementation uses JavaPoet API to create the Java file
 * contents.
 * 
 */
public class GopherJGenerator {

	public static final String GENERATED_FILE_NAME = "GopherJDto.java";

	// flat for writing output to disk
	private static boolean writeOutputToDisk = true;;

	// singleton instance of a GopherJGenerator
	private static GopherJGenerator generator;

	/**
	 * Private GopherJGenerator constructor.
	 * 
	 * Only want to create one of these.
	 */
	private GopherJGenerator() {
		super();
	}

	/**
	 * Gets the singleton instance of this GopherJGenerator.
	 * 
	 * @return DataStore
	 */
	public static GopherJGenerator getInstance(boolean writeOutputToDisk) {
		if (generator == null) {
			generator = new GopherJGenerator();
			GopherJGenerator.writeOutputToDisk = writeOutputToDisk;
		}
		return generator;
	}

	/**
	 * Builds a Java class file from the provided components.
	 */
	public String generate(Generatable generatable) {

		// an example of down-casting - not sure needed here
		if (generatable instanceof JsonDocument) {
			System.out.println("WADL file : " + ((JsonDocument) generatable).getWadlUrl());
		} else if (generatable instanceof XmlDocument) {
			System.out.println("WSDL file : " + ((XmlDocument) generatable).getWsdlUrl());
		}

		String javaClassName = generatable.getJavaClassName() == null ? "GopherJDto" : generatable.getJavaClassName();

		// generate class
		TypeSpec.Builder classBuilder = TypeSpec.classBuilder(javaClassName).addModifiers(Modifier.PUBLIC)
				.addJavadoc(javaClassName + " - This class was auto generated.");

		// generate fields
		for (Component component : generatable.getComponents()) {

			classBuilder
					.addField(FieldSpec.builder(component.getType(), component.getName(), Modifier.PRIVATE).build());
		}

		// generate methods
		for (Component component : generatable.getComponents()) {

			MethodSpec getMethod = MethodSpec.methodBuilder("get" + generateAccessorName(component.getName()))
					.addStatement(String.format("return %s", component.getName())).addModifiers(Modifier.PUBLIC)
					.returns(component.getType()).build();
			classBuilder.addMethod(getMethod);

			MethodSpec setMethod = MethodSpec.methodBuilder("set" + generateAccessorName(component.getName()))
					.addStatement(String.format("this.%s = %s", component.getName(), component.getName()))
					.addModifiers(Modifier.PUBLIC).addParameter(component.getType(), component.getName()).build();
			classBuilder.addMethod(setMethod);
		}

		// generate file
		TypeSpec tyepSpec = classBuilder.build();

		JavaFile javaFile = JavaFile.builder("gopherj", tyepSpec).build();

		String javaOutput = javaFile.toString();

		// prints output to console
		System.out.println(javaOutput);

		String targetOutput = "CONSOLE";

		// option to write file to disk after generation
		if (writeOutputToDisk) {
			// writes the output to disk
			targetOutput = writeFileToDisk(javaOutput);
		}

		// record if data store was created
		if (DataStore.getInstance() != null) {
			DataStore.getInstance().recordResults(generatable.getInputPath(), targetOutput, "SUCCESS");
		}

		return javaOutput;

	}

	/* Generates a camel-case accessor method name. */
	private String generateAccessorName(String name) {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	/**
	 * Writes the contents of the provided outputString to disk.
	 * 
	 * @param outputString
	 *            Output string that will be written to disk.
	 */
	public String writeFileToDisk(String outputString) {

		FileWriter writer = null;

		try {

			File ouputFile = new File(GENERATED_FILE_NAME);

			// delete old file if it exists
			if (ouputFile.exists()) {
				ouputFile.delete();
			}

			// create a new file
			boolean created = ouputFile.createNewFile();

			// validate file was created
			if (!created) {
				System.out.println(System.out.format("File %s not created", ouputFile.getName()));
				return "ERROR";
			}

			writer = new FileWriter(ouputFile);

			// write the contents of the file to the file
			writer.write(outputString);

			System.out.format("File generated: %s", ouputFile.getAbsolutePath());
			return ouputFile.getAbsolutePath();

		} catch (IOException e) {
			System.out.println("Error writing File");
			return "ERROR";
		} finally {

			// close the writer
			if (writer != null) {
				try {
					writer.close();
				} catch (Exception e) {
					System.out.println("Error closing FileWriter");
				}
			}
		}
	}

	/**
	 * Store the input and the result of the file parsing as a Result file.
	 * 
	 * @param jsonInput
	 * @param javaOutput
	 * @return File path of Result.
	 * @throws IOException
	 */
	public String storeParseResult(String jsonInput, String javaOutput, String filePath) throws IOException {

		ObjectOutputStream obOutStream = null;

		try {

			File file = new File(filePath);

			// create stream
			obOutStream = new ObjectOutputStream(new FileOutputStream(file));

			// write to output stream
			obOutStream.writeObject(new Result(jsonInput, javaOutput));

		} finally {

			// close if output stream was created
			if (obOutStream != null) {
				obOutStream.close();
			}
		}

		return filePath;
	}

	/**
	 * Read and return a Result object file.
	 * 
	 * @param file
	 * @return Result object.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Result readParseResult(String filePath) throws IOException, ClassNotFoundException {

		Result result = null;

		ObjectInputStream obInputStream = null;

		try {

			// read object from input stream
			obInputStream = new ObjectInputStream(new FileInputStream(filePath));

			// cast into result
			result = (Result) obInputStream.readObject();

		} finally {
			// close if input stream was created
			if (obInputStream != null) {
				obInputStream.close();
			}
		}

		return result;
	}
}
