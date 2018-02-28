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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * This is the actual game of the MineSweeper suite. It follows the Model part 
 * of the Model-View-Adapter design pattern.
 * 
 * @author Nicholas Moss
 */
public class MineSweeper implements ModelAPI,
                                    Serializable {
    // Reference to the 'controlling' adapter
    private transient MineSweeperAdapter adapter;
    // Timer reference and a game identifier.
    private MineSweeperTimer timer;
    private final String gameIdentifier;
    
    // Game parameters
    private GameParameters params;
    private static final Random RANDOM = new Random( );
    
    // Game details.
    private LandNode[][] landGrid;
    private int markingCount;
    private int[][] mineLocations;
    private boolean firstMove;
    
    /**
     * Default constructor. Initializes variables to null.
     */
    public MineSweeper( ) {
        adapter = null;
        params = null;
        timer = null;
        
        landGrid = null;
        mineLocations = null;
        gameIdentifier = null;
        firstMove = true;
        markingCount = 0;
        
    } // End of MineSweeper( )
    
    /**
     * A constructor. Initializes variables to null and sets an identifier for 
     * this object. 
     * 
     * @param gameIdentifier An identifier of the MineSweeper object.
     */
    public MineSweeper( String gameIdentifier ) {
        adapter = null;
        params = null;
        
        landGrid = null;
        mineLocations = null;
        this.gameIdentifier = gameIdentifier;
        firstMove = true;
        markingCount = 0;

    } // End of MineSweeper( )
    
    /**
     * A constructor. Initializes variables to null and sets no identifier for 
     * this object.
     * 
     * @param adapter The MineSweeperAdapter (Adapter) object used to interface 
     * with the MineSweeper (Model) object.
     */
    public MineSweeper( MineSweeperAdapter adapter ) {
        this.adapter = adapter;
        params = null;
        landGrid = null;
        mineLocations = null;
        gameIdentifier = "";
        firstMove = true;
        markingCount = 0;

    } // End of MineSweeper( )
    
    /**
     * This function is used to get the identifier of this instance of the 
     * MineSweeper object.
     * @return An identifier of the MineSweeper object.
     */
    @Override
    public String getGameIdentifier( ) {
        return gameIdentifier;
        
    } // End of getGameIdentifier( )
    
    /**
     * This function is used to set the adapter object that 'controls' the game.
     * 
     * @param adapter The MineSweeperAdapter object used for control.
     */
    @Override
    public void setAdapter( MineSweeperAdapter adapter ) {
        this.adapter = adapter;
        
    } // End of setAdapter( )
    
    /**
     * This function is used to set the initial parameters of the game.
     * 
     * @param params A GameParameters object of the game parameters.
     * @return An arrayList of error enumerations or SUCCESS.
     */
    @Override
    public ArrayList<GameStatus> setGameParams( GameParameters params ) {
        // Make sure the game has been cleared first.
        clearGame( );
        // Check for valid parameters.
        ArrayList<GameStatus> errors = checkValidParams( params );
        
        // If there were no errors, set the parameters and return null
        if( errors == null ) {
            this.params = params;
            return null;
        }
        
        // If we get this far there were errors, return them.
        return errors;
        
    } // End of setGameParams( )
    
    /**
     * This function is used to check for valid game parameters. It can probably
     * be broken down into smaller functions but for its scope this is fine.
     * 
     * @param params A GameParameters object of the game parameters.
     * @return An arrayList containing the error enumerations for a single 
     * enumeration indicating SUCCESS.
     */
    private ArrayList<GameStatus> checkValidParams( GameParameters params ) {
        int area = params.getRows()*params.getColumns();
        int columns = params.getColumns();
        int rows = params.getRows();
        int mines = params.getMines();
        ArrayList<GameStatus> errors = new ArrayList<>();
        
        // Block of if statements for the errors.
        if( columns < MIN_AXIS_SIZE )
            errors.add( GameStatus.COLUMNS_BELOW_MIN );
        if( columns > MAX_AXIS_SIZE )
            errors.add( GameStatus.COLUMNS_ABOVE_MAX );
        if( rows < MIN_AXIS_SIZE )
            errors.add( GameStatus.ROWS_BELOW_MIN);
        if( rows > MAX_AXIS_SIZE )
            errors.add( GameStatus.ROWS_ABOVE_MAX );
        if( mines > (area * MAX_MINE_PERCENT) ) 
            errors.add( GameStatus.MINES_ABOVE_MAX );
        // Check if the mines are below the minimum allowable amount.
        if( mines < (area * MIN_MINE_PERCENT) ) 
            errors.add( GameStatus.MINES_BELOW_MIN );
        
        // If there were no errors just return null.
        if( errors.isEmpty() )
            return null;
        
        return errors;
        
    } // End of checkValidParams( )
    
    /**
     * This function is used to return a set of game parameters to the adapter.
     * 
     * @return The current GameParameters object or null.
     */
    @Override
    public GameParameters getCurrentGameParams( ) {
        return params;
        
    } // End of getCurrentGameParams( )
    
    /**
     * This function is used to create a new game. It uses the internal game 
     * parameters to generate a grid, generate mine locations, set the mines, 
     * and set the proximity values around the first checked node.
     * 
     * @param coordinate The (x, y) coordinate of the starting move.
     */
    @Override
    public void createNewGame( int[] coordinate ) {
        // Create the playing grid.
        createLandGrid( );
        // Generate random and unique mine coordinates.
        generateMineLocations( coordinate );
        // Arm the mines.
        setMineLocations( );
        // Generate proximity values for all nodes adjacent to the mines.
        generateProximityValues( );
        // Start the timer for the game
        timer = new MineSweeperTimer( adapter );
        timer.start( );
        
    } // End of createNewGame( )
    
    /**
     * This function is used to iterate through the marked states for the node.
     * Additionally, it adds or removes 1 from the current count of markings.
     * 
     * @param coordinate The (x, y) coordinate of the node.
     * @return The current marking after the marked state switch.
     */
    @Override
    public Marking nextMarking( int[] coordinate ) {
        
        // Get the current Marked status.
        Marking currentStatus = getNode( coordinate ).getMarking();
        
        // We need to do different behavior based off of the marking count.
        // If we are at or above the total mine count we cannot add more flags.
        if( markingCount >= params.getMines() ) {
            // If the status isnt marked return so we dont add another flag.
            if( currentStatus == Marking.NOT_MARKED ) {
                return Marking.NOT_MARKED;
            }
            // Otherwise we can remove the flag
            else {
                // Pivot to the next marking state
                Marking status = getNode( coordinate ).nextMarking( );
                // Change the marking count.
                markingCountSwitch( status );
                setViewMarkingCount( );
                // Return the new marking state.
                return status;
            }
        }
        
        // Pivot to the next marking state.
        Marking status = getNode( coordinate ).nextMarking( );
        // Change the marking count.
        markingCountSwitch( status );
        // Send the marking count to the adapter and then the view.
        setViewMarkingCount( );
        // Check for the win condition.
        checkWinConditions( );
        // Return the new marking state.
        return status;
        
    } // End of nextMarking( )
    
    /**
     * This function is used to increment or decrement the current count of 
     * MARKED nodes. 
     * 
     * @param status The (newest) marking status.
     */
    private void markingCountSwitch( Marking status ) {
        switch( status ) {
            case NOT_MARKED:
                break;
            case MARKED:
                markingCount++;
                break;
            case QUESTIONED:
                markingCount--;
                break;
        }
        
    } // End of markingCountSwitch( )
    
    /**
     * This function is used to generate the proximity values of each node that
     *  is adjacent to a mine.
     * 
     * The goal is to increment the values of all adjacent nodes at the 
     * following positions:
     * 
     * (x-1, y-1),  (x, y-1),   (x+1, y-1)
     * (x-1, y),    (x, y),     (x+1, y)
     * (x-1, y+1),  (x, y+1),   (x+1, y+1)
     * 
     */
    private void generateProximityValues( ) {
        for( int[] position : mineLocations ) {
            // Get a list of nodes near each mine.
            int[][] nodes = getAdjacentNodes( position );
            // Iterate through the nearby node list to increment their values.
            for( int[] coordinate : nodes ) {
                getNode( coordinate ).incrementProximityCount();
            }
        }
        
    } // End of generateProximityValues( )
    
    /**
     * This function is used to generate the mine locations for the playing 
     * grid. All values are guaranteed to be unique, within bounds, and outside 
     * of the starting 'safe' zone.
     * 
     * @param coordinate The (x, y) coordinate of the node.
     */
    private void generateMineLocations( int[] coordinate ) {
        // Set a reference for the mine locations array.
        mineLocations = new int[params.getMines()][2];
        // Determine the 'safe zone' around the first used coordinate.
        int[][] safeNodes = getAdjacentNodes( coordinate );
        // A reusable reference for the mineCoordinate.
        int[] mineCoordinate;
        
        // Loop for the specified amount of mines.
        for( int i = 0; i < params.getMines(); i++ ) {
            
            // Generate a new (x, y) coordinate.
            do {
                
                mineCoordinate = new int[] {
                    RANDOM.nextInt( params.getColumns() ),
                    RANDOM.nextInt( params.getRows() )
                };
            // Repeat the (x, y) creation if they fail either check.
            } while( !ensureUniquePositions(mineLocations, mineCoordinate) || 
                     !ensureSafeZone(mineCoordinate, safeNodes) );
            
            mineLocations[i] = mineCoordinate;
        }

        
    } // End of generateMineLocations( )
    
    /**
     * This function is used to map the mine locations to the land grid and then
     *  arm the mines.
     */
    private void setMineLocations( ) {
        // Iterate through the mine locations.
        for( int[] coordinate : mineLocations ) {
            // Get the node and arm it.
            getNode( coordinate ).setArmedStatus( true );
        }
        
    } // End of setMineLocations( )
    
    /**
     * This function is used to ensure a set of unique coordinate values.
     * 
     * @param positions The array of coordinates to check.
     * @param coordinate The (x, y) coordinate to check for.
     * @return True if the coordinate is unique, false otherwise.
     */
    private boolean ensureUniquePositions( int[][] positions,
                                           int[] coordinate ) {
        for( int[] position : positions ) {
            if( Arrays.equals(position, coordinate) )
                return false;
        }
        
        return true;
        
    } // End of ensureUniquePositions( )
    
    
    /**
     * This function is used to ensure that the first checked node and its 
     * adjacent nodes are free of mines.
     * 
     * @param coordinate An (x, y) coordinate of the mine location.
     * @param safeNodes An array of safe nodes to check against.
     * @return True if the mine location is not part of the safe zone. False 
     * otherwise.
     */
    private boolean ensureSafeZone( int[] coordinate, int[][] safeNodes ) {
        for( int[] node : safeNodes ) {
            if( Arrays.equals(coordinate, node) )
                return false;
        }
        return true;
        
    } // End of ensureSafeZone( )
    
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
                if( boundsCheck(x, params.getColumns() -1) && 
                    boundsCheck(y, params.getRows() -1) ) {
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
     * This function is used to create the landGrid to specifications and to 
     * instantiate all LandNode objects within the landGrid.
     */
    private void createLandGrid( ) {
        // Create the landGrid dimensions.
        landGrid = new LandNode[params.getRows()][params.getColumns()];
        // Iterate through each row.
        for( LandNode[] nodeRow : landGrid ) {
            // Iterate through each column.
            for( int x = 0; x < nodeRow.length; x++ ) {
                // Create a new LandNode.
                nodeRow[x] = new LandNode( );
            }
        }
        
    } // End of createLandGrid( )
    
    /**
     * This function is used to fetch a specified land node.
     * 
     * @param coordinate The (x, y) coordinate of the node.
     * @return The specified land node.
     */
    @Override
    public LandNode getNode( int[] coordinate ) {
        // Since the arrays are references as (row, column) but the (x, y) 
        // format is (column, row) this function will just map it for us.
        return landGrid[coordinate[1]][coordinate[0]];
        
    } // End of getNode( )
    
    /**
     * This function is used to clear out a game. 
     */
    @Override
    public void clearGame( ) {
        params = null;
        landGrid = null;
        mineLocations = null;
        firstMove = true;
        markingCount = 0;
        
    } // End of clearGame( )
    
    /**
     * This function is used to handle winning a game. It saves the time, score,
     * and user to a file.
     */
    @Override
    public void winGame( ) {
        // Stop the timer.
        timer.stopGame();
        // Inform the adapter and give it the elapsed game time.
        adapter.winGame( timer.getTime() );
        
    } // End of winGame( )
    
    /**
     * This function is used to handle losing a game.
     * 
     * @param coordinate The (x, y) coordinate of the losing move.
     */
    @Override
    public void loseGame( int[] coordinate ) {
        timer.stopGame( );
        adapter.loseGame( mineLocations, coordinate );
        
    } // End of loseGame( )
    
    /**
     * This function is used a static function used to generate random game 
     * parameters. This can be called even if there is no MineSweeper object. 
     * All parameters are guaranteed to be valid numbers.
     * 
     * @return An int array containing {mines, rows, columns} - in that order.
     */
    public static GameParameters getStaticRandomGameParams( ) {
        int randomRows = generateRandomAxisSize( );
        int randomColumns = generateRandomAxisSize( );
        int randomMines = generateRandomMineCount( randomRows * randomColumns );
        
        return new GameParameters( randomMines, randomRows, randomColumns );
    
    } // End of getStaticRandomGameParams( )
    
    /**
     * This function is used to generate random game parameters. All parameters 
     * are guaranteed to be valid numbers.
     * 
     * @return An int array containing {mines, rows, columns} - in that order.
     */
    @Override
    public GameParameters getRandomGameParams( ) {
        return getStaticRandomGameParams( );
        
    } // End of getRandomGameParams( )
    
    /**
     * This function is used to generate an axis value between MIN_AXIS_SIZE and
     * MAX_AXIS_SIZE - with both values being inclusive.
     * 
     * @return A randomly generated size between the min and max axis sizes.
     */
    private static int generateRandomAxisSize( ) {
        int axis = 0;
        // Get the next random value.
        do {
            axis = RANDOM.nextInt( MAX_AXIS_SIZE + 1 );
        } while (axis < MIN_AXIS_SIZE);
        
        return axis;
        
    } // End of generateRandomAxisSize( )
    
    /**
     * This function is used to generate a random mine count. The mine count 
     * is guaranteed to be within the minimum and maximum allowable number of 
     * mines.
     * 
     * @param area The total area of the playing grid.
     * @return The number of mines.
     */
    private static int generateRandomMineCount( int area ) {
        int randomMines = 0;
        // Get the next random value.
        do {
            randomMines = RANDOM.nextInt( area );
        // Repeat if mine count exceeds the min or max boundary.
        } while( !(randomMines >= (area * MIN_MINE_PERCENT) &&
                   randomMines <= (area * MAX_MINE_PERCENT)) );

        return randomMines;
        
    } // End of generateRandomMineCount( )
    
    /**
     * This function is used to get a list of the mine locations. Should be used
     *  by the adapter. 
     * 
     * @return An int[][] array of all of the mine locations in use.
     */
    @Override
    public int[][] getMineLocations( ) {
        return mineLocations;
        
    } // End of getMineLocations( )
    
    /**
     * This function dumps the entire nested array of LandNodes that represents 
     * the playing grid of the game.
     * 
     * @return The nested array of LandNodes.
     */
    @Override
    public LandNode[][] getGrid( ) {
        return landGrid;
        
    } // End of getGrid( )
    
    /**
     * This function returns the timer object of the game.
     * 
     * @return Returns a MineSweeperTimer object.
     */
    public MineSweeperTimer getTimer( ) {
        return timer;
        
    } // End of getTimer( )
    
    /**
     * This function is used to check if the user won the game.
     */
    public void checkWinConditions( ) {
        // If we have marked the maximum amount of mines
        if( markingCount == params.getMines() ) {
            // Iterate through the rows
            for( LandNode[] row : landGrid ) {
                // Iterate through each node of the row
                for( LandNode node : row ) {
                    // If the node has NOT been touched, return
                    if( !node.isActivated() ) {
                        return;
                    }
                }
            }
            
        }
        // If we arent at the maximum mines, return
        else {
            return;
        }
        // If we made it this far we clearly won.
        winGame( );
        
    } // End of checkWinConditions( )
    
    /**
     * This function is used to reveal and return the positions of any nearby 
     * nodes as a set of (x, y) coordinates.
     * 
     * @param coordinate The (x, y) coordinate of the center node.
     */
    public void revealNearbyNodes( int[] coordinate ) {
        // Set the nearby marked count and prox count.
        int markedCount = 0;
        int proxCount = getNode( coordinate ).getProximityCount( );
        // Create a dynamic list of adjacent nodes.
        ArrayList<int[]> adjacentNodes = new ArrayList<>();
        // Get an array of the nearby nodes.
        int[][] nearbyNodes = getAdjacentNodes( coordinate );
        
        // Iterate through the adjacentNodes to determine their Marking state
        for( int[] node : nearbyNodes ) {
            // If the node is Marked then increase the count of marked nodes.
            if( getNode(node).getMarking() == Marking.MARKED ) {
                markedCount++;
            }
            else {
                // If not marked and is has not been revealed then add it
                if( !getNode(node).isRevealed() ) {
                    adjacentNodes.add( node );
                }
            }
        }
        
        // If the count of nearby markings are equal to the prox count
        if( markedCount == proxCount ) {
            // Iterate through the valid nodes to reveal
            for( int[] node : adjacentNodes ) {
                // Reveal the node internally and on the adapter.
                getNode( node ).revealNode( );
                adapter.revealNode( node );
               
                // If there are no mines nearby then recursively call this
                if( getNode(node).getProximityCount() == 0 ) {
                    revealNearbyNodes(node);
                }
            }
        }
        
    } // End of revealNearbyNodes( )
    
    /**
     * This function is used to check a node for various properties.
     * 
     * @param coordinate The (x, y) coordinate of the node to check.
     */
    public void checkNode( int[] coordinate ) { 
        // If this is the first move of the game.
        if( firstMove ) { 
            // Create the game, display the grid, and set firstMove to false.
            createNewGame( coordinate );
            displayGrid( );
            firstMove = false;
        }
        
        // Reveal the node.
        getNode( coordinate ).revealNode( );
        
        // If the node is marked in any way just return to do nothing
        if( getNode(coordinate).getMarking() != Marking.NOT_MARKED ) {
            return;
        }
        // If the node was armed we lost
        if( getNode(coordinate).isArmed() ) {
            // Lose the game.
            loseGame( coordinate );
        }
        // If we didn't step on a mine
        else {
            // Send the prox count to the adapter
            adapter.setNodeProx( coordinate,
                                 getNode(coordinate).getProximityCount() );
            // If there are no nearby mines
            if( getNode(coordinate).getProximityCount() == 0 ) {
                // Set the prox count and then reveal any nearby mines.
                adapter.setNodeProx( coordinate, 0 );
                revealNearbyNodes( coordinate );
            }
        }
        
        checkWinConditions( );
        
    } // End of checkNode( )
    
    /**
     * This function is used to dump the grid and its contents to the console.
     */
    public void displayGrid( ) { 
        // Get the grid and iterate through the rows.
        for( LandNode[] row : landGrid ) {
            // Iterate through the columns,
            for( LandNode column : row ) {
                // If a mine render and asterix
                if( column.isArmed() )
                    System.out.print( " *" );
                else {
                    // If not a mine and proximity value of 0, render a space
                    if( column.getProximityCount() == 0 )
                        System.out.print( "  " );
                    // If any other proximity value render that number.
                    else
                        System.out.print( " " + 
                            ((Integer)column.getProximityCount()).toString() );
                }
            }
            // New line for start of a new row.
            System.out.println( );
        }
        System.out.println( );
        
    } // End of displayGrid( )
    
    /**
     * This function is used to update the adapter (then view) with the current 
     * count of marked nodes and the total number of mines in the game.
     */
    public void setViewMarkingCount( ) {
        adapter.setViewMarkingCount( markingCount, params.getMines() );
        
    } // End of setViewMarkingCount( )
    
} // End of MineSweeper class.