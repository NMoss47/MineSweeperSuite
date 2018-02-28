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
 * This interface just contains a set of strings. These strings contain 
 * information about the game. Specifically its Author, title, version number,
 * and general 'about' information. These were placed in its own file so we 
 * can have a easy-to-find spot to change the information.
 * 
 * @author Nicholas Moss
 */
public interface Version
{
    public final String GAMETITLE = "MineSweeper";
    public final String VERSION = "v0.9";
    public final String AUTHOR = "Nicholas Moss";
    public final String ABOUT = "This is a rather trivial implementation of " + 
            "the MineSweeper game. It is built using the Model, View, Adapter" +
            " (MVA) design pattern. The Model contains the game and the logic" +
            " associated with the game. The View is implemented using Swing " + 
            "components. The adapter acts as a mediator between these two " + 
            "components.\n\nEventually, if time allows, there will be another" +
            " set of classes and another adapter to allow for machine " + 
            "learning experiementation.";
    
} // End of Verion interface.
