package ui.menu;

import javax.swing.*;
import java.awt.*;

// the main panel for the tournament app
public class AppPanel extends JPanel {
    // EFFECTS: constructs a new AppPanel with the given AppFrame
    public AppPanel(AppFrame frame) {
        super();

        setLayout(new BorderLayout());

        GamesPanel gamesPanel = new GamesPanel(frame);
        add(gamesPanel, BorderLayout.CENTER);

        TeamsPanel teamsPanel = new TeamsPanel(frame);
        add(teamsPanel, BorderLayout.EAST);
    }
}
