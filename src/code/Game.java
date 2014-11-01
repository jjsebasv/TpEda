package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.naming.directory.InvalidAttributesException;

public class Game {

	public Board board;
	private boolean visual;
	private boolean tree;
	private boolean prune;
	private int maxtime;
	private int depth;
	private int turn;
	private String file;
	
	public Game(String file, boolean visual, int maxtime, int depth, boolean tree, boolean prune){
		this.visual = visual;
		this.maxtime = maxtime;
		this.depth = depth;
		this.tree = tree;
		this.prune = prune;
		this.file = file;
		getTurn(file);
		board = new Board(file);
	}

	private void getTurn(String file) {
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String l = br.readLine();
			this.turn = Integer.valueOf(l.toCharArray()[0]);
			if ( this.turn < 1 && turn > 2 ){
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
		return new Game(file, visual,maxtime, depth, tree, prune);
	}
	
	public void exeMove(Move m){
		this.board = m.getBoard();
	}
	
}
