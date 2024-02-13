package model.strategy;

import model.game.Game;
import model.Team;
import model.game.GameContext;
import model.participant.Participant;
import model.participant.ResultParticipant;
import model.participant.TeamParticipant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SingleEliminationStrategy implements Strategy {

    // REQUIRES: teams.size() > 0 && (teams.size() & (teams.size() - 1)) == 0
    // MODIFIES: ctx
    // EFFECTS: generates a schedule for the given teams using a single elimination strategy
    @Override
    public List<Game> generateSchedule(GameContext ctx, List<Team> teams) {
        return generateRounds(ctx, teams)
            .stream()
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }

    // REQUIRES: teams.size() > 0 && (teams.size() & (teams.size() - 1)) == 0
    // MODIFES: ctx
    // EFFECTS: generates a list of rounds for the given teams using a single elimination strategy
    protected List<List<Game>> generateRounds(GameContext ctx, List<Team> teams) {
        List<List<Game>> rounds = new ArrayList<>();
        List<Participant> participants = teams.stream().map(TeamParticipant::new).collect(Collectors.toList());

        while (participants.size() > 1) {
            List<Game> round = ctx.pairParticipants(participants);
            rounds.add(round);

            participants = round
                .stream()
                .map((game) -> new ResultParticipant(game, true))
                .collect(Collectors.toList());
        }

        return rounds;
    }
}
