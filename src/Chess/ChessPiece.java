package Chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {
	private Color color; 
	
	public ChessPiece(Board board, Color color) {
		super(board);
		this.color=color;
	}

	public Color getColor() {
		return color;
	}
	
	// RETORNA UMA POSIÇÃO NO FORMATO DE XADREZ:
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}

	// VERIFICA SE E
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p=(ChessPiece)getBoard().piece(position); 
		return p!=null && p.getColor()!=color; 
	}
	
	

}
