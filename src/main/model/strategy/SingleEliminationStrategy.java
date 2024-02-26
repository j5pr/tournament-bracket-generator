package model.strategy;

import model.game.Game;
import model.Team;
import model.game.GameContext;
import model.participant.Participant;
import model.participant.ResultParticipant;
import model.participant.TeamParticipant;
import org.json.JSONObject;
import persistence.Context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// a bracket generation strategy that uses single elimination: i.e. a team that loses is immediately eliminated
public class SingleEliminationStrategy implements Strategy {

    // REQUIRES: canGenerateSchedule()
    // MODIFIES: ctx
    // EFFECTS: generates a schedule for the given teams using a single elimination strategy
    @Override
    public List<Game> generateSchedule(GameContext ctx, List<Team> teams) {
        return generateRounds(ctx, teams)
            .stream()
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }

    // EFFECTS: returns true if the list of teams has a positive size that is a power of 2
    @Override
    public boolean canGenerateSchedule(List<Team> teams) {
        int size = teams.size();
        return size != 0 && (size & (size - 1)) == 0;
    }

    // REQUIRES: teams.size() > 0 && (teams.size() & (teams.size() - 1)) == 0
    // MODIFIES: ctx
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

    // EFFECTS: deserializes the given JSON object into this (no fields to deserialize, so is equivalent to a no-op)
    @Override
    public void deserialize(JSONObject object, Context context) {
        // no fields to deserialize
    }

    // EFFECTS: serialize this strategy to a JSON object
    @Override
    public JSONObject serialize() {
        return new JSONObject().put("type", "SingleEliminationStrategy");
    }
}
