import java.awt.Image;

import javax.swing.ImageIcon;

public class Knight extends Piece{

	private static final Image WHITE_KNIGHT_IMAGE = new ImageIcon("Images/whiteKnight.png").getImage();
	private static final Image BLACK_KNIGHT_IMAGE = new ImageIcon("Images/blackKnight.png").getImage();

	public Knight(boolean isWhite, boolean hasMoved) {
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
		
		// Checking that the knight moves in L motion
		int dx = Math.abs(finalCol - initCol);
		int dy = Math.abs(finalRow - initRow);

		boolean valid = dx * dy == 2;
		
		if (valid) {
			this.setMoved(true);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Image getImage() {
		if (this.isWhite()) {
			return WHITE_KNIGHT_IMAGE;
		}
		else {
			return BLACK_KNIGHT_IMAGE;
		}
	}

	@Override
	public String toString() {
		return "Kinght";
	}
	
	

}
