package model.game;

import model.Team;
import model.participant.Participant;
import model.participant.ResultParticipant;
import model.participant.TeamParticipant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// a context for making games that may depend on each other; stores/manages an id counter
public class GameContext {
    int id;

    // EFFECTS: creates a new GameContext whose next generated game will have an id of 1
    public GameContext() {
        id = 0;
    }

    // MODIFIES: this
    // EFFECTS: creates a new game with the given participants, and increments
    //          the this.id counter
    public Game createGame(Participant participantA, Participant participantB) {
        return new Game(++id, participantA, participantB);
    }

    // MODIFIES: this
    // EFFECTS: creates a new game with the given teams, and increments this.id
    public Game createGame(Team teamA, Team teamB) {
        return createGame(new TeamParticipant(teamA), new TeamParticipant(teamB));
    }

    // MODIFIES: this
    // EFFECTS: creates a new game based on the result of the given games, and
    //          increments this.id
    public Game createGame(Game gameA, Game gameB, boolean winnerA, boolean winnerB) {
        return createGame(new ResultParticipant(gameA, winnerA), new ResultParticipant(gameB, winnerB));
    }

    // REQUIRES: participants.size() % 2 == 0
    // MODIFIES: this
    // EFFECTS: creates a list of games by pairing the provided participants
    public List<Game> pairParticipants(List<Participant> participants) {
        List<Game> games = new ArrayList<>();

        for (int i = 0; i < participants.size(); i += 2) {
            games.add(createGame(participants.get(i), participants.get(i + 1)));
        }

        return games;
    }

    // REQUIRES: games.size() % 2 == 0
    // MODIFIES: this
    // EFFECTS: creates a list of games by pairing the results of the provided games
    public List<Game> pairGames(List<Game> games, boolean winner) {
        return pairParticipants(
            games
                .stream()
                .map((game) -> new ResultParticipant(game, winner)).collect(Collectors.toList())
        );
    }
}
