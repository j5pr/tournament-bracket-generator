package model;

import model.game.Game;
import model.game.GameContext;
import model.strategy.Strategy;

import java.util.ArrayList;
import java.util.List;

// represents a tournament, which has teams, is bracketed using a strategy, and can hold games
public class Tournament {
    private List<Team> teams;
    private Strategy strategy;

    private List<Game> games;

    // EFFECTS: create a new tournament with no teams, without a strategy, and
    //          without any games
    public Tournament() {
        teams = new ArrayList<>();
        games = null;
    }

    // REQUIRES: team != null
    // MODIFIES: this
    // EFFECTS: add a team to the tournament
    public void addTeam(Team team) {
        teams.add(team);
    }

    // REQUIRES: strategy != null
    // MODIFIES: this
    // EFFECTS: generate the games of the tournament using the current strategy
    public void generateGames() {
        games = strategy.generateSchedule(new GameContext(), teams);
    }

    // REQUIRES: getGames() != null
    // EFFECTS: returns the game in this tournament identified by id if it's ready;
    //          or null if it is not ready or does not exist
    public Game findReadyGame(int id) {
        return games
            .stream()
            .filter((game) -> game.getId() == id)
            .filter(Game::isReady)
            .findAny()
            .orElse(null);
    }

    // getters and setters
    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
