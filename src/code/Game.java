package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.naming.directory.InvalidAttributesException;

import minimax.minimax;

public class Game {

	public Board board;
	private boolean visual;
	private boolean tree;
	private boolean prune;
	private long maxtime;
	private int depth;
	private int turn;
	private String file;
	
	public Game(String file, boolean visual, long maxtime, int depth, boolean tree, boolean prune, int turn){
		this.visual = visual;
		this.maxtime = maxtime;
		this.depth = depth;
		this.tree = tree;
		this.prune = prune;
		this.file = file;
		this.turn = turn;
		this.board = new Board(file);
		
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
 					fw.write(board.getBox(i, j).getCharacter());
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
	
	public Game duplicate(){
		int aTurn;
		if( turn == 1 )
			aTurn = 2;
		else
			aTurn = 1;
		return new Game(file, visual,maxtime, depth, tree, prune,aTurn);
	}
	
	public void setTurn(int turn) {
		this.turn = turn;
	}

	public void exeMove(Move m){
		this.board = m.getBoard();
	}
	

	public void move(int fil, int col, int fil2, int col2) throws Exception {
		board = board.move(board, fil,col,fil2, col2);	
		this.turn = 2;
		board.printBoard();
		System.out.println("-- LE TOCA MOVER A LA PC --");
		board = minimax.minMax(this, depth, prune, maxtime);
		if(board == null)
			System.out.println("ES NULL");
		board.printBoard();
		System.out.println("-------");
		this.turn = 1;
	}
	
	
	
}
