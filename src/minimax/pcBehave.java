package minimax;

import code.Board;
import code.Game;
import code.Move;
import exceptions.BoardOutOfBoundsException;
import exceptions.InvalidMoveException;


public class pcBehave {
	
	
	public static Board minimax(Game game, int depth, boolean prune, long time) {
		System.out.println("ENTRO");
		
		Board aux = mm(game, game.board, depth, game.getTurn(), null).getBoard();
		System.out.println("este x");
		aux.printBoard();
		return aux;
		
	}
	
	private static Move mm(Game game, Board board, int depth, int turn, NodeII me) {
		System.out.println(depth);
		Move answer = null;
		
		
		if(depth == 0){
			return answer;
		}
		
		for(int i = 0; i < board.getDimention(); i++){
			for( int j = 0; j < board.getDimention(); j++){
				
				if(board.getBox(i, j).getPiece().getPlayer().getTurn() == turn ){
					
					for (Move m : board.getMoves(i, j)) {
						Game aGame = game.duplicate( board );
						aGame.exeMove(m);

						if(turn == 1)
							turn = 2;
						else if(turn == 2)
							turn = 1;
						
						if(me == null){
							me = new NodeII(aGame.board);
						}
						
						if(me.chosen == null){
							me.chosen = new NodeII(aGame.board,m.getValue());
						}
						
						Move aux = mm(aGame,m.getBoard(), depth-1, turn, me.chosen);
						
						if(answer == null || aux == null){ //primera vez que compara
							answer = m;
						}else{ 
							if(turn == 1){ // estoy parado en el movimiento de 2 -- Pc -- tengo que elegir el maximo
								if(answer.getValue() < aux.getValue()){
									answer = aux;
									
								}
							}else{ // estoy parado en el movimiento de 1 -- H -- tengo que elegir el minimo
								if(answer.getValue() > aux.getValue()){
									answer = aux;
								}
							}
						}
						System.out.println(me.chosen == null);
						me.chosen.board = answer.getBoard();
						me.chosen.value = answer.getValue();
					}
					me.value = me.chosen.value;
				}
			}
		}
		answer.setBoard(me.board);
		return answer;
	}
	

}
