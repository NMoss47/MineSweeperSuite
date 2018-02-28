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
import java.util.LinkedList;

/**
 * This class is used to return formatted strings to a view to inform the user 
 * of any errors in the game state. These formatted strings are HTML compatible 
 * which allows for multi-line statements in JLabels. This class isn't fleshed 
 * out enough.
 * 
 * @author Nicholas Moss
 */
public final class ErrorHandler<T> {
    
    // A linked list of formatted error strings.
    private static LinkedList<String> errors;
    
    /**
     * Default constructor. Initializes a list of errors and automatically 
     * builds starting and ending HTML tags.
     */
    public static void ErrorHandler( ) {
        errors = new LinkedList<>();
        startErrorMessage( );
        
    } // End of ErrorHandler( )
    
    /**
     * This function starts the errorMessage string with HTML tags.
     */
    private static void startErrorMessage( ) {
        errors = new LinkedList<>();
        errors.addFirst( "<html>" );
        errors.addLast( "</html>" );
        
    } // End of startErrorMessage( )
    
    /**
     * This function adds a string to the error list. The function automatically
     *  formats the string with HTML tags.
     * @param error 
     */
    public static void addErrorMessage( String error ) {
        errors.add( 1, "<p>" + error + "</p>" );
        
    } // End of addErrorMessage( )
    
    /**
     * This function returns the entire set of errors as one HTML formatted 
     * string for injection into whatever takes text (and plays ball with HTML).
     * 
     * @return An HTML formatted string of error messages.
     */
    public static String getErrorText( ) {
        // Empty string.
        String errorMessage = "";
        // Iterate through the list and concatenate the error message.
        for( String error : errors ) {
            errorMessage += error;
        }
        
        return errorMessage;
        
    } // End of getErrorText( )
    
    /**
     * This static function is used to generate one HTML string of the errors 
     * passed in.
     * 
     * @param list An ArrayList of GameStatus errors.
     * @return An HTML string of the error messages.
     */
    public static String generateFormattedErrorText( 
                                                ArrayList<GameStatus> list ) {
        
        startErrorMessage( );
        
        for( GameStatus status : list ) {
            addErrorMessage( GameStatus.getMessage(status) );
        }

        return getErrorText( );
        
    } // End of generateFormattedErrorText( )
    
} // End of ErrorHandler class.
