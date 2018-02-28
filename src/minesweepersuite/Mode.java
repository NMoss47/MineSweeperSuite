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

import java.util.HashMap;

/**
 * This enumeration is used to make game mode switching easier. With this 
 * enumeration we can avoid blindly passing strings. It also serves as a single 
 * point where we can change game parameters without running around in the rest 
 * of the code.
 * 
 * @author Nicholas Moss
 */
public enum Mode {
    // Enumerated Values
    NONE,            // No mode - not playable
    EASY,           // Easy mode
    MEDIUM,         // Medium Mode
    HARD,           // Hard mode
    CUSTOM,         // Custom mode (created through view)
    RANDOM;         // Random mode

    // Defined values
    private static final GameParameters EASY_MODE = 
                                    new GameParameters( 10, 8, 8 );
    private static final GameParameters MEDIUM_MODE = 
                                    new GameParameters( 40, 16, 16 );
    private static final GameParameters HARD_MODE = 
                                    new GameParameters( 99, 16, 30 );
    
    // Key-Value store for the enumerated values.
    private final static HashMap<Mode, GameParameters> map = new HashMap<>();
    
    // Static initializer
    static {
        map.put( NONE,      null );
        map.put( EASY,      EASY_MODE );
        map.put( MEDIUM,    MEDIUM_MODE );
        map.put( HARD,      HARD_MODE );
        map.put( CUSTOM,    null );
        map.put( RANDOM,    null );

    } // End of static initializer.

    /**
     * This function returns an int[] of the game parameters based off of the
     * Difficulty enumeration.
     *
     * @param mode The Mode enumeration of the game.
     * @return int array of the game parameters.
     */
    public static GameParameters getParams( Mode mode ) {
        if( mode == RANDOM ) {
            map.replace( RANDOM, MineSweeper.getStaticRandomGameParams() );
        }
        
        return map.get( mode );
    
    } // End of getParams( )
    
} // End of Mode enumeration.
