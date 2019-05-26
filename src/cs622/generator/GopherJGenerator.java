package cs622.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import cs622.Generatable;
import cs622.component.Component;
import cs622.document.JsonDocument;
import cs622.document.XmlDocument;

/**
 * Generator that creates the Java source file. It will base the generation on
 * the Generatable's components passed into generate().
 * 
 */
public class GopherJGenerator {

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

		// generate class
		TypeSpec.Builder classBuilder = TypeSpec.classBuilder("GopherJDto").addModifiers(Modifier.PUBLIC)
				.addJavadoc("GopherJDto - This class was auto generated.");

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

		String output = javaFile.toString();

		System.out.println(output);

		writeFileToDisk(output);

		return output;

	}

	/**
	 * Writes the contents of the provided outputString to disk.
	 * 
	 * @param outputString
	 *            Output string that will be written to disk.
	 */
	private void writeFileToDisk(String outputString) {

		// writes the contents of the provided string to a file

		FileWriter writer = null;

		try {

			File ouputFile = new File("GopherJDto.java");

			// create a new file
			boolean created = ouputFile.createNewFile();

			// validate file was created
			if (!created) {
				System.out.println(System.out.format("File %s not created", ouputFile.getName()));
				return;
			}

			writer = new FileWriter(ouputFile);

			// write the contents of the file to the file
			writer.write(outputString);

		} catch (IOException e) {
			System.out.println("Error writing File");
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

	private String generateAccessorName(String name) {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

}
