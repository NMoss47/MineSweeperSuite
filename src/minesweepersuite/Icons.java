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

import java.net.URL;
import javax.swing.ImageIcon;

/**
 * This class is used to group all the image assets together for use in the 
 * game. The first section are final strings to the relative path of the assets.
 * These cannot be changed during runtime. We could even push this out into an 
 * interface so we can swap out "tile sets" for a different look and feel.
 * 
 * The second section contains ImageIcon objects. We can access them in the rest
 *  of the code with statements like the following:
 *      button.setDisabledIcon( icon.nodeIcon );
 *      button.setIcon( icon.flagIcon );
 *      JButton button = new JButton( icon.revealedNodeIcon );
 * 
 * @author Nicholas Moss
 */
public final class Icons {
    
    // File locations
    // Root directory
    private final String ROOT_DIR = "assets/";
    
    // Two Main Icons
    private final String NODE = "basictile.png";
    private final String REVEALED_NODE = "basictile_revealed.png";
    // Mine Icons
    private final String MINE = "basictile_mine.png";
    private final String MINE_RED = "basictile_mine_red.png";
    // Flag Icons
    private final String FLAG = "basictile_flag.png";
    private final String FLAG_QUESTION = "basictile_flag_question.png";
    private final String FLAG_RED = "basictile_flag_red.png";
    // Number Icons
    private final String PROX_ONE = "basictile_revealed_one.png";
    private final String PROX_TWO = "basictile_revealed_two.png";
    private final String PROX_THREE = "basictile_revealed_three.png";
    private final String PROX_FOUR = "basictile_revealed_four.png";
    private final String PROX_FIVE = "basictile_revealed_five.png";
    private final String PROX_SIX = "basictile_revealed_six.png";
    private final String PROX_SEVEN = "basictile_revealed_seven.png";
    private final String PROX_EIGHT = "basictile_revealed_eight.png";
   
    // Static implementations
    // Main Icons
    public final ImageIcon NODE_ICON = getIcon( ROOT_DIR + NODE );
    public final ImageIcon REVEALED_NODE_ICON = getIcon( 
                                                ROOT_DIR + REVEALED_NODE );
    // Mine Icons
    public final ImageIcon MINE_ICON = getIcon( ROOT_DIR + MINE );
    public final ImageIcon MINE_RED_ICON = getIcon( ROOT_DIR + MINE_RED );
    // Flag Icons
    public final ImageIcon FLAG_ICON = getIcon( ROOT_DIR + FLAG );
    public final ImageIcon FLAG_QUESTION_ICON = getIcon( 
                                                ROOT_DIR + FLAG_QUESTION );
    public final ImageIcon FLAG_RED_ICON = getIcon( ROOT_DIR + FLAG_RED );
    // Number Icons
    public final ImageIcon PROX_ONE_ICON = getIcon( ROOT_DIR + PROX_ONE );
    public final ImageIcon PROX_TWO_ICON = getIcon( ROOT_DIR + PROX_TWO );
    public final ImageIcon PROX_THREE_ICON = getIcon( ROOT_DIR + PROX_THREE );
    public final ImageIcon PROX_FOUR_ICON = getIcon( ROOT_DIR + PROX_FOUR );
    public final ImageIcon PROX_FIVE_ICON = getIcon( ROOT_DIR + PROX_FIVE );
    public final ImageIcon PROX_SIX_ICON = getIcon( ROOT_DIR + PROX_SIX );
    public final ImageIcon PROX_SEVEN_ICON = getIcon( ROOT_DIR + PROX_SEVEN );
    public final ImageIcon PROX_EIGHT_ICON = getIcon( ROOT_DIR + PROX_EIGHT );
    
    /**
     * This function is used to load the images from a class loader. This is 
     * done because the file paths of the images wont really work when we 
     * compile this as a JAR.
     * 
     * @param loc The file name / path of the image.
     * @return The specified ImageIcon.
     */
    public ImageIcon getIcon( String loc ) {
        // Get that class loader.
        ClassLoader loader = this.getClass().getClassLoader();
        // Generate the URL of the image from the class loader.
        URL imageURL = loader.getResource( loc );
        // Return the image based off of its URL.
        return new ImageIcon(imageURL);
        
    } // End of getIcon( )

} // End of Icons class.
