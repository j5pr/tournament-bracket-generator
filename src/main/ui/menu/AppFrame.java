package ui.menu;

import model.Tournament;
import persistence.JsonReader;
import ui.data.GamesTableModel;
import ui.data.TeamsListModel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class AppFrame extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private Tournament tournament;

    private final TeamsListModel teamsListModel;
    private final GamesTableModel gamesTableModel;


    // EFFECTS: constructs a new AppFrame
    public AppFrame() {
        super("Tournament App");

        tournament = new Tournament();

        // udfuhasduhif
        try (JsonReader reader = new JsonReader("test-reader-4tse")) {
            tournament = reader.readTournament();
        } catch (IOException e) {
            //
        }

        setLayout(new FlowLayout());
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        teamsListModel = new TeamsListModel(this);
        gamesTableModel = new GamesTableModel(this);

        setJMenuBar(new AppMenu(this));

        add(new AppPanel(this));

        pack();
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public TeamsListModel getTeamsListModel() {
        return teamsListModel;
    }

    public GamesTableModel getGamesTableModel() {
        return gamesTableModel;
    }

    public void update() {
        teamsListModel.update();
        gamesTableModel.update();
    }
}
