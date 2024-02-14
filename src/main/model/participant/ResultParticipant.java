package model.participant;

import model.game.Game;
import model.Team;

// a participant that is to be determined by the result of a particular game
public class ResultParticipant implements Participant {
    private final Game game;
    private final boolean winner;

    // EFFECTS: create a new participant that depends on a given game: the winner
    //          of the game if winner == true, otherwise the loser
    public ResultParticipant(Game game, boolean winner) {
        this.game = game;
        this.winner = winner;
    }

    // EFFECTS: return the name of the team that represents this participant,
    //          or a placeholder if the game is not finished
    @Override
    public String getName() {
        if (!isAvailable()) {
            String type = winner ? "Winner" : "Loser";
            return type + " of game " + game.getId();
        }

        return getTeam().getName();
    }

    // EFFECTS: return whether the game is finished or not
    @Override
    public boolean isAvailable() {
        return game.isFinished();
    }

    // REQUIRES: isAvailable()
    // EFFECTS: returns the team that represents this participant
    @Override
    public Team getTeam() {
        return winner ? game.getWinner() : game.getLoser();
    }
}
