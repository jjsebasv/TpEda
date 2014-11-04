package code;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import exceptions.EndGameException;
import exceptions.IllegalPieceException;
import exceptions.InvalidArgumentsException;
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
	private boolean finished;
	
	// ------------------------------------ CONSTRUCTORES ------------------------------------ // 
	
	public Game(Board board, boolean visual, long maxtime, Integer depth, boolean tree, boolean prune, int turn){
		this.finished = false;
		this.board = board;
		this.visual = visual;
		this.maxtime = maxtime * 1000;
		this.depth = depth;
		this.tree = tree;
		this.prune = prune;
		this.turn = turn;
		if ( visual ){
			this.tree = false;
		}
	}
	
	public Game(String file, boolean visual, long maxtime, int depth, boolean tree, boolean prune, int turn){
		this.visual = visual;
		this.maxtime = maxtime * 1000;
		this.depth = depth;
		this.tree = tree;
		this.prune = prune;
		this.file = file;
		this.turn = turn;
		this.finished = false;
		this.board = new Board(file);
		if ( visual ){
			this.tree = false;
		}
	}


	// ------------------------------------ METODOS  ------------------------------------ // 
	
	
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


	public void exeMove(Move m){
		this.board = m.getBoard();
		
	}
	

	public void move(int fil, int col, int fil2, int col2) throws InvalidMoveException, WinGameException, IllegalPieceException, EndGameException {
		
		if(this.board.getBox(fil, col).getPiece().getPlayer().getTurn() != this.turn){
			throw new InvalidMoveException();
		}else{
			this.board = board.move( fil,col,fil2, col2);	
			
			if(this.turn == 1){
				this.setTurn(2);
			}else if(this.turn == 2){
				this.setTurn(1);
			}

			if(depth == 0)
				depth = 2;
			if(maxtime == 0)
				maxtime = 1000000000;
			board = pcBehave.minimax(this, depth, prune, maxtime,tree);
			this.turn = 1;
		}
	
	}


	public Game copy() throws IllegalPieceException {
		Board newBoard = board.copyBoard(board);
		return new Game(newBoard, this.visual,this.maxtime, this.depth, this.tree, this.prune, this.turn);
	}
	
	public void parserMove(String string) throws InvalidMoveException, WinGameException, IllegalPieceException, EndGameException, InvalidArgumentsException{
		if ( string.length() != 10 ){
			throw new InvalidArgumentsException();
		}
		int x = Integer.valueOf(string.charAt(1)-'0');
		int y = Integer.valueOf(string.charAt(3)-'0');
		int i = Integer.valueOf(string.charAt(6)-'0');
		int j = Integer.valueOf(string.charAt(8)-'0');
		move(x,y,i,j);
	}
	
	// ------------------------------------ GETTERS Y SETTERS ------------------------------------ // 
	
	
	public void setTurn(int turn) {
		this.turn = turn;
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
	

	public int getTurn(){
		return this.turn;
	}

	public boolean isFinished() {
		return this.finished;
	}

	
}
