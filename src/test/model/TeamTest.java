package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TeamTest {
    private Team teamA;
    private Team teamB;

    @BeforeEach
    public void runBefore() {
        teamA = new Team("Team A");
        teamB = new Team("Team B");
    }

    @Test
    public void testGetName() {
        assertEquals("Team A", teamA.getName());
        assertEquals("Team B", teamB.getName());
    }

    @Test
    public void testSetName() {
        teamA.setName("123456");
        assertEquals("123456", teamA.getName());
    }

    @Test
    public void testToString() {
        assertEquals("Team A", teamA.toString());
        assertEquals("Team B", teamB.toString());
    }
}
