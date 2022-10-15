import java.awt.Image;

import javax.swing.ImageIcon;

public class Queen extends Piece {

	private static final Image WHITE_QUEEN_IMAGE = new ImageIcon("Images/whiteQueen.png").getImage();
	private static final Image BLACK_QUEEN_IMAGE = new ImageIcon("Images/blackQueen.png").getImage();

	public Queen(boolean isWhite, boolean hasMoved) {
		super(isWhite, hasMoved);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canMove(Piece[][] board, int initRow, int initCol, int finalRow, int finalCol) {

		
		boolean rookMove = new Rook(this.isWhite(), this.hasMoved()).canMove(board, initRow, initCol, finalRow, finalCol);
		boolean bishopMove = new Bishop(this.isWhite(), this.hasMoved()).canMove(board, initRow, initCol, finalRow, finalCol);
		
		boolean valid = rookMove || bishopMove;
		
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
			return WHITE_QUEEN_IMAGE;
		}
		else {
			return BLACK_QUEEN_IMAGE;
		}
	}
	
	@Override
	public String toString() {
		return "Queen";
	}
	

}
