package model.strategy;

import model.game.Game;
import model.Team;
import model.game.GameContext;

import java.util.List;

// a strategy used to generate games for a tournament using a GameContext and a list of teams
public interface Strategy {
    // generate a list of games to be played using ctx and the provided teams list
    List<Game> generateSchedule(GameContext ctx, List<Team> teams);

    // returns whether or not the provided list of teams is valid input for generateSchedule
    boolean canGenerateSchedule(List<Team> teams);
}
