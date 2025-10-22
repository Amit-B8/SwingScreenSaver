import javax.swing.*;


/**
 * The Driver class is the start point for the ScreenSaver program.
 * This class will create a window and add a ScreenSaver panel that displays it.
 * The ScreenSaver will continuously draw random lines on the screen and resets after a set number of lines.
 */

// Driver class to test the screen saver program
public class Driver{
    /**
     * The main method runs the ScreenSaver program.
     * It creates a JFrame window and adds a ScreenSaver panel that sets the window size and makes the program visible.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        // Create a new JFrame frame object that represents the application window for the Screen Saver
        JFrame frame = new JFrame("Screen Saver with Timer and User Input");
        // Sets default close operation so the program exits when X is clicked
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Create a new ScreenSaver object or panel that will draw random lines
        ScreenSaver saver = new ScreenSaver();
        // This is the frames size that can be adjusted to whatever size
        frame.setSize(1000, 1000);
        // Now make the screen saver added and visible. It adds the ScreenSaver panel to the JFrame so it can be displayed
        frame.add(saver);
        // Will make the JFrame and anything inside screen visible
        frame.setVisible(true);

        // If there was multiple ScreenSaver objects each would maintain its own line counter, timer, and Random generator.
        // JFrame and ScreenSaver are long-lived objects while
        // Graphics object only exists temporarily by Swing while drawing each frame.
    }
}
