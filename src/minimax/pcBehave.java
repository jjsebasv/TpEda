package minimax;

import code.Board;
import code.Game;
import code.Move;
import exceptions.BoardOutOfBoundsException;
import exceptions.InvalidMoveException;


public class pcBehave {
	
	
	public static Board minimax(Game game, int depth, boolean prune, long time) {
		
		return mm(game, game.board, depth, game.getTurn()).getBoard();
		
	}
	
	private static Move mm(Game game, Board board, int depth, int turn ) {
		Move answer = null;
		
		if(depth == 0){
			return null;
		}
		
		for(int i = 0; i < board.getDimention(); i++){
			for( int j = 0; j < board.getDimention(); j++){
				
				if(board.getBox(i, j).getPiece().getPlayer().getTurn() == turn ){
					
					for (Move m : board.getMoves(i, j)) {
						Game aGame = game.duplicate( board );
						aGame.exeMove(m);
						System.out.println(board == aGame.board);
						if(turn == 1)
							turn = 2;
						else if(turn == 2)
							turn = 1;
						
						Move aux = mm(aGame,aGame.board, depth-1, turn);
						
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
						
					}					
				}
				
				
			}
		}
		
		return answer;
	}
	

}
