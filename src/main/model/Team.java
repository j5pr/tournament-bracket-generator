package model;

import org.json.JSONObject;
import persistence.Context;
import persistence.Deserializable;
import persistence.Serializable;

import java.util.function.Supplier;

// represents a team, which can play games and participate in a tournament
public class Team implements Serializable, Deserializable {
    public static final Supplier<Team> MAKE = () -> new Team(null);

    private String name;

    // EFFECTS: create a new team with the given name
    public Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // EFFECTS: returns the name of this team
    @Override
    public String toString() {
        return name;
    }

    // REQUIRES: object has a "name" field
    // MODIFIES: ctx
    // EFFECTS: applies the given object's data to this team
    @Override
    public void deserialize(JSONObject object, Context ctx) {
        name = object.getString("name");
        ctx.put(Team.class, name, this);
    }

    // EFFECTS: returns a JSONObject representing this team
    @Override
    public JSONObject serialize() {
        return new JSONObject().put("name", name);
    }
}
