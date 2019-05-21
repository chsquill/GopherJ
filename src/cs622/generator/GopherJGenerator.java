package cs622.generator;

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

		return output;

	}

	private String generateAccessorName(String name) {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

}
