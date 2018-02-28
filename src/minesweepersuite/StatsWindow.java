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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class creates a window that shows the best game modes, score, and times 
 * of the game so far.
 * 
 * @author Nicholas Moss
 */
public class StatsWindow extends JFrame {
   
    // References to the adapter, view, and statistics objects.
    private final MineSweeperAdapter adapter;
    // These two probably shouldn't be here but my patience it wearing thin.
    private final MineSweeperGUI gui;
    private final MineSweeperStatistics stats;
    
    // GUI Components
    private final JPanel mainPanel;
    private final CustomGridBagConstraints constraints;

    // Labels
    private final JLabel rankLabel;
    private final JLabel userLabel;
    private final JLabel timeLabel;
    private final JLabel gridSizeLabel;
    private final JLabel mineLabel;
    
    private JTextField userField;

    /**
     * Default constructor.
     * 
     * @param adapter The MineSweeperAdapter object.
     */
    public StatsWindow( MineSweeperAdapter adapter ) {
        super( "Statistics" );
        this.adapter = adapter;
        gui = adapter.getView( );
        stats = adapter.getGameStatistics( );

        mainPanel = new JPanel( );
        constraints = new CustomGridBagConstraints( );
        
        rankLabel = new JLabel( "Rank" );
        userLabel = new JLabel( "User" );
        timeLabel = new JLabel( "Time" );
        gridSizeLabel = new JLabel( "Grid" );
        mineLabel = new JLabel( "Mines" );
        
        userField = null;
        
    } // End of StatsWindow( )
    
    /**
     * This function is used to stitch together the interface. It does the real 
     * work setting up the interface.
     */
    public void initComponents( ) {
        constraints.fill = GridBagConstraints.NONE;
        constraints.setInset( 5, 25, 5, 25 );
        constraints.setPadding( 25, 5 );
        mainPanel.setLayout( new GridBagLayout() );
                
        constraints.setCoordinates( 0, 0 );
        mainPanel.add( rankLabel, constraints );
        constraints.setCoordinates( 1, 0 );
        mainPanel.add( userLabel, constraints );
        constraints.setCoordinates( 2, 0 );
        mainPanel.add( timeLabel, constraints );
        constraints.setCoordinates( 3, 0 );
        mainPanel.add( gridSizeLabel, constraints );
        constraints.setCoordinates( 4, 0 );
        mainPanel.add( mineLabel, constraints );
        
        constraints.setCoordinates( 0, 0 );
        constraints.setInset( 0, 0, 0, 0 );
        
        // Add a window listener/adapter for win the window closes.
        this.addWindowListener( new WindowAdapter( ) {
            @Override
            // Save the entry, make and initialize a NewGameWindow
            public void windowClosing( WindowEvent event ) {
                saveEntry( );
                gui.createNewGameWindow();
            }
        });
        
        this.setLayout( new BorderLayout( ));
        this.add( mainPanel, BorderLayout.CENTER );
        populateStats( );
        this.setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        this.setSize( 600, 400 );
        this.setLocationRelativeTo( null );
        this.setAlwaysOnTop( true );
        this.setVisible( true );

    } // End of initComponents( )
    
    /**
     * This function is used to populate the list of statistics dynamically. 
     * It reads data from the adapter to display the statistics.
     */
    public void populateStats( ) {
        // Iterate through eveything in the list
        for( int i = 0; i < stats.getEntrySize(); i++ ) {
            // Create an array list with JComponents and populate it
            ArrayList<JComponent> components = createStatisticComponents( 
                                                i, stats.getEntry(i) );
            // Iterate through the array list
            for( int f = 0; f < components.size(); f++ ) {
                // Set the coordinate offsets
                constraints.setCoordinates( f , i +1);
                mainPanel.add( components.get( f), constraints );
            }
        }
        
    } // End of populateStats( )
    
    /**
     * This function returns an ArrayList of JComponents based off of the 
     * information in the StatisticEntry. 
     * 
     * @param rank The rank of the StatisticEntry object.
     * @param entry A StatisticEntry object containing information on the game.
     * @return An ArrayList of JComponents to attach to the GUI.
     */
    public ArrayList<JComponent> createStatisticComponents( int rank, 
                                                        StatisticEntry entry) {
        // Create the ArrayList
        ArrayList<JComponent> labels = new ArrayList<>();
        // Start adding stuff to the list. Rank+1 to offset the 0 of the loop.
        labels.add( new JLabel( ((Integer)(rank+1)).toString()) );
        // If the entry was finalized use a JLabel
        if( entry.isFinalized() ) {
            labels.add( new JLabel( entry.getUser()));
        }
        // If the entry was not finalized, use a text field.
        else {
            userField = new JTextField( entry.getUser() );
            userField.setColumns( 10 );
            // Add a key listener to the field.
            // If the user presses ENTER in the field, save and dispose
            userField.addKeyListener( new KeyAdapter() {
                @Override
                public void keyPressed( KeyEvent event ) {
                    if( event.getKeyCode() == KeyEvent.VK_ENTER ) 
                        disposeWindow( );
                }
            });
            
            labels.add( userField );
        }
        // Keep adding stuff.
        labels.add( new JLabel( entry.getTimeAsString()) );
        labels.add( new JLabel( entry.getRows() + ", " + entry.getColumns()) );
        labels.add( new JLabel( entry.getMines()) );
        
        return labels;
        
    } // End of createStatisticComponents( )
    
    /**
     * This function is used to dispose of the window.
     */
    public void disposeWindow( ) {
        this.dispatchEvent( new WindowEvent( this, WindowEvent.WINDOW_CLOSING));
        //this.dispose( );
                
    } // End of disposeWindow( )
    
    /**
     * This function is used to save the user string for a recently finished 
     * game. 
     */
    public void saveEntry(  ) {
        if( userField != null ) {
            // Calling setUser automagically finalized the StatisticEntry.
            adapter.updateStats( userField.getText() );
            // Get rid of the field.
            userField = null;
        }
        
    } // End of saveEntry( )
    
} // End of StatsWindow class.
