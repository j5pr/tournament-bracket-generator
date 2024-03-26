package ui.menu;

import model.strategy.DoubleEliminationStrategy;
import model.strategy.RoundRobinStrategy;
import model.strategy.SingleEliminationStrategy;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

// the menu bar for the tournament app
public class AppMenu extends JMenuBar {
    private final AppFrame frame;

    // EFFECTS: constructs a new AppMenu for the given AppFrame
    public AppMenu(AppFrame frame) {
        super();
        this.frame = frame;

        addFileMenu();
        addTournamentMenu();
    }

    // MODIFIES: this
    // EFFECTS: adds the file menu to the menu bar for saving and loading
    private void addFileMenu() {
        JMenu fileMenu = new JMenu("File");
        add(fileMenu);

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(this::save);
        fileMenu.add(saveItem);

        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.addActionListener(this::load);
        fileMenu.add(loadItem);
    }

    // MODIFIES: this
    // EFFECTS: adds the tournament menu to the menu bar for selecting tournament strategy and generating bracket
    private void addTournamentMenu() {
        JMenu tournamentMenu = new JMenu("Tournament");
        add(tournamentMenu);

        JMenu strategyMenu = new JMenu("Strategy");
        tournamentMenu.add(strategyMenu);

        JMenuItem singleEliminationItem = new JMenuItem("Single Elimination");
        singleEliminationItem.addActionListener(this::useSingleElimination);
        strategyMenu.add(singleEliminationItem);

        JMenuItem doubleEliminationItem = new JMenuItem("Double Elimination");
        doubleEliminationItem.addActionListener(this::useDoubleElimination);
        strategyMenu.add(doubleEliminationItem);

        JMenuItem roundRobinItem = new JMenuItem("Round Robin");
        roundRobinItem.addActionListener(this::useRoundRobin);
        strategyMenu.add(roundRobinItem);

        JMenuItem generateItem = new JMenuItem("Generate Bracket");
        generateItem.addActionListener(this::generate);
        tournamentMenu.add(generateItem);
    }

    // EFFECTS: saves the current tournament to a file
    private void save(ActionEvent e) {
        String fileName = JOptionPane.showInputDialog(frame, "Enter file name:");

        if (fileName == null) {
            return;
        }

        try (JsonWriter writer = new JsonWriter(fileName)) {
            writer.writeObject(frame.getTournament());
        } catch (IOException err) {
            JOptionPane.showMessageDialog(frame, "Unable to save to " + fileName);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads a tournament from a file
    private void load(ActionEvent e) {
        String fileName = JOptionPane.showInputDialog(frame, "Enter file name:");

        if (fileName == null) {
            return;
        }

        try (JsonReader reader = new JsonReader(fileName)) {
            frame.setTournament(reader.readTournament());
            frame.update();
        } catch (IOException err) {
            JOptionPane.showMessageDialog(frame, "Unable to load from " + fileName);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the tournament strategy to single elimination
    private void useSingleElimination(ActionEvent e) {
        frame.getTournament().setStrategy(new SingleEliminationStrategy());
        frame.update();
    }

    // MODIFIES: this
    // EFFECTS: sets the tournament strategy to double elimination
    private void useDoubleElimination(ActionEvent e) {
        frame.getTournament().setStrategy(new DoubleEliminationStrategy());
        frame.update();
    }

    // MODIFIES: this
    // EFFECTS: sets the tournament strategy to use round-robin, prompting the user for the number of teams per group
    private void useRoundRobin(ActionEvent e) {
        int teamsPerGroup = Integer.parseInt(JOptionPane.showInputDialog(frame, "How many teams per group?"));
        frame.getTournament().setStrategy(new RoundRobinStrategy(teamsPerGroup));
        frame.update();
    }

    // MODIFIES: this
    // EFFECTS: generates games for the tournament using the previously selected strategy
    private void generate(ActionEvent e) {
        frame.getTournament().generateGames();
        frame.update();
    }
}
