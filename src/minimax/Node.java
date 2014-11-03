package minimax;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

import code.Move;

public class Node {
	
	public Move move;
	private int figure;
	private int colour;
	private Node chosenNext;
	private boolean prune = false;
	public Deque<Node> next = new LinkedList<>();
	public String label;
	public int value;
	
	public Node(){
		
	}
	
	public Node(Move m){
		this.move = move;
	}
	
	
	public void Next(Move m){
		Node aux = new Node();
		aux.move = m;
		next.add(aux);
	}
	
	public void setFigure(int i) {
		// TODO Auto-generated method stub
		this.figure = i;
	}
	
	public void setColour(int c){
		this.colour = c;
	}
	
	public void setChosen(Node cn){
		this.chosenNext = cn;
	}
	
	public void setPrune(){
		this.prune = true;
	}

}
