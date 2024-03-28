package model;

import model.event.Event;
import model.event.EventLog;
import model.game.Game;
import model.game.GameContext;
import model.strategy.Strategy;
import org.json.JSONObject;
import persistence.Context;
import persistence.Deserializable;
import persistence.Serializable;
import persistence.serializers.ListSerializer;

import java.util.ArrayList;
import java.util.List;

// represents a tournament, which has teams, is bracketed using a strategy, and can hold games
public class Tournament implements Serializable, Deserializable {
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
        EventLog.getInstance().logEvent(new Event("Added team to tournament: " + team.getName()));
    }

    // REQUIRES: team != null
    // MODIFIES: this
    // EFFECTS: removes a team to the tournament
    public void removeTeam(Team team) {
        teams.remove(team);
        EventLog.getInstance().logEvent(new Event("Removed team from tournament: " + team.getName()));
    }

    // REQUIRES: strategy != null
    // MODIFIES: this
    // EFFECTS: generate the games of the tournament using the current strategy
    public void generateGames() {
        games = strategy.generateSchedule(new GameContext(), teams);
        EventLog.getInstance().logEvent(new Event("Generated games for tournament"));
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

    // MODIFIES: this
    // EFFECTS: deserializes the given JSON object into this
    @Override
    public void deserialize(JSONObject object, Context ctx) {
        teams = ListSerializer.deserialize(object.getJSONArray("teams"), ctx, Team.MAKE);

        strategy = !object.isNull("strategy")
            ? Strategy.deserializeFrom(object.getJSONObject("strategy"), ctx)
            : null;

        games = !object.isNull("games")
            ? ListSerializer.deserialize(object.getJSONArray("games"), ctx, Game.MAKE)
            : null;
    }

    // EFFECTS: returns a JSON object representing this tournament
    @Override
    public JSONObject serialize() {
        return new JSONObject()
            .put("teams", ListSerializer.serialize(teams))
            .put("strategy", strategy != null ? strategy.serialize() : null)
            .put("games", games != null ? ListSerializer.serialize(games) : null);
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
