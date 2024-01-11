package Chess.pieces;

import Chess.ChessMatch;
import Chess.ChessPiece;
import Chess.Color;
import boardgame.Board;
import boardgame.Position;

public class King extends ChessPiece{
	private ChessMatch chessMatch; 

	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch=chessMatch; 
	}
	
	// MÉTODOS: 
	
	// MÉTODO AUXILIAR DO possibleMoves:
	// VERIFICA SE O KING PODE MOVER PARA DETERMINADA POSIÇÃO: 
	private boolean canMove(Position position) {
		ChessPiece p=(ChessPiece) getBoard().piece(position);
		return p==null || p.getColor() != getColor(); 
	}
	
	// MÉTODO AUXILIAR PARA TESTAR SE NA POSIÇÃO PASSADA EXISTE UMA TORRE E SE ELA 	ESTÁ APTA PARA UM ROQUE: 
	private boolean testRookCastling(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
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
		
		// JOGADA ROQUE(PEQUENO): 
		if (getMoveCount() == 0 && !chessMatch.getCheck()) {
			// ACESSANDO 3 CASAS A DIREITA DO REI, QUE VAI SER A TORRE:
			Position posT1 = new Position(position.getRow(), position.getColumn() + 3);
			// VERIFICANDO SE A TORRE ESTÁ APTA PARA ROQUE:
			if (testRookCastling(posT1)) {
				Position p1 = new Position(position.getRow(), position.getColumn() + 1);
				Position p2 = new Position(position.getRow(), position.getColumn() + 2);
				// VERIFICANDO SE AS CASAS A DIREITA DO REI ESTÃO VAZIAS: 
					if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
						mat[position.getRow()][position.getColumn() + 2] = true;
					}
				}
		
		// JOGADA ROQUE(GRANDE):
			
		// ACESSANDO 4 CASAS A ESQUERDA DO REI, QUE VAI SER A TORRE:	
		Position posT2 = new Position(position.getRow(), position.getColumn() - 4);
		// VERIFICANDO SE A TORRE ESTÁ APTA PARA ROQUE:
		if (testRookCastling(posT2)) {
			Position p1 = new Position(position.getRow(), position.getColumn() - 1);
			Position p2 = new Position(position.getRow(), position.getColumn() - 2);
			Position p3 = new Position(position.getRow(), position.getColumn() - 3);
			// VERIFICANDO SE AS 3 CASAS A ESQUERDA DO REI ESTÃO VAZIAS: 
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
					mat[position.getRow()][position.getColumn() - 2] = true;
				}
			}
		}
	return mat;
	}
	
}
