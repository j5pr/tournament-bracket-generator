package ui.menu;

import model.Team;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

// the panel for displaying and managing teams in the tournament
public class TeamsPanel extends JPanel {
    private final AppFrame frame;
    private JList<Team> teamsList;

    // EFFECTS: constructs a new TeamsPanel with the given AppFrame
    public TeamsPanel(AppFrame frame) {
        super();
        this.frame = frame;

        setLayout(new BorderLayout());
        setVisible(true);

        addTitle();
        addTeamsList();
        addButtonPanel();
    }

    // MODIFIES: this
    // EFFECTS: adds a title label to the panel
    private void addTitle() {
        JLabel teamsLabel = new JLabel("Manage Teams");
        teamsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(teamsLabel, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: adds a scrollable list of teams to the panel
    private void addTeamsList() {
        teamsList = new JList<>(frame.getTeamsListModel());
        teamsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        teamsList.setLayoutOrientation(JList.VERTICAL);
        teamsList.setVisibleRowCount(8);

        add(new JScrollPane(teamsList), BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: adds a panel with buttons for adding, removing, and clearing all teams
    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));

        JButton addButton = new JButton("+");
        addButton.addActionListener(this::addTeam);
        buttonPanel.add(addButton, 0);

        JButton removeButton = new JButton("-");
        removeButton.addActionListener(this::removeTeam);
        buttonPanel.add(removeButton, 1);

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(this::clearTeams);
        buttonPanel.add(clearButton, 2);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: prompts the user for a team name and adds a new team to the tournament
    private void addTeam(ActionEvent e) {
        String teamName = JOptionPane.showInputDialog(frame, "Enter team name:");

        if (teamName != null) {
            frame.getTournament().addTeam(new Team(teamName));
            frame.update();
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the selected team from the tournament, if one is selected
    private void removeTeam(ActionEvent e) {
        frame.getTournament().getTeams().remove(teamsList.getSelectedValue());
        frame.update();
    }

    // MODIFIES: this
    // EFFECTS: clears all teams from the tournament
    private void clearTeams(ActionEvent e) {
        frame.getTournament().setTeams(new ArrayList<>());
        frame.update();
    }
}
