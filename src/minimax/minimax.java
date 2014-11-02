package minimax;

import java.util.List;

import code.Board;
import code.Game;
import code.Move;

public class minimax {
	

	public static Board miniMax(Game actualGame, int depth, Integer prune, Node actual, long current, long fin) {
		System.out.println("final menos inicial "+(fin-current));
		
		if (depth == 0 || actualGame.getTurn() > 2) {
			return actualGame.board;
		}
		Board board = actualGame.board;
		board.printBoard();
		Move answer = new Move(board, Integer.MIN_VALUE); // A partir de aca,
															// todos los moves
															// son mejores
		List<Move> possibleMoves;
		Game auxGame = null;
		Integer actualPrune = null; 
		Node next = null, nodeAnswer = null; 
		if (prune != null)
			actualPrune = Integer.MAX_VALUE;
		
		for (int i = 0; i < board.getDimention(); i++) {
			for (int j = 0; j < board.getDimention(); j++) {
				if (current > fin){
					System.out.println("tiempo agotado");
					return null;
				}
				//System.out.println("board side"+board.getBox(i, j).getSide()+"   "+"actual game t"+ actualGame.getTurn());
				if (board.getBox(i, j).getSide() == actualGame.getTurn()) {
					System.out.println("i"+i+"j"+j);
					possibleMoves = board.getMoves(i, j);
					System.out.println("posible moves size "+possibleMoves.size());
					for (Move m : possibleMoves) {
						auxGame = actualGame.duplicate();
						auxGame.exeMove(m);
						boolean flag = (auxGame.board==null);
						System.out.println(flag);
						if (actual != null){ 
							next = new Node();
						}
						System.out.println();
						Board resp = miniMax(auxGame, depth - 1, actualPrune,
								next, System.currentTimeMillis(),fin);
						if (resp == null)
							return null;
						m.newValue(-next.move.getValue());
						if (next != null) { 
							if (depth % 2 == 0)
								next.setFigure(1);
							else
								next.setFigure(2);
							actual.setChosen(next);
						}
						
						if (m.getValue() > answer.getValue()) {
							if (actual != null) { 
								nodeAnswer = next;
							}
							answer = m;
							if (answer.getValue() == Integer.MAX_VALUE) {
								if (nodeAnswer != null)
									nodeAnswer.setColour(1);
								return answer.getBoard();
							}
						}
						if (prune != null) { // poda
							if (m.getValue() >= prune) {
								if (nodeAnswer != null)
									nodeAnswer.setColour(1);
								if (actual != null) { 
									next = new Node();
									next.setPrune();
									next.setColour(2);
									if (depth % 2 == 0)
										next.setFigure(1);
									else
										next.setFigure(2);
									actual.setChosen(next);
								}
								return answer.getBoard();
							} else {
								actualPrune = -answer.getValue();
							}
						}
					}
				}
			}
		}
		if (nodeAnswer != null)
			nodeAnswer.setColour(1);
		return answer.getBoard();
	}

}
