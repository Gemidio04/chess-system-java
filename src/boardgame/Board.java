package boardgame;

import Chess.pieces.Rook;

public class Board {
	private int rows; 
	private int columns; 
	private Piece[][] pieces;
	
	public Board(int rows, int columns) {
		if(rows<1 || columns<1) {
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column!"); 
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}
	
	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	// MÉTODOS:
	
	public Piece piece(int row, int column) {
		if(!positionExists(row, column)) {
			throw new BoardException("Position not on the board"); 
		}
		return pieces[row][column]; 
	}
	
	public Piece piece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Position not on the board"); 
		}
		return pieces[position.getRow()][position.getColumn()]; 
	}
	
	public void placePiece(Piece piece, Position position) {
		if(thereIsAPiece(position)) {
			throw new BoardException("There is already a piece on position "+position); 
		}
		pieces[position.getRow()][position.getColumn()]=piece; 
		piece.position=position;
	}
	
	// REMOVENDO PEÇAS: 
	public Piece removePiece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		if(piece(position)==null) {
			return null; 
		}
		// REMOVENDO A PEÇA DO TABULEIRO DE XADREZ: 
		Piece aux=piece(position); 
		aux.position=null; 
		pieces[position.getRow()][position.getColumn()]=null;
		return aux;
	}
	

	// VERIFICA SE ESSA POSIÇÃO PASSADA EXISTE. RETORNA VERDADEIRO SE EXISTIR,
	// E RETORNA FALSO SE NÃO EXISTIR(SERVE PARA AS 2 positionExists): 
	private boolean positionExists(int row, int column) {
		return row>=0 && row<rows && column>=0 && column<columns; 
	}
	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn()); 
	}
	
	
	// PRIMEIRO VERIFICA SE A POSIÇÃO EXISTE COM O MÉTODO positionExists,
	//SE EXISTIR, VERIFICA SE TÊM UMA PEÇA NA POSIÇÃO PASSADA E RETORNA ELA:
	public boolean thereIsAPiece(Position position){
		if(!positionExists(position)) {
			throw new BoardException("Position not on the board"); 
		}
		return piece(position)!=null;	
	}
	
	
}
