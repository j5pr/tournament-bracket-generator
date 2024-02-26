package persistence.serializers;

import org.json.JSONArray;
import persistence.Context;
import persistence.Deserializable;
import persistence.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

// utility class for serializing and deserializing lists of objects
public class ListSerializer {
    // EFFECTS: deserialize the given the serialized JSON array, a context, and a function to create new objects
    public static <T extends Deserializable> List<T> deserialize(JSONArray list, Context ctx, Supplier<T> create) {
        List<T> result = new ArrayList<>();

        for (int i = 0; i < list.length(); i++) {
            T obj  = create.get();
            obj.deserialize(list.getJSONObject(i), ctx);
            result.add(obj);
        }

        return result;
    }

    // EFFECTS: serializes the given iterable of objects into a JSON array
    public static <T extends Serializable> JSONArray serialize(Iterable<T> list) {
        if (list == null) {
            return null;
        }

        JSONArray result = new JSONArray();

        for (Serializable obj : list) {
            result.put(obj.serialize());
        }

        return result;
    }
}
