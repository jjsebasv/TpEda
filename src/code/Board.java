package code;

import java.util.ArrayList;

public class Board {

	private int rows;
	private int cols;
	private ArrayList<ArrayList<Box>> board;

	public Board(int r, int c) {
		this.rows = r;
		this.cols = c;
		this.board = new ArrayList<>();
		this.createBoard();
	}

	public Box getBox(int x, int y) {
		return board.get(x).get(y);

	}

	private void createBoard() {
		int value = 0;
		for (int i = 0; i < this.rows; i++) {
			Box auxB;
			ArrayList<Box> aux = new ArrayList<>();
			for (int j = 0; j < this.cols; j++) {
				if (i == (int) this.rows / 2 && j == (int) this.cols / 2) {
					value = this.rows * this.cols;
					auxB = new Box(value, 'K');
				} else {
					value = Math.min((this.rows - 1) - i, i)
							+ Math.min((this.cols - 1) - j, j);
					if (((i == 0 || i == this.rows - 1) && (j <= this.cols / 2 + 1 && j >= this.cols / 2 - 1))
							|| ((j == 0 || j == this.cols - 1) && (i <= this.rows / 2 + 1 && i >= this.rows / 2 - 1))
							|| ((i == 1 || i == this.rows - 2) && j == this.cols / 2)
							|| ((j == 1 || j == this.cols - 2) && i == this.rows / 2)) {
						auxB = new Box(value, 'E');
					} else if ((i <= (this.rows / 2 + 1))
							&& (i >= (this.rows / 2 - 1))
							&& (j <= (this.cols / 2 + 1))
							&& (j >= (this.cols / 2 - 1))) {
						auxB = new Box(value, 'G');
					} else {
						auxB = new Box(value, '-');
					}

				}
				aux.add(auxB);
			}
			this.board.add(aux);
		}
	}

	public void move(int iFrom, int jFrom, int iTo, int jTo) { // Esto tendría
																// que devolver
																// un Board?
		if (belongsToRows(iFrom) && belongsToRows(iTo) && belongsToCols(jFrom)
				&& belongsToCols(iTo) && !getBox(iFrom, jFrom).isEmpty()
				&& getBox(iTo, jTo).isEmpty()) {
				
			getBox(iTo, jTo).setCharacter(getBox(iFrom,jFrom).getCharacter());
			getBox(iFrom, jFrom).setCharacter('-');
		}
	}

	public void printBorad() {
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				System.out.print(getBox(i, j).getCharacter() + " ");
			}
			System.out.println();
		}

	}

	private boolean belongsToRows(int x) {
		return (x >= this.rows || x < 0);
	}

	private boolean belongsToCols(int y) {
		return (y >= this.rows || y < 0);
	}

}
