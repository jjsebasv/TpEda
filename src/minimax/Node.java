package minimax;

import code.Move;

public class Node {
	
	public Move move;
	private int figure;
	private int colour;
	private Node chosenNext;
	private boolean prune = false;
	
	
	
	
	public void setFigure(int i) {
		// TODO Auto-generated method stub
		this.figure = i;
	}
	
	public void setColour(int c){
		
	}
	
	public void setChosen(Node cn){
		this.chosenNext = cn;
	}
	
	public void setPrune(){
		this.prune = true;
	}

}
