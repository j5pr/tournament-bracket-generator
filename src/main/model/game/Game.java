package model.game;

import model.Team;
import model.participant.Participant;
import org.json.JSONObject;
import persistence.Context;
import persistence.Deserializable;
import persistence.Serializable;

import java.util.function.Supplier;

// represents a game, with an id, two participants, and their respective scores
public class Game implements Serializable, Deserializable {
    public static final Supplier<Game> MAKE = () -> new Game(0, null, null);

    private int id;

    private Participant participantA;
    private Participant participantB;

    private int scoreA;
    private int scoreB;

    private boolean finished;

    private Team winner;
    private Team loser;

    // EFFECTS: create a new game with the given id and participants, that has
    //          no scores, is not finished, and has no winner or loser
    public Game(int id, Participant teamA, Participant teamB) {
        this.id = id;
        this.participantA = teamA;
        this.participantB = teamB;
        this.scoreA = 0;
        this.scoreB = 0;
        this.finished = false;
        this.winner = null;
        this.loser = null;
    }

    // EFFECTS: return whether the game is able to be played or not,
    //          i.e. both participants are available and the game is not finished
    public boolean isReady() {
        return !isFinished() && participantA.isAvailable() && participantB.isAvailable();
    }

    // REQUIRES: isReady() && scoreA != scoreB
    // MODIFIES: this
    // EFFECTS: complete the game, set the winner and loser based on the current
    //          score, and return the winner
    public Team complete() {
        Team a = participantA.getTeam();
        Team b = participantB.getTeam();

        if (scoreA > scoreB) {
            winner = a;
            loser = b;
        } else {
            winner = b;
            loser = a;
        }

        finished = true;
        return winner;
    }

    // REQUIRES: isReady()
    // MODIFIES: this
    // EFFECTS: add the given amount to the score of team A
    public void addScoreA(int amount) {
        scoreA += amount;
    }

    // REQUIRES: isReady()
    // MODIFIES: this
    // EFFECTS: add the given amount to the score of team B
    public void addScoreB(int amount) {
        scoreB += amount;
    }

    // persistence

    // EFFECTS: serialize this game to a JSON object
    @Override
    public JSONObject serialize() {
        JSONObject obj = new JSONObject();

        obj.put("id", id);
        obj.put("participantA", participantA.serialize());
        obj.put("participantB", participantB.serialize());
        obj.put("scoreA", scoreA);
        obj.put("scoreB", scoreB);
        obj.put("finished", finished);
        obj.put("winner", winner == null ? null : winner.getName());
        obj.put("loser", loser == null ? null : loser.getName());

        return obj;
    }

    // MODIFIES: this, ctx
    // EFFECTS: deserialize the given JSON object into this
    @Override
    public void deserialize(JSONObject object, Context ctx) {
        id = object.getInt("id");

        participantA = Participant.deserializeFrom(object.getJSONObject("participantA"), ctx);
        participantB = Participant.deserializeFrom(object.getJSONObject("participantB"), ctx);

        scoreA = object.getInt("scoreA");
        scoreB = object.getInt("scoreB");

        finished = object.getBoolean("finished");

        winner = object.isNull("winner") ? null : ctx.get(Team.class, object.getString("winner"));
        loser = object.isNull("loser") ? null : ctx.get(Team.class, object.getString("loser"));

        ctx.put(Game.class, id, this);
    }

    // getters & setters

    public int getId() {
        return id;
    }

    public boolean isFinished() {
        return finished;
    }

    public Participant getParticipantA() {
        return participantA;
    }

    public Participant getParticipantB() {
        return participantB;
    }

    public int getScoreA() {
        return scoreA;
    }

    public void setScoreA(int scoreA) {
        this.scoreA = scoreA;
    }

    public int getScoreB() {
        return scoreB;
    }

    public void setScoreB(int scoreB) {
        this.scoreB = scoreB;
    }

    public Team getWinner() {
        return winner;
    }

    public Team getLoser() {
        return loser;
    }
}
