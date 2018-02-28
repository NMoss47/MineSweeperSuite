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

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * This enumeration lays out various game states. These states will be used by 
 * the model to detail any errors and pushed to the view for user interaction.
 * 
 * The GameStatusInterface contains the error strings associated with the 
 * GameStatus enumerations.
 * 
 * @author Nicholas Moss
 */
public enum GameStatus implements GameStatusInterface {
    
    ROWS_BELOW_MIN( ROWS_BELOW ),           // Rows below min threshold.
    ROWS_ABOVE_MAX( ROWS_ABOVE ),           // Rows above max threshold.
    COLUMNS_BELOW_MIN( COLUMNS_BELOW ),     // Columns below min threshold.
    COLUMNS_ABOVE_MAX( COLUMNS_ABOVE ),     // Columns above max threshold.
    MINES_BELOW_MIN( MINES_BELOW ),         // Mines below min threshold.
    MINES_ABOVE_MAX( MINES_ABOVE ),         // Mines above max threshold.
    FAILURE( FAIL ),                        // A function has failed.
    SUCCESS( PASS );                        // A function has succeeded.
    
    // This variable holds the error string.
    private final String info;
    
    // Map the enumeration to the error string.
    private static final Map<GameStatus, String> gameStatusErrors = 
            // Make sure it is unmodifiable.
            Collections.unmodifiableMap( initializeEnumMapping() );
    
    /**
     * Default 'constructor' of the enumerated value.
     * @param info 
     */
    private GameStatus( String info ) {
        this.info = info;
    }
    
    /**
     * This function is used to map an enumeration to an error string.
     * 
     * @return A Map of enumerations and their corresponding error strings.
     */
    private static Map initializeEnumMapping( ) {
        // Make a small, temporary reference with size of GameStatus.class
        Map<GameStatus, String> map = new EnumMap( GameStatus.class );
        // For each enumeration map the corresponding error string.
        for( GameStatus status : GameStatus.values() ) {
            map.put( status, status.info );
        }
        
        return map;
        
    } // end of initializeEnumMapping( )

    /**
     * This function is used to return a string based off of the GameStatus 
     * enumeration. This can be used to build a set of error messages.
     * 
     * @param status The GameStatus enumeration.
     * @return A string of the enumeration's corresponding error string.
     */
    public static String getMessage( GameStatus status ) {
        return gameStatusErrors.get( status );
        
    } // End of getMessage( )
        
} // End of GameStatus enumeration
