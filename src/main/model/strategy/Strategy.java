package model.strategy;

import model.game.Game;
import model.Team;
import model.game.GameContext;

import java.util.List;

public interface Strategy {
    List<Game> generateSchedule(GameContext ctx, List<Team> teams);
}
