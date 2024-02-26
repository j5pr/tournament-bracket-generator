package persistence.serializers;

import model.Team;
import model.game.Game;
import model.participant.Participant;
import model.participant.ResultParticipant;
import model.participant.TeamParticipant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.Context;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ListSerializerTest {
    private Context ctx;

    private List<Game> list1;
    private List<Team> list2;
    private List<Team> list3;

    @BeforeEach
    public void runBefore() {
        Team team1 = new Team("Team 1");
        Team team2 = new Team("Team 2");

        Game game = new Game(1, new TeamParticipant(team1), new TeamParticipant(team2));

        list1 = List.of(game);
        list2 = List.of();
        list3 = List.of(team1, team2);

        ctx = new Context();

        ctx.put(Team.class, "Team 1", team1);
        ctx.put(Team.class, "Team 2", team2);
        ctx.put(Game.class, 1, game);
    }

    @Test
    public void testList1() {
        List<Game> result = ListSerializer.deserialize(ListSerializer.serialize(list1), ctx, Game.MAKE);

        assertEquals(list1.size(), result.size());

        Game game = result.get(0);

        assertEquals(1, game.getId());
        assertEquals("Team 1", game.getParticipantA().getName());
        assertEquals("Team 2", game.getParticipantB().getName());
        assertEquals(0, game.getScoreA());
        assertEquals(0, game.getScoreB());

        assertTrue(game.isReady());
        assertFalse(game.isFinished());
    }

    @Test
    public void testList2() {
        List<Team> result = ListSerializer.deserialize(ListSerializer.serialize(list2), ctx, Team.MAKE);
        assertEquals(0, result.size());
    }

    @Test
    public void testList3() {
        List<Team> result = ListSerializer.deserialize(ListSerializer.serialize(list3), ctx, Team.MAKE);
        assertEquals(list3.size(), result.size());
        assertEquals("Team 1", result.get(0).getName());
        assertEquals("Team 2", result.get(1).getName());
    }
}
