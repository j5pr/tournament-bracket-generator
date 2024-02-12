package model.participant;

import model.Team;

public class TeamParticipant implements Participant {
    private final Team team;

    // EFFECTS: create a new participant that represents the given team
    public TeamParticipant(Team team) {
        this.team = team;
    }

    // EFFECTS: return the name of this.team
    @Override
    public String getName() {
        return team.getName();
    }

    // EFFECTS: returns true, since this participant is always available
    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public Team getTeam() {
        return team;
    }
}
