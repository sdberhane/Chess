import java.awt.Image;

import javax.swing.ImageIcon;

public class Pawn extends Piece {

	private static final Image WHITE_PAWN_IMAGE = new ImageIcon("Images/whitePawn.png").getImage();
	private static final Image BLACK_PAWN_IMAGE = new ImageIcon("Images/blackPawn.png").getImage();


	public Pawn(boolean isWhite, boolean hasMoved) {
		super(isWhite, hasMoved);
	}

	@Override
	public boolean canMove(Piece[][] board, int initRow, int initCol, int finalRow, int finalCol) {
		int dx = finalCol - initCol;
		int dy = finalRow - initRow;


		// Checks if pawn is going in wrong direction
		if (this.isWhite()) {
			if(finalRow > initRow) {
				return false;
			}
		} else {
			if (finalRow < initRow) {
				return false;
			}
		}

		// In the instance that it is not attempting to take a piece
		if (dx  == 0) {
			// Moving 2 spaces
			if (Math.abs(dy) == 2) {
				// Can't move if it's not in starting position
				if(this.hasMoved()) {
					return false;
				}

				// Can't move two spaces if there is a piece in the path
				int dir = Math.abs(dy) / dy;

				if (board[initRow + dir][initCol] != null || board[initRow + (dir * 2)][initCol] != null) {
					return false;
				}

				// 1 square up
			} else if (Math.abs(dy) == 1) {
				int dir = Math.abs(dy) / dy;
				if (board[initRow + dir][finalCol] != null) {
					return false;
				}	
				// Any move that moves up more than 2 squares
				else {
					//return false;
				}
			} else {
				return false;
			}
			// When movement is not just up
		} else {
			// Cannot attack empty square
			if (board[finalRow][finalCol] == null) {
				return false;
			}
			// Has to be one diagonal square move
			if (Math.abs(dy) != 1 || Math.abs(dx) != 1) {
				return false;
			}

		}

		return true;
	}


	@Override
	public Image getImage() {
		if (this.isWhite()) {
			return WHITE_PAWN_IMAGE;
		}
		else {
			return BLACK_PAWN_IMAGE;
		}
	}

	@Override
	public String toString() {
		return "Pawn";
	}


}
