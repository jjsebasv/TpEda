package minimax;

import java.util.List;

import code.Board;
import code.Game;
import code.Move;
import exceptions.BoardOutOfBoundsException;
import exceptions.InvalidMoveException;

public class minimax {
	
	
	
	public static Board minMax(Game game, Integer depth, boolean prune, Long time) throws InvalidMoveException, BoardOutOfBoundsException{
		Board auxBoard = null;
		System.out.println("ACTUAL GAME BOARD MINIMAX:");
		game.board.printBoard();
		
		Integer pru = (prune)? 1 : 0;
		if( depth == 0 ){
			auxBoard =  minMaxTimeR(game.board,game,null,time,Integer.MAX_VALUE,pru,game.board.getDimention()).getBoard();
		}else{
			System.out.println("con depth");
			//auxBoard = minMaxDepthR()
		}
		

		if(auxBoard != null){
			System.out.println("MINMAX");
			game.board = auxBoard;
		}
		game.board.printBoard();
		return game.board;
	}
	
	
	private static Move minMaxTimeR(Board board, Game game, Node current, long finalTime, int bestChoise, Integer actualPrune, int dimention) throws InvalidMoveException, BoardOutOfBoundsException{
		Game auxGame = game.duplicate(board);
		auxGame.board = board;
		
		Move answer = new Move (board, bestChoise);
		Node nodeAnswer = new Node();
			
		List<Move> posibleMoves;
		
		for(int i = 0; i < dimention; i++){
			for(int j = 0; j < dimention; j++){
				if(System.currentTimeMillis() > finalTime){ //si el tiempo se agota, devuelve lo mejor que encontro
					nodeAnswer = current;
					return answer;
				}
				
				

				if( board.getBoard()[i][j].getPiece().getC() == 'N' ){
					System.out.println("juega pc");

					posibleMoves = board.getMoves(i, j);
					System.out.println("posible moves ("+i+","+j+") : "+posibleMoves.size());
					for (Move m : posibleMoves) {
						auxGame.exeMove(m);
						if ( current == null ){
							current = new Node();
						}
						current.move = m;
				
						Move resp = minMaxTimeR(auxGame.board, auxGame, new Node(), finalTime, bestChoise, actualPrune, dimention);
						current.Next(resp);
						m.setValue(- resp.getValue() );
						
						if (actualPrune != null) { // si es con poda
							if (m.getValue() >= actualPrune) {
								nodeAnswer.setColour(1);
							Node son = new Node();
								son.label = "Poda";
								son.setColour(2);
								current.next.offerLast(son);
								game.board = answer.getBoard();
								return answer;
							} else {
								actualPrune = -answer.getValue();
							}
						}
						
						if(resp.getValue() > answer.getValue()){
							bestChoise = resp.getValue();
							answer = resp;
							nodeAnswer = current.next.getLast();
						}

					}
				}
			}
		}
		if(nodeAnswer == null)
			nodeAnswer = current;
		nodeAnswer.setColour(1);
		game.board = answer.getBoard();
		return answer;
	}
	
	private static Move minMaxDepthR(Board board, Game game, Node actualNode, int depth, int bestChoise, Integer actualPrune, int dimention){
		return null;
	}
	
	
	
}
