package code;

public class Box {
	
	private int value;
	private boolean empty;
	private char character;
	private int side;
	
	public Box(int value, char cc){
		this.value = value;
		if( cc == '-'){
			this.side = 0;
			this.empty = true;
		}
		else{
			this.empty = false;
			if(cc == 'K' || cc == 'G'){
				this.side = 1;
			}else{
				this.side = 2;
			}
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
	
	public void setEmpty(boolean b){
		this.empty = b;
	}
	
	public int getSide(){
		return this.side;
	}
	
	public void setSide(int s){
		this.side = s;
	}
}
