package Chess.pieces;

import Chess.ChessPiece;
import Chess.Color;
import boardgame.Board;
import boardgame.Position;

public class Pawn extends ChessPiece{

	
	public Pawn(Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0);
		
		//PEÃO BRANCO: 
		if(getColor()==getColor().WHITE) {
			p.setValues(position.getRow()-1, position.getColumn());
			// SE A POSIÇÃO EXISTIR E NÃO TIVER UMA PEÇA NELA, O PEÃO PODE MOVER PRA LÁ:
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
				mat[p.getRow()][p.getColumn()]=true;  
			} 
			p.setValues(position.getRow()-2, position.getColumn());
			Position p2 = new Position(position.getRow()-1, position.getColumn());
			// FAZENDO O MESMO QUE O IF ACIMA SÓ QUE 2 CASAS ACIMAS: 
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getMoveCount()==0
			   && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2)){
				mat[p.getRow()][p.getColumn()]=true;   
			}
			// TESTANDO A POSIÇÃO NA 1º DIAGONAL: 
			p.setValues(position.getRow()-1, position.getColumn()-1);
			// SE A POSIÇÃO EXISTIR E TIVER UMA PEÇA NELA, O PEÃO PODE MOVER PRA LÁ:
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
				mat[p.getRow()][p.getColumn()]=true;  
			}
			// TESTANDO A POSIÇÃO NA 2º DIAGONAL: 
			p.setValues(position.getRow()-1, position.getColumn()+1);
			// SE A POSIÇÃO EXISTIR E NÃO TIVER UMA PEÇA NELA, O PEÃO PODE MOVER PRA LÁ:
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
				mat[p.getRow()][p.getColumn()]=true;  
			}
		// MESMO RACIOCINIO SÓ QUE AGORA PARA A PEÇA PRETA
		}else{
			p.setValues(position.getRow()+1, position.getColumn());
			// SE A POSIÇÃO EXISTIR E NÃO TIVER UMA PEÇA NELA, O PEÃO PODE MOVER PRA LÁ:
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
				mat[p.getRow()][p.getColumn()]=true;  
			} 
			p.setValues(position.getRow()+2, position.getColumn());
			Position p2 = new Position(position.getRow()+1, position.getColumn());
			// FAZENDO O MESMO QUE O IF ACIMA SÓ QUE 2 CASAS ACIMAS: 
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getMoveCount()==0
			   && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2)){
				mat[p.getRow()][p.getColumn()]=true;   
			}
			// TESTANDO A POSIÇÃO NA 1º DIAGONAL: 
			p.setValues(position.getRow()+1, position.getColumn()+1);
			// SE A POSIÇÃO EXISTIR E TIVER UMA PEÇA NELA, O PEÃO PODE MOVER PRA LÁ:
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
				mat[p.getRow()][p.getColumn()]=true;  
			}
			// TESTANDO A POSIÇÃO NA 2º DIAGONAL: 
			p.setValues(position.getRow()-1, position.getColumn()+1);
			// SE A POSIÇÃO EXISTIR E NÃO TIVER UMA PEÇA NELA, O PEÃO PODE MOVER PRA LÁ:
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
				mat[p.getRow()][p.getColumn()]=true;  
			}
		}
		return mat;
	}
	
	@Override
	public String toString() {
		return "P";
	}
}
