package minimax;

import java.util.List;

import code.Board;
import code.Game;
import code.Move;
import exceptions.BoardOutOfBoundsException;
import exceptions.InvalidMoveException;

public class minimax {
	
	
	
	public static Board minMax(Game actualGame, Integer depth, boolean prune, Long time) throws InvalidMoveException, BoardOutOfBoundsException{
		Board board = actualGame.board;
		
		if( depth == null){
			depth = Integer.parseInt(Long.toString(time % board.getDimention()));
		}
		Integer pru = (prune)? Integer.MAX_VALUE : null;
		
		Node first = new Node();
		
		Board auxBoard = minMaxR(board,actualGame, first, depth, System.currentTimeMillis() + time, Integer.MIN_VALUE, pru, board.getDimention()).getBoard();
		if(auxBoard != null){
			System.out.println("MINMAX");
			actualGame.board = auxBoard;
		}
		board.printBoard();
		return board;
	}
	
	private static Move minMaxR(Board board, Game game, Node actualNode, int depth, long finalTime, int bestChoise, Integer actualPrune, int dimention) throws InvalidMoveException, BoardOutOfBoundsException{
		Game auxGame = game.duplicate(board);
		auxGame.board = board;
		
		Move answer = new Move (board, bestChoise);
		Node nodeAnswer = null;
		
		if( depth == 0 ){ 
			return answer; 
		}	
			
		List<Move> possibleMoves;
		
		for(int i = 0; i < dimention; i++){
			for(int j = 0; j < dimention; j++){
				if(System.currentTimeMillis() > finalTime){ //si el tiempo se agota, devuelve lo mejor que encontro
					nodeAnswer = actualNode;
					return answer;
				}
				if(board.getBox(i, j).getPiece().getPlayer().getTurn() == auxGame.getTurn() ){

					possibleMoves = board.getMoves(i, j);
					for (Move m : possibleMoves) {
						auxGame.exeMove(m);
						actualNode.move = m;
					
						System.out.println((auxGame.board == null) + " board ");
						System.out.println((auxGame == null) + " auxGame ");
						System.out.println((actualPrune == null) + " actualPrune ");
						
						Move resp = minMaxR(auxGame.board, auxGame, new Node(), depth - 1, finalTime, bestChoise, actualPrune, dimention);
						
						
						actualNode.Next(resp);
						m.setValue(- resp.getValue() );
						
						
						if (actualPrune != null) { // si es con poda
							if (m.getValue() >= actualPrune) {
								nodeAnswer.setColour(1);
								Node son = new Node();
								son.label = "Poda";
								son.setColour(2);
								if (depth % 2 == 0)
									son.setFigure(1);
								else
									son.setFigure(2);
								actualNode.next.offerLast(son);
								game.board = answer.getBoard();
								return answer;
							} else {
								actualPrune = -answer.getValue();
							}
						}
						
						if (depth % 2 == 0)
							actualNode.setFigure(1);
						else
							actualNode.setFigure(2);
						
						if(resp.getValue() > answer.getValue()){
							bestChoise = resp.getValue();
							answer = resp;
							nodeAnswer = actualNode.next.getLast();
						}
						
						
							
					}
				}
			}
		}
		if(nodeAnswer == null)
			nodeAnswer = actualNode;
		nodeAnswer.setColour(1);
		game.board = answer.getBoard();
		return answer;
	}
	
}
