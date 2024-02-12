package model.strategy;

import model.Game;
import model.Team;
import model.participant.Participant;
import model.participant.TeamParticipant;

import java.util.ArrayList;
import java.util.List;

public class RoundRobinStrategy implements Strategy {
    private final int groupSize;

    // REQUIRES: groupSize > 0
    // EFFECTS: constructs a round-robin strategy with the given group size,
    //          and initializes the current id to 1
    public RoundRobinStrategy(int groupSize) {
        this.groupSize = groupSize;
    }

    // REQUIRES: teams.size() % groupSize == 0
    // EFFECTS: generates a schedule for the given teams using a round-robin strategy
    public List<Game> generateSchedule(List<Team> teams) {
        List<List<Team>> groups = generateGroups(teams);
        List<Game> games = new ArrayList<>();

        int id = 1;

        for (List<Team> group : groups) {
            games.addAll(generateGames(id, group));
            id += group.size();
        }

        return games;
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
    private List<Game> generateGames(int id, List<Team> groups) {
        List<Game> games = new ArrayList<>();

        for (int i = 0; i < groups.size(); i++) {
            for (int j = i + 1; j < groups.size(); j++) {
                Participant participantA = new TeamParticipant(groups.get(i));
                Participant participantB = new TeamParticipant(groups.get(j));

                games.add(new Game(id++, participantA, participantB));
            }
        }

        return games;
    }
}
