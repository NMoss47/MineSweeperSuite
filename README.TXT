

Introduction

I started the MineSweeperSuite as a way to practice more coding fundamentals.
It started out fairly organized but as time progressed it got messy. 
I did, however, learn more techniques and best practices through it.

Overview

    The MineSweeperSuite follows the Model-View-Adapter design pattern. This 
architecture tries to seperate out the user interface and the bread-and-butter 
parts of the application through the View and the Model. The adapter is 
supposed to be a kind of mediator between the two.

    The Model of the MVA design pattern is contained within the MineSweeper.java
file. This file contains most of the logic for creating and playing a game of 
mine sweeper. 

    The view of the design pattern is contained within the MineSweeperGUI.java 
file. It contains most of the logic required to create the view as a Java 
Swing application. GUI programming is not my speciality. 

    The Adapter of the design pattern is contained within the
MineSweeperAdapter.java file. Most of its methods simply call a corresponding 
method in either the view or the model.

    A breakdown of the architecture by classes can be seen here:

    Main (1):
        MineSweeperSuite.java           | Contains Main(). Creates MVA components.

    Model (9):
        MineSweeper.java                | The game itself. Contains game logic.
        GameStatus.java                 | Enum used to game state/errors.
        LandNode.java                   | Object representing a unit of land.
        Marking.java                    | Enum used to representing marking state.
        StatisticEntry.java             | Serialized object for a game time record.
        MineSweeperStatistics.java      | Serialized object of game time records.
        MineSweeperTimer.java           | Crappy Swing timer of elapsed game time.
        Mode.java                       | Enum of game modes. Contains various params.
        GameParameters.java             | Object conntaining game parameters.

    View (7):
        MineSweeperGUI.java             | The view/GUI of the game. Interfaces with adapter.
        NewGameWindow.java              | View component. Used to start a new game.
        StatsWindow.java                | View components. Used to view/update game records.
        LandButton.java                 | Custom JToggleButton used to represent LandNode.
        CustomGridBagConstraints.java   | GridBagCosntraints object with "convenience functions".
        Icons.java                      | Class used to read and retreive ImageIcons for view.
        ErrorHandler.java               | Used to create dynamic error messages.

    Adapter (2):
        MineSweeperAdapter.java         | Adapter component. Is interface between model and view.
        MineSweeperIO.java              | Used to write/read objects to/from a file.


    Interfaces/APIs (9):
        ModelAPI.java                   | Constants and function declarations. Used with Model.
        ViewAPI.java                    | Constants and function declarations. Used with View.
        AdapterAPI.java                 | Function declarations. Used with Adapter.
        GridAPI.java                    | Grid-specific function declarations. Used with Model/View.
        IOAPI.java                      | Constants and functon declarations. Used with Adapter.
        TimerAPI.java                   | Function declarations. Used with Model/Adapter.
        Verion.java                     | Constants. Used with View.
        GameStatusInterface.java        | Constants. Used with Model.
        LandInterface.java              | Function declarations. Used with Model.

This README will change in the future.