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
 * This interface was created so we can 'bind' common functionality between the 
 * ModelAPI and the ViewAPI since they both used very similar ideas. Honestly, 
 * this could have been made as some kind of abstract class (maybe). Extendible 
 * interfaces are cool too, though!
 * 
 * @author Nicholas Moss
 * @param <T> A generic type. Some of the functions implemented in the other 
 * interfaces are void so I needed something that could roll with the punches.
 */
public interface GridAPI<T> {
    
    public int[][] getAdjacentNodes( int[] coordinate );
    public T getGrid( );
    public T getNode( int[] coordinate );
    public boolean boundsCheck( int value, int boundary );
    
} // End of the GridAPI interface.
