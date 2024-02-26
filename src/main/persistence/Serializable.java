package persistence;

import org.json.JSONObject;

// represents an object that can be serialized to a JSON object
public interface Serializable {
    // serialize this object to a JSON object
    JSONObject serialize();
}
