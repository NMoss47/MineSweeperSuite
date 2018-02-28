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

/**
 * This class is represents the MineSweeperSuite game parameters. It contains a 
 * series of convenience functions that should lend itself to readability.
 * 
 * @author Nicholas Moss
 */
public class GameParameters implements Serializable {
    
    // The game parameters
    private final Integer mines;
    private final Integer rows;
    private final Integer columns;
    
    /**
     * Default constructor.
     * 
     * @param mines The number of mines in the game.
     * @param rows The number of rows in the game.
     * @param columns The number of columns in the game.
     */
    public GameParameters( int mines, int rows, int columns ) {
        this.mines = mines;
        this.rows = rows;
        this.columns = columns;
        
    } // End of GameParameters( )
    
    /**
     * A constructor. Parses Integers from strings. Should be safe if the view 
     * implements some client validation. Otherwise it is bunk.
     * 
     * @param mines The number of mines in the game.
     * @param rows The number of rows in the game.
     * @param columns The number of columns in the game.
     */
    public GameParameters( String mines, String rows, String columns ) {
        this.mines = Integer.parseInt( mines );
        this.rows = Integer.parseInt( rows );
        this.columns = Integer.parseInt( columns );

    } // End of GameParameters( )
    
    /**
     * This function gets the number of mines.
     * 
     * @return The number of mines in the parameters.
     */
    public int getMines( ) {
        return mines;
        
    } // End of getMines( )
    
    /**
     * This function gets the number of rows.
     * 
     * @return The number of rows in the parameters.
     */
    public int getRows( ) {
        return rows;
        
    } // End of getRows( )
    
    /**
     * This function gets the number of columns.
     * 
     * @return The number of columns in the parameters.
     */
    public int getColumns( ) {
        return columns;
        
    } // End of getColumns( )
    
    /**
     * This function gets the number of mines.
     * 
     * @return The string of the number of mines in the parameters.
     */
    public String getStringMines( ) {
        return mines.toString();
        
    } // End of getStringMines( )
    
    /**
     * This function gets the number of rows.
     * 
     * @return The string of the number of rows in the parameters.
     */
    public String getStringRows( ) {
        return rows.toString( );
        
    } // End of getStringRowS( )
    
    /**
     * This function gets the number of columns.
     * 
     * @return The string of the number of columns in the parameters.
     */
    public String getStringColumns( ) {
        return columns.toString( );
        
    } // End of getStringColumns( )
    
    /**
     * An overridden toString() method to dump the contents if we need it.
     * 
     * @return A string representing the object.
     */
    @Override
    public String toString( ) {
        return mines + ":" + rows + ":" + columns;
        
    } // End of toString( )
    
} // End of the GameParameters class.
