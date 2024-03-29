package Chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {
	private Color color; 
	private int moveCount; 
	
	public ChessPiece(Board board, Color color) {
		super(board);
		this.color=color;
	}

	public Color getColor() {
		return color;
	}
	
	public int getMoveCount() {
		return moveCount; 
	}
	//ITERAVA A VARIÁVEL: 
	public void increaseMoveCount() {
		moveCount++;
	}
	
	//DECREMENTA A VARIÁVEL: 
	public void decreaseMoveCount() {
		moveCount--;
	}
	
	// RETORNA UMA POSIÇÃO NO FORMATO DE XADREZ:
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}

	// RETORNA UMA PEÇA ADVERSÁRIA:
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p=(ChessPiece)getBoard().piece(position); 
		return p!=null && p.getColor()!=color; 
	}
	
	

}
