package model.participant;

import model.Team;

public interface Participant {
    String getName();

    boolean isAvailable();

    Team getTeam();
}
