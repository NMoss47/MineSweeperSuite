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

import java.util.ArrayList;

/**
 * The ViewAPI interface is used to set some basic constants and provide a set 
 * of methods to call. These methods are mostly used to communicate with the 
 * adapter.
 * 
 * @author Nicholas Moss
 */
public interface ViewAPI extends GridAPI {
    // Constants
    // Main Window Settings
    public int WINDOW_WIDTH = 1920;
    public int WINDOW_HEIGHT = 1080;
    
    // API Functions
    // Game Overview
    public void startGame( int rows, int columns );
    public void clearGame( );
    public void winGame( boolean newRecord );
    public void loseGame( int[][] mineLocations, int[] coordinate );
    public void pause( );
    public void setErrors( ArrayList<GameStatus> errorList );
    public void checkWinConditions( );
    
    // Nodes
    @Override
    public LandButton getNode( int[] coordinate );
    @Override
    public LandButton[][] getGrid( );
    public void revealNode( int[] coordinate );
    public void checkNode( int[] coordinate );
    public void nextMarking( int[] coordinate, Marking marking );
    public boolean isNodeMarked( int[] coordinate );
    public void setNodeProx( int[] coordinate, int proxCount );
    public int[][] getMarkedLocations( );

    
} // End of ViewAPI interface.
