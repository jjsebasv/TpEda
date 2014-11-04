package code;

public class Move {
	
	private int value;
	private Board board;
	private int iFrom;
	private int jFrom;
	private int iTo;
	private int jTo;
	private int turn;
	
	// ------------------------------------ CONSTRUCTORES ------------------------------------ //
	
	public Move(Board board, int value,int iFrom, int jFrom, int iTo, int jTo, int turn){
		this.value = value;
		this.board = board;
		this.iFrom = iFrom;
		this.iTo = iTo;
		this.jFrom = jFrom;
		this.jTo = jTo;
		this.turn = turn;
	}
	
	
	// ------------------------------------ METODOS  ------------------------------------ //
	
	/*
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
	*/
	
	// ------------------------------------ GETTERS Y SETTERS ------------------------------------ //
	
	public int getIfrom(){
		return this.iFrom;
	}
	
	public int getIto(){
		return this.iTo;
	}
	
	public int getJfrom(){
		return this.jFrom;
	}
	
	public int getJTo(){
		return this.jTo;
	}
	
	public int getTurn(){
		return this.turn;
	}
	
	public Move(int value){
		this.value = value;
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
	
	public void setBoard(Board board){
		this.board = board;
	}
	
	public String toString(){
		int x = iFrom;
		int y = jFrom;
		int i = iTo;
		int j = jTo;
		return "turn: "+turn+"("+x+","+y+")("+i+","+j+")";
	}
}
