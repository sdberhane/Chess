/**
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class GameBoard extends JPanel {

	private Chess chess;
	private JLabel status; // current status text

	// Game constants
	public static final int BOARD_WIDTH = 600;
	public static final int BOARD_HEIGHT = 600;
	
	public static final int UNIT = BOARD_WIDTH / 8;

	public static Point selected, current, movedTo;
	public static boolean pieceSelected = false;

	public static int xDraggingDiscrep;
	public static int yDraggingDiscrep;


	/**
	 * Initializes the game board.
	 */
	public GameBoard(JLabel statusInit) {
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// Enable keyboard focus on the court area.
		// When this component has the keyboard focus, key events are handled by its key listener.
		setFocusable(true);

		status = statusInit; // initializes the status JLabel

		chess = new Chess();

		/*
		 * Listens for mouseclicks.  Updates the model, then updates the game board
		 * based off of the updated model.
		 */
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				selected = e.getPoint();
				current = e.getPoint();

				if (selected.x/UNIT <= 7 && selected.x >= 0 && selected.y/UNIT <= 7 && selected.y >= 0) {
					if (chess.getCell(selected.x/UNIT, selected.y/UNIT) != null) {
						pieceSelected = true;
						xDraggingDiscrep = selected.x - ((UNIT * ((int) (selected.x/UNIT)))); 
						yDraggingDiscrep = selected.y - ((UNIT * ((int) (selected.y/UNIT)))); 
					}
				}
				repaint();
			}



			@Override
			public void mouseReleased(MouseEvent e) {

				movedTo = e.getPoint();

				chess.playTurn(selected.x/UNIT, selected.y/UNIT, movedTo.x/UNIT, movedTo.y/UNIT);
				pieceSelected = false;

				repaint(); // repaints the game board
				updateStatus(); // updates the status JLabel


			}
		});


		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				current = e.getPoint();
				repaint();
			}

		});
	}

	/**
	 * (Re-)sets the game to its initial state.
	 */
	public void reset() {
		status.setText("Player 1's Turn");
		repaint();

		// Makes sure this component has keyboard/mouse focus
		requestFocusInWindow();
	}

	/**
	 * Updates the JLabel to reflect the current state of the game.
	 */
	private void updateStatus() {
		if (chess.getCurrentPlayer()) {

			if (chess.check(chess.getCurrentPlayer())) {
				status.setText("Player 1's Turn. Player 1 is in Check");
			} else {
				status.setText("Player 1's Turn");
			}
		} else {
			if (chess.check(chess.getCurrentPlayer())) {
				status.setText("Player 2's Turn. Player 2 is in Check");
			} else {
				status.setText("Player 2's Turn");
			}
		}



		int winner = chess.checkWinner();
		if (winner == 1) {
			status.setText("Player 1 wins!!!");
		} else if (winner == 2) {
			status.setText("Player 2 wins!!!");
		} else if (winner == 3) {
			status.setText("It's a tie.");
		}

	}

	/**
	 * Draws the game board.
	 */
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Fill in squares
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if ((r + c) % 2 == 0) {
					g.setColor(new Color(255, 255, 204));
				}
				else {
					g.setColor(new Color (0, 102, 0));
				}
				g.fillRect(c*UNIT, r*UNIT, UNIT, UNIT);
			}
		}

		g.setColor(Color.black);

		// Vertical Lines
//		g.drawLine(BOARD_WIDTH/8, 0, BOARD_WIDTH/8, BOARD_HEIGHT);
//		g.drawLine((2*BOARD_WIDTH)/8, 0, (2*BOARD_WIDTH/8), BOARD_HEIGHT);
//		g.drawLine((3*BOARD_WIDTH)/8, 0, (3*BOARD_WIDTH/8), BOARD_HEIGHT);
//		g.drawLine((4*BOARD_WIDTH)/8, 0, (4*BOARD_WIDTH/8), BOARD_HEIGHT);
//		g.drawLine((5*BOARD_WIDTH)/8, 0, (5*BOARD_WIDTH/8), BOARD_HEIGHT);
//		g.drawLine((6*BOARD_WIDTH)/8, 0, (6*BOARD_WIDTH/8), BOARD_HEIGHT);
//		g.drawLine((7*BOARD_WIDTH)/8, 0, (7*BOARD_WIDTH/8), BOARD_HEIGHT);
//
//		// Horizontal Lines 
//		g.drawLine(0, BOARD_HEIGHT/8, BOARD_WIDTH, BOARD_HEIGHT/8);
//		g.drawLine(0, (2*BOARD_HEIGHT/8), BOARD_WIDTH, (2*BOARD_HEIGHT/8));
//		g.drawLine(0, (3*BOARD_HEIGHT/8), BOARD_WIDTH, (3*BOARD_HEIGHT/8));
//		g.drawLine(0, (4*BOARD_HEIGHT/8), BOARD_WIDTH, (4*BOARD_HEIGHT/8));
//		g.drawLine(0, (5*BOARD_HEIGHT/8), BOARD_WIDTH, (5*BOARD_HEIGHT/8));
//		g.drawLine(0, (6*BOARD_HEIGHT/8), BOARD_WIDTH, (6*BOARD_HEIGHT/8));
//		g.drawLine(0, (7*BOARD_HEIGHT/8), BOARD_WIDTH, (7*BOARD_HEIGHT/8));


		// Drawing Pieces
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (chess.getCell(j, i) != null) {
					if (!(pieceSelected && selected.x/UNIT == j && selected.y/UNIT == i)) {
						g.drawImage(chess.getCell(j, i).getImage(), (j * UNIT) + 10, (i * UNIT) + 10, UNIT - 20, UNIT - 20, this);
					} 
				}
			}
		}

		//chess.getCell(selected.x/100, selected.y/100).getImage().
		if (pieceSelected) {
			g.drawImage(chess.getCell(selected.x/UNIT, selected.y/UNIT).getImage(), current.x - xDraggingDiscrep, current.y - yDraggingDiscrep, UNIT - 20, UNIT - 20, this);
		}
	}

	/**
	 * Returns the size of the game board.
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
	}
}