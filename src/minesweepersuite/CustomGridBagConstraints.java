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

import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * This class is an extension of the GridBagCosntraints object. It was created 
 * so I can use a couple of my own convenience functions so I can make this 
 * code a little easier to read and write.
 * 
 * It isn't as much of an improvement as I had wanted since it is still wordy.
 * But it gets the job done, I suppose.
 * 
 * @author Nicholas Moss
 */
public class CustomGridBagConstraints extends GridBagConstraints {
    
    /**
     * Default constructor. Just makes a regular GridBagConstraints object.
     */
    public CustomGridBagConstraints( ) {
        // Call to super.
        super( );
        
    } // End of CustomGridBagConstraints( )
        
    /**
     * This function is used to set the gridx and gridy variables of the 
     * GridBagConstraints object.
     * 
     * @param x The intended gridx value. Specifies the column cell.
     * @param y The intended gridy value. Specifies the row cell.
     */
    public void setCoordinates( int x, int y ) {
        gridx = x;
        gridy = y;
        
    } // End of setCoordinates( )
        
    /**
     * This function is used to set the Inset variable of the 
     * GridBagConstraints object. Inset values are the space at the edges.
     * 
     * Refer to the Java documentation for more information.
     * 
     * @param top Top inset value.
     * @param left Left inset value.
     * @param bottom Bottom inset value.
     * @param right Right inset value.
     */
    public void setInset( int top, int left, int bottom, int right) {
        insets = new Insets( top, left, bottom, right );
        
    } // end of setInset( )
        
    /**
     * This function is used to set the width and height of the rows and 
     * columns of the GridBagConstraints object.
     * 
     * Refer to the Java documentation for more information.
     * 
     * @param width Specifies the number of cells in the row.
     * @param height Specifies the number of cells in the column.
     */
    public void setThickness( int width, int height ) {
        gridwidth = width;
        gridheight = height;
        
    } // End of setThickness( )
        
    /**
     * This function is used to set the weighting of the rows and columns in
     *  the GridBagConstraints object.
     * 
     * @param x The weighting value of x.
     * @param y The weighting value of y.
     */
    public void setWeight( double x, double y ) {
        weightx = x;
        weighty = y;
        
    } // End of setWeight
        
    /**
     * This function is used to set the internal padding of a component. It 
     * adds to the minimum width and height values of the component.
     * 
     * @param x Horizontal padding value.
     * @param y Vertical padding value.
     */
    public void setPadding( int x, int y ) {
        ipadx = x;
        ipady = y;
        
    } // End of setPadding( )
        
    /**
     * This function is used to set the anchor value of the 
     * GridBagConstraints object.
     * 
     * Refer to the Java documentation for more information.
     * 
     * @param anchornumber The enumerated anchor value.
     */
    public void setAnchor( int anchornumber ) {
        anchor = anchornumber;
        
    } // End of setAnchor( )
        
} // End of CustomGridBagConstraints class