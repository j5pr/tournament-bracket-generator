package model.strategy;

import model.game.Game;
import model.Team;
import model.game.GameContext;
import org.json.JSONObject;
import persistence.Context;
import persistence.Deserializable;
import persistence.Serializable;

import java.util.List;
import java.util.Objects;

// a strategy used to generate games for a tournament using a GameContext and a list of teams
public interface Strategy extends Serializable, Deserializable {
    // generate a list of games to be played using ctx and the provided teams list
    List<Game> generateSchedule(GameContext ctx, List<Team> teams);

    // returns whether or not the provided list of teams is valid input for generateSchedule
    boolean canGenerateSchedule(List<Team> teams);

    // EFFECTS: deserializes the given JSON object into a new Strategy of the appropriate type
    static Strategy deserializeFrom(JSONObject obj, Context ctx) {
        String type = obj.getString("type");
        Strategy strategy = null;

        if (type.equals("DoubleEliminationStrategy")) {
            strategy = new DoubleEliminationStrategy();
        }

        if (type.equals("RoundRobinStrategy")) {
            strategy = new RoundRobinStrategy(1);
        }

        if (type.equals("SingleEliminationStrategy")) {
            strategy = new SingleEliminationStrategy();
        }

        Objects.requireNonNull(strategy).deserialize(obj, ctx);
        return strategy;
    }
}
