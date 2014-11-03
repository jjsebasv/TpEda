package minimax;

import java.util.ArrayList;
import java.util.List;

import code.Board;
import code.Box;
import code.Move;

public class NodeII {
	
	private Move move; //el movimiento que me trajo
	public NodeII chosen;
	private int value; //el valor que tengo
	public Board board; //el board que tengo
	public List<NodeII> children;
	
	
	
	// ------------------------------------ CONSTRUCTORES ------------------------------------ //
	

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

	// ------------------------------------ METODOS   ------------------------------------ //
	
	public void link(NodeII other){
		
		this.children.add(other);
	}

	public String toString(){
		return "node value: "+this.getValue();
	}
	
	// ------------------------------------ GETTERS Y SETTERS  ------------------------------------ //
	
	public Move getMove() {
		return move;
	}

	public void setMove(Move move) {
		this.move = move;
	}


	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
