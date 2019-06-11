package cs622.document;

import cs622.generator.Result;

/**
 * XmlDocument is used to parse an XML document in to Components.
 */
public class XmlDocument extends Document {

	/** Wsdl of the XML if there is one */
	private String wsdlUrl;

	public XmlDocument() {
		this("No WSDL Url specified.");
	}

	public XmlDocument(String wsdlUrl) {
		this.wsdlUrl = wsdlUrl;
	}

	/**
	 * Parses the XML string into Components
	 */
	@Override
	public Result parse(String input) {
		System.out.println("XML parser not implemented.");
		return null;
	}

	public String getWsdlUrl() {
		return wsdlUrl;
	}

	public void setWsdlUrl(String wsdlUrl) {
		this.wsdlUrl = wsdlUrl;
	}

	@Override
	public boolean validInput(String input) {
		return false;
	}

}
