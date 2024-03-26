package ui.menu;

import javax.swing.*;
import java.awt.*;

public class AppPanel extends JPanel {
    // EFFECTS: constructs a new AppPanel
    public AppPanel(AppFrame frame) {
        super();

        setLayout(new BorderLayout());

        GamesPanel gamesPanel = new GamesPanel(frame);
        add(gamesPanel, BorderLayout.CENTER);

        TeamsPanel teamsPanel = new TeamsPanel(frame);
        add(teamsPanel, BorderLayout.EAST);
    }
}
