package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.naming.directory.InvalidAttributesException;

import exceptions.EndGameException;
import exceptions.IllegalPieceException;
import exceptions.InvalidMoveException;
import exceptions.WinGameException;

import minimax.pcBehave;


public class Game {

	public Board board;
	private boolean visual;
	private boolean tree;
	private boolean prune;
	private long maxtime;
	private int depth;
	private int turn;
	private String file;
	
	public Game(Board board, boolean visual, long maxtime, int depth, boolean tree, boolean prune, int turn){
		this.board = board;
		this.visual = visual;
		this.maxtime = maxtime;
		this.depth = depth;
		this.tree = tree;
		this.prune = prune;
		this.file = file;
		this.turn = turn;
	}
	
	public Game(String file, boolean visual, long maxtime, int depth, boolean tree, boolean prune, int turn){
		this.visual = visual;
		this.maxtime = maxtime;
		this.depth = depth;
		this.tree = tree;
		this.prune = prune;
		this.file = file;
		this.turn = turn;
		this.board = new Board(file);
		System.out.println("GAME: "+ turn);
		
		System.out.println("DEPTH : " + this.depth);
		System.out.println("MAXTIME: " + this.maxtime);
		
		
		/*if ( turn == 2 ){
			board = minimax.minMax(this, depth, prune,maxtime);
		}
		else{
			board = new Board(file);
		}
		this.turn = 1;*/
	}


	/*
	//private void getTurn(String file) {
		//try {
			//FileReader fr = new FileReader(file);
			//BufferedReader br = new BufferedReader(fr);
			//String l = br.readLine();
			//this.turn = Integer.valueOf(l.toCharArray()[0]);
			//if ( this.turn < 1 && turn > 2 ){
				System.out.println("f");
				br.close();
				throw new InvalidAttributesException();
			}	
			br.close();
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	
	public int getTurn(){
		return this.turn;
	}

	public void saveGame(){
		File fpw = new File(System.currentTimeMillis()+".txt");
		try {
 	        FileWriter fw = new FileWriter(fpw);
 	        fw.write(this.turn);
 	        fw.write("\n");	
 	        
 	        for (int i = 0; i < board.getDimention(); i++) {
 				for (int j = 0; j < board.getDimention(); j++) {
 					fw.write(board.getBox(i, j).getPiece().getC());
 				}
 				fw.write("\n");
 			}
 	        
 	        fw.write("\n");
 	        fw.close();
 	    } catch (IOException e) {
 	        // TODO Auto-generated catch block
 	        e.printStackTrace();
 	    }
	}
	
	public Game duplicate(Board b){
		int aTurn;
		if( turn == 1 )
			aTurn = 2;
		else
			aTurn = 1;
		
		//Board aux = new Box[dimention][dimention];
		Game gm = new Game(b, visual,maxtime, depth, tree, prune,aTurn);
		return gm;
	}
	
	public void setTurn(int turn) {
		this.turn = turn;
	}

	public void exeMove(Move m){
		this.board = m.getBoard();
		
	}
	

	public void move(int fil, int col, int fil2, int col2) throws Exception {
		System.out.println("turno del game: " + this.turn);
		
		if(this.board.getBox(fil, col).getPiece().getPlayer().getTurn() != this.turn){
			throw new InvalidMoveException();
		}else{
			this.board = board.move( fil,col,fil2, col2);	
			
			if(this.turn == 1){
				this.setTurn(2);
			}else if(this.turn == 2){
				this.setTurn(1);
			}
	
			board.printBoard();
			Integer p = new Integer(0);
			if ( prune ){
				p = 1;
			}else{
				p = 0;
			}
			
			
			System.out.println("-- LE TOCA MOVER A LA PC --");

			//board = pcBehave.minimax(this, 2, prune, System.currentTimeMillis()+maxtime);
			//board = MyMinimax.minimax(this, depth,p,null, System.currentTimeMillis()+maxtime).getBoard();
			board = pcBehave.minimax(this, 2, prune, System.currentTimeMillis()+maxtime);
			//board = MyMinimax.minimax(this, depth, prune, System.currentTimeMillis()+maxtime);
			board.printBoard();
			System.out.println("-- YA JUGO LA PC ---");
			this.turn = 1;

			//board = MyMinimax.minimax(this, depth,p,null, System.currentTimeMillis()+maxtime).getBoard();
			board = pcBehave.minimax(this, 2, prune, System.currentTimeMillis()+maxtime);
			//board.printBoard();
			//System.out.println("-- YA JUGO LA PC ---");
			//this.turn = 1;

	
		}
	
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public boolean isVisual() {
		return visual;
	}

	public void setVisual(boolean visual) {
		this.visual = visual;
	}

	public boolean isTree() {
		return tree;
	}

	public void setTree(boolean tree) {
		this.tree = tree;
	}

	public boolean isPrune() {
		return prune;
	}

	public void setPrune(boolean prune) {
		this.prune = prune;
	}

	public long getMaxtime() {
		return maxtime;
	}

	public void setMaxtime(long maxtime) {
		this.maxtime = maxtime;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Game copy() throws IllegalPieceException {
		Board newBoard = board.copyBoard(board);
		return new Game(board, this.visual,this.maxtime, this.depth, this.tree, this.prune, this.turn);
	}
	
}
