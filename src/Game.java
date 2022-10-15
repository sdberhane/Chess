import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 */
public class Game implements Runnable {
    public void run() {
        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Chess");
        frame.setLocation(800, 800);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        // Game board
        final GameBoard board = new GameBoard(status);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we define it as an
        // anonymous inner class that is an instance of ActionListener with its actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be called.
        final JButton instruct = new JButton("Instructions");
        instruct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // board.reset();
            	 final JFrame instructions = new JFrame("Instructions");
            	 instructions.setLocation(400, 400);
            	 
            	 final Instructions inst = new Instructions();
            	 instructions.add(inst, BorderLayout.CENTER);
            	 
            	 instructions.pack();
                 instructions.setVisible(true);

            }
        });
        control_panel.add(instruct);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.reset();
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}
