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

import java.io.PrintWriter;

/**
 * This static class is used to handle file I/O operations, terminal operations,
 *  and other miscellaneous operations. Only some of the functions are 
 * implemented. Future revisions of the MineSweeperSuite will expand on the 
 * capabilities of this class. This class may be outright removed.
 * 
 * @author Nicholas Moss
 * @param <T> Generic type since we aren't entirely sure what objects we will 
 * be handling.
 */
public final class MineSweeperIO<T> implements IOAPI {
    
    private static PrintWriter printWriter;

    /**
     * This function is used to read from a file. Primarily used to load save 
     * data.
     * 
     * @param file The string of the filename/path.
     */
    public static void readFile( String file ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    } // End of readFile( )

    /**
     * This function is used to read a generic object from a file.
     * 
     * @param fileName The location of the file with the saved object.
     * @return Returns the object from the file.
     */
    public static Object readObjectFromFile( String fileName ) {
        return IOAPI.readObjectFromFile( fileName );
        
    } // End of readObjectFromFile( )
    
    /**
     * This function is used to create a new file. Primarily used to save data 
     * from a game.
     * 
     * @param file The string of the filename/path.
     */
    public static void createFile( String file ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    } // End of createFile( )
    
    
    /**
     * This function is used to write a string and add a new line to the file.
     * 
     * @param data The string to write, ideally without a new line.
     */
    public static void writeLine( String data ) {
        printWriter.write( data + "\n" );
        printWriter.flush( );
    } // End of writeLine( )

    /**
     * This function writes a string to a file without a new line.
     * 
     * @param data The string to be written (without a new line).
     */
    public static void writeChars( String data ) {
        printWriter.write( data );
        
    } // End of writeChars( )
    
    /**
     * This function is used as a followup to writeChars( ). It adds a new line 
     * and flushes the characters so they can be written.
     */
    public static void writeFlush( ) {
        printWriter.write( "\n" );
        printWriter.flush( );
        
    } // End of writeFlush( )

    /**
     * This function is used to write an object to some specified file.
     * 
     * @param object The object to write.
     * @param file The file to write to.
     */
    public static void writeObjectToFile( Object object, String file ) {
        IOAPI.writeObjectToFile( object, file );
        
    } // End of writeObjectToFile( ) 
    
} // End of MineSweeperIO class.
