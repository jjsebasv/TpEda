package code;

import java.io.File;
import java.io.FileReader;

public class Game {

	private Board board;
	private boolean visual;
	private boolean tree;
	private boolean prune;
	private int maxtime;
	private int depth;
	private int turn;
	
	public Game(String file, boolean visual, int maxtime, int depth, boolean tree, boolean prune){
		this.visual = visual;
		this.maxtime = maxtime;
		this.depth = depth;
		this.tree = tree;
		this.prune = prune;
		board = new Board(file);
	}

	
}
