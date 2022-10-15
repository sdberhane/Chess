import java.awt.Image;

import javax.swing.ImageIcon;

public class Bishop extends Piece{

	private static final Image WHITE_BISHOP_IMAGE = new ImageIcon("Images/whiteBishop.png").getImage();
	private static final Image BLACK_BISHOP_IMAGE = new ImageIcon("Images/blackBishop.png").getImage();

	public Bishop(boolean isWhite, boolean hasMoved) {
		super(isWhite, hasMoved);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canMove(Piece[][] board, int initRow, int initCol, int finalRow, int finalCol) {

		// Can't move to place with piece of same color
		if (board[finalRow][finalCol] != null) {
			if (board[finalRow][finalCol].isWhite() == this.isWhite()) {
				return false;
			}
		}

		// Checking that the bishop van only move diagonally
		int dx = (finalCol - initCol);
		int dy = (finalRow - initRow);

		if (Math.abs(dy) != Math.abs(dx)) {
			return false;
		}
		// Checking that there is no piece in its path
		int rowDirection, colDirection;
		
		if (dx > 0) {
			colDirection = 1;
		} else {
			colDirection = -1;
		}

		if (dy > 0) {
			rowDirection = 1;
		} else {
			rowDirection = -1;
		}

		
		int steps = 1;
		for (int i = initCol + colDirection; i != finalCol; i += colDirection) {
			if (board[initRow + (rowDirection * steps)][i] != null) {
				return false;
			}
			
			steps++;
		}

		this.setMoved(true);

		return true;
	}

	@Override
	public Image getImage() {
		if (this.isWhite()) {
			return WHITE_BISHOP_IMAGE;
		}
		else {
			return BLACK_BISHOP_IMAGE;
		}
	}
	
	@Override
	public String toString() {
		return "Bishop";
	}
	

}
