package ui.data;

import model.Team;
import ui.menu.AppFrame;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.util.HashSet;
import java.util.Set;

public class TeamsListModel implements ListModel<Team> {
    private final AppFrame frame;
    private final Set<ListDataListener> listeners;

    // EFFECTS: constructs a new TeamsListModel
    public TeamsListModel(AppFrame frame) {
        this.listeners = new HashSet<>();
        this.frame = frame;
    }

    @Override
    public int getSize() {
        return frame.getTournament().getTeams().size();
    }

    @Override
    public Team getElementAt(int index) {
        return frame.getTournament().getTeams().get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        listeners.add(l);
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        listeners.remove(l);
    }

    public void update() {
        for (ListDataListener l : listeners) {
            l.contentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, getSize() - 1));
        }
    }
}
