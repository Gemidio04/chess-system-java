package Chess;

import boardgame.BoardException;
import boardgame.Position;

public class ChessPosition {
	private char column; 
	private int row;
	
	public ChessPosition(char column, int row) {
		if(column<'a' || column>'h' || row<1 || row>8){
			throw new ChessException("Erro instantiating ChessPosition. Valid values are from a1 to h8"); 
		}
		this.column = column;
		this.row = row;
	}

	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	// MÉTODOS: 
	
	// CONVERTE DE MATRIZ NO MODELO XADREZ PARA MATRIZ NO MODELO NÚMERICO: 
	protected Position toPosition() {
		return new Position(8-row, column-'a');
	}
	
	// CONVERTE DE MATRIZ NO MODELO NÚMERICO PARA MATRIZ NO MODELO XADREZ: 
	protected static ChessPosition fromPosition(Position position) {
		return new ChessPosition((char)('a'-position.getColumn()),8-position.getRow());
	}
	
	@Override
	public String toString(){
		return " " + column + row;
	}
	
}
