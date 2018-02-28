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

/**
 * This is the API for the Adapter part of the MVA architecture. This is, 
 * effectively, the 'controller' of the MineSweeper game. Most of these methods 
 * are used to call similarly named methods in the attached models and views.
 * 
 * @author Nicholas Moss
 */
public interface AdapterAPI {
    
    // Application
    public void startApplication( );
    public void addModelInstance( MineSweeper mineSweeper );
    public void addViewInstance( MineSweeperGUI mineSweeperGUI );
    
    // Game Overview
    public boolean startNewGame( GameParameters params );
    public GameParameters getModeParams( Mode mode );
    public void winGame( long time );
    public void loseGame( int[][] mineLocations, int[] coordinate );
    public void clearGame( );
    public void checkWinConditions( );
    
    // Nodes
    public void revealNearbyNodes( int[] coordinate );
    public Marking nextMarking( int[] coordinate );
    public void checkNode( int[] coordinate );
    public void revealNode( int[] coordinate );

} // End of AdapterAPI interface.
