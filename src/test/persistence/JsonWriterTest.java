package persistence;

import model.Team;
import model.Tournament;
import model.game.Game;
import model.strategy.DoubleEliminationStrategy;
import model.strategy.RoundRobinStrategy;
import model.strategy.SingleEliminationStrategy;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {
    @Test
    public void testInvalidFile() {
        try (JsonWriter ignored = new JsonWriter("b\0ad_f\tile")) {
            fail("Expected JSON writer to fail");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testEmptyTournament() {
        Tournament tournament = new Tournament();
        Tournament read = writeAndRead("test-writer-empty", tournament);

        assertNotNull(read);
        assertEquals(0, read.getTeams().size());
        assertNull(read.getGames());
        assertNull(read.getStrategy());
    }

    @Test
    public void testFullTournament1() {
        Tournament tournament = new Tournament();

        for (int i = 1; i <= 16; i++) {
            tournament.addTeam(new Team("Team " + i));
        }

        tournament.setStrategy(new DoubleEliminationStrategy());
        tournament.generateGames();

        Game first = tournament.getGames().get(0);
        first.setScoreA(1);
        first.setScoreB(2);
        first.complete();

        Tournament read = writeAndRead("test-writer-16tde", tournament);

        assertNotNull(read);
        assertEquals(tournament.getTeams().size(), read.getTeams().size());
        assertEquals(tournament.getGames().size(), read.getGames().size());
        assertEquals(DoubleEliminationStrategy.class, read.getStrategy().getClass());

        Game readFirst = read.getGames().get(0);

        assertNotNull(readFirst);
        assertEquals(first.getScoreA(), readFirst.getScoreA());
        assertEquals(first.getScoreB(), readFirst.getScoreB());
        assertTrue(readFirst.isFinished());
    }

    @Test
    public void testFullTournament2() {
        Tournament tournament = new Tournament();

        for (int i = 1; i <= 128; i++) {
            tournament.addTeam(new Team("Team " + i));
        }

        tournament.setStrategy(new SingleEliminationStrategy());
        tournament.generateGames();

        Game first = tournament.getGames().get(0);
        first.setScoreA(1);
        first.setScoreB(2);
        first.complete();

        Tournament read = writeAndRead("test-writer-128tse", tournament);

        assertNotNull(read);
        assertEquals(tournament.getTeams().size(), read.getTeams().size());
        assertEquals(tournament.getGames().size(), read.getGames().size());
        assertEquals(SingleEliminationStrategy.class, read.getStrategy().getClass());

        Game readFirst = read.getGames().get(0);

        assertNotNull(readFirst);
        assertEquals(first.getScoreA(), readFirst.getScoreA());
        assertEquals(first.getScoreB(), readFirst.getScoreB());
        assertTrue(readFirst.isFinished());
    }

    @Test
    public void testFullTournament3() {
        Tournament tournament = new Tournament();

        for (int i = 1; i <= 81; i++) {
            tournament.addTeam(new Team("Team " + i));
        }

        tournament.setStrategy(new RoundRobinStrategy(9));
        tournament.generateGames();

        Game first = tournament.getGames().get(0);
        first.setScoreA(1);
        first.setScoreB(2);
        first.complete();

        Tournament read = writeAndRead("test-writer-81trr", tournament);

        assertNotNull(read);
        assertEquals(tournament.getTeams().size(), read.getTeams().size());
        assertEquals(tournament.getGames().size(), read.getGames().size());
        assertEquals(RoundRobinStrategy.class, read.getStrategy().getClass());

        Game readFirst = read.getGames().get(0);

        assertNotNull(readFirst);
        assertEquals(first.getScoreA(), readFirst.getScoreA());
        assertEquals(first.getScoreB(), readFirst.getScoreB());
        assertTrue(readFirst.isFinished());
    }

    private Tournament writeAndRead(String file, Tournament tournament) {
        try (JsonWriter writer = new JsonWriter(file)) {
            writer.writeObject(tournament);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }

        try (JsonReader reader = new JsonReader(file)) {
            return reader.readTournament();
        } catch (IOException e) {
            return fail("Exception should not have been thrown");
        }
    }
}
