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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This interface is the API used to do file, terminal, and other operations in 
 * the MineSweeperSuite package. It contains some static methods. This 
 * interface will be expanded in further versions of the MineSweeperSuite.
 * 
 * @author Nicholas Moss
 * @param <T> A generic type since the implementing classes should be able to 
 * read multiple types of objects.
 */
public interface IOAPI<T> {
   
    // Default files
    public final String STATISTICS_FILE = "statistics.txt";
    
    // IO Methods
    // These are commented out for the foreseeable future. 
    /*
    public T readObjectFromFile( String file );
    public void writeObjectToFile( T object, String file );
    public void readFile( String file );
    public void createFile( String file );
    
    public void writeLine( String data);
    public void writeChars( String data );
    public void writeFlush( );*/
    
    
    /**
     * This function is used to read a generic object from a file.
     * 
     * @param fileName The location of the file with the saved object.
     * @return Returns the object from the file.
     */
    public static Object readObjectFromFile( String fileName ) {
        File file = new File( fileName );
        if( file.exists() && file.length() > 0 ) {
            // Try-with-resources, one filestream and one objectstream
            try( FileInputStream fis = new FileInputStream(file);
                 ObjectInputStream ois = new ObjectInputStream(fis); ) {
                // Return whatever object we read.
                return ois.readObject( );
            }
            catch( IOException | ClassNotFoundException ex ) {
                ex.printStackTrace( );
            }
        }
        
        // If there is no file or nothing in it, return null.
        return null;
        
    } // End of readObjectFromFile( )
    
    /**
     * This function is used to write an object to some specified file.
     * 
     * @param object The object to write.
     * @param file The file to write to.
     */
    public static void writeObjectToFile( Object object, String file ) {
        // Try-with-resources. One filestream and one objectstream.
        try ( FileOutputStream fos = new FileOutputStream(file);
              ObjectOutputStream oos = new ObjectOutputStream(fos); ) {
            // Write the object to the stream and then flush
            oos.writeObject( object );
            oos.flush();
        }
        catch( IOException ex ) {
            ex.printStackTrace( );
        }
        
    } // End of writeObjectToFile( )

} // End of IOAPI interface.
