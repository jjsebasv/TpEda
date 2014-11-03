package minimax;

import code.Board;

public class NodeII {
	
	public NodeII chosen;
	public int value;
	public Board board;
	
	public NodeII(Board board){
		this.board = board;
		
	}
	
	public NodeII(Board board, int val){
		this.board = board;
		this.value = val;
	}

}
