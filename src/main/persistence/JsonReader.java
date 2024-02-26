package persistence;

import model.Tournament;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.stream.Collectors;

// A reader that can read tournament data from a file
public class JsonReader extends BufferedReader {
    private Context context;

    // EFFECTS: constructs a reader to read from the file with the given name
    public JsonReader(String fileName) throws FileNotFoundException {
        super(new FileReader(fileName));

        context = new Context();
    }

    // MODIFIES: this
    // EFFECTS: reads the tournament from the file and returns it
    public Tournament readTournament() {
        JSONObject json = new JSONObject(lines().collect(Collectors.joining(System.lineSeparator())));
        Tournament obj = new Tournament();
        obj.deserialize(json, context);
        return obj;
    }

    // MODIFIES: this
    // EFFECTS: resets the context of this reader
    public void reset() {
        context = new Context();
    }
}
