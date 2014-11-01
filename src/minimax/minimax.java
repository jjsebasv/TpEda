package minimax;

import java.util.List;

import code.Board;
import code.Game;
import code.Move;

public class minimax {
	

	public static Board miniMax(Game actualGame, int depth, Integer prune, Node actual, Long timeBound) {
		if (depth == 0 || actualGame.getTurn() > 2) {
			return actualGame.board;
		}
		Board board = actualGame.board;
		Move answer = new Move(board, Integer.MIN_VALUE); // A partir de aca,
															// todos los moves
															// son mejores
		List<Move> possibleMoves;
		Game auxGame;
		Integer actualPrune = null; 
		Node next = null, nodeAnswer = null; 
		if (prune != null)
			actualPrune = Integer.MAX_VALUE;
		for (int i = 0; i < board.getDimention(); i++) {
			for (int j = 0; j < board.getDimention(); j++) {
				if (System.currentTimeMillis() > timeBound)
					return null;
				if (board.getBox(i, j).getSide() == actualGame.getTurn()) {
					possibleMoves = board.getMoves(i, j);
					for (Move m : possibleMoves) {
						auxGame = actualGame.duplicate();
						auxGame.exeMove(m);
						if (actual != null){ 
							
							next = new Node();
						}
						Board resp = miniMax(auxGame, depth - 1, actualPrune,
								next, timeBound);
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
