package code;

public class Move {
	
	private int value;
	private Board board;
	
	public Move(Board board, int value){
		this.value = value;
		this.board = board;
	}

	public int getValue(){
		return this.value;
	}
	
	public void newValue(int nValue){
		this.value = nValue;
	}
	
	public Board getBoard(){
		return this.board;
	}
	
}
