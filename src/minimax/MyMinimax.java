package minimax;

import java.util.List;

import code.Board;
import code.Game;
import code.Move;
import exceptions.IllegalPieceException;

public class MyMinimax {

	
<<<<<<< HEAD
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
=======
	public static Board minimax(Game game, int depth, boolean prune, long time){
		Move aux = new Move();
		if ( depth == 0 ){
			System.out.println("POOOOOOOOOOOOR TIEMPOOOOOOOOOO");
			aux = minimaxTime(game,prune, time,2);
		}else{
			//aux = minimaxDepth(game,prune,depth);
			System.out.println("por depth");
		}
		return aux.getBoard();
	}


	private static Move minimaxTime(Game game, boolean prune, long time, int turn) {
		
		Move answer = null;
		MyNode node = null;
		
		
		if (time < System.currentTimeMillis()){
			System.out.println("MEEEEEEEEE QUEDE SINNNNN TIEMPO");
			return answer;
		}
		
		for (int i = 0; i < game.board.getDimention(); i++) {
			for (int j = 0; j < game.board.getDimention(); j++) {
			
				if ( game.board.myPiece(i, j, turn) ){
					for (Move move : game.board.getMoves(i,j)) {
						node = new MyNode(move);
						Game auxGame = game;
						auxGame.board = move.getBoard();
						int newturn = (turn==2)? 1 : 2;
						node.setNext(new MyNode(minimaxTime(auxGame,prune,time,newturn)));
					}
					
					if ( answer == null && node != null ) {
						answer = node.move;
					}
					else{
						// elijo min
						if ( turn == 1 ){
							if ( answer.getValue() > node.move.getValue() ){
								answer = node.getMove();
>>>>>>> d393a110e9bd32140115fe39f2df950fbef1f691
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
