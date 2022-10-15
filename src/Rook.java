import java.awt.Image;

import javax.swing.ImageIcon;

public class Rook extends Piece {

	private static final Image WHITE_ROOK_IMAGE = new ImageIcon("Images/whiteRook.png").getImage();
	private static final Image BLACK_ROOK_IMAGE = new ImageIcon("Images/blackRook.png").getImage();

	public Rook(boolean isWhite, boolean hasMoved) {
		super(isWhite, hasMoved);
	}

	@Override
	public boolean canMove (Piece[][] board, int initRow, int initCol, int finalRow, int finalCol) {

		// Can't move to place with piece of same color
		if (board[finalRow][finalCol] != null) {
			if (board[finalRow][finalCol].isWhite() == this.isWhite()) {
				return false;
			}
		}
		
		// Checking that the rook can only move up and down or side to side
		int dx = (finalCol - initCol);
		int dy = (finalRow - initRow);

		if (dx != 0 && dy != 0) {
			return false;
		}

		// Checking that there is no piece in its path
		
		// Up
		if (dx == 0 && dy < 0) {
			for (int i = initRow - 1; i > finalRow; i--) {
				if (board[i][initCol] != null) {
					return false;
				}
			}
		}
		// Down
		else if (dx == 0 && dy > 0) {
			for (int i = initRow + 1; i < finalRow; i++) {
				if (board[i][initCol] != null) {
					return false;
				}
			}
		}
		// Left
		else if (dy == 0 && dx < 0){
			for (int i = initCol - 1; i > finalCol; i--) {
				if (board[initRow][i] != null) {
					return false;
				}
			}
		}
		// Right
		else {
			for (int i = initCol + 1; i < finalCol; i++) {
				if (board[initRow][i] != null) {
					return false;
				}
			}
		}


		return true;
	}

	@Override
	public Image getImage() {
		if (this.isWhite()) {
			return WHITE_ROOK_IMAGE;
		}
		else {
			return BLACK_ROOK_IMAGE;
		}
	}
	
	@Override
	public String toString() {
		return "Rook";
	}
	
}
