package minimax;

import java.util.ArrayList;
import java.util.List;

import code.Board;
import code.Move;

public class NodeII {


	private Move move; //el movimiento que me trajo
	public NodeII chosen;
	private int value; //el valor que tengo
	public Board board; //el board que tengo
	public List<NodeII> children;
	private String colour;
	private String shape;
	
	
	
	// ------------------------------------ CONSTRUCTORES ------------------------------------ //
	

	public NodeII getChosen() {
		return chosen;
	}

	public void setChosen(NodeII chosen) {
		this.chosen = chosen;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public List<NodeII> getChildren() {
		return children;
	}

	public void setChildren(List<NodeII> children) {
		this.children = children;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

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
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((board == null) ? 0 : board.hashCode());
		result = prime * result
				+ ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((chosen == null) ? 0 : chosen.hashCode());
		result = prime * result + ((move == null) ? 0 : move.hashCode());
		result = prime * result + value;
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NodeII other = (NodeII) obj;
		if (board == null) {
			if (other.board != null)
				return false;
		} else if (!board.equals(other.board))
			return false;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (chosen == null) {
			if (other.chosen != null)
				return false;
		} else if (!chosen.equals(other.chosen))
			return false;
		if (move == null) {
			if (other.move != null)
				return false;
		} else if (!move.equals(other.move))
			return false;
		if (value != other.value)
			return false;
		return true;
	}

}
