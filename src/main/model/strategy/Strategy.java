package model.strategy;

import model.Game;
import model.Team;

import java.util.List;

public interface Strategy {
    List<Game> generateSchedule(List<Team> teams);
}
