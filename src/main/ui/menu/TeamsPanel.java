package ui.menu;

import model.Team;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class TeamsPanel extends JPanel {
    private final AppFrame frame;
    private JList<Team> teamsList;

    // EFFECTS: constructs a new TeamsPanel
    public TeamsPanel(AppFrame frame) {
        super();
        this.frame = frame;

        setLayout(new BorderLayout());
        setVisible(true);

        addTitle();
        addTeamsList();
        addButtonPanel();
    }

    private void addTitle() {
        JLabel teamsLabel = new JLabel("Manage Teams");
        teamsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(teamsLabel, BorderLayout.NORTH);
    }

    private void addTeamsList() {
        teamsList = new JList<>(frame.getTeamsListModel());
        teamsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        teamsList.setLayoutOrientation(JList.VERTICAL);
        teamsList.setVisibleRowCount(8);

        add(new JScrollPane(teamsList), BorderLayout.CENTER);
    }

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

    private void addTeam(ActionEvent e) {
        String teamName = JOptionPane.showInputDialog("Enter team name:");

        if (teamName != null) {
            frame.getTournament().addTeam(new Team(teamName));
            frame.update();
        }
    }

    private void removeTeam(ActionEvent e) {
        frame.getTournament().getTeams().remove(teamsList.getSelectedValue());
        frame.update();
    }

    private void clearTeams(ActionEvent e) {
        frame.getTournament().setTeams(new ArrayList<>());
        frame.update();
    }
}
