package ui.data;

import model.Team;
import ui.menu.AppFrame;

import javax.swing.*;

// a list model for teams in a tournament
public class TeamsListModel extends AbstractListModel<Team> {
    private final AppFrame frame;

    // EFFECTS: constructs a new TeamsListModel with the given AppFrame
    public TeamsListModel(AppFrame frame) {
        this.frame = frame;
    }

    // EFFECTS: returns the number of teams in the tournament
    @Override
    public int getSize() {
        return frame.getTournament().getTeams().size();
    }

    // EFFECTS: returns the team at the given index
    @Override
    public Team getElementAt(int index) {
        return frame.getTournament().getTeams().get(index);
    }

    // EFFECTS: notifies listeners that the contents of the list model have changed
    public void fireContentsChanged() {
        super.fireContentsChanged(this, 0, getSize() - 1);
    }
}
