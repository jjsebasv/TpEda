package minimax;

import java.util.List;

import code.Board;
import code.Game;
import code.Move;

public class minimax {
	
	
	
	public static Board minMax(Game actualGame, Integer depth, boolean prune, Long time){
		Board board = actualGame.board;
		if( depth == null){
			depth = Integer.parseInt(Long.toString(time % board.getDimention()));
		}
		Integer pru = (prune)? Integer.MAX_VALUE : null;
		
		Node first = new Node();
		
		Board auxBoard = minMaxR(board,actualGame, first, depth, System.currentTimeMillis() + time, Integer.MIN_VALUE, pru, board.getDimention()).getBoard();
		if(auxBoard != null){
			System.out.println("MINMAX");
			board = auxBoard;
		}
		board.printBoard();
		return board;
	}
	
	private static Move minMaxR(Board board, Game game, Node actualNode, int depth, long finalTime, int bestChoise, Integer actualPrune, int dimention){
		Game auxGame = game.duplicate();
		auxGame.board = board;
		Move answer = new Move (board, bestChoise);
		Node nodeAnswer = null;
		if( depth == 0 ){
			return answer; 
		}	
			
		List<Move> possibleMoves;
		
		
		for(int i = 0; i < dimention; i++){
			for(int j = 0; j < dimention; j++){
				if(System.currentTimeMillis() > finalTime){
					return null;
				}
				if(board == null)
					return null;
				if(board.getBox(i, j).getSide() == auxGame.getTurn() ){
					possibleMoves = board.getMoves(i, j);
					for (Move m : possibleMoves) {
						auxGame.exeMove(m);
						actualNode.move = m;
		
						Move resp = minMaxR(auxGame.board, auxGame, new Node(), depth - 1, finalTime, bestChoise, actualPrune, dimention);
						
						if(resp == null){ // si el tiempo se agota en el medio, devuelve el mejor valor que encontro
							return null;
						}
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
		nodeAnswer.setColour(1);
		game.board = answer.getBoard();
		return answer;
	}
	
}
