package minimax;

import java.util.List;

import code.Board;
import code.DotGenerator;
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
		
		*/
		NodeII me = new NodeII(game.board);
		
		me.setMove(new Move(game.board,Integer.MIN_VALUE,null,null,1));
		
		Board aux = mm2(game, game.board, depth, game.getTurn(),me).getBoard();
		Move auxMove = null;
		DotGenerator.export(me);
		for(NodeII n:me.children){
			if(auxMove == null)
				auxMove = n.getMove();
			else{
				if(n.getMove().getValue() > auxMove.getValue())
					auxMove = n.getMove();
			}
		}
		
		return auxMove.getBoard();
	}
	
	
	private static Move mm2(Game game, Board board, int depth, int turn,NodeII me){

		List<Move> l = null;
		Move answer = null;
		NodeII nAns = null;
		
		if(depth == 0)
			return answer;
		for(int i = 0; i < board.getDimention(); i++){
			for( int j = 0; j < board.getDimention(); j++){
				if( board.getBox(i, j).getPiece().getPlayer().getTurn() == game.getTurn()){

					l = board.getMoves(i, j);

					for(int m = 0; m < l.size(); m++ ){
						Game aGame = game.duplicate(l.get(m).getBoard());
						if(turn == 1)
							turn = 2;
						else 
							turn = 1;
						
						aGame.exeMove(l.get(m));
						nAns = new NodeII(l.get(m));
						
						Move resp = mm2(aGame,aGame.board,depth-1,turn,nAns);
						
						if(answer == null){
							answer = l.get(m);
						}
						if(resp == null){
							resp = l.get(m);
						}
						nAns.setValue(resp.getValue());
							
						me.link(nAns);
						
						
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
		for (NodeII n : me.children) {
			if(me.chosen == null)
				me.chosen = n;
			if(game.getTurn() == 2){
				if(me.chosen.getValue()>n.getMove().getValue()){
					me.chosen = n;
				}
			}else{
				if(me.chosen.getValue()<n.getMove().getValue()){
					me.chosen = n;
				}
			}
		}
		if(turn == 1)
			turn = 2;
		else
			turn = 1;
		
		return me.chosen.getMove();
	}
	
	
	

}
