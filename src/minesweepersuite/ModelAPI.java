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
 * The ModelAPI allows to a set of functions that an adapter can call to work 
 * with the Model. 
 * 
 * This particular API contains several game constants and methods to control 
 * the game. This API also extends the GridAPI. In order to adhere to the 
 * extended API we must use the override annotation on any methods declared in
 * extended API.
 * 
 * @author Nicholas Moss
 */
public interface ModelAPI extends GridAPI {
    // Game Constants
    public static final int MAX_AXIS_SIZE = 100;
    public static final int MIN_AXIS_SIZE = 6;
    public static final double MAX_MINE_PERCENT = 0.6;
    public static final double MIN_MINE_PERCENT = 0.1;
    
    // Method declarations
    
    // API functions
    // Setup
    public void setAdapter( MineSweeperAdapter adapter );
    public ArrayList<GameStatus> setGameParams( GameParameters params );
    //public int[] getRandomGameParams( );
    public GameParameters getRandomGameParams( );
    public GameParameters getCurrentGameParams( );
    
    // Game Overview
    public String getGameIdentifier( );
    public void createNewGame( int[] coordinate );
    public void clearGame( );
    public void loseGame( int[] coordinate );
    public void winGame( );
    
    // Nodes
    @Override
    public LandNode getNode( int[] coordinate );
    @Override
    public LandNode[][] getGrid( );
    public Marking nextMarking( int[] coordinate );
    public int[][] getMineLocations( );

} // End of ModelAPI interface.
