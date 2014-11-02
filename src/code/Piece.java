package code;

import exceptions.IllegalPieceException;

public class Piece {

	private char c;
	private Player player;
	
	public Piece(char c) throws IllegalPieceException{
		this.c = c;
		this.player = new Player(getSide(c));
	}

	public char getC() {
		return c;
	}

	public void setC(char c) {
		this.c = c;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	private static int getSide(char c) throws IllegalPieceException {
		switch (c) {
		case 'K':
			return 1;
		case 'G':
			return 1;
		case 'N':
			return 2;
		case '0':
			return 0;
		default:
			throw new IllegalPieceException();
		}
	}
	
	
	
}
