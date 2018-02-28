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
import java.util.Collections;

/**
 * The MineSweeperStatistics class is a container for a list of StatisticEntry 
 * objects. This class got kind of messy and is probably leaky as all hell.
 * 
 * Eventually, however, I will implement support for sorting the records by 
 * the game mode rather than just the times. That would be nice.
 * 
 * @author Nicholas Moss
 */
public class MineSweeperStatistics implements Serializable {
    
    private final int MAXENTRIES = 10;
    private final ArrayList<StatisticEntry> statisticList;
    
    /**
     * Default constructor. Creates a new list of StatisticEntry.
     */
    public MineSweeperStatistics( ) {
        statisticList = new ArrayList<>( MAXENTRIES );
        
    } // End of MineSweeperStatistics( )
    
    /**
     * This function adds a new StatisticEntry object to the list.
     * 
     * @param entry A StatisticEntry object to add.
     */
    public void newEntry( StatisticEntry entry ) {
        statisticList.add( entry );
        
    } // End of addEntry( )
    
    /**
     * This function is used to finalize the last added entry.
     * 
     * @param position An integer of the position of the entry in the list.
     */
    public void finalizeEntry( int position ) {
        statisticList.get( position ).setFinalized( );
        
    } // End of finalizeEntry
    
    /**
     * This function is used to finalize the last added entry.
     */
    public void finalizeEntry( ) {
        getLastEntry( ).setFinalized( );
        
    } // End of finalizeEntry( )
    
    /**
     * This function should return the last added entry. It will break if other 
     * entries were not finalized.
     * 
     * @return A StatisticEntry object or null if everything was finalized.
     */
    public StatisticEntry getLastEntry( ) {
        for( StatisticEntry entry : statisticList ) {
            if( !entry.isFinalized() )
                return entry;
        }
        
        return null;
        
    } // End of getLastEntry( )
    
    /**
     * This function simply removes the last element in the list.
     */
    public void removeLastEntry( ) {
        if( statisticList.size() > MAXENTRIES ) {
            statisticList.remove( statisticList.size() - 1 );
            sortEntries( );
        }
    } // End of removeEntry( )
    
    /**
     * This function sorts the StatisticEntry objects. Currently it sorts by 
     * the elapsed game time.
     */
    public void sortEntries( ) {
        Collections.sort( statisticList );
        
    } // End of sortEntries
    
    /**
     * This function retrieves an entry from the list at a specified point.
     * 
     * @param pos An integer of the entry's position.
     * @return A StatisticEntry object.
     */
    public StatisticEntry getEntry( int pos ) {
        return statisticList.get( pos );
        
    } // End of getEntry
    
    /**
     * This function returns the current size of the list.
     * 
     * @return An integer of the current size of the list.
     */
    public int getEntrySize( ) {
        return statisticList.size();
        
    } // End of getEntrySize( )
    
    /**
     * This function returns the StatisticEntry object in last place.
     * 
     * @return A StatisticEntry object in last place.
     */
    public StatisticEntry getLowestRank( ) {
        sortEntries( );
        return statisticList.get( statisticList.size() -1 );
        
    } // End of getLowestRank( )
    
    /**
     * This function is used to determine if some game time is a new best time.
     * 
     * @param time The elapsed game time as a long.
     * @return True if the new time is within the worst time in the list. False 
     * otherwise.
     */
    public boolean newBestTime( long time ) {
        // If the list is empty or less than MAXENTRIES, return true.
        if( statisticList.size() < MAXENTRIES )
            return true;
        // If there is a list get the lowest rank one and compare the times.
        return time <= getLowestRank( ).getTime( );
        
    } // End of newBestTime( )
    
    /**
     * This function is used to see if the new time fits within the list of 
     * records. If it does we will add it to the list, sort, and remove any 
     * extra entries. If it doesn't we just return false.
     * 
     * @param time The Long value of the elapsed game time.
     * @param params The current Game Parameters object.
     * @return True if the time is a new record (and added), false otherwise.
     */
    public boolean newBestTimeAdd( long time, GameParameters params ) {
        // Check to see if this is a new record.
        if( newBestTime( time ) ) {
            // Add the new entry.
            statisticList.add( new StatisticEntry( time, params ) );
            // Sort it
            Collections.sort( statisticList );
            // Remove the entry outside the max bounds.
            removeLastEntry( );
            // Return successfully added.
            return true;
        }
        // Return the fact that we didn't add it.
        return false;
        
    } // End of newBestTimeAdd( )
        
} // End of MineSweeperStatistics class.
