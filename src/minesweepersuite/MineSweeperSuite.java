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
 * The MineSweeperSuite class is an MVA (Model, View, Adapter) implementation 
 * of the Mine Sweeper game. This particular class contains the entry point of 
 * the application and static references to the MVA components.
 * 
 * @author Nicholas Moss
 */
public class MineSweeperSuite {

    // Static references to the Model, View, and Adapter.
    static MineSweeperAdapter adapter;
    static MineSweeper model;
    static MineSweeperGUI view;
    
    /**
     * This is the main entry point of the MineSweeperSuite. There are currently
     *  no command line arguments.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Create the MVA components.
        adapter = new MineSweeperAdapter( );
        // With references to the adapter.
        model = new MineSweeper( adapter );
        view = new MineSweeperGUI( adapter );
        
        // Link the adapter to the Model and View.
        adapter.addModelInstance( model );
        adapter.addViewInstance( view );
        
        // Start the application (which really just starts the GUI)
        adapter.startApplication();
        
    } // End of Main( )
    
} // End of MineSweeperSuite class.
