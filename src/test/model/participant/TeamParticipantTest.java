package model.participant;

import model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TeamParticipantTest {
    private Team team;
    private TeamParticipant participant;

    @BeforeEach
    public void runBefore() {
        team = new Team("Team A");
        participant = new TeamParticipant(team);
    }

    @Test
    public void testName() {
        assertEquals(team.getName(), participant.getName());
    }

    @Test
    public void testAvailable() {
        assertTrue(participant.isAvailable());
    }

    @Test
    public void testGetTeam() {
        assertEquals(team, participant.getTeam());
    }
}
