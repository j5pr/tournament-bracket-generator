package persistence;

import java.io.*;

// A writer that can write JSON data to a file
public class JsonWriter extends PrintWriter {
    // EFFECTS: constructs a writer that writes to the file with the given name
    public JsonWriter(String fileName) throws IOException {
        super(new FileWriter("./data/" + fileName + ".json"));
    }

    // MODIFIES: this
    // EFFECTS: writes the given object to the file
    public void writeObject(Serializable obj) {
        println(obj.serialize().toString());
    }
}
