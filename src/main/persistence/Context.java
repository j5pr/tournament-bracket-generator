package persistence;

import java.util.HashMap;
import java.util.Map;

// represents the intermediate context for deserialization
public class Context {
    private final Map<Class<?>, Map<Object, Object>> data;

    // EFFECTS: creates a new context with no saved data
    public Context() {
        data = new HashMap<>();
    }

    // MODIFIES: this
    // EFFECTS: stores the given value of the provided type in this context, associated with key
    public <T> void put(Class<T> type, Object key, T value) {
        if (!data.containsKey(type)) {
            data.put(type, new HashMap<>());
        }

        data.get(type).put(key, value);
    }

    // MODIFIES: this
    // EFFECTS: stores the given value of the provided type in this context, associated with key
    public <T> void put(Class<T> type, int key, T value) {
        put(type, (Object)key, value);
    }

    // REQUIRES: the provided type and key have been previously stored in this context
    // EFFECTS: returns the value of the provided type associated with key
    public <T> T get(Class<T> type, Object key) {
        return type.cast(data.get(type).get(key));
    }

    // REQUIRES: the provided type and key have been previously stored in this context
    // EFFECTS: returns the value of the provided type associated with key
    public <T> T get(Class<T> type, int key) {
        return get(type, (Object)key);
    }
}
