package model.strategy;

import model.Team;
import model.game.Game;
import model.game.GameContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RoundRobinStrategyTest {
    private Strategy strategy;

    private Team[] teams;

    @BeforeEach
    public void runBefore() {
        strategy = new RoundRobinStrategy(2);

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

        assertEquals(2, games.size());

        assertEquals(teams[0], games.get(0).getParticipantA().getTeam());
        assertEquals(teams[1], games.get(0).getParticipantB().getTeam());

        assertEquals(teams[2], games.get(1).getParticipantA().getTeam());
        assertEquals(teams[3], games.get(1).getParticipantB().getTeam());
    }

    @Test
    public void testGenerateSchedule8() {
        List<Game> games = strategy.generateSchedule(new GameContext(), Arrays.asList(teams));

        assertEquals(4, games.size());

        assertEquals(teams[0], games.get(0).getParticipantA().getTeam());
        assertEquals(teams[1], games.get(0).getParticipantB().getTeam());
        assertEquals(teams[2], games.get(1).getParticipantA().getTeam());
        assertEquals(teams[3], games.get(1).getParticipantB().getTeam());
        assertEquals(teams[4], games.get(2).getParticipantA().getTeam());
        assertEquals(teams[5], games.get(2).getParticipantB().getTeam());
        assertEquals(teams[6], games.get(3).getParticipantA().getTeam());
        assertEquals(teams[7], games.get(3).getParticipantB().getTeam());
    }

    @Test
    public void testCanGenerateSchedule() {
        assertTrue(strategy.canGenerateSchedule(List.of()));
        assertFalse(strategy.canGenerateSchedule(List.of(teams[0])));
        assertTrue(strategy.canGenerateSchedule(List.of(teams[0], teams[1])));
        assertFalse(strategy.canGenerateSchedule(List.of(teams[0], teams[1], teams[2])));
        assertTrue(strategy.canGenerateSchedule(List.of(teams[0], teams[1], teams[2], teams[3])));
        assertFalse(strategy.canGenerateSchedule(List.of(teams[0], teams[1], teams[2], teams[3], teams[4])));
    }
}
