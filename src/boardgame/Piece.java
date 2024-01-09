package boardgame;

public abstract class Piece {

	protected Position position;
	private Board board;
	
	public Piece(Board board) {
		this.board = board;
		position = null;
	}

	protected Board getBoard() {
		return board;
	}
	
	// MÉTODOS:
	public abstract boolean[][] possibleMoves();
	
	// VERIFICA SE É POSSÍVEL A PEÇA SER MOVIDA PARA DADA POSIÇÃO: 
	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}
	
	// VERIFICA SE EXISTE PELO MENOS 1 MOVIMENTO POSSÍVEL PARA DETERMINADA PEÇA: 
	public boolean isThereAnyPossibleMove() {
		boolean[][] mat = possibleMoves();
		for (int i=0; i<mat.length; i++) {
			for (int j=0; j<mat.length; j++) {
				if (mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
}
