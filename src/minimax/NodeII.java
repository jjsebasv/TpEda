package minimax;

import java.util.ArrayList;
import java.util.List;

import code.Board;
import code.Box;
import code.Move;

public class NodeII {
	
	public Move move; //el movimiento que me trajo
	public NodeII chosen;
	public int turn;
	private Box from;
	private Box to;
	private int value; //el valor que tengo
	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public Box getFrom() {
		return from;
	}

	public void setFrom(Box from) {
		this.from = from;
	}

	public Box getTo() {
		return to;
	}

	public void setTo(Box to) {
		this.to = to;
	}

	public Board board; //el board que tengo
	public List<NodeII> children;
	
	public NodeII(Board board, Box form, Box to, int turn){
		this.board = board;
		children = new ArrayList<>();
		this.from = from;
		this.to = to;
		this.turn = turn;
	}
	
	public NodeII(Board board, int val, Box from, Box to, int turn){
		this.board = board;
		this.value = val;
		children = new ArrayList<>();
		this.from = from;
		this.to = to;
		this.turn = turn;
	}
	
	public NodeII(Move m, Box from, Box to, int turn){
		this.move = m;
		this.board = m.getBoard();
		this.value = m.getValue();
		children = new ArrayList<>();
		this.from = from;
		this.to = to;
		this.turn = turn;
	}
	
	
	// sin box
	
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


	public void setValue(int value) {
		this.value = value;
	}

	public void link(NodeII other){
		
		this.children.add(other);
	}

	public int getValue() {
		return value;
	}

}
