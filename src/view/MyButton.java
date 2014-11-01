package view;

import javax.swing.JButton;

public class MyButton extends JButton {

	private int fil;
	private int col;
	private char figure;


	public MyButton(int i, int j, char figure2) {
		this.fil = i;
		this.col = j;
		this.figure = figure2;
	}

	public int getFil() {
		return this.fil;
	}

	public void setFil(int fil) {
		this.fil = fil;
	}

	public int getCol() {
		return this.col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public char getFigure() {
		return figure;
	}

	public void setFigure(char figure) {
		this.figure = figure;
	}
	
}
