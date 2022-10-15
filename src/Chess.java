
import java.util.LinkedList;

public class Chess {

	private Piece[][] board;
	private boolean gameOver;
	private boolean isWhiteTurn;

	private LinkedList<Boolean> pawnMoves;



	public Chess() {
		reset();

		// White Pawns
		for (int i = 0; i < 8; i++) {
			board[6][i] = new Pawn(true, false);
		}

		// Black Pawns
		for (int i = 0; i < 8; i++) {
			board[1][i] = new Pawn(false, false);
		}

		// Rooks
		board[0][0] = new Rook(false, false);
		board[0][7] = new Rook(false, false);

		board[7][0] = new Rook(true, false);
		board[7][7] = new Rook(true, false);

		// Knights
		board[0][1] = new Knight(false, false);
		board[0][6] = new Knight(false, false);

		board[7][1] = new Knight(true, false);
		board[7][6] = new Knight(true, false);

		// Bishops
		board[0][2] = new Bishop(false, false);
		board[0][5] = new Bishop(false, false);

		board[7][2] = new Bishop(true, false);
		board[7][5] = new Bishop(true, false);

		// Queens
		board[0][3] = new Queen(false, false);
		board[7][3] = new Queen(true, false);

		// Kings
		board[0][4] = new King(false, false);
		board[7][4] = new King(true, false);

		// This list will be used to track whether or not the last made move was a 2 space pawn move
		// making en passant valid
		pawnMoves = new LinkedList<Boolean>();

	}

	public void reset() {
		gameOver = false;
		isWhiteTurn = true;
		board = new Piece[8][8];



	}

	public boolean playTurn(int initCol, int initRow, int finalCol, int finalRow) {
		if (board[initRow][initCol] == null || gameOver) {
			return false;
		}

		// Renders move invalid if played by wrong side
		if (board[initRow][initCol].isWhite() != isWhiteTurn) {
			return false;
		}

		// Checks if move is valid for specified piece
		if (!board[initRow][initCol].canMove(board, initRow, initCol, finalRow, finalCol)) {

			// Catches if it's an en passant move
			if (board[initRow][initCol].toString().equals("Pawn") && Math.abs(finalRow - initRow) == 1 && Math.abs(finalCol - initCol) == 1 && board[finalRow][finalCol] == null) {
				if (board[initRow][finalCol] != null) {
					if (pawnMoves.getLast() && board[initRow][finalCol].toString().equals("Pawn")) {
						enPassant(initCol, initRow, finalCol, finalRow);
						if (check(isWhiteTurn)) {
							undoEnPassant(initCol, initRow, finalCol, finalRow);

							return false;
						}
						isWhiteTurn = !isWhiteTurn;
					} else {

						return false;
					}
				} 
			}
			return false;
		}

		// Checks if is a castle
		if(board[initRow][initCol].toString().equals("King") && Math.abs(finalCol - initCol) == 2) {
			castle(board, isWhiteTurn, finalCol > initCol);
			if (check(isWhiteTurn)) {
				uncastle(board, isWhiteTurn, finalCol > initCol);

				return false;
			} else {
				pawnMoves.add(false);
			}
			// Makes sure all requirements are met for en passant
		} else {

			// Altering to see what board would look like after to possible move
			Piece start = board[initRow][initCol];
			Piece finish = board[finalRow][finalCol];

			board[finalRow][finalCol] = start; 
			board[initRow][initCol] = null;

			// Piece cannot move if their king is in check and the move does not stop that
			if (check(isWhiteTurn)) {
				board[finalRow][finalCol] = finish; 
				board[initRow][initCol] = start;
				return false;
			}

			// Checks for en passant availability
			if (board[finalRow][finalCol].toString().equals("Pawn") && Math.abs(finalRow - initRow) == 2) {
				pawnMoves.add(true);
			} else {
				pawnMoves.add(false);
			}
		}

		if (!gameOver) {
			isWhiteTurn = !isWhiteTurn;
		} else {
			return false;
		}


		board[finalRow][finalCol].setMoved(true);
		return true;
	}

	// Returns 0 if game is ongoing, 1 if White wins, 2 if Black wins
	public int checkWinner() {
		if (checkmate(false)) {
			isWhiteTurn = !isWhiteTurn;
			gameOver = true;
			return 1;
		} else if (checkmate(true)) {
			isWhiteTurn = !isWhiteTurn;
			gameOver = true;
			return 2;
		}


		return 0;
	}

	public boolean getCurrentPlayer() {
		return isWhiteTurn;
	}

	public Piece getCell(int c, int r) {
		return board[r][c];
	}

	public void castle (Piece[][] board, boolean white, boolean kingSide) {
		if (kingSide && white) { 
			Piece tempKing = board[7][4];
			Piece tempRook = board[7][7];

			board[7][6] = tempKing;
			board[7][5] = tempRook;
			board[7][4] = null;
			board[7][7] = null;
		} else if (!kingSide && white) {
			Piece tempKing = board[7][4];
			Piece tempRook = board[7][0];

			board[7][2] = tempKing;
			board[7][3] = tempRook;
			board[7][4] = null;
			board[7][0] = null;
		} else if (kingSide && !white) {
			Piece tempKing = board[0][4];
			Piece tempRook = board[0][7];

			board[0][6] = tempKing;
			board[0][5] = tempRook;
			board[0][4] = null;
			board[0][7] = null;
		} else {
			Piece tempKing = board[0][4];
			Piece tempRook = board[0][0];

			board[0][2] = tempKing;
			board[0][3] = tempRook;
			board[0][4] = null;
			board[0][0] = null;
		}
	}

	public void uncastle (Piece[][] board, boolean white, boolean kingSide) {
		if (kingSide && white) { 
			Piece tempKing = board[7][6];
			Piece tempRook = board[7][5];

			board[7][4] = tempKing;
			board[7][7] = tempRook;
			board[7][6] = null;
			board[7][5] = null;
		} else if (!kingSide && white) {
			Piece tempKing = board[7][2];
			Piece tempRook = board[7][3];

			board[7][4] = tempKing;
			board[7][0] = tempRook;
			board[7][2] = null;
			board[7][3] = null;
		} else if (kingSide && !white) {
			Piece tempKing = board[0][6];
			Piece tempRook = board[0][5];

			board[0][4] = tempKing;
			board[0][7] = tempRook;
			board[0][6] = null;
			board[0][5] = null;
		} else {
			Piece tempKing = board[0][2];
			Piece tempRook = board[0][3];

			board[0][4] = tempKing;
			board[0][0] = tempRook;
			board[0][2] = null;
			board[0][3] = null;
		}
	}

	public void enPassant(int initCol, int initRow, int finalCol, int finalRow) {
		Piece attackingPawn = board[initRow][initCol];

		board[finalRow][finalCol] = attackingPawn;
		board[initRow][initCol] = null;

		board[initRow][finalCol] = null;
	}

	public void undoEnPassant (int initCol, int initRow, int finalCol, int finalRow) {
		Piece attackingPawn = board[finalRow][finalCol];

		board[initRow][initCol] = attackingPawn;
		board[finalRow][finalCol] = null;


		board[initRow][finalCol] = new Pawn(isWhiteTurn, true);
	}


	/*
	 * Helper function that returns the position of the king of the specified color 
	 * The position is in the form of an integer array of length 2 in which 
	 * the first value represents the row component and the second represents the column component
	 */
	public int[] getKingCoordinates(boolean white) {

		int row = 0, col = 0;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] != null) {
					if (board[i][j].toString().equals("King") && board[i][j].isWhite() == white) {
						row = i;
						col = j;
					}
				}
			}
		}

		int[] kingPos = new int[2];
		kingPos[0] = row;
		kingPos[1] = col;

		return kingPos;
	}

	// Checks if specified color is in check
	public boolean check(boolean white) {

		int[] kingPos = getKingCoordinates(white);

		int row = kingPos[0];
		int col = kingPos[1];

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {

				if (board[i][j] != null) {
					if( board[i][j].isWhite() != white && board[i][j].canMove(board, i, j, row, col)) {
						return true;
					}
				}
			}
		}

		return false;
	}


	// Checks if specified color is in checkmate
	public boolean checkmate(boolean white) {

		if (check(white)) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {

					for (int r = 0; r < 8; r++) {
						for (int c = 0; c < 8; c++) {

							if (board[i][j] != null) {
								if( board[i][j].isWhite() == white) {
									isWhiteTurn = white;

									Piece start = board[i][j];
									Piece finish = board[r][c];
									boolean moved = board[i][j].hasMoved();

									if (playTurn(j, i, c, r)) {				
										board[r][c] = finish; 
										board[i][j] = start;
										board[i][j].setMoved(moved);

										isWhiteTurn = white;

										return false;
									} else {
										board[r][c] = finish; 
										board[i][j] = start;
										board[i][j].setMoved(moved);
									}
								}
							}
						}
					}
				}
			}
			return true;
		} else {
			return false;
		}

	}
}

