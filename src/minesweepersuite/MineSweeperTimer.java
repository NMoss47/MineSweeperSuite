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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import javax.swing.Timer;

/**
 * This class is used to create a very basic swing timer for the MineSweeper 
 * game.
 * 
 * @author Nicholas Moss
 */
public class MineSweeperTimer implements TimerAPI,
                                         ActionListener {
    
    // The swing timer to use.
    private final Timer timer;
    
    // Total elapsed time of the game
    private long runningTime;
    // Duration object for hour/minute/second formatting
    private Duration duration;
    // Formatted times.
    private long hours;
    private long minutes;
    private long seconds;
    // The output string format of the time.
    private final static String FORMAT = "%01d:%02d:%02d";
    
    // Time calculations.
    private final long startTick;       // Initial starting time.
    private long deltaTick;             // Incremental paused time.
    private long totalPauseTime;        // Total amount of paused time.
    
    // Reference to external JLabel
    private final MineSweeperAdapter adapter;
    private boolean paused;
    
    /**
     * Default constructor. Just initializes values. Call start() to start the 
     * timer.
     * 
     * @param adapter The JLabel object to periodically update.
     */
    public MineSweeperTimer( MineSweeperAdapter adapter ) {
        // Get the initial time.
        startTick = System.currentTimeMillis( );
        // Set a pause time value
        totalPauseTime = 0;
        // Set the reference to the GUI label
        this.adapter = adapter;
        // Create the timer and fire every 100ms
        timer = new Timer( 100, this );
        
    } // End of MineSweeperTimer( )
    
    /**
     * An implementation of actionPerformed. Calculates the elapsed game time 
     * from milliseconds and updates the JLabel text to reflect the current game
     *  time.
     * 
     * @param action An action event object.
     */
    @Override
    public void actionPerformed( ActionEvent action ) {
        // The elapsed time is currentTime - startTick - pausedTimeDelta
        // Measure in millis
        runningTime = System.currentTimeMillis() - startTick - totalPauseTime;
        duration  = Duration.ofMillis(runningTime);
        // Format for hours
        hours = duration.toHours();
        duration = duration.minusHours( hours );
        // Format for minutes
        minutes = duration.toMinutes();
        duration = duration.minusMinutes( minutes );
        // Format for seconds
        seconds = duration.toMillis()/1000;

        // Upodate the view's timer label.
        updateTimer( String.format("%01d:%02d:%02d", hours, minutes, seconds) );
        
    } // End of actionPerformed( )
    
    /**
     * This function is used to inform the adapter of the current time value as 
     * a formatted string. 
     * 
     * @param timeValue A formatted string containing the elapsed game time.
     */
    @Override
    public void updateTimer( String timeValue ) {
        adapter.updateTimer( timeValue );
        
    } // End of updateTimer( )
    
    /**
     * This function is used to unpause/resume the timer.
     */
    @Override
    public void resumeGame( ) {
        deltaTick = System.currentTimeMillis() - deltaTick;
        totalPauseTime += deltaTick;
        paused = false;
        timer.start( );
        
    } // End of resume( )
    
    /**
     * This function starts the timer.
     */
    public void start( ) {
        paused = false;
        timer.start();
        
    } // End of start( )
    
    /**
     * This function is used to pause the timer.
     */
    @Override
    public void pauseGame( ) {
        deltaTick = System.currentTimeMillis( );
        paused = true;
        timer.stop( );
        
    } // End of pause( )
    
    /**
     * This function is used to stop the timer.
     */
    @Override
    public void stopGame( ) {
        timer.stop();
        
    } // End of stop( )
    
    /**
     * This function is used to determine if the timer is paused or not.
     * 
     * @return True if the timer is paused/in a stopped state. False otherwise.
     */
    @Override
    public boolean isPaused( ) {
        return paused;
        
    } // End of getPausedStatus( )
    
    /**
     * This function is used to get the elapsed game time. It should only be 
     * called after the timer has stopped.
     * 
     * @return A string of the elapsed game time. It has a format of 
     * hours:minutes:seconds.
     */
    public String getTimeAsString( ) {
        return String.format( FORMAT, hours, minutes, seconds );
        
    } // End of getTime( )
    
    /**
     * This function simply returns the current running time in Long.
     * 
     * @return The current elapsed game time in Long.
     */
    public long getTime( ) { 
        return runningTime;
        
    } // End of getTime( )
    
    /**
     * This function is used to convert a long time variable into a formatted 
     * string in hours:minutes:seconds. Eventually there will be other 
     * functions to allow for other formats.
     * 
     * @param runningTime The time (in long) to convert.
     * @return A formatted string in hours:minutes:seconds.
     */
    public static String formatTime( long runningTime ) {
        Duration dur  = Duration.ofMillis(runningTime);
        // Format for hours
        long hrs = dur.toHours();
        dur = dur.minusHours( hrs );
        // Format for minutes
        long mins = dur.toMinutes();
        dur = dur.minusMinutes( mins );
        // Format for seconds
        long secs = dur.toMillis()/1000;

        // Return a formatted string.
        return String.format( FORMAT , hrs, mins, secs);
        
    } // End of formatTime( )
    
} // End of MineSweeperTimer class.
