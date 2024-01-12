package Chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Chess.pieces.Bishop;
import Chess.pieces.King;
import Chess.pieces.Knight;
import Chess.pieces.Pawn;
import Chess.pieces.Queen;
import Chess.pieces.Rook;
import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;


public class ChessMatch {
	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable; 

	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
	}
	
	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}

	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i=0; i<board.getRows(); i++) {
			for (int j=0; j<board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	
	// FAZ UM MOVIMENTO: 
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		// TESTA SE O JOGADOR ESTÁ COM O SEU REI EM CHECK, SE ESTIVER, VOLTA A
		// PEÇA PARA O SEU DESTINO,ASSIM ELA FICARÁ COMO ESTAVA ANTES DO CHECK: 
		if (testCheck(currentPlayer)) {	
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in check");
		}
		// MOVIMENTO DA PEÇA:
		ChessPiece movedPiece=(ChessPiece)board.piece(target); 
		// FAZ O MESMO TESTE DO IF ACIMA SÓ QUE AGORA COM O OPONENTE DO JOGADOR:
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		// VERIFICA SE A JOGADA FEITA DEIXOU O REI EM CHACKMATE: 
		if (testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		}else {
			nextTurn();
		}
		
		// EN PASSANT: 
		
		// VERIFICANDO SE A PEÇA MOVIDA É UM PEÃO E SE
		// MOVEU DUAS CASAS PARA A ESQUERDA OU DIREITA:
		if(movedPiece instanceof Pawn && 
		((target.getRow())==source.getRow()-2
		||(target.getRow())==source.getRow()+2)){
			enPassantVulnerable=movedPiece;
		}
		else{ 
			enPassantVulnerable=null; 	
		}
		return (ChessPiece)capturedPiece;
	}
	
	// MOVE UMA PEÇA DA ORIGEM(SOURCE) PARA O DESTINO(TARGET): 
	private Piece makeMove(Position source, Position target) {
		ChessPiece p=(ChessPiece)board.removePiece(source);
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		// ROQUE:  
		
		// CONDIÇÃO QUE VÊ SE O REI EFETUOU A JOGADA ROQUE(PEQUENO):
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}

		// CONDIÇÃO QUE VÊ SE O REI EFETUOU A JOGADA ROQUE(GRANDE): 
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}
		
		// EN PASSANT:
		
		if(p instanceof Pawn){
			// VERIFICA SE O PEÃO TEVE UM DESTINO NA DIAGONAL E SE NÃO CAPTUROU PEÇA: 
			if(source.getColumn()!=target.getColumn() && capturedPiece==null){
				Position pawnPosition;
				// SE O PEÃO É BRANCO: 
				if(p.getColor()==Color.WHITE) {
					// A VARIÁVEL RECEBE A POSIÇÃO DELE +1 NA LINHA:
					pawnPosition=new Position(target.getRow()+1, target.getColumn());
				// SE O PEÃO É PRETO:	
				}else{
					// A VARIÁVEL RECEBE A POSIÇÃO DELE -1 NA LINHA:
					pawnPosition=new Position(target.getRow()-1, target.getColumn());
				}
				// APÓS ISSO, A PEÇA É REMOVIDA, TIRADA DA LISTA DE PEÇAS
				// DO TABULEIRO E COLOCADO NA LISTA DE PEÇAS CAPTURADAS: 
				capturedPiece=board.removePiece(pawnPosition);
				capturedPieces.add(capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}
		}
		
		// EN :
		
		return capturedPiece;
	}

	// DEFAZ O MOVIMENTO DE UMA PEÇA:
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p =(ChessPiece)board.removePiece(target);
		p.decreaseMoveCount();		
		board.placePiece(p, source);
		
		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}

		// ROQUE: 
		
		// CONDIÇÃO QUE VÊ SE O REI EFETUOU A JOGADA ROQUE(PEQUENO): 
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece)board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}
				
		// CONDIÇÃO QUE VÊ SE O REI EFETUOU A JOGADA ROQUE(GRANDE): 
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece)board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}			
		
		// EN PASSANT:
		
		if(p instanceof Pawn){
			// VERIFICA SE O PEÃO TEVE UM DESTINO NA DIAGONAL E SE NÃO CAPTUROU PEÇA: 
			if(source.getColumn()!=target.getColumn() && capturedPiece==enPassantVulnerable){
				ChessPiece pawn=(ChessPiece)board.removePiece(target);
				Position pawnPosition; 
				// SE O PEÃO É BRANCO: 
				if(p.getColor()==Color.WHITE) {
					// A VARIÁVEL RECEBE A POSIÇÃO 3 NA LINHA:
					pawnPosition=new Position(3, target.getColumn());
				// SE O PEÃO É PRETO:	
				}else{
					// A VARIÁVEL RECEBE A POSIÇÃO 4 NA LINHA:
					pawnPosition=new Position(4, target.getColumn());
				}
				board.placePiece(pawn, pawnPosition);
			}
		}
	}

	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
			throw new ChessException("The chosen piece is not yours");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("The chosen piece can't move to target position");
		}
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("There is no " + color + " king on the board");
	}

	// VERIFICANDO SE O REI ESTÁ EM CHECK:
	private boolean testCheck(Color color) {
		// PEGANDO A POSIÇÃO DO REI E A LISTA DE PEÇAS DO OPONTENTE:
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces){
			//VERIFICANDO OS MOVIMENTOS POSIVEIS DAS PEÇAS DO ADVERSÁRIO:
			boolean[][] mat = p.possibleMoves();
			// SE NA MATRIZ A POSIÇÃO DO REI FOR VERDADEIRO, O REI ESTÁ EM CHECK. 
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}

	// VERIFICA SE ESTÁ EM CHECKMATE: 
	private boolean testCheckMate(Color color) {
		// VERIFICA SE NÃO ESTÁ EM CHECK:
		if(!testCheck(color)) {
			return false; 
		}
		// PEGA TODAS AS PEÇAS DA COR PASSADA: 
		List<Piece> list=piecesOnTheBoard.stream().filter(x->((ChessPiece)x).getColor()==color).collect(Collectors.toList()); 
		for(Piece p: list){
			// PEGA TODOS OS MOVIMENTOS POSSÍVEIS
			// PARA O JOGADOR DA COR PASSADA: 
			boolean[][] mat=p.possibleMoves();
			for(int i=0;i<board.getRows();i++) {
				for(int j=0;j<board.getColumns();j++){
					// PEGANDO A MATRIZ DE PEÇAS: 
					if(mat[i][j]){
						// MOVENDO A LISTA DE PEÇAS COM A CRIAÇÃO DAS VARIÁVEIS DE ORIGEM
						// (PEÇAS DO JOGADOR) E CRIANDO A VARIÁVEL DE DESTINO(MATRIZ DE PEÇAS): 
						Position source=((ChessPiece)p).getChessPosition().toPosition();
						Position target=new Position(i,j);
						// MOVIMENTO A ORIGEM PARA O DESTINO: 
						Piece capturedPiece=makeMove(source, target);
						// APÓS MOVIMENTAR, TESTAMOS SE AINDA ESTÁ EM CHECK:
						boolean testCheck=testCheck(color); 
						// DESFAZENDO O MOVIMENTO(O MOVIMENTO FEITO ANTERIORMENTE SÓ FOI FEITO PARA TESTAR):
						undoMove(source, target, capturedPiece);
						if(!testCheck) {
							return false; 
						}
					}
				}
			}
		}
		return true; 
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}
	
	private void initialSetup() {
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));
        
        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
	}
}