package ui.data;

import model.game.Game;
import ui.menu.AppFrame;

import javax.swing.table.AbstractTableModel;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// a table model for games in a tournament
public class GamesTableModel extends AbstractTableModel {
    private final AppFrame frame;

    // EFFECTS: constructs a new GamesTableModel with the given AppFrame
    public GamesTableModel(AppFrame frame) {
        this.frame = frame;
    }

    // EFFECTS: returns the list of games in the tournament
    private List<Game> getGames() {
        List<Game> games = frame.getTournament().getGames();

        return games == null
            ? List.of()
            : games.stream().sorted(Comparator.comparingInt(Game::getId)).collect(Collectors.toList());
    }

    // EFFECTS: returns the number of rows in the table
    @Override
    public int getRowCount() {
        List<Game> games = frame.getTournament().getGames();
        return games != null ? games.size() : 0;
    }

    // EFFECTS: returns the number of columns in the table (always 6)
    @Override
    public int getColumnCount() {
        return 6;
    }

    // EFFECTS: returns the name of the column at the given index
    @Override
    public String getColumnName(int columnIndex) {
        return new String[]{
            "ID",
            "Team 1",
            "Team 2",
            "Score 1",
            "Score 2",
            "Winner"
        }[columnIndex];
    }

    // EFFECTS: returns the value at the given row and column
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Game game = getGames().get(rowIndex);

        switch (columnIndex) {
            case 0:
                return game.getId();
            case 1:
                return game.getParticipantA();
            case 2:
                return game.getParticipantB();
            case 3:
                return game.getScoreA();
            case 4:
                return game.getScoreB();
            case 5:
                return game.getWinner();
            default:
                return null;
        }
    }

    // EFFECTS: returns the game at the given row
    public Game getGame(int row) {
        return getGames().get(row);
    }
}
