package minimax;

import java.util.List;

import code.Board;
import code.Game;
import code.Move;
import exceptions.IllegalPieceException;


public class pcBehave {
	
	
	public static Board minimax(Game game, int depth, boolean prune, long time) throws IllegalPieceException {
		/*
		NodeII nAux = new NodeII(game.board);
		Move move = mm(game, game.board, depth, game.getTurn());
		boolean f = (move==null);
		System.out.println("move null?"+f);
		Board aux = move.getBoard();
		
		System.out.println("este x");
		aux.printBoard();
		
		return aux;
		*/
		
		Board aux = mm2(game, game.board, depth, game.getTurn()).getBoard();
		
		game.setTurn(1);
		return aux;
	}
	
	

	private static Move mm2(Game game, Board board, int depth, int turn) throws IllegalPieceException{

		NodeII node = new NodeII(board);
		List<Move> l = null;
		Move answer = null;
		
		if(depth == 0)
			return answer;
		for(int i = 0; i < board.getDimention(); i++){
			for( int j = 0; j < board.getDimention(); j++){
				if( board.getBox(i, j).getPiece().getPlayer().getTurn() == game.getTurn()){

					l = board.getMoves2(i, j);
					System.out.println(l.size());
					System.out.println("entro al PRIMER if");

					for(int m = 0; m < l.size(); m++ ){
						Game aGame = game.duplicate(l.get(m).getBoard());
						if(turn == 1)
							turn = 2;
						else 
							turn = 1;
						Move resp = mm2(aGame,aGame.board,depth-1,turn);
						
						if(answer == null){
							answer = l.get(m);
						}else{
							if(resp == null){
								resp = l.get(m);
							}
							if(game.getTurn() == 2){
								if(answer.getValue()>resp.getValue()){
									answer = resp;
								}
							}else{
								if(answer.getValue()<resp.getValue()){
									answer = resp;
								}
							}
						}
						
					}
				}
			}
		}
		return answer;
	}
	
	private static Move mm(Game game, Board board, int depth, int turn) throws IllegalPieceException {
		Move answer = null;
		
/*		if(me == null){
			me = new NodeII(game.board);
		}
*/	
		if(depth == 0){
			return answer;
		}
		
		for(int i = 0; i < board.getDimention(); i++){
			for( int j = 0; j < board.getDimention(); j++){
				
				if(board.getBox(i, j).getPiece().getPlayer().getTurn() == turn ){
					
					for (Move m : board.getMoves2(i, j)) {
						Game aGame = game.duplicate( board );
						aGame.exeMove(m);

						if(turn == 1)
							turn = 2;
						else if(turn == 2)
							turn = 1;
						
						
					
						Move aux = mm(aGame,m.getBoard(), depth-1, turn);
												
						
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
						}/*
						System.out.println(me.chosen == null);
						me.board = answer.getBoard();
						me.value = answer.getValue();
						*/
					}
					
				}
			}
		}
		
		return answer;
	}
	

}
