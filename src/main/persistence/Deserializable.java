package persistence;

import org.json.JSONArray;
import org.json.JSONObject;

// represents an object that can be deserialized from a JSON object
public interface Deserializable {
    // deserialize this object from a JSON object, writing over any existing data
    void deserialize(JSONObject object, Context context);
}
