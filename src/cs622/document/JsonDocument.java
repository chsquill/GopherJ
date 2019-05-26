package cs622.document;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import cs622.component.ArrayComponent;
import cs622.component.Component;
import cs622.component.DoubleComponent;
import cs622.component.IntegerComponent;
import cs622.component.LongComponent;
import cs622.component.StringComponent;
import cs622.document.exception.DocumentParseException;
import cs622.document.exception.JsonParseException;

/**
 * JsonDocument is used to parse a Json document in to Components.
 */
public class JsonDocument extends Document {

	/** Wadl of the Json if there is one */
	private String wadlUrl;

	public JsonDocument() {
		this("No WADL Url specified.");
	}

	public JsonDocument(String wadlUrl) {
		this.setWadlUrl(wadlUrl);
	}

	@Override
	public void parse(String json) {

		// parses the incoming json string into a Component[]

		ArrayList<Component> comps = new ArrayList<>();

		try {

			JSONObject o = new JSONObject(json);

			String[] names = JSONObject.getNames(o);

			for (String string : names) {

				Object ob = o.get(string);

				switch (ob.getClass().getTypeName()) {

				case "java.lang.String":
					comps.add(new StringComponent(string));
					break;
				case "java.lang.Double":
					comps.add(new DoubleComponent(string));
					break;
				case "java.lang.Integer":
					comps.add(new IntegerComponent(string));
					break;
				case "java.lang.Long":
					comps.add(new LongComponent(string));
					break;
				case "org.json.JSONArray":
					comps.add(new ArrayComponent(string));
					break;
				default:
					break;
				}
			}

		} catch (JSONException e) {
			throw new JsonParseException(e.getMessage());
		} catch (Exception e) {
			throw new DocumentParseException(e.getMessage());
		}

		// set the components that will describe the parsed json
		setComponents(comps.toArray(new Component[comps.size()]));
	}

	public String getWadlUrl() {
		return wadlUrl;
	}

	public void setWadlUrl(String wadlUrl) {
		this.wadlUrl = wadlUrl;
	}

}
