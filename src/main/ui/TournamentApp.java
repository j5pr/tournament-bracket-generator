package ui;

import model.Team;
import model.Tournament;
import model.game.Game;
import model.participant.Participant;
import model.participant.ResultParticipant;
import model.strategy.DoubleEliminationStrategy;
import model.strategy.RoundRobinStrategy;
import model.strategy.SingleEliminationStrategy;
import model.strategy.Strategy;

import java.util.Scanner;

// console interface to manage a tournament
public class TournamentApp {
    private final Tournament tournament;
    private final Scanner in;

    // EFFECTS: creates a new tournament app with an empty tournament
    public TournamentApp() {
        tournament = new Tournament();
        in = new Scanner(System.in);

        in.useDelimiter("\n");
    }

    // MODIFIES: this
    // EFFECTS: start the application and process user input
    public void start() {
        while (true) {
            displayMenu();

            char command = prompt(null).charAt(0);

            if (command == 'q') {
                break;
            }

            processCommand(command);
        }

        System.out.println("bye");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(char command) {
        switch (command) {
            case 'a':
                addTeam();
                break;
            case 'l':
                listTeams();
                break;
            case 's':
                selectStrategy();
                break;
            case 'g':
                generateBracket();
                break;
            case 'v':
                viewBracket();
                break;
            case 'i':
                inputResult();
                break;
            default:
                System.out.println("Invalid selection!");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new team and adds it to the tournament
    private void addTeam() {
        String name = prompt("What is the name of the team?");
        tournament.addTeam(new Team(name));
    }

    // EFFECTS: prints a list of current teams to the console
    private void listTeams() {
        System.out.println("Current teams:");

        for (Team team : tournament.getTeams()) {
            System.out.println("\t- " + team.getName());
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts the user for a strategy and sets the tournament to use it;
    //          if a games have already been generated, reset the bracket
    private void selectStrategy() {
        if (tournament.getGames() != null) {
            tournament.setGames(null);
        }

        System.out.println("Which strategy to use?");
        System.out.println("\t(s) single elimination");
        System.out.println("\t(d) double elimination");
        System.out.println("\t(r) round robin");

        Strategy strategy = null;

        char command = prompt(null).charAt(0);

        if (command == 's') {
            strategy = new SingleEliminationStrategy();
        } else if (command == 'd') {
            strategy = new DoubleEliminationStrategy();
        } else if (command == 'r') {
            int size = Integer.parseInt(prompt("What group size to use for round robin?"));
            strategy = new RoundRobinStrategy(size);
        } else {
            System.out.println("Invalid selection!");
        }

        if (strategy != null) {
            tournament.setStrategy(strategy);
            System.out.println("Strategy selected!");
        }
    }

    // MODIFIES: this
    // EFFECTS: generates a bracket using the selected strategy
    private void generateBracket() {
        if (tournament.getStrategy() == null) {
            System.out.println("Invalid selection!");
            return;
        }

        tournament.generateGames();
        System.out.println("Bracket generated!");
    }

    // EFFECTS: prints all of the games in the bracket
    private void viewBracket() {
        if (tournament.getGames() == null) {
            System.out.println("Invalid selection!");
            return;
        }

        int round = 1;
        int upUntil = 0;

        System.out.println("Round 1:");

        for (Game game : tournament.getGames()) {
            int highestNeeded = 0;

            Participant participantA = game.getParticipantA();
            Participant participantB = game.getParticipantB();

            if (participantA instanceof ResultParticipant) {
                highestNeeded = ((ResultParticipant)participantA).getGame().getId();
            }

            if (participantB instanceof ResultParticipant) {
                highestNeeded = ((ResultParticipant)participantB).getGame().getId();
            }

            if (highestNeeded > upUntil) {
                upUntil = game.getId();
                System.out.println("\nRound " + (++round) + ":");
            }

            System.out.println("\t" + formatGame(game));
        }
    }

    // EFFECTS: formats a game into a readable string
    private String formatGame(Game game) {
        Participant participantA = game.getParticipantA();
        Participant participantB = game.getParticipantB();

        StringBuilder str = new StringBuilder();

        str.append(game.getId()).append(". ");
        str.append(participantA.getName()).append(" vs. ").append(participantB.getName());

        if (game.isReady() || game.isFinished()) {
            str.append(" (").append(game.getScoreA()).append(" - ").append(game.getScoreB()).append(")");
        }

        return str.toString();
    }

    // MODIFIES: this
    // EFFECTS: asks users for scores of a game, and sets the score of that game
    private void inputResult() {
        int id = Integer.parseInt(prompt("What is the id of the game to edit?"));
        Game game = tournament.findReadyGame(id);

        if (game == null) {
            System.out.println("Could not find a game that is ready with the specified id!");
            return;
        }

        int scoreA = Integer.parseInt(prompt("What is score for " + game.getParticipantA().getName() + "?"));
        int scoreB = Integer.parseInt(prompt("What is score for " + game.getParticipantB().getName() + "?"));

        game.setScoreA(scoreA);
        game.setScoreB(scoreB);
        game.complete();

        System.out.println("Successfully inputted scores!");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nPlease select an action:");

        System.out.println("\t(a) add team");
        System.out.println("\t(l) list teams");

        if (tournament.getGames() == null) {
            System.out.println("\t(s) select strategy");
        } else {
            System.out.println("\t(s) select a new strategy");
        }

        if (tournament.getStrategy() != null) {
            System.out.println("\t(g) generate bracket");
        }

        if (tournament.getGames() != null) {
            System.out.println("\t(v) view bracket");
            System.out.println("\t(i) input result");
        }

        System.out.println("\t(q) quit");
    }

    // EFFECTS: prompts the user for input and returns the string provided
    private String prompt(String prompt) {
        if (prompt != null) {
            System.out.println(prompt);
        }
        System.out.print("> ");
        String result = in.nextLine();
        System.out.println();
        return result;
    }
}
