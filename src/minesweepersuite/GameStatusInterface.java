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
 * This interface serves to be a single point for storage for error strings of 
 * the GameStatus enumeration.
 * 
 * @author Nicholas Moss
 */
public interface GameStatusInterface {
    // Static string declarations
    public static final String ROWS_BELOW = 
            "Rows need to be above " + MineSweeper.MIN_AXIS_SIZE;
    public static final String ROWS_ABOVE = 
            "Rows need to be below " + MineSweeper.MAX_AXIS_SIZE;
    public static final String COLUMNS_BELOW = 
            "Columns need to be above " + MineSweeper.MIN_AXIS_SIZE;
    public static final String COLUMNS_ABOVE = 
            "Columns need to be below " + MineSweeper.MAX_AXIS_SIZE;
    public static final String MINES_BELOW = 
            "Mines needs to be above " + MineSweeper.MIN_MINE_PERCENT*100
            + "% of area.";
    public static final String MINES_ABOVE = 
            "Mines needs to be below " + MineSweeper.MAX_MINE_PERCENT*100
            + "% of area.";
    public static final String FAIL = "Something failed!";
    public static final String PASS = "Success";
    
} // End of gameStatusInterface interface.
