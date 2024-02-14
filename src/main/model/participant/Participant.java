package model.participant;

import model.Team;

// represents any object that can participate in a game
public interface Participant {
    // returns the text name for this participant
    String getName();

    // whether the team this participant represents is currently available
    boolean isAvailable();

    // get the team that this participant represents
    Team getTeam();
}
