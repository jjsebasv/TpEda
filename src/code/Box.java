package code;

public class Box {
	
	private int value;
	private boolean empty;
	private char character;
	
	
	public Box(int value, char cc){
		this.value = value;
		if( cc == '-'){
			this.empty = true;
		}
		else{
			this.empty = false;
		}
		this.character = cc;
	}

	public int getValue(){
		return this.value;
	}
	
	public boolean isEmpty(){
		return this.empty;
	}
	
	public char getCharacter(){
		return this.character;
	}
	
	public void setCharacter(char cc){
		this.character = cc;
	}
	
}
