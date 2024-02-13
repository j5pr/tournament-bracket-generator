package model;

import model.game.GameContext;
import model.strategy.DoubleEliminationStrategy;
import model.strategy.RoundRobinStrategy;
import model.strategy.SingleEliminationStrategy;
import model.strategy.Strategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TournamentTest {
    private Team teamA;
    private Team teamB;
    private Team teamC;
    private Team teamD;

    private Tournament tournament;

    @BeforeEach
    public void runBefore() {
        teamA = new Team("Team A");
        teamB = new Team("Team B");
        teamC = new Team("Team C");
        teamD = new Team("Team D");

        tournament = new Tournament();
    }

    @Test
    public void testGenerateGames() {
        Strategy a = new RoundRobinStrategy(2);
        Strategy b = new SingleEliminationStrategy();
        Strategy c = new DoubleEliminationStrategy();

        List<Team> teams = List.of(teamA, teamB, teamC, teamD);
        tournament.setTeams(teams);

        assertNull(tournament.getGames());

        tournament.setStrategy(a);
        tournament.generateGames();
        assertEquals(a.generateSchedule(new GameContext(), teams).size(), tournament.getGames().size());

        tournament.setStrategy(b);
        tournament.generateGames();
        assertEquals(b.generateSchedule(new GameContext(), teams).size(), tournament.getGames().size());

        tournament.setStrategy(c);
        tournament.generateGames();
        assertEquals(c.generateSchedule(new GameContext(), teams).size(), tournament.getGames().size());
    }

    @Test
    public void testSetGetTeams() {
        List<Team> a = List.of(teamA);
        List<Team> b = List.of(teamB);
        List<Team> ab = List.of(teamA, teamB);

        assertEquals(List.of(), tournament.getTeams());

        tournament.setTeams(a);
        assertEquals(a, tournament.getTeams());

        tournament.setTeams(b);
        assertEquals(b, tournament.getTeams());

        tournament.setTeams(ab);
        assertEquals(ab, tournament.getTeams());
    }

    @Test
    public void testAddTeams() {
        assertEquals(List.of(), tournament.getTeams());

        tournament.addTeam(teamA);
        assertEquals(List.of(teamA), tournament.getTeams());

        tournament.addTeam(teamB);
        assertEquals(List.of(teamA, teamB), tournament.getTeams());
    }

    @Test
    public void testSetGetStrategy() {
        Strategy a = new RoundRobinStrategy(3);
        Strategy b = new SingleEliminationStrategy();
        Strategy c = new DoubleEliminationStrategy();

        assertNull(tournament.getStrategy());

        tournament.setStrategy(a);
        assertEquals(a, tournament.getStrategy());

        tournament.setStrategy(b);
        assertEquals(b, tournament.getStrategy());

        tournament.setStrategy(c);
        assertEquals(c, tournament.getStrategy());
    }
}
