package model.strategy;

import model.game.Game;
import model.Team;
import model.game.GameContext;
import org.json.JSONObject;
import persistence.Context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class RoundRobinStrategy implements Strategy {
    private int groupSize;

    // REQUIRES: groupSize > 0
    // EFFECTS: constructs a round-robin strategy with the given group size,
    //          and initializes the current id to 1
    public RoundRobinStrategy(int groupSize) {
        this.groupSize = groupSize;
    }

    // REQUIRES: canGenerateSchedule()
    // MODIFIES: ctx
    // EFFECTS: generates a schedule for the given teams using a round-robin strategy
    @Override
    public List<Game> generateSchedule(GameContext ctx, List<Team> teams) {
        return generateGroups(teams)
            .stream()
            .map((group) -> generateGames(ctx, group))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }

    // EFFECTS: returns true if teams.size() is a multiple of groupSize
    @Override
    public boolean canGenerateSchedule(List<Team> teams) {
        return teams.size() % groupSize == 0;
    }

    // REQUIRES: teams.size() % groupSize == 0
    // EFFECTS: generates groups of teams from the given list of teams,
    //          where each group has groupSize teams
    private List<List<Team>> generateGroups(List<Team> teams) {
        List<List<Team>> groups = new ArrayList<>();
        List<Team> currentGroup = null;

        for (int i = 0; i < teams.size(); i++) {
            if (i % groupSize == 0) {
                currentGroup = new ArrayList<>();
                groups.add(currentGroup);
            }

            currentGroup.add(teams.get(i));
        }

        return groups;
    }

    // EFFECTS: returns games for a given group, starting from the given id
    // MODIFIES: ctx
    private List<Game> generateGames(GameContext ctx, List<Team> groups) {
        List<Game> games = new ArrayList<>();

        for (int i = 0; i < groups.size(); i++) {
            for (int j = i + 1; j < groups.size(); j++) {
                games.add(ctx.createGame(groups.get(i), groups.get(j)));
            }
        }

        return games;
    }

    // MODIFIES: this
    // EFFECTS: deserializes the given JSON object into this strategy
    @Override
    public void deserialize(JSONObject object, Context context) {
        groupSize = object.getInt("groupSize");
    }

    // EFFECTS: serialize this strategy to a JSON object
    @Override
    public JSONObject serialize() {
        return new JSONObject()
            .put("type", "RoundRobinStrategy")
            .put("groupSize", groupSize);
    }
}
