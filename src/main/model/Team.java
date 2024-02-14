package model;

// represents a team, which can play games and participate in a tournament
public class Team {
    private String name;

    // EFFECTS: create a new team with the given name
    public Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
