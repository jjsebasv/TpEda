package minimax;

import java.util.List;

import code.Board;
import code.Game;
import code.Move;
import exceptions.IllegalPieceException;

public class MyMinimax {

	
	public static Move minimax(Game state, int depth, Integer prune, Node me, Long timeBound) throws IllegalPieceException {
		//if (depth == 0 || state.getTurn() > 2) {
			//return new Move(state.value());
		//}
		Board board = state.getBoard();
		Move answer = new Move(Integer.MIN_VALUE); // Inicializo el valor del mejor movimiento como -inf para que cualquier movimiento sea mejor
		List<Move> possibleMoves;
		Game stateAux;
		Integer actualPrune = null; // poda actual
		Node son = null, nodeAnswer = null; // hijos y nodo respuesta para el arbol de llamadas
		if (prune != null)
			actualPrune = Integer.MAX_VALUE;
		for (int i = 0; i < board.getDimention(); i++) {
			for (int j = 0; j < board.getDimention(); j++) {
				if (System.currentTimeMillis() > timeBound)
					return null;
				if (board.getBox(i, j).getPiece().getPlayer().getTurn() == state.getTurn()) {
					possibleMoves = board.getMoves(i, j);
					for (Move move : possibleMoves) {
						stateAux = state.copy();
						stateAux.board = move.getBoard();
						if (me != null) // Si es creando el arbol de llamadas
							son = new Node();
						// System.out.println(blancos(4-depth)+"Entre: "+move);
						Move resp = minimax(stateAux, depth - 1, actualPrune, son, timeBound);
						if (resp == null)
							return null;
						move.setValue(-resp.getValue());
						// System.out.println(blancos(4-depth)+"Sali: "+move);
						//if (son != null) { // Si es creando el arbol de llamadas
							//son.setLabel(move.toString());
							//if (depth % 2 == 0)
								//son.setForm("ellipse");
							//else
								//son.setForm("box");
							//me.link(son);
						//}
						if (move.getValue() > answer.getValue()) {
							if (me != null) { // Si es creando el arbol de llamadas
								nodeAnswer = son;
							}
							answer = move;
							if (answer.getValue() == Integer.MAX_VALUE) {
								//if (nodeAnswer != null)
									//nodeAnswer.setColor("salmon");
								return answer;
							}
						}
						if (prune != null) { // si es con poda
							if (move.getValue() >= prune) {
								//if (nodeAnswer != null)
									//nodeAnswer.setColor("salmon");
								//if (me != null) { // Si es creando el arbol de llamadas
									//son = new Node();
									//son.setLabel("Poda");
									//son.setColor("gray");
									//if (depth % 2 == 0)
									//	son.setForm("ellipse");
									//else
									//	son.setForm("box");
									//me.link(son);
								//}
								return answer;
							} else {
								actualPrune = -answer.getValue();
							}
						}
					}
				}
			}
		}
		//if (nodeAnswer != null)
			//nodeAnswer.setColor("salmon");
		return answer;
	}
}
