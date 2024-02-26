package model.participant;

import model.Team;
import org.json.JSONObject;
import persistence.Context;

// represents a participant that is a team
public class TeamParticipant implements Participant {
    private Team team;

    // EFFECTS: create a new participant that represents the given team
    public TeamParticipant(Team team) {
        this.team = team;
    }

    // EFFECTS: return the name of this.team
    @Override
    public String getName() {
        return team.getName();
    }

    // EFFECTS: returns true, since this participant is always available
    @Override
    public boolean isAvailable() {
        return true;
    }

    // EFFECTS: returns this.team
    @Override
    public Team getTeam() {
        return team;
    }

    // MODIFIES: this
    // EFFECTS: deserializes the given JSON object into this participant
    @Override
    public void deserialize(JSONObject object, Context ctx) {
        team = ctx.get(Team.class, object.getString("team"));
    }

    // EFFECTS: serialize this participant to a JSON object
    @Override
    public JSONObject serialize() {
        return new JSONObject()
            .put("type", "TeamParticipant")
            .put("team", team.getName());
    }
}
