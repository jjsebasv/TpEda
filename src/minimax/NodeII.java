package minimax;

import java.util.ArrayList;
import java.util.List;

import code.Board;
import code.Move;

public class NodeII {
	
	public Move move; //el movimiento que me trajo
	public NodeII chosen;
	public int value; //el valor que tengo
	public Board board; //el board que tengo
	public List<NodeII> children;
	
	public NodeII(Board board){
		this.board = board;
		children = new ArrayList<>();
		
	}
	
	public NodeII(Board board, int val){
		this.board = board;
		this.value = val;
		children = new ArrayList<>();
	}
	
	public NodeII(Move m){
		this.move = m;
		this.board = m.getBoard();
		this.value = m.getValue();
		children = new ArrayList<>();
	}
	
	public void link(NodeII other){
		
		this.children.add(other);
	}

}
