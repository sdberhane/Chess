import java.awt.*;


public abstract class Piece {
	
	private boolean isWhite;
	private boolean hasMoved;
	
	public Piece(boolean isWhite, boolean hasMoved) {
		this.isWhite = isWhite;
		this.hasMoved = hasMoved;
	}

	public boolean isWhite() {
		return isWhite;
	}

	public void setWhite(boolean isWhite) {
		this.isWhite = isWhite;
	}

	public boolean hasMoved() {
		return hasMoved;
	}

	public void setMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}
	
	
	public abstract boolean canMove (Piece[][] board, int initRow, int initCol, int finalRow, int finalCol);
	
	public abstract Image getImage();
	
	public abstract String toString();
}
