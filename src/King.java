import java.awt.Image;

import javax.swing.ImageIcon;

public class King extends Piece {

	private static final Image WHITE_KING_IMAGE = new ImageIcon("Images/whiteKing.png").getImage();
	private static final Image BLACK_KING_IMAGE = new ImageIcon("Images/blackKing.png").getImage();

	public King(boolean isWhite, boolean hasMoved) {
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

		// Checking that the king can only move one square at a time
		int dx = Math.abs(finalCol - initCol);
		int dy = Math.abs(finalRow - initRow);

		if (((dx + dy) > 2) || (dx == 2 && dy == 0) || (dx == 0 && dy == 2) ) {
			// Checking for castling
			if (dx == 2 && dy == 0) {
				// Checks if there are pieces in between
				int dir = (finalCol - initCol) / dx;

				if (board[finalRow][initCol + dir] != null && board[finalRow][initCol + 2 * dir] != null) {
					return false;
				}
				
				// Checks if either king or specified rook has moved
				if (finalCol > initCol) {
					if (this.hasMoved() || board[finalRow][initCol + 3].hasMoved()) {
						return false;
					}
				} else {
					if (this.hasMoved() || board[finalRow][initCol - 4].hasMoved()) {
						return false;
					}
				}
				
				// Returns true if castle is valid
				this.setMoved(true);
				return true;
			}
			return false;
		}

		return true;
	}

	

	@Override
	public Image getImage() {
		if (this.isWhite()) {
			return WHITE_KING_IMAGE;
		}
		else {
			return BLACK_KING_IMAGE;
		}
	}

	@Override
	public String toString() {
		return "King";
	}


}
