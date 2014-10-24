package code;

import java.util.ArrayList;

public class Board {
	
	private int rows;
	private int cols;
	private ArrayList<ArrayList<Box>> board;
	
	public Board(int r, int c){
		this.rows = r;
		this.cols = c;
		this.board = new ArrayList<>();
		this.createBoard();
	}
	
	public Box getBox(int x, int y){
		return board.get(x).get(y);
		
	}
	
	
	private void createBoard(){
		int value = 0;
		for(int i = 0; i < this.rows; i++ ){
			ArrayList<Box> aux = new ArrayList<>();
			for(int j = 0; j < this.cols; j++){
				if( i == (int)this.rows/2 && j == (int)this.cols/2){
					value = this.rows * this.cols;
				}else {
					value = Math.min( (this.rows-1) - i , i) + Math.min((this.cols-1) - j, j);  
					
				}
				Box auxB = new Box(value);
				aux.add(auxB);
			}
			this.board.add(aux);
		}		
	}
	
	public void printBorad(){
		for(int i = 0; i < this.rows; i++){
			for(int j = 0; j < this.cols; j++){
				System.out.print(getBox(i, j).getValue() + " ");
			}
			System.out.println();
		}
		
	}

}
