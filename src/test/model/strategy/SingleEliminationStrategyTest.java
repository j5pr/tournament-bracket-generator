package model.strategy;

import model.game.Game;
import model.Team;
import model.game.GameContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SingleEliminationStrategyTest {
    private Strategy strategy;

    private Team[] teams;

    @BeforeEach
    public void runBefore() {
        strategy = new SingleEliminationStrategy();

        teams = new Team[] {
            new Team("Team A"),
            new Team("Team B"),
            new Team("Team C"),
            new Team("Team D"),
            new Team("Team E"),
            new Team("Team F"),
            new Team("Team G"),
            new Team("Team H")
        };
    }

    @Test
    public void testGenerateSchedule2() {
        List<Game> games = strategy.generateSchedule(new GameContext(), List.of(teams[0], teams[1]));

        assertEquals(1, games.size());
        assertEquals(teams[0], games.get(0).getParticipantA().getTeam());
        assertEquals(teams[1], games.get(0).getParticipantB().getTeam());
    }

    @Test
    public void testGenerateSchedule4() {
        List<Game> games = strategy.generateSchedule(new GameContext(), List.of(teams[0], teams[1], teams[2], teams[3]));

        assertEquals(3, games.size());

        assertEquals(teams[0], games.get(0).getParticipantA().getTeam());
        assertEquals(teams[1], games.get(0).getParticipantB().getTeam());

        assertEquals(teams[2], games.get(1).getParticipantA().getTeam());
        assertEquals(teams[3], games.get(1).getParticipantB().getTeam());

        assertEquals("Winner of game 1", games.get(2).getParticipantA().getName());
        assertEquals("Winner of game 2", games.get(2).getParticipantB().getName());
    }

    @Test
    public void testGenerateSchedule8() {
        List<Game> games = strategy.generateSchedule(new GameContext(), Arrays.asList(teams));

        assertEquals(7, games.size());

        assertEquals(teams[0], games.get(0).getParticipantA().getTeam());
        assertEquals(teams[1], games.get(0).getParticipantB().getTeam());
        assertEquals(teams[2], games.get(1).getParticipantA().getTeam());
        assertEquals(teams[3], games.get(1).getParticipantB().getTeam());
        assertEquals(teams[4], games.get(2).getParticipantA().getTeam());
        assertEquals(teams[5], games.get(2).getParticipantB().getTeam());
        assertEquals(teams[6], games.get(3).getParticipantA().getTeam());
        assertEquals(teams[7], games.get(3).getParticipantB().getTeam());

        assertEquals("Winner of game 1", games.get(4).getParticipantA().getName());
        assertEquals("Winner of game 2", games.get(4).getParticipantB().getName());
        assertEquals("Winner of game 3", games.get(5).getParticipantA().getName());
        assertEquals("Winner of game 4", games.get(5).getParticipantB().getName());
        assertEquals("Winner of game 5", games.get(6).getParticipantA().getName());
        assertEquals("Winner of game 6", games.get(6).getParticipantB().getName());
    }
}
