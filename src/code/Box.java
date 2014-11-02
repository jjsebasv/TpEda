package code;

import exceptions.IllegalPieceException;

public class Box {
	
	private int value;
	private int fila;
	private int columna;
	private Piece piece;
	
	
	public Box(int value, int fil, int col, char c) throws IllegalPieceException{
		this.value = value;
		this.piece = new Piece(c);
	}

	public int getValue(){
		return this.value;
	}
	
	public boolean isEmpty(){
		return this.piece.getC() == '0';
	}
	
	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public int getColumna() {
		return columna;
	}

	public void setColumna(int columna) {
		this.columna = columna;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public void setValue(int value) {
		this.value = value;
	}


	public String toString() {
		return "(" + fila + ", " + columna + ")";
	}
	
	public boolean isEnemy() {
		return piece.getPlayer().getTurn() == 2;
	}
	
}
