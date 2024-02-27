package persistence;

import model.Tournament;
import model.game.Game;
import model.strategy.SingleEliminationStrategy;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {
    @Test
    public void testInvalidFile() {
        try (JsonReader ignored = new JsonReader("bad-file")) {
            fail("Expected JSON writer to fail");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testEmptyTournament() {
        try (JsonReader reader = new JsonReader("test-reader-empty")) {
            Tournament read = reader.readTournament();

            assertNotNull(read);
            assertEquals(0, read.getTeams().size());
            assertNull(read.getGames());
            assertNull(read.getStrategy());

            reader.reset();

            Tournament read2 = reader.readTournament();

            assertNotNull(read2);
            assertEquals(0, read2.getTeams().size());
            assertNull(read2.getGames());
            assertNull(read2.getStrategy());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testFullTournament() {
        try (JsonReader reader = new JsonReader("test-reader-4tse")) {
            Tournament read = reader.readTournament();

            assertNotNull(read);
            assertEquals(4, read.getTeams().size());
            assertEquals(3, read.getGames().size());
            assertEquals(SingleEliminationStrategy.class, read.getStrategy().getClass());

            Game readFirst = read.getGames().get(0);

            assertNotNull(readFirst);
            assertEquals(1, readFirst.getScoreA());
            assertEquals(2, readFirst.getScoreB());
            assertTrue(readFirst.isFinished());

            Game readSecond = read.getGames().get(1);

            assertNotNull(readSecond);
            assertEquals(3, readSecond.getScoreA());
            assertEquals(0, readSecond.getScoreB());
            assertTrue(readSecond.isFinished());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
