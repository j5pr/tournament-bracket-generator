package model.game;

import model.Team;
import model.participant.Participant;

// represents a game, with an id, two participants, and their respective scores
public class Game {
    private final int id;

    private final Participant participantA;
    private final Participant participantB;

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
