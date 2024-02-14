package model.game;

import model.Team;
import model.participant.Participant;
import model.participant.ResultParticipant;
import model.participant.TeamParticipant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameContextTest {
    private Team teamA;
    private Team teamB;

    private GameContext context;

    @BeforeEach
    public void runBefore() {
        teamA = new Team("Team A");
        teamB = new Team("Team B");

        context = new GameContext();
    }

    @Test
    public void testCreateGame() {
        Game result = context.createGame(new TeamParticipant(teamA), new TeamParticipant(teamB));

        assertNotNull(result);
        assertEquals(teamA, result.getParticipantA().getTeam());
        assertEquals(teamB, result.getParticipantB().getTeam());
    }

    @Test
    public void testCreateGame2() {
        Game result = context.createGame(teamA, teamB);

        assertNotNull(result);
        assertEquals(teamA, result.getParticipantA().getTeam());
        assertEquals(teamB, result.getParticipantB().getTeam());
    }

    @Test
    public void testCreateGame3() {
        Game game1 = context.createGame(teamA, teamB);
        Game game2 = context.createGame(teamA, teamB);
        Game result = context.createGame(game1, game2, true, false);

        assertNotNull(result);
        assertEquals("Winner of game 1", result.getParticipantA().getName());
        assertEquals("Loser of game 2", result.getParticipantB().getName());
    }

    @Test
    public void testPairParticipants() {
        Participant participantA = new TeamParticipant(teamA);
        Participant participantB = new TeamParticipant(teamB);

        Game game1 = context.createGame(teamA, teamB);
        Game game2 = context.createGame(teamA, teamB);

        Participant participantC = new ResultParticipant(game1, true);
        Participant participantD = new ResultParticipant(game2, false);

        List<Game> games = context.pairParticipants(List.of(participantA, participantB, participantC, participantD));

        assertEquals(2, games.size());
        assertEquals(teamA, games.get(0).getParticipantA().getTeam());
        assertEquals(teamB, games.get(0).getParticipantB().getTeam());
        assertEquals("Winner of game 1", games.get(1).getParticipantA().getName());
        assertEquals("Loser of game 2", games.get(1).getParticipantB().getName());
    }

    @Test
    public void testPairGames() {
        Game game1 = context.createGame(teamA, teamB);
        Game game2 = context.createGame(teamA, teamB);

        List<Game> games = context.pairGames(List.of(game1, game2), true);

        assertEquals(1, games.size());
        assertEquals("Winner of game 1", games.get(0).getParticipantA().getName());
        assertEquals("Winner of game 2", games.get(0).getParticipantB().getName());
    }
}
