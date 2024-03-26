package model.participant;

import model.game.Game;
import model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ResultParticipantTest {
    private Team teamA;
    private Team teamB;

    private ResultParticipant winner;
    private ResultParticipant loser;

    private Game game;

    @BeforeEach
    public void runBefore() {
        teamA = new Team("Team A");
        teamB = new Team("Team B");

        Participant participantA = new TeamParticipant(teamA);
        Participant participantB = new TeamParticipant(teamB);

        game = new Game(1, participantA, participantB);

        winner = new ResultParticipant(game, true);
        loser = new ResultParticipant(game, false);
    }

    @Test
    public void testName1() {
        assertEquals("Winner of game 1", winner.getName());
        assertEquals("Loser of game 1", loser.getName());
        assertEquals("Winner of game 1", winner.toString());
        assertEquals("Loser of game 1", loser.toString());
    }

    @Test
    public void testName2() {
        game.setScoreA(1);
        game.complete();

        assertEquals(teamA.getName(), winner.getName());
        assertEquals(teamB.getName(), loser.getName());
    }

    @Test
    public void testAvailable1() {
        assertFalse(winner.isAvailable());
        assertFalse(loser.isAvailable());
    }

    @Test
    public void testAvailable2() {
        game.setScoreA(1);
        game.complete();

        assertTrue(winner.isAvailable());
        assertTrue(loser.isAvailable());
    }

    @Test
    public void testGetTeam1() {
        assertNull(winner.getTeam());
        assertNull(loser.getTeam());
    }

    @Test
    public void testGetTeam2() {
        game.setScoreA(1);
        game.complete();

        assertEquals(teamA, winner.getTeam());
        assertEquals(teamB, loser.getTeam());
    }

    @Test
    public void testGetGame() {
        assertEquals(game, winner.getGame());
        assertEquals(game, loser.getGame());
    }
}
