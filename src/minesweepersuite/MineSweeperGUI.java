/*
 * MIT License
 * 
 * Copyright (c) 2018 Nicholas Moss
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 */

package minesweepersuite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * This class is the GUI of the mine sweeper application. It is also the view 
 * component of the Model-View-Adapter design pattern.
 * 
 * @author Nicholas Moss
 */
public class MineSweeperGUI extends JFrame implements ViewAPI, 
                                                      ActionListener, 
                                                      Version {
    
    // The adapter
    private final MineSweeperAdapter adapter;
    
    // Supplmentary windows
    private NewGameWindow newGameWindow;
    private StatsWindow statsWindow;
    
    // Grid bag constraints
    private final CustomGridBagConstraints constraints;
    
    // GUI Components
    // Icons
    private final Icons ICONS = new Icons( );
    // Main/Game Panels
    private final JPanel mainPanel;       // This panel holds everything
    private final JPanel pausePanel;      // This panel obscures the game.
    private final JScrollPane scrollPane; // This is the scrollable pane.
    private final JPanel gamePanel;       // This is the game panel.
    
    // Informational and Button Panels
    private final JPanel statusPanel;   // This panel holds the timer and score
    private final JPanel buttonPanel;   // This panel holds some buttons
    private final JPanel sbPanel;       // This panel holds the status/btn panel
    
    // Buttons and other such stuff
    private final JButton pause;          // Used to pause the game
    private final JButton newGame;        // Used to clear and start a new game
    private final JButton stats;          // Used to view the stats
    private final JLabel timeLabel;       // Used to see current elapsed time.
    private final JLabel mineRatioLabel;  // Used to see current marked mines.
    private LandButton[][] grid;          // Grid of land buttons.

    private boolean gameStarted;
    
    /**
     * Default constructor. Simply initializes objects.
     * 
     * @param adapter A reference to the MineSweeperAdapter.
     */
    public MineSweeperGUI( MineSweeperAdapter adapter ) {
        
        super( GAMETITLE + " - " + VERSION );
        this.adapter = adapter;
        grid = null;
        constraints = new CustomGridBagConstraints( );
        constraints.fill = GridBagConstraints.BOTH;
                
        // Panel initializations
        mainPanel = new JPanel( );
        pausePanel = new JPanel( );
        gamePanel = new JPanel( );
        scrollPane = new JScrollPane( );
        statusPanel = new JPanel( );
        buttonPanel = new JPanel( );
        
        sbPanel = new JPanel( );
        
        // Buttons and such
        pause = new JButton( "Pause" );
        newGame = new JButton( "New Game" );
        stats = new JButton( "Stats" );
        timeLabel = new JLabel("Time: ");
        mineRatioLabel = new JLabel("Mines: ");
        
        gameStarted = false;
        
    } // End of MineSweeperGUI( )
    
    /**
     * This function creates and sets up the view/GUI.
     */
    public void initComponents( ) {
        
        this.setSize( WINDOW_WIDTH, WINDOW_HEIGHT );
        
        // Setup the panels
        mainPanel.setLayout(new BorderLayout( ));
        gamePanel.setLayout(new GridBagLayout( ));
        pausePanel.setBackground( Color.BLACK );
        pausePanel.setOpaque( true );
        
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER) );
        statusPanel.setLayout(new FlowLayout(FlowLayout.CENTER) );
        sbPanel.setLayout(new FlowLayout(FlowLayout.CENTER) );
        
        // Add components to the button, stats, and sb panels
        pause.addActionListener( this );
        newGame.addActionListener( this );
        stats.addActionListener( this );
        
        buttonPanel.add( pause );
        buttonPanel.add( newGame );
        buttonPanel.add( stats );
        // Add stuff to the status panel
        statusPanel.add( timeLabel );
        statusPanel.add( new JPanel() );
        statusPanel.add( mineRatioLabel );
        
        // Add the status and button panels to the sbPanel
        sbPanel.add( statusPanel );
        sbPanel.add( new JPanel() );
        sbPanel.add( buttonPanel );
        
        // Add the game grid/layer to the JScrollPane
        scrollPane.getViewport().add( gamePanel );
        scrollPane.validate();

        // Add the various panels to the main panels
        mainPanel.add( sbPanel, BorderLayout.NORTH );
        mainPanel.add( scrollPane, BorderLayout.CENTER );
        
        scrollPane.setVisible( true );
        gamePanel.setVisible( true );
        mainPanel.setVisible( true );
        
        this.add( mainPanel );
        this.setVisible( true );
        this.setDefaultCloseOperation( EXIT_ON_CLOSE );
        
        // Since this is the first boot we need to ask what difficulty the
        // player wants.
        newGameWindow = new NewGameWindow( adapter );
        newGameWindow.initComponents();
        
    } // End of initComponents( )
    
    /**
     * This is the ActionListener implementation of actionPerformed. It handles 
     * the main buttons of the GUI.
     * 
     * @param buttonPress The ActionEvent of a button.
     */
    @Override
    public void actionPerformed( ActionEvent buttonPress ) {
        // Pause button
        if( buttonPress.getSource() == pause ) {
            // This button should only work if a game is going on.
            if( gameStarted ) {
                pause( );
                SwingUtilities.invokeLater( new Runnable() {
                    @Override
                    public void run() {
                        obscureOverlay( );
                    }
                });
            }
        }
        // New Game button
        if( buttonPress.getSource() == newGame ) {
            adapter.clearGame( );
            clearTimer( );
            SwingUtilities.invokeLater( new Runnable() {
                @Override
                public void run() {
                    createNewGameWindow( );
                }
            });
        }
        // Stats button
        if( buttonPress.getSource() == stats ) {
            SwingUtilities.invokeLater( new Runnable() {
                @Override
                public void run() {
                    // If stats is pressed during the beginning, remove it.
                    newGameWindow.disposeWindow( );
                    newGameWindow = null;
                    createStatsWindow( );
                }
            });
        }
        
    } // End of actionPerformed( )
    
    /**
     * This function is called to create a new game.
     * 
     * @param rows The number of rows of the playing grid.
     * @param columns The number of columns of the playing grid.
     */
    @Override
    public void startGame( int rows, int columns ) {
        // Create the grid.
        grid = new LandButton[rows][columns];
        gamePanel.removeAll( );
        // Set 1px paddings and buffer space.
        constraints.setInset( 1, 1, 0, 0 );
        
        // Iterate through each row.
        for( int row = 0; row < rows; row++ ) {
            // Iterate through each column.
            for( int column = 0; column < columns; column++ ) {
                // Set the constraints.
                constraints.setCoordinates( column, row );
                // Create the button with new coordinates and ref to adapter.
                LandButton button = new LandButton( new int[] {column, row}, 
                                                    adapter );
                // Initialize the button.
                button.initComponent( );
                // Add the button to the grid and then to the gamePanel.
                grid[row][column] = button;
                gamePanel.add( grid[row][column], constraints );
            }
        }
        
        // Reset the constraints and repaint the components
        constraints.setCoordinates( 0, 0 );
        constraints.setInset( 0, 0, 0, 0 );
        
        gameStarted = true;
        
        this.validate( );
        this.repaint( );
        
    } // End of startGame( )

    /**
     * This function is used to clear the playing grid.
     */
    @Override
    public void clearGame( ) {
        grid = null;
        gamePanel.removeAll( );
        timeLabel.setText( "Time: ");
        mineRatioLabel.setText( "Mines: ");
        this.repaint( );
        
    } // End of clearGame( )
    
    /**
     * This function is called when the user loses a game. It really just calls 
     * a another function. Wrapping it this way lets us change things too.
     * 
     * @param mineLocations An array of (x, y) coordinates of all of the mines.
     * @param coordinate The (x, y) coordinate of the losing move.
     */
    @Override
    public void loseGame( int[][] mineLocations, int[] coordinate ) {
        endGameReveal( mineLocations, coordinate );
        gameStarted = false;
        
    } // End of loseGame( ) 
    
    /**
     * This function is called when the user wins a game. It simply opens up 
     * a StatsWindow is the user got a new record or a NewGameWindow if the 
     * user didn't.
     * 
     * @param newRecord True if the won game was a new record.
     */
    @Override
    public void winGame( boolean newRecord ) {
        gameStarted = false;
        if( newRecord )
            createStatsWindow( );
        else
            createNewGameWindow( );
        
    } // End of winGame( )
    
    /**
     * This function can be called to reveal a specific node without being it 
     * being clicked on by the user. Used in conjunction with a reveal-nodes 
     * mechanism.
     * 
     * @param coordinate The (x, y) coordinate of the node.
     */
    @Override
    public void revealNode( int[] coordinate ) {
        getNode( coordinate ).reveal();
        
    } // End of revealNode( )
    
    /**
     * This function is used to pause the game (and hide the landGrid).
     */
    @Override
    public void pause( ) {
        // If we are paused
        if( adapter.isPaused() ) {
            pause.setLabel( "Pause" );
            // TODO: disable buttons and overly some panel to hide buttons
            disableLandButtons( );
            adapter.resumeGame( );
        }
        else {
            pause.setLabel( "Resume" );
            // TODO: enable buttons and remove JPanel
            adapter.pauseGame();
        }
        
    } // End of pause( )
    
    /**
     * This function is used to stop and clear the timer and to reflect that in 
     * the view.
     */
    public void clearTimer( ) {
        adapter.stopGame( );
        timeLabel.setText( "Time: " );
        
    } // End of clearTimer( )
    
    /**
     * This function tells the adapter to check the specified node. The adapter 
     * will then call the view to update to set that particular node.
     * 
     * @param coordinate The (x, y) coordinate of the node.
     */
    @Override
    public void checkNode( int[] coordinate ) {
        adapter.checkNode( coordinate );
        
    } // End of checkNode( )
    
    @Override
    public void nextMarking( int[] coordinate, Marking marking ) {
        getNode( coordinate ).nextMarking( marking );
        
    } // End of nextMarking( )
    
    /**
     * Internal convenience function to retrieve a specific LandButton at some 
     * coordinate. 
     * 
     * @param coordinate The (x, y) coordinate of the node.
     * @return The LandButton object at that coordinate.
     */
    @Override
    public LandButton getNode( int[] coordinate ) {
        return grid[coordinate[1]][coordinate[0]];
        
    } // End of getNode( )
    
    /**
     * This function is used to see if a specific LandButton was marked by a 
     * user as containing a mine.
     * 
     * @param coordinate the (x, y) coordinate of the node.
     * @return True if the user marked the node for a mine, false otherwise.
     */
    @Override
    public boolean isNodeMarked( int[] coordinate ) {
        return getNode( coordinate ).isMarkedForMine( );
        
    } // End of isNodeMarked( )
    
    /**
     * This function is used to update a node with the mine proximity value.
     * 
     * @param coordinate The (x, y) coordinate of the node.
     * @param proxCount The proximity value to insert.
     */
    @Override
    public void setNodeProx( int[] coordinate, int proxCount ) {
        getNode( coordinate ).setProxCount( proxCount );
        
    } // End of setNodeProx( )
    
    /**
     * This function is used to get an array of user marked mine locations.
     * 
     * @return An int[][] array of user marked mine coordinates.
     */
    @Override
    public int[][] getMarkedLocations( ) {
        // Create a dynamically sized array list.
        ArrayList<int[]> locations = new ArrayList<>();
        // Iterate through the rows
        for( LandButton[] row : grid ) {
            // Iterate through the columns
            for( LandButton node : row ) {
                // If the node is marked add its coordinates
                if( node.isMarkedForMine() ) {
                    locations.add( node.getCoordinate());
                }
            }
        }
        // Convert the array list to an int[][] and return it
        return locations.toArray( new int[locations.size()][] );
        
    } // End of getMarkedLocations( )

    /**
     * This function is used to check for the win conditions.
     */
    @Override
    public void checkWinConditions( ) {
        adapter.checkWinConditions( );
        
    } // End of checkWinConditions( )
    
    /**
     * This function is used to send errors for the NewGameWindow involving the 
     * game parameters.
     * 
     * @param errorList An array list of the game status for error handling.
     */
    @Override
    public void setErrors( ArrayList<GameStatus> errorList ) {
        newGameWindow.setErrorText( 
                ErrorHandler.generateFormattedErrorText( errorList ) );
        
    } // End of setErrors( )
    
    /**
     * This function dumps the entire grid of LandButton objects that represent 
     * the interactive components of the game.
     * 
     * @return The nested array of LandButton objects.
     */
    @Override
    public LandButton[][] getGrid( ) {
        return grid;
        
    } // End of getGrid( )
    
    /**
     * This function is used to reveal a mine.
     * @param coordinate The (x, y) coordinate of the node containing a mine.
     */
    public void setNodeMine( int[] coordinate ) {
        getNode( coordinate ).revealMine( );
    
    } // End of setNodeMine( )
    
    /**
     * This function is used to return a set of (x, y) coordinates of the 
     * adjacent nodes from a specified (x, y) coordinate. The returned list is 
     * guaranteed to be within bounds.
     * 
     * @param coordinate The (x, y) coordinate of the node.
     * @return A nested array of (x, y) coordinates of the nearby nodes.
     */
    @Override
    public int[][] getAdjacentNodes( int[] coordinate ) {
        // Create an array list since the number of nodes are dynamic.
        ArrayList<int[]> nearbyNodes = new ArrayList<>();
        
        // Calculate the x offsets
        for( int xval = -1; xval <= 1; xval++) {
            // Calculate the y offsets
            for( int yval = -1; yval <= 1; yval++ ) {
                int x = coordinate[0] + xval;
                int y = coordinate[1] + yval;
                // If the coordinates are within bounds add them to the list.
                if( boundsCheck(x, grid.length -1) && 
                    boundsCheck(y, grid[0].length -1) ) {
                    nearbyNodes.add( new int[] {x, y} );
                }
            }
        }
        // Convert the ArrayList to an array of coordinates and return them.
        return nearbyNodes.toArray( new int[nearbyNodes.size()][] );
        
    } // End of getAdjacentNodes( )
    
    /**
     * Checks to see if a value is between 0 and some boundary.
     * 
     * @param value The value to be checked.
     * @param boundary The upper boundary.
     * @return True if the value is within the boundary, false otherwise.
     */
    @Override
    public boolean boundsCheck( int value, int boundary ) {
        return ( (0 <= value) && (value <= boundary) );
        
    } // End of boundsCheck( )
    
    /**
     * This function is used to update the game elapsed timer with a string.
     * 
     * @param timeValue The current game elapsed time.
     */
    public void updateTimer( String timeValue ) {
        timeLabel.setText( timeValue );
        
    } // End of updateTimer( ) 
    
    /**
     * This function is used to reveal all unseen mines and void all flags 
     * placed in the wrong location.
     * 
     * @param mineLocations An array of (x, y) coordinates of all of the mines.
     * @param coordinate The (x, y) coordinate of the losing move.
     */
    private void endGameReveal( int[][] mineLocations, int[] coordinate ) {
        // Disable all of the buttons
        disableLandButtons( );
        // Iterate through the rows of the playing grid.
        for( LandButton[] row : grid ) {
            // Iterate through the columns
            for( LandButton node : row ) {
                // If the node coordinate is in the set of mine locations
                if( contains(node.getCoordinate(), mineLocations) ) {
                    // If the node is not Marking.MARKED
                    if( node.getMarking() != Marking.MARKED ) {
                        // Reveal the hidden mine
                        node.revealNewMine();
                    }
                }
                // If the node is NOT in the set of mine locations
                else {
                    // If the node is marked (and not a proper mine spot)
                    if( node.getMarking() == Marking.MARKED ) {
                        // Red flag
                        node.invalidMarking();
                    }
                }
            }
        }
        // Reveal the stepped on node.
        setNodeMine( coordinate );
    
    } // End of endGameReveal( )
    
    /**
     * This function is used to see if a specific (x, y) coordinate is in a 
     * larger set of coordinates.
     * @param coordinate The (x, y) coordinate to check.
     * @param coordinates The set of coordinates to check in.
     * @return True if the set contains the coordinate, false otherwise.
     */
    private boolean contains( int[] coordinate, int[][] coordinates ) {
        for( int[] node : coordinates ) {
            if( Arrays.equals( coordinate, node)) {
                return true;
            }
        }
        return false;
        
    } // End of contains( )
    
    /**
     * This function is used to disable the mouse listener on all of the grid 
     * buttons.
     */
    public void disableLandButtons( ) {
        // Iterate through each button on the grid.
        for( LandButton[] row : grid ) {
            for( LandButton button : row ) {
                // Remove the moust listener
                button.removeMouseAdapter( );
            }
        }
        
    } // End of disableLandButtons( ) 
    
    /**
     * This function is used to inform the user how many nodes they marked as 
     * containing a mine.
     * 
     * @param markingCount The current count of marked nodes.
     * @param totalMines The total number of mines in the game.
     */
    public void setMarkingCount( int markingCount, int totalMines ) {
        // Create a string like "count / mines"
        mineRatioLabel.setText( ((Integer)markingCount).toString() + " / " + 
                                ((Integer)totalMines).toString() );
        validate( );
        repaint( );
        
    } // End of setMarkingCount( )
    
    /**
     * This function starts and initializes a StatsWindow.
     */
    public void createStatsWindow( ) {
        statsWindow = new StatsWindow( adapter );
        statsWindow.initComponents( );
        
    } // End of createStatsWindow( )
    
    /**
     * This function starts and initializes a NewGameWindow.
     */
    public void createNewGameWindow( ) {
        newGameWindow = new NewGameWindow( adapter );
        newGameWindow.initComponents();
        
    } // End of createNewGameWindow( ) 
    
    /**
     * This function is used to obscure the playing grid when a user pauses the 
     * game. It is the only part of the game not actually implemented yet.
     */
    public void obscureOverlay( ) {
        throw new UnsupportedOperationException( "Not supported yet." );
        
    } // End of obscureOverlay( )
    
    /**
     * This function returns the reference to the final Icons class containing 
     * the icons used in the buttons.
     * 
     * @return The reference to the static and final Icons class.
     */
    public Icons getIcons( ) {
        return ICONS;
        
    } // End of getIcons( )
    
} // End of MineSweeperGUI class.
