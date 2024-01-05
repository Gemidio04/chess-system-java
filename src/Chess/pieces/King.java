package Chess.pieces;

import Chess.ChessPiece;
import Chess.Color;
import boardgame.Board;
import boardgame.Position;

public class King extends ChessPiece{

	public King(Board board, Color color) {
		super(board, color);
	}
	
	// MÉTODOS: 
	
	// MÉTODO AUXILIAR DO possibleMoves:
	// VERIFICA SE O KING PODE MOVER PARA DETERMINADA POSIÇÃO: 
	private boolean canMove(Position position) {
		ChessPiece p=(ChessPiece) getBoard().piece(position);
		return p==null || p.getColor() != getColor(); 
	}
	
	@Override
	public String toString(){
		return "K";
	}


	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat=new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0); 
		
		// ACIMA: 
		p.setValues(position.getRow()-1, position.getColumn());
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()]=true; 
		}
		
		// ABAIXO: 
		p.setValues(position.getRow()+1, position.getColumn());
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()]=true; 
		}
		
		// ESQUERDA: 
		p.setValues(position.getRow(), position.getColumn()-1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()]=true; 
		}
		
		// DIREITA: 
		p.setValues(position.getRow(), position.getColumn()+1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()]=true; 
		}
		
		// NOROESTE: 
		p.setValues(position.getRow()-1, position.getColumn()-1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()]=true; 
		}
		
		// NORDESTE: 
		p.setValues(position.getRow()-1, position.getColumn()+1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()]=true; 
		}
		
		// SULDOESTE: 
		p.setValues(position.getRow()+1, position.getColumn()-1);
			if(getBoard().positionExists(p) && canMove(p)) {
				mat[p.getRow()][p.getColumn()]=true; 
		}
			
		// SULDESTE: 
		p.setValues(position.getRow()+1, position.getColumn()+1);
			if(getBoard().positionExists(p) && canMove(p)) {
				mat[p.getRow()][p.getColumn()]=true; 
		}			
			
		return mat;
	}

}
