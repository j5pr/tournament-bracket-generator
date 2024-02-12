package model.strategy;

import model.Game;
import model.Team;

import java.util.ArrayList;
import java.util.List;

public class RoundRobinStrategy implements Strategy {
    private final int groupSize;

    // EFFECTS: constructs a round-robin strategy with the given group size
    public RoundRobinStrategy(int groupSize) {
        this.groupSize = groupSize;
    }

    // REQUIRES: teams.size() > 0 and teams.size() % groupSize == 0
    // EFFECTS: generates a schedule for the given teams using a round-robin strategy
    public List<Game> generateSchedule(List<Team> teams) {
        List<List<Team>> groups = generateGroups(teams);

        return null;
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
}
