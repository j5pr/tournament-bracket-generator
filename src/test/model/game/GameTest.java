package model.game;

import model.Team;
import model.game.Game;
import model.participant.Participant;
import model.participant.ResultParticipant;
import model.participant.TeamParticipant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Team teamA;
    private Team teamB;

    private Game gameA;
    private Game gameB;

    @BeforeEach
    public void runBefore() {
        teamA = new Team("Team A");
        teamB = new Team("Team B");

        Participant participantA = new TeamParticipant(teamA);
        Participant participantB = new TeamParticipant(teamB);

        gameA = new Game(1, participantA, participantB);
        gameB = new Game(2, participantA, new ResultParticipant(gameA, true));
    }

    @Test
    public void testIsReady() {
        assertTrue(gameA.isReady());
        assertFalse(gameB.isReady());
    }

    @Test
    public void testComplete() {
        gameA.addScoreA(2);
        gameA.addScoreB(3);

        assertEquals(teamB, gameA.complete());
        assertEquals(teamB, gameA.getWinner());
        assertEquals(teamA, gameA.getLoser());

        assertTrue(gameA.isFinished());
    }

    @Test
    public void testAddGetScore() {
        gameA.setScoreA(5);
        gameA.setScoreB(3);

        gameA.addScoreA(99);
        gameA.addScoreB(3);

        assertEquals(5 + 99, gameA.getScoreA());
        assertEquals(3 + 3, gameA.getScoreB());
    }

    @Test
    public void testGetId() {
        assertEquals(1, gameA.getId());
        assertEquals(2, gameB.getId());
    }

    @Test
    public void testGetParticipant() {
        assertEquals(teamA, gameA.getParticipantA().getTeam());
        assertEquals(teamB, gameA.getParticipantB().getTeam());

        gameA.setScoreB(1);
        gameA.complete();

        assertEquals(teamA, gameB.getParticipantA().getTeam());
        assertEquals(teamB, gameB.getParticipantB().getTeam());
    }
}
