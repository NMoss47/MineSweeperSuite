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
import static minesweepersuite.Marking.*;

/**
 * This class represents a single unit of land in the MineSweeper game.
 * 
 * @author Nicholas Moss
 */
public class LandNode implements LandInterface, Serializable {
    
    // Arming status of the node.
    private boolean armed;
    // Count of nearby mines.
    private int proximityCount;
    // Marking enumeration state.
    private Marking marking;
    // If the node was revealed or not.
    private boolean revealed;
 
    
    /**
     * Default constructor.
     */
    public LandNode( ) {
        armed = false;
        proximityCount = 0;
        marking = NOT_MARKED;
        revealed = false;
        
    } // End of LandNode( )

    /**
     * This function is used to get the armed status of the land node.
     * 
     * @return True if the node is armed, false otherwise.
     */
    @Override
    public boolean isArmed( ) {
        return armed;
        
    } // End of getArmedStatus( )

    /**
     * This function is used to set the armed status of the land node.
     * 
     * @param armed Set to true if the node should be armed.
     */
    @Override
    public void setArmedStatus( boolean armed ) {
        this.armed = armed;
        
    } // End of setArmedStatus( )

    /**
     * This function is used to return the count of mines in adjacent nodes.
     * 
     * @return The count of mines in adjacent nodes.
     */
    @Override
    public int getProximityCount( ) {
        return proximityCount;
        
    } // End of getProximityCount( )

    /**
     * This function is used to increment the count of mines in nearby nodes.
     * Should be used internally only.
     */
    @Override
    public void incrementProximityCount( ) {
        proximityCount++;
        
    } // End of incrementProximityCount( )

    /**
     * This function is used to check the marking enumeration of the land node.
     * 
     * @return An enumerated value of the marked status of the land node.
     */
    @Override
    public Marking getMarking( ) {
        return marking;
        
    } // End of getMarking( )
    
    /**
     * This function is used to switch between the marking enumerations for the 
     * land node. It returns the newest state.
     * 
     * @return The marked state of the node after the state has changed.
     */
    @Override
    public Marking nextMarking( ) {
        // Based off of our current Marking state we need to pivot to another.
        switch( marking ) {
            case NOT_MARKED:
                marking = MARKED;
                break;
            
            case MARKED:
                marking = QUESTIONED;
                break;
                
            case QUESTIONED:
                marking = NOT_MARKED;
                break;
        }
        // Return the new Marking state.
        return marking;
        
    } // End of nextMarking( )
    
    /**
     * This function sets the node to the revealed state.
     */
    @Override
    public void revealNode( ) {
        revealed = true;
        
    } // End of revealNode( )
    
    /**
     * This function checks to see if a node has been revealed or not.
     * 
     * @return True if the node has been revealed. False otherwise.
     */
    @Override
    public boolean isRevealed( ) {
        return revealed;
        
    } // End of getRevealedStatus( )
    
    /**
     * This function checks to see if the node has been "touched" in any way.
     * 
     * @return True if the node has been marked or revealed, false otherwise.
     */
    public boolean isActivated( ) {
        return revealed || marking == Marking.MARKED;
        
    } // End of isActivated( )
    
} // End of LandNode class.
