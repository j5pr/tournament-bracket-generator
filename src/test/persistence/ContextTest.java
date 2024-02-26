package persistence;

import model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContextTest {
    private Context ctx;

    private Team team;
    private Team team2;

    @BeforeEach
    public void runBefore() {
        ctx = new Context();
        team = new Team("hi");
        team2 = new Team("hi2");
    }

    @Test
    public void testStringKey() {
        ctx.put(Team.class, "hi", team);
        ctx.put(Team.class, "hi2", team2);

        assertEquals(team, ctx.get(Team.class, "hi"));
        assertEquals(team2, ctx.get(Team.class, "hi2"));
    }

    @Test
    public void testIntKey() {
        ctx.put(Team.class, 1, team);
        ctx.put(Team.class, 7, team2);

        assertEquals(team, ctx.get(Team.class, 1));
        assertEquals(team2, ctx.get(Team.class, 7));
    }
}
