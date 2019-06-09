package cs622.document;

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
	public void parse(String input) {
		System.out.println("XML parser not implemented.");
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
