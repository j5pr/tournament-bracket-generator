package ui.data;

import model.Team;
import model.game.Game;
import model.participant.Participant;
import ui.menu.AppFrame;

import javax.swing.event.ListDataEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GamesTableModel implements TableModel {
    private final AppFrame frame;
    private final Set<TableModelListener> listeners;

    public GamesTableModel(AppFrame frame) {
        this.frame = frame;
        this.listeners = new HashSet<>();
    }

    private List<Game> getGames() {
        List<Game> games = frame.getTournament().getGames();
        return games == null
            ? List.of()
            : games.stream().sorted(Comparator.comparingInt(Game::getId)).collect(Collectors.toList());
    }

    @Override
    public int getRowCount() {
        return getGames().size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

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

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return new Class<?>[]{
            Integer.class,
            Participant.class,
            Participant.class,
            Integer.class,
            Integer.class,
            Team.class
        }[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

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

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }

    public void update() {
        for (TableModelListener l : listeners) {
            l.tableChanged(new TableModelEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, getRowCount() - 1));
        }
    }

    public Game getGame(int row) {
        return getGames().get(row);
    }
}
