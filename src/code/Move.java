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
	
}
