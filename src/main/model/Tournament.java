package model;

import model.strategy.Strategy;

import java.util.ArrayList;
import java.util.List;

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

    // MODIFIES: this
    // EFFECTS: generate the games of the tournament using the current strategy
    public void generateGames() {
        games = strategy.generateSchedule(teams);
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
}
