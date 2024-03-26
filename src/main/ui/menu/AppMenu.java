package ui.menu;

import model.strategy.DoubleEliminationStrategy;
import model.strategy.RoundRobinStrategy;
import model.strategy.SingleEliminationStrategy;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class AppMenu extends JMenuBar {
    private final AppFrame frame;

    // EFFECTS: constructs a new AppMenu
    public AppMenu(AppFrame frame) {
        super();
        this.frame = frame;

        addFileMenu();
        addTournamentMenu();
    }

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

    private void save(ActionEvent e) {
        String fileName = JOptionPane.showInputDialog("Enter file name:");

        try (JsonWriter writer = new JsonWriter(fileName)) {
            writer.writeObject(frame.getTournament());

            System.out.println("Saved " + fileName);
        } catch (IOException err) {
            System.out.println("Unable to save to " + fileName);
        }
    }

    private void load(ActionEvent e) {
        String fileName = JOptionPane.showInputDialog("Enter file name:");

        try (JsonReader reader = new JsonReader(fileName)) {
            frame.setTournament(reader.readTournament());
            frame.getTeamsListModel().update();

            System.out.println("Loaded " + fileName);
        } catch (IOException err) {
            System.out.println("Unable to load from " + fileName);
        }
    }

    private void useSingleElimination(ActionEvent e) {
        frame.getTournament().setStrategy(new SingleEliminationStrategy());
    }

    private void useDoubleElimination(ActionEvent e) {
        frame.getTournament().setStrategy(new DoubleEliminationStrategy());
    }

    private void useRoundRobin(ActionEvent e) {
        int teamsPerGroup = Integer.parseInt(JOptionPane.showInputDialog("How many teams per group?"));
        frame.getTournament().setStrategy(new RoundRobinStrategy(teamsPerGroup));
    }

    private void generate(ActionEvent e) {
        frame.getTournament().generateGames();
        frame.update();
    }
}
