# Tournament Bracket Management Application

### Project Proposal

I would like to design a tournament bracket management application.
The application would allow a user to design and visualize a tournament bracket
of various types, input the teams to participate in the tournament, and generate
a visual view of the generated bracket.
Using this generated view, the user would be able to input the results of each
game in the tournament, and input the results of each game. When all games
are complete, the filled-in bracket would be available for the user to view.

This application would be useful for people running tournaments, as it would
provide an interface and visualization for planning tournaments.
I am interested in this project because I have experience running game tournaments,
and would find benefit from such an application. I also find interest in
different ways of visualizing data, which would be a core part of this application.

### User Stories

As a user:
1. I want to be able to create teams and add them to a tournament
2. I want to be able to view the list of teams in a tournament
3. I want to be able to select the type of tournament bracket to use, such as:
    - _Single Elimination_
    - _Double Elimination_
    - _Round Robin_
4. I want to generate a list of games based on the selected bracket type
5. I want to view the list of games in a bracket view, based on the list of games generated
6. I want to input scores for each game, and have the bracket updated accordingly
7. I want to be able to save my tournament to file (if I so choose)
8. I want to be able to be able to load my tournament from file (if I so choose)

### Instructions for Grader
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by
  clicking the "+" button in the Team Manager sidebar once the application is running.
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by
  selecting a team in the team list, and clicking the "-" button in the Team Manager.
- You can locate my visual component by looking at the splash screen, which is an image displayed for at
  least 2500ms when the application is first run, before the main window is available.
- You can save the state of my application by clicking the "Save" button in the File menu,
  which can be found in the menu bar of the main window.
- You can reload the state of my application by clicking the "Load" button in the File menu,
  which can be found in the menu bar of the main window.

### Phase 4: Task 2
```
Thu Mar 28 14:08:33 PDT 2024
Added team to tournament: team 1
Thu Mar 28 14:08:35 PDT 2024
Added team to tournament: team 2
Thu Mar 28 14:08:37 PDT 2024
Added team to tournament: team 3
Thu Mar 28 14:08:39 PDT 2024
Added team to tournament: team 4
Thu Mar 28 14:08:41 PDT 2024
Added team to tournament: team 5
Thu Mar 28 14:08:43 PDT 2024
Removed team from tournament: team 3
Thu Mar 28 14:08:47 PDT 2024
Generated games for tournament
```

### Phase 4: Task 3
If I had more time to work on this project, I would work on making the design
of my serialization framework more portable. For example, the use of a Context
object when deserializing classes is an obvious trade-off in design for
simplicity. I would improve this by removing the need for Context to be manually
tracked, and incorporate a reference-by-identifier system.

This can also be applied to the GameContext object in my main model. Currently,
GameContext is only used to track the creation of Games and assign appropriate
identifiers. I would improve this by removing the GameContext class, and expose
specific methods to Strategy classes that would create a queue of un-identified
games, and assign identifiers at the end. This would reduce the need for an
entire class to keep track of one trivial piece of information.
