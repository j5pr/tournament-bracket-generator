package ui.menu;

import model.game.Game;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// the panel for displaying games in the tournament
public class GamesPanel extends JPanel {
    private final AppFrame frame;

    // EFFECTS: constructs a new GamesPanel with the given AppFrame
    public GamesPanel(AppFrame frame) {
        super();
        this.frame = frame;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setVisible(true);

        addTitle();
        addGamesList();
    }

    // MODIFIES: this
    // EFFECTS: adds a title label to the panel
    private void addTitle() {
        JLabel gamesLabel = new JLabel("Games");
        gamesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(gamesLabel);
    }

    // MODIFIES: this
    // EFFECTS: adds a list of games to the panel
    private void addGamesList() {
        JTable gamesList = new JTable(frame.getGamesTableModel());
        gamesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        gamesList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    Game game = frame.getGamesTableModel().getGame(gamesList.getSelectedRow());

                    if (game != null && game.isReady()) {
                        int scoreA = Integer.parseInt(
                                JOptionPane.showInputDialog("Enter score for " + game.getParticipantA().getName())
                        );

                        int scoreB = Integer.parseInt(
                                JOptionPane.showInputDialog("Enter score for " + game.getParticipantB().getName())
                        );

                        game.setScoreA(scoreA);
                        game.setScoreB(scoreB);
                        game.complete();

                        frame.update();
                    }
                }
            }
        });

        add(new JScrollPane(gamesList));
    }
}
