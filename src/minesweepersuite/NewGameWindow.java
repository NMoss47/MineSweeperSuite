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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

/**
 * This object is a separate window for view of the MineSweeperSuit. Its 
 * responsibilities are to get the parameters for a new game and provide a few 
 * convenience buttons for the user. Additionally it also renders error 
 * messages for the user if there are any issues with the game parameters.
 * 
 * @author Nicholas Moss
 */
public class NewGameWindow extends JFrame implements ActionListener {
    
    // The adapter.
    private final MineSweeperAdapter adapter;
    
    // GUI components
    CustomGridBagConstraints constraints;

    private final JPanel mainPanel;
    
    private final JLabel mineCountLabel;
    private final JLabel rowCountLabel;
    private final JLabel columnCountLabel;
    private final JLabel errorLabel;
    
    private final JFormattedTextField mineCountField;
    private final JFormattedTextField rowCountField;
    private final JFormattedTextField columnCountField;
    
    private final NumberFormat numberFormat;
    private final NumberFormatter numberFormatter;
    
    private final Dimension buttonSize;
    private final JButton startButton;
    private final JButton clearButton;
    private final JButton easyButton;
    private final JButton mediumButton;
    private final JButton hardButton;
    private final JButton randomButton;
        
    /**
     * Default constructor for NewGameWindow. Just initializes objects.
     * 
     * @param adapter The MineSweeperAdapter used to actually initiate a new 
     * game.
     */
    public NewGameWindow( MineSweeperAdapter adapter ) {
        // Initialize the frame, constraints, and the main panel.
        super( "New Game!" );
        this.adapter = adapter;
        
        constraints = new CustomGridBagConstraints( );
        //buttonListener = new NewGameWindowButtonListener( );
        mainPanel = new JPanel( );
        
        // Initialize the JLabels
        mineCountLabel = new JLabel( "Mines:" );
        rowCountLabel = new JLabel( "Rows:" );
        columnCountLabel = new JLabel( "Columns:" );
        errorLabel = new JLabel( "" );
        
        // Initialize the formatters.
        // Set to integers only.
        numberFormat = NumberFormat.getInstance( );
        // No commas or anything for the number format.
        numberFormat.setGroupingUsed( false );
        numberFormatter = new NumberFormatter( numberFormat );
        numberFormatter.setValueClass( Integer.class );
        // Set minimum and maximum.
        numberFormatter.setMinimum( 0 );
        numberFormatter.setMaximum( Integer.MAX_VALUE );
        // Don't allow invalid inputs.
        numberFormatter.setAllowsInvalid( false );
        
        // Initialize the text fields.
        mineCountField = new JFormattedTextField( numberFormatter );
        rowCountField = new JFormattedTextField( numberFormatter );
        columnCountField = new JFormattedTextField( numberFormatter );
        
        // Initiailize the buttons
        buttonSize = new Dimension( 115, 30 );
        startButton = new JButton( "Start" );
        clearButton = new JButton( "Clear" );
        easyButton = new JButton( "Easy" );
        mediumButton = new JButton( "Medium" ); 
        hardButton = new JButton( "Hard" );
        randomButton = new JButton( "Random" ); 
        
    } // End of NewGameWindow( )
    
    /**
     * This function instantiates the various GUI components and does the real 
     * work setting up the NewGameWindow.
     */
    public void initComponents( ) {
        // Grid Bag Constraint initializations and default values.
        constraints.fill = GridBagConstraints.NONE;
        constraints.setCoordinates( 0, 0 );
        constraints.setInset( 10, 10, 10, 10 );
        constraints.setWeight( 0, 0 );
        
        // Manage field sizes
        mineCountField.setColumns( 5 );
        rowCountField.setColumns( 5 );
        columnCountField.setColumns( 5 );
        
        // Add the button listener to the buttons
        startButton.addActionListener( this );
        clearButton.addActionListener( this );
        easyButton.addActionListener( this );
        mediumButton.addActionListener( this );
        hardButton.addActionListener( this );
        randomButton.addActionListener( this );
        
        // Set button sizes for consistency
        startButton.setPreferredSize( buttonSize );
        clearButton.setPreferredSize( buttonSize );
        easyButton.setPreferredSize( buttonSize );
        mediumButton.setPreferredSize( buttonSize );
        hardButton.setPreferredSize( buttonSize );
        randomButton.setPreferredSize( buttonSize );
        
        // Main panel - Grid Bag Layout
        mainPanel.setLayout( new GridBagLayout() );
        
        // Add the error label if it is needed - place at the top.
        constraints.setCoordinates( 0, 0 );
        // Set the width to take up all of it - will autocenter.
        constraints.setThickness( 9, 1 );
        // Make sure the error label is clear.
        errorLabel.setText( "" );
        mainPanel.add( errorLabel, constraints );
        // Reset the width of the constraints.
        constraints.setThickness( 1, 1 );
        
        // Add buttons to the left side.
        constraints.setCoordinates( 1, 1 );
        mainPanel.add( easyButton, constraints );
        constraints.setCoordinates( 1, 3 );
        mainPanel.add( mediumButton, constraints );
        constraints.setCoordinates( 1, 5 );
        mainPanel.add( hardButton, constraints );
        constraints.setCoordinates( 1, 7 );
        mainPanel.add( randomButton, constraints );
        
        // Add fields to the right side.
        // Mine count.
        constraints.setCoordinates( 5, 1 );
        mainPanel.add( mineCountLabel, constraints );
        constraints.setCoordinates( 6, 1 );
        mainPanel.add( mineCountField, constraints );
        // Row count.
        constraints.setCoordinates( 5, 3 );
        mainPanel.add( rowCountLabel, constraints );
        constraints.setCoordinates( 6, 3 );
        mainPanel.add( rowCountField, constraints );
        // Column count.
        constraints.setCoordinates( 5, 5 );
        mainPanel.add( columnCountLabel, constraints );
        constraints.setCoordinates( 6, 5 );
        mainPanel.add( columnCountField, constraints );
        // Add the start and clear buttons
        constraints.setCoordinates( 1, 9 );
        mainPanel.add( startButton, constraints );
        constraints.setCoordinates( 6, 9 );
        mainPanel.add( clearButton, constraints );
        
        // Frame settings.
        mainPanel.setVisible( true );
        this.add( mainPanel );
        this.setDefaultCloseOperation( EXIT_ON_CLOSE );
        this.setSize( 600, 400 );
        // This centers the frame on the screen.
        this.setLocationRelativeTo( null );
        this.setVisible( true );
        this.setAlwaysOnTop( true );
        
    } // End of initComponents( )
    
    /**
     * This function is used to dispose of the NewGameWindow window.
     */
    public void disposeWindow( ) {
        this.dispose();
        
    } // End of disposeWindow( )

    /**
     * This is the overridden actionPerformed method of the ActionListener 
     * attached to the buttons.
     * 
     * @param buttonPress The action event.
     */
    @Override
    public void actionPerformed( ActionEvent buttonPress ) {
        if( buttonPress.getSource() == startButton ) {
            clearErrorText( );
            GameParameters params = new GameParameters(
                        mineCountField.getText(),
                        rowCountField.getText(),
                        columnCountField.getText() );
            
            if( adapter.startNewGame( params ) ) 
                disposeWindow( );
        }
        
        // Clear button
        if( buttonPress.getSource() == clearButton ) {
            modeButton( Mode.NONE );
        }
        
        // Easy button
        if( buttonPress.getSource() == easyButton ) {
            modeButton( Mode.EASY );
        }
        
        // Medium Button
        if( buttonPress.getSource() == mediumButton ) {
            modeButton( Mode.MEDIUM );
        }
        
        // Hard Button
        if( buttonPress.getSource() == hardButton ) {
            modeButton( Mode.HARD );
        }
        
        // Random Button
        if( buttonPress.getSource() == randomButton ) {
            modeButton( Mode.RANDOM );
        }
        
    } // End of actionPerformed( )
    
    /**
     * This function is used to clear the text for the *CountFields. 
     */
    public void clearCountFields( ) {
        mineCountField.setText( "" );
        rowCountField.setText( "" );
        columnCountField.setText( "" );
        
    } // End of clearCountfields( )
    
    /**
     * This function is used to set the text for the *CountFields.
     * 
     * @param params A GameParameters object containing the parameters.
     */
    public void setCountFields( GameParameters params ) {
        mineCountField.setText( params.getStringMines() );
        rowCountField.setText (params.getStringRows() );
        columnCountField.setText( params.getStringColumns() );
        
    } // End of setCountFields( )
    
    /**
     * This function is used to clear the error text and retrieve the game 
     * parameters for the various enumerated modes.
     * 
     * @param mode A Mode enumeration of the game difficulty.
     */
    public void modeButton( Mode mode ) {
        // Clear the error label.
        clearErrorText( );
        // Get the parameters
        GameParameters params = adapter.getModeParams( mode );
        // If there are no params, set to nothing. Else set to their values.
        if( params == null ) 
            clearCountFields( );
        else 
            setCountFields( params );
        
    } // End of modeButton( )
    
    /**
     * This function sets the text for the errorLabel.
     * 
     * @param error A string of the errors to display.
     */
    
    public void setErrorText( String error ) {
        errorLabel.setText( error );
        this.repaint();
        
    } // End of setErrorText( )
    
    /**
     * This function clears the text of the errorLabel.
     */
    public void clearErrorText( ) {
        errorLabel.setText( "" );
        this.repaint( );
        
    } // End of clearErrorText( )
    
} // End of NewGameWindow class.
