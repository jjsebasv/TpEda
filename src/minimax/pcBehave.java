package minimax;

import java.util.List;

import code.Board;
import code.DotGenerator;
import code.Game;
import code.Move;
import exceptions.IllegalPieceException;


public class pcBehave {
	
	
	public static Board minimax(Game game, Integer depth, boolean prune, long time,boolean tree) throws IllegalPieceException {

		NodeII me = new NodeII(game.board);
		
		me.setMove(new Move(game.board,Integer.MIN_VALUE,0,0,0,0,1));
		
		Integer p = new Integer(0);
		if ( prune ){
			p = 1;
		}else{
			p = 0;
		}
		
		Board aux = mm2(game, game.board, depth, game.getTurn(),me,time+System.currentTimeMillis(), p, tree).getBoard();
		Move auxMove = null;
		for(NodeII n:me.children){
			if(auxMove == null)
				auxMove = n.getMove();
			else{
				if(n.getMove().getValue() > auxMove.getValue()){
					auxMove = n.getMove();
					me.chosen = n;
				}
			}
		}
		me.chosen.setColour("red");
		return auxMove.getBoard();
	}
	
	
	private static Move mm2(Game game, Board board, int depth, int turn,NodeII me,long fTime, int bestChoise, boolean tree){

		List<Move> l = null;
		Move answer = null;
		NodeII nAns = null;
		
		if(depth == 0)
			return answer;
		for(int i = 0; i < board.getDimention() ; i++){
			for( int j = 0; j < board.getDimention() ; j++){
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
						
						if(bestChoise != 0){
							if(game.getTurn() == 2){
								if(bestChoise>l.get(m).getValue()){
									nAns.setColour("grey");
									me.link(nAns);
									continue;
								}else{
									bestChoise = l.get(m).getValue();
								}
							}else{
								if(bestChoise<l.get(m).getValue()){
									nAns.setColour("grey");
									me.link(nAns);
									continue;
								}else{
									bestChoise = l.get(m).getValue();
								}
							}							
						}
						
						Move resp = mm2(aGame,aGame.board,depth-1,turn,nAns, fTime, bestChoise,tree);
						
						if(answer == null){
							answer = l.get(m);
							if(bestChoise != 0){ //que estoy con poda
								bestChoise = l.get(m).getValue();
							}
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
						
						if( System.currentTimeMillis() > fTime){ //es como si terminara aca
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
							
							if ( tree ){
								DotGenerator.export(me);
							}
							
							return me.chosen.getMove();
						}
						
					}
				}
			}
		}
		for (NodeII n : me.children) {
			if(me.chosen == null)
				me.chosen = n;
			if(game.getTurn() == 2){
				me.chosen.setShape("ellipse");
				if(me.chosen.getValue()>n.getMove().getValue()){
					me.chosen = n;
				}
			}else{
				me.chosen.setShape("box");
				if(me.chosen.getValue()<n.getMove().getValue()){
					me.chosen = n;
				}
			}
		}
		if(turn == 1)
			turn = 2;
		else
			turn = 1;
		
		me.chosen.setColour("red");
		DotGenerator.export(me);
		return me.chosen.getMove();
	}


}
