package cs622.document;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cs622.component.Component;
import cs622.component.GenericComponent;
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

	/**
	 * Parses the JSON string into Components
	 */
	@Override
	public void parse(String json) {

		// parses the incoming JSON string into a Components

		ArrayList<Component<?>> comps = new ArrayList<>();

		try {

			JSONObject o = new JSONObject(json);

			String[] names = JSONObject.getNames(o);

			for (String name : names) {

				Object ob = o.get(name);

				// special case for JSONArray, create types as ArrayList
				if (ob instanceof JSONArray) {
					ob = new ArrayList<>();
				}

				// create generic component
				comps.add(new GenericComponent<>(name, ob));
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

	@Override
	public boolean validInput(String input) {
		try {
			JSONObject o = new JSONObject(input);
			return true;
		} catch (JSONException e) {
			return false;
		}
	}
}
