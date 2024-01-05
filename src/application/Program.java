package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Chess.ChessException;
import Chess.ChessMatch;
import Chess.ChessPiece;
import Chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in); 
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>(); 
		
		while(true) {
			try {
				UI.clearScreen();
				UI.printMatch(chessMatch, captured);
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source=UI.readChessPosition(sc);
				
				boolean[][] possibleMoves=chessMatch.possibleMoves(source); 
				UI.clearScreen();
				System.out.println(chessMatch.getPieces());
				UI.printBoard(chessMatch.getPieces(), possibleMoves);
				
				System.out.println();
				System.out.print("Target: ");
				ChessPosition target=UI.readChessPosition(sc); 

				ChessPiece capturedPiece=chessMatch.performChessMove(source, target);
				
				// VERIFICANDO SE A PEÇA FOI CAPTURADA:
				if(capturedPiece!=null) {
					captured.add(capturedPiece);
				}
			}catch(ChessException ex) {
				System.out.println(ex.getMessage());
				sc.nextLine(); 
			}catch(InputMismatchException ex) {
				System.out.println(ex.getMessage());
				sc.nextLine(); 
			}
			
		}
		
	
	
	}

}
