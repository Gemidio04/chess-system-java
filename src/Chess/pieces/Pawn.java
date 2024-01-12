package Chess.pieces;

import Chess.ChessMatch;
import Chess.ChessPiece;
import Chess.Color;
import boardgame.Board;
import boardgame.Position;

public class Pawn extends ChessPiece{
	private ChessMatch chessMatch; 
	
	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch=chessMatch; 
	}
	
	// MOVIMENTOS POSSÍVEIS DO PEÃO: 
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0);
		
		//PEÃO BRANCO: 
		if(getColor()==getColor().WHITE){
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
			
			// EN PASSANT-PEÇA BRANCA: 
			
			//(LADO ESQUERDO):
			
			// VERIFICANDO SE ESTÁ NA ÚNICA POSIÇÃO POSSIVEL PARA SE FAZER A JOGADA: 	
			if(position.getRow()==3){
				Position left=new Position(position.getRow(),position.getColumn()-1);
				// VERIFICANDO SE A POSIÇÃO EXISTE, É UM OPONENTE, E ESTÁ VULNERAVEL: 
				if(getBoard().positionExists(left) && isThereOpponentPiece(left)
				   && getBoard().piece(left)==chessMatch.getEnPassantVulnerable()){
					// ATENDENDO A VERIFICAÇÃO O PEÃO PODE SER MOVIDO PARA A DIAGONAL ESQUERDA:
					mat[left.getRow()-1][left.getColumn()]=true; 
				}
			}
			// LADO DIRETO:
	
			// VERIFICANDO SE ESTÁ NA ÚNICA POSIÇÃO POSSIVEL PARA SE FAZER A JOGADA: 	
				Position right=new Position(position.getRow(),position.getColumn()+1);
				// VERIFICANDO SE A POSIÇÃO EXISTE, É UM OPONENTE, E ESTÁ VULNERAVEL: 
				if(getBoard().positionExists(right) && isThereOpponentPiece(right)
					&& getBoard().piece(right)==chessMatch.getEnPassantVulnerable()){
					// ATENDENDO A VERIFICAÇÃO O PEÃO PODE SER MOVIDO PARA A DIAGONAL DIREITA:
					mat[right.getRow()-1][right.getColumn()]=true; 
				}
			
		// MESMO RACIOCINIO SÓ QUE AGORA PARA A PEÇA PRETA:
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
			
			// EN PASSANT-PEÇA PRETA: 
			
			//LADO ESQUERDO:
			
			// VERIFICANDO SE ESTÁ NA ÚNICA POSIÇÃO POSSIVEL PARA SE FAZER A JOGADA: 	
			if(position.getRow()==4){
				Position left=new Position(position.getRow(),position.getColumn()-1);
				// VERIFICANDO SE A POSIÇÃO EXISTE, É UM OPONENTE, E ESTÁ VULNERAVEL: 
				if(getBoard().positionExists(left) && isThereOpponentPiece(left)
				   && getBoard().piece(left)==chessMatch.getEnPassantVulnerable()){
					mat[left.getRow()+1][left.getColumn()]=true; 
				}
			}
			// LADO DIRETO:
	
			// VERIFICANDO SE ESTÁ NA ÚNICA POSIÇÃO POSSIVEL PARA SE FAZER A JOGADA: 	
				Position right=new Position(position.getRow(),position.getColumn()+1);
				// VERIFICANDO SE A POSIÇÃO EXISTE, É UM OPONENTE, E ESTÁ VULNERAVEL: 
				if(getBoard().positionExists(right) && isThereOpponentPiece(right)
					&& getBoard().piece(right)==chessMatch.getEnPassantVulnerable()){
					mat[right.getRow()+1][right.getColumn()]=true; 
				}
		}	
		
	return mat;
	}	
		
	@Override
	public String toString() {
		return "P";
	}
}
