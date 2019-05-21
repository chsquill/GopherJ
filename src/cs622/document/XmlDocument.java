package cs622.document;

/**
 * XmlDocument is used to parse an XML document in to Components.
 */
public class XmlDocument extends Document {

	/** Wsdl of the XML if there is one */
	private String wsdlUrl;

	public XmlDocument(String wsdlUrl) {
		this.wsdlUrl = wsdlUrl;
	}

	@Override
	public void parse(String input) {

		// TODO generate components from XML

	}

	public String getWsdlUrl() {
		return wsdlUrl;
	}

	public void setWsdlUrl(String wsdlUrl) {
		this.wsdlUrl = wsdlUrl;
	}

}
