// This imports JPanel which is a Swing container that can display graphics and components
import javax.swing.JPanel;
// This will import JTextField which is a text input box component used for user input
// JText field allows user input
import javax.swing.JTextField;
// Imports Timer from javax.swing which repeatedly triggers an event at fixed time intervals like 1 second
import javax.swing.Timer;
// Imports BorderLayout a layout manager that places components in regions like NORTH
import java.awt.BorderLayout;
// // This imports Graphics which is the class that provides methods for drawing shapes and text on a component
import java.awt.Graphics;
// Imports ActionEvent which represents an event such as a button click or a timer tick
import java.awt.event.ActionEvent;
// Imports ActionListener which is an interface that listens for and handles ActionEvents
import java.awt.event.ActionListener;
// Imports Random which is a utility class for generating random numbers
import java.util.Random;


/**
 * The ScreenSaver class is a JPanel that simulates a screen saver.
 * It uses a Swing Timer to keep drawing random lines on the panel every second.
 * After a set number of lines, the screen will clear and starts over.
 * The user can type a number into the text box and press Enter to change
 * how many lines are drawn before the screen resets. If the input is invalid,
 * it goes back to the last valid value.
 * This class shows how to use Swing components like JPanel, JTextField, and Timer.
 * It also does custom drawing with paintComponent and Graphics as well as Handle events with ActionListener.
 *
 *
 * @author Amit Boodhoo
 * @since 2025-09-25
 */

// Project had three parts that are there through commit history
// - Exercise 27.18:
// Originally, the program continuously called repaint() at the end of paintComponent(),
// which causes it to draw random lines extremely fast almost instantly filling the screen.
// It would then clear and start over but really fast. Calling repaint() too fast
// can slow down the program and make the screen lag or freeze.
//
// - Exercise 27.19:
// This part used javax.swing.Timer that triggers repaint() every second
// instead of continuously. This makes the animation smoother and more efficient.
// The class implements ActionListener, and the timer calls actionPerformed()
// once per second to repaint the panel.

// - Exercise 27.20:
// 27.20 was this current section. It adds a JTextField so that the user can type in how many lines
// to draw before the screen clears. The input is handled by an inner class (TextFieldEventHandler)
// that checks if the input is valid so only digits. If it's valid it will update the number of lines.
// If input not valid it goes to the previous number of lines value


// JPanel is a Swing component that acts as a drawing surface.
// This class extends JPanel meaning inherits its properties and behaviors so it can override paintComponent()
// and draw custom graphics directly on the panel.
// it also implements ActionListener so this class can respond to events like Timer
// The actionPerformed() method must be defined since it is required by ActionListener.
// In short JPanel is drawing surface and ActionListener responds to Timer event
public class ScreenSaver extends JPanel implements ActionListener {

// Variables private so these fields are only accessible within this class ScreenSaver
// Keeping them private is for encapsulation and prevents outside classes from modifying them directly
// Could be public but that would risk unwanted changes

    /** Keeps track of how many lines have been drawn so far. */
    private int linesCount;
    /** Generate random coordinates for drawing lines. */
    private Random randGenerator;
    /** Swing timer that triggers the screen to repaint periodically. */
    Timer timer;
    /** Default or the user input specified number of lines before clearing the screen. */
    private int userLinesNumber = 100;
    /** Text box field where the user can input the number of lines. */
    private JTextField userInputLines;

    /**
     * Creates a new ScreenSaver panel with a timer and user input field.
     * Initializes the random generator, line counter, and sets up the layout.
     */

    public ScreenSaver() {
        // Initializes the count of lines to be 0
        linesCount = 0;
        // This is a random generator
        randGenerator = new Random();
        // Creates a timer that calls actionPerformed every 1000 ms or 1 second.
        // The 'this' keyword passes the current ScreenSaver object as the ActionListener.
        // If value changed from 1000 to 100, the lines would appear much faster.
        timer = new Timer(1000, this);
        // Starts the timer when an object created
        timer.start();
        // Text box field for user input with the default being 100
        userInputLines = new JTextField("CLick here to change number of lines and then click enter. Default will be 100 lines ", 5);
        // This is for when the user presses enter to change the value to what they wanted
        userInputLines.addActionListener(new TextFieldEventHandler());
        // Sets the layout
        setLayout(new BorderLayout());
        // Adds the user input lines text box to the top or North side of the screen
        add(userInputLines, BorderLayout.NORTH);
    }

    /**
     * Called each time the timer goes which is once per second.
     * It then requests the panel to repaint itself.
     *
     * @param e the event generated by the timer
     */
    // The actionPerformed method is called automatically every time the Timer ticks
    // The parameter 'e' is an ActionEvent object that contains
    // information about the event like source, time, command
    // ActionEvent comes from the java.awt.event package and
    // represents any action event like pressing Enter or a timer firing
    // Called by Timer every second. Calls repaint to ask
    // panel to redraw since it now implements ActionListener
    public void actionPerformed(ActionEvent e) {
        // This will redraw the screen
        repaint();
    }

    /**
     * Paints one random line on the panel and resets after reaching the user input number of lines
     * Overrides the JPanel's paintComponent method to draw random lines on the screen.
     *
     * @param g the Graphics object used for drawing
     */
    // Inherits and overrides paintComponent() from javax.swing.JPanel compiler and checks for correct method match
    // Technically does not need Override but helps the compiler verify method correctness automatically
    @Override
    // Graphics is an abstract class because it defines what drawing operations are possible but not how they are actually done
    public void paintComponent(Graphics g) {
        // If the lines is equal to 0 then clear the screen. Happens when linesCount is greater than or equal to userLinesNumber or 100
        // This if statement needed else it will only draw one line at a time and clear
        // Clears the panel when a new cycle starts
        // super.paintComponent(g) is necessary since without it,
        // old drawings would never be erased, and the screen would fill up
        // with overlapping lines forever. Removing this call would create a continuous line buildup.
        if (linesCount == 0) {
            // This will clear the background after every cycle
            super.paintComponent(g);
        }

        // Pick random line coordinates
        // These are the start points
        int x1 = randGenerator.nextInt(getWidth());
        int y1 = randGenerator.nextInt(getHeight());
        // These are the end points
        int x2 = randGenerator.nextInt(getWidth());
        int y2 = randGenerator.nextInt(getHeight());

        // Draws the line only one
        g.drawLine(x1, y1, x2, y2);

        // Count how many lines drawn so far
        linesCount += 1;

        // This will reset the paint count back to 0 if the line count goes over default value or user input value.
        // When reaching the limit (userLinesNumber), it will reset to clear screen next repaint
        if (linesCount >= userLinesNumber) {
            // Resets the counter to 0
            linesCount = 0;
        }

    }

    /**
     * Inner class that handles text field input from the user.
     * Updates the number of lines to draw if the input is valid.
     */
    // This is the inner class to handle text field input form the user
    // It could be anonymous since ActionListener has only one method and this handler is used only once
    // Anonymous inner classes have no name so also wouldn't need a constructor for it
    // TextFieldEventHandler handles user input from the JTextField and
    // ActionListener is the interface that lets it listen and respond when Enter is pressed
    // Private because it only needs to be used inside ScreenSaver and can directly use its fields
    // If it were Public it would need a constructor and pass those values in manually so more steps as shown in class
    private class TextFieldEventHandler implements ActionListener {
        /**
         * Called when the user presses Enter in the text field.
         * Checks the input and then updates the line count.
         *
         * @param e the event triggered by pressing Enter
         */
        // No return type so void but will handle text input from user
        public void actionPerformed(ActionEvent e) {
            // Gets the user input
            String inputLinesText = userInputLines.getText();

            // Check that the text is not empty
            if (!inputLinesText.isEmpty()) {
                // Creates a boolean variable that assumes that the text contains only digits
                boolean onlyDigits = true;
                // Then is will loop through each character of the text
                for (int i = 0; i < inputLinesText.length(); i++) {
                    // If there is a character that is not a digit it will mark it false
                    if (!Character.isDigit(inputLinesText.charAt(i))) {
                        // This will set the boolean to false if there is not only numbers in the user input text
                        onlyDigits = false;
                    }
                }
                // If all characters are digits
                if (onlyDigits) {
                    // It will convert the text into a number if only digits is true
                    int userValue = Integer.parseInt(inputLinesText);
                    // If the number is greater than 0
                    if (userValue > 0) {
                        // Update how many lines to draw
                        userLinesNumber = userValue;
                        // Restart the counter so drawing begins fresh
                        linesCount = 0;
                    }
                }
                // Checks if it's not all digits
                else
                {
                    // If that is the case then it wil go back and revert to the old value it had
                    userInputLines.setText("" + userLinesNumber);
                }
            }
        }
    }

}
