package code;

public class Move {
	
	private int value;
	private Board board;
	private Box from;
	private Box to;
	private int turn;
	
	public Move(Board board, int value, Box from, Box to, int turn){
		this.value = value;
		this.board = board;
		this.from = from;
		this.to = to;
		this.turn = turn;
	}
	
	public Box getFrom(){
		return this.from;
	}
	
	public Box getTo(){
		return this.to;
	}
	
	public int getTurn(){
		return this.turn;
	}
	
	public Move(int value){
		this.value = value;
	}
	
	public Move(){
		
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
	
	public void setValue(int v){
		this.value = v;
	}
	
	public String toString(){
		String string = "";
		for (int i = 0; i < board.getDimention(); i++) {
			for (int j = 0; j < board.getDimention(); j++) {
				string += board.getBoard()[i][j].getPiece().getC();
			}
			string += String.valueOf('\n');
		}
		return string;
	}
	
	
	public void setBoard(Board board){
		this.board = board;
	}
}
