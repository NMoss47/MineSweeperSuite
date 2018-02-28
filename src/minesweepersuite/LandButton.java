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

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JToggleButton;

/**
 * This class represents the "land node" button that contains (or doesn't 
 * contain) a mine. JToggleButton was chosen since it can remain in a clicked 
 * state.
 * 
 * @author Nicholas Moss
 */
public class LandButton extends JToggleButton {
    
    private final MineSweeperAdapter adapter;   // Reference to adapter.
    private final Icons icon;                         // Referemce to the icons.
    private final int[] coordinate;             // The (x, y) coordinate.
    private int proxCount;                      // Count of nearby mines.
    private Marking markingStatus;              // Enumeration of marked state.
    private boolean revealed;                   // True if node was revealed.
    private MouseAdapter mouseAdapter;          // The mouse adapter for clicks.
    
    /**
     * Default constructor for the button.
     * 
     * @param coordinate The (x, y) coordinate of the node.
     * @param adapter The MineSweeperAdapter object since the button is a part 
     * of the view.
     */
    public LandButton( int[] coordinate, MineSweeperAdapter adapter ) {
        super( );
        this.adapter = adapter;
        this.icon  = adapter.getView().getIcons( );
        this.coordinate = coordinate;
        proxCount = 0;
        markingStatus = Marking.NOT_MARKED;
        revealed = false;
        mouseAdapter = null;
        
    } // End of LandButton( )
    
    /**
     * This function sets up the LandButton object. Should be called after the 
     * button has been initialized.
     */
    public void initComponent( ) {
        // Create the mouse listener.
        mouseAdapter = createMouseAdapter( );
        this.addMouseListener( mouseAdapter );
        // Set the size of the icon. This should probably be in an interface.
        this.setPreferredSize( new Dimension(50, 50) );
        this.setIcon( icon.NODE_ICON );
        this.setVisible( true );
        
    } // End of initComponent( )
    
    /**
     * This function is used to set the proximity value of the button.
     * 
     * @param proxCount The proximity value.
     */
    public void setProxCount( int proxCount ) {
        this.proxCount = proxCount;
        setProxIcon( proxCount );
        this.repaint();
        //this.setText( ((Integer)proxCount).toString() );
        
    } // End of setProxCount( )
    
    /**
     * This function is used to disable the LandButton after it is left clicked.
     */
    public void disableButton( ) {
        this.setEnabled( false );
        
    } // End of disableButton( )
    
    /**
     * This function is used to enable the LandButton for clicking.
     */
    public void enableButton( ) {
        this.setEnabled( true );
        
    } // End of enableButton( )
    
    /**
     * This function is used to see if the button was already pressed/revealed. 
     * It is used to prevent the user from marking an already revealed node.
     * 
     * @return True if the button was revealed, false otherwise.
     */
    public boolean isRevealed( ) {
        return revealed;
        
    } // End of isRevealed( )
    
    /**
     * This function is used to reveal the node. It prevents further interaction
     *  with the button. Exits if the node is marked in any way.
     */
    public void reveal( ) {
        // If the node is not marked, reveal the node and check it
        if( markingStatus == Marking.NOT_MARKED )  {
            revealed = true;
            // Disable the button for the rest of the game
            disableButton( );
            adapter.checkNode( coordinate );
        }
        
    } // End of reveal( )

    /**
     * This function is used to reveal a mine that was 'stepped on'.
     */
    public void revealMine( ) {
        this.setDisabledIcon( icon.MINE_RED_ICON );
        disableButton( );
        this.validate( );
        this.repaint( );
        
    } // End of revealMine( )
    
    /**
     * This function is used to reveal a mine location that hasn't been pressed.
     */
    public void revealNewMine( ) {
        // Disable the button and remove the mouse listener to prevent further
        // interactions.
        disableButton( );
        this.removeMouseListener( mouseAdapter );
        // Set the new icon and repaint.
        this.setDisabledIcon( icon.MINE_ICON );
        this.validate( );
        this.repaint( );
        
    } // End of revealNewMine( )
    
    /**
     * This function checks to see if the user marked the node as containing a 
     * mine.
     * 
     * @return True if the user marked the node for a mine - false otherwise.
     */
    public boolean isMarkedForMine( ) {
        return this.markingStatus == Marking.MARKED;
        
    } // End of isMarkedForMine( )
    
    /**
     * Draw something on the button based off of the marked status of the land.
     * 
     * @param status An enumeration of the marking status of the land node.
     */
    public void nextMarking( Marking status ) {
        // Set the marking status.
        this.markingStatus = status;
        // If the node has not been revealed.
        if( !revealed ) {
            // Based off of the marking status, set the icon and enabled state.
            switch( status ) {
                case NOT_MARKED:
                    this.setIcon( icon.NODE_ICON );
                    this.enableButton( );
                    break;
                    
                case MARKED:
                    this.setDisabledIcon( icon.FLAG_ICON );
                    this.disableButton( );
                    break;
                    
                case QUESTIONED:
                    this.setDisabledIcon( icon.FLAG_QUESTION_ICON );
                    this.disableButton( );
                    break;
            }
            
            // Make sure we repaint the component.
            this.validate();
            this.repaint();
        }
        
    } // End of nextMarking( )
    
    /**
     * This function returns the current Marking status of the LandButton.
     * 
     * @return The current Marking status of the LandButton.
     */
    public Marking getMarking( ) {
        return markingStatus;
        
    } // End of getMarking( )
    
    /**
     * This function is used to get the internal proximity count of this node.
     * It should match its coordinate counterpart in the model.
     * 
     * @return The number of nearby mines.
     */
    public int getProximityCount( ) {
        return proxCount;
        
    } // End of getProximityCount( )
    
    /**
     * This function is used to set the icon of the proximity count.
     * 
     * @param proximityCount The number of adjacent mines.
     */
    public void setProxIcon( int proximityCount ) {
        // Switch statement based off of the proximity count.
        switch( proximityCount ) {
            case 0:
                this.setDisabledIcon( icon.REVEALED_NODE_ICON );
                break;
                
            case 1:
                this.setDisabledIcon( icon.PROX_ONE_ICON );
                break;
                
            case 2:
                this.setDisabledIcon( icon.PROX_TWO_ICON );
                break;
                
            case 3:
                this.setDisabledIcon( icon.PROX_THREE_ICON );
                break;
                
            case 4:
                this.setDisabledIcon( icon.PROX_FOUR_ICON );
                break;
                
            case 5:
                this.setDisabledIcon( icon.PROX_FIVE_ICON );
                break;
                
            case 6:
                this.setDisabledIcon( icon.PROX_SIX_ICON );
                break;    
                
            case 7:
                this.setDisabledIcon( icon.PROX_SEVEN_ICON );
                break;
                
            case 8:
                this.setDisabledIcon( icon.PROX_EIGHT_ICON );
                break;
                
        }
        
        // Make sure we repaint the object.
        this.validate( );
        this.repaint( );
        
    } // End of setProxIcon( )
    
    /**
     * This function is used to get the internal coordinate of the LandButton.
     * 
     * @return The (x, y) coordinate of the LandButton.
     */
    public int[] getCoordinate( ) {
        return coordinate;
        
    } // End of getCoordinate( )
    
    /**
     * This function sets the icon of the LandButton to an invalid marking icon.
     */
    public void invalidMarking( ) {
        this.setDisabledIcon( icon.FLAG_RED_ICON );
        
    } // End of invalidMarking( )
    
    /**
     * This function is used to create a MouseListener for the LandButton.
     * 
     * Left click presses the button and reveal its contents.
     * Right click iterates through the Marking states with this pattern:
     * NOT_MARKED -> MARKED -> QUESTIONED -> NOT_MARKED
     * 
     * @return A MouseAdapter object unique to each button.
     */
    public MouseAdapter createMouseAdapter( ) {
        return new MouseAdapter( ) {
            @Override
            public void mouseClicked( MouseEvent click ) {
                // Left click.
                if( click.getButton() == MouseEvent.BUTTON1 ) {
                    // If we are already revealed.
                    if( isRevealed() ) { 
                        // Reveal the nearby nodes.
                        adapter.revealNearbyNodes( coordinate );
                    }
                    else {
                        // Reveal the node.
                        reveal( );
                    }
                }
                // Right click.
                if( click.getButton() == MouseEvent.BUTTON3 ) {
                    if( !revealed ) {
                        // Iterate through the marking via the adapter.
                        nextMarking( adapter.nextMarking(coordinate) );
                    }
                }
            } // End of mouseClicked( )
        };
        
    } // End of createNewMouseAdapter( )
    
    /**
     * This function clears out the mouse adapter so the button is unclickable.
     */
    public void removeMouseAdapter( ) {
        this.removeMouseListener( mouseAdapter );
        
    } // End of removeMouseAdapter( )
    
} // End of LandButton class.
