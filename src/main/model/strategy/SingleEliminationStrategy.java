package model.strategy;

import model.Game;
import model.Team;
import model.participant.Participant;
import model.participant.ResultParticipant;
import model.participant.TeamParticipant;

import java.util.ArrayList;
import java.util.List;

public class SingleEliminationStrategy implements Strategy {

    // REQUIRES: teams.size() > 0 && (teams.size() & (teams.size() - 1)) == 0
    // EFFECTS: generates a schedule for the given teams using a single elimination strategy
    @Override
    public List<Game> generateSchedule(List<Team> teams) {
        List<Game> games = new ArrayList<>();
        int id = 1;

        List<Participant> participants = new ArrayList<>();

        for (Team team : teams) {
            participants.add(new TeamParticipant(team));
        }

        while (participants.size() > 1) {
            List<Game> round = generateRound(id, participants);

            games.addAll(round);
            id += participants.size();

            participants.clear();
            for (Game game : round) {
                participants.add(new ResultParticipant(game, true));
            }
        }

        return games;
    }

    // EFFECTS: returns games for a given list of participants, starting from the given id
    protected List<Game> generateRound(int id, List<Participant> participants) {
        List<Game> games = new ArrayList<>();

        for (int i = 0; i < participants.size(); i += 2) {
            Participant participantA = participants.get(i);
            Participant participantB = participants.get(i + 1);

            games.add(new Game(id++, participantA, participantB));
        }

        return games;
    }
}
