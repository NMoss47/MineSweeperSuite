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
 * The StatisticEntry class represents the information contained in a
 * "statistics save". It contains the difficulty of the game, the time taken,
 * and the name of the user that beat the game.
 * 
 * @author Nicholas Moss
 */
public class StatisticEntry implements Serializable, Comparable {

    private String user;
    private final long time;
    private final GameParameters params;
    private boolean finalized;
    
    /**
     * Default constructor.
     * 
     * @param time The elapsed game time.
     * @param params The GameParameters set.
     */
    public StatisticEntry( long time, GameParameters params ) {
        user = "user";
        this.time = time;
        this.params = params;
        finalized = false;
        
    } // End of StatisticEntry( )
    
    /**
     * Default constructor.
     * 
     * @param user The name of the user.
     * @param time The elapsed game time.
     * @param params The GameParameters set.
     */
    public StatisticEntry( String user, long time, GameParameters params ) {
        this.user = user;
        this.time = time;
        this.params = params;
        finalized = false;

    } // End of StatisticEntry( )

    /**
     * This function gets the name of the user.
     * 
     * @return A string of the name of the user.
     */
    public String getUser() {
        return user;

    } // End of getUser( )
    
    /**
     * This function is used to set or update the user name.
     * 
     * @param user The string containing the user's name.
     */
    public void setUser( String user ) {
        if( !finalized ) {
            this.user = user;
            setFinalized( );
        }
        
    } // End of setUser( )

    /**
     * This function gets the elapsed game time as the long primitive data type.
     * 
     * @return The elapsed game time.
     */
    public long getTime() {
        return time;

    } // End of getTime( )

    /**
     * This function gets the elapsed game time as a formatted string. The 
     * string is in the form of hours:minutes:seconds.
     * 
     * @return A formatted string of the game time in hours:minutes:seconds.
     */
    public String getTimeAsString() {
        return MineSweeperTimer.formatTime( time );

    } // End of getTimeAsString( )

    /**
     * This function returns a string of the number of rows in the game.
     * 
     * @return The number of rows in the game as a string.
     */
    public String getRows() {
        return params.getStringRows();

    } // End of getRows( )

    /**
     * This function returns a string of the number of columns in the game.
     * 
     * @return The number of columns in the game as a string.
     */
    public String getColumns() {
        return params.getStringColumns();

    } // End of getColumns( )

    /**
     * This function returns a string of the number of mines in the game.
     * 
     * @return The number of mines in the game as a string.
     */
    public String getMines() {
        return params.getStringMines();

    } // End of getMines( )

    /**
     * This function compares this object to another (similar) object.
     * 
     * @param obj The other object to compare to.
     * @return >1 if the caller is greater, 0 if equal, <1 if the caller is 
     * less than the compared to object.
     */
    @Override
    public int compareTo( Object obj ) {
        // If they are equal return 0.
        if( this.time == ((StatisticEntry)obj).getTime())
            return 0;
        // Return 1 if this object is greater than the other; -1 otherwise.
        return this.time > ((StatisticEntry)obj).getTime() ? 1 : -1;

    } // End of compareTo( )
    
    /**
     * This function sets an internal field to finalized, indicating that the 
     * object is done being modified. Once finalized no further modifications 
     * are allowed.
     */
    public void setFinalized( ) {
        finalized = true;
        
    } // End of setFinalized( )
    
    /**
     * This function is called to see if the object is set as finalized.
     * 
     * @return True if this object was set to finalized, false otherwise.
     */
    public boolean isFinalized( ) {
        return finalized;
        
    } // End of isFinalized( )
    
    /**
     * An overridden toString() method to dump the contents if we need it.
     * 
     * @return A string representing the object.
     */
    @Override
    public String toString( ) {
        return user + " " + time + " " + params.toString( );
        
    } // End of toString( )

} // End of StatisticEntry class.

