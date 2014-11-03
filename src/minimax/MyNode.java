package minimax;

import code.Move;

public class MyNode {

	public Move move;
	public MyNode next;
	
	public MyNode(Move m){
		this.move = m;
	}

	public Move getMove() {
		return move;
	}

	public void setMove(Move move) {
		this.move = move;
	}

	public MyNode getNext() {
		return next;
	}

	public void setNext(MyNode next) {
		this.next = next;
	}
	
	
}
