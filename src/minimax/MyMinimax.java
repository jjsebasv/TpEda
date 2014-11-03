package minimax;

import code.Board;
import code.Game;
import code.Move;

public class MyMinimax {

	
	public static Board minimax(Game game, int depth, boolean prune, long time){
		Move aux = new Move();
		if ( depth == 0 ){
			System.out.println("POOOOOOOOOOOOR TIEMPOOOOOOOOOO");
			aux = minimaxTime(game,prune, null, time,2);
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
							}
						}
						// elijo max 
						else{
							if ( answer.getValue() < node.move.getValue() ){
								answer = node.getMove();
							}
						}
					}
				}
				
				
			}
		}
		
		System.out.println("mejor tablero de esta pasada");
		answer.getBoard().printBoard();
		return answer;
	}
}
