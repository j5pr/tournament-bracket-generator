package model.participant;

import model.Team;
import org.json.JSONObject;
import persistence.Context;
import persistence.Deserializable;
import persistence.Serializable;

// represents any object that can participate in a game
public interface Participant extends Serializable, Deserializable {
    // returns the text name for this participant
    String getName();

    // whether the team this participant represents is currently available
    boolean isAvailable();

    // get the team that this participant represents
    Team getTeam();

    // EFFECTS: deserializes the given JSON object into a participant with the appropriate type
    static Participant deserializeFrom(JSONObject obj, Context ctx) {
        String type = obj.getString("type");
        Participant participant = null;

        if (type.equals("ResultParticipant")) {
            participant = new ResultParticipant(null, false);
        } else if (type.equals("TeamParticipant")) {
            participant = new TeamParticipant(null);
        }

        if (participant == null) {
            return null;
        }

        participant.deserialize(obj, ctx);
        return participant;
    }
}
