package code;

import java.util.ArrayList;
import java.util.Arrays;

import javax.naming.directory.InvalidAttributesException;

import exceptions.EndGameException;
import exceptions.WinGameException;

public class Board {

	private int rows;
	private int cols;
	private Box board[][];

	public Board(int r, int c) throws Exception{
		if (r >= 7 && c >= 7) {
			this.rows = r;
			this.cols = c;
			this.board = new Box[r][c];
			this.createBoard();
		}else{
			throw new InvalidAttributesException();
		}
	}

	public Box getBox(int x, int y) {
		if (!belongsToRows(x) || !belongsToCols(y))
			return null;
		return board[x][y];

	}

	private void createBoard() {
		int value = 0;
		for (int i = 0; i < this.rows; i++) {
			Box auxB;
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
				this.board[i][j] = auxB;
			}
		}
	}

	public void move(int iFrom, int jFrom, int iTo, int jTo) throws Exception { // Esto
																				// tendría
		// que devolver
		// un Board?
		if (validateMove(iFrom, jFrom, iTo, jTo)) {
			swap(iFrom, jFrom, iTo, jTo);
			if((iTo == 0 && jTo == 0) || (iTo == this.rows -1 && jTo == 0) || (iTo == 0 && jTo == this.cols - 1) || (iTo == this.rows-1 && jTo == this.cols-1)){
				throw new WinGameException();
			}
			eat(iTo, jTo);
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
		return (x < this.rows && x >= 0);
	}

	private boolean belongsToCols(int y) {
		return (y < this.rows && y >= 0);
	}

	private void swap(int iF, int jF, int iT, int jT) {
		Box from = getBox(iF, jF);
		Box to = getBox(iT, jT);

		to.setCharacter(from.getCharacter());
		to.setSide(from.getSide());
		to.setEmpty(false);
		from.setCharacter('-');
		from.setSide(0);
		from.setEmpty(true);

	}

	private boolean validateMove(int iF, int jF, int iT, int jT) {
		if (iF != iT && jF != jT) {// no son en línea recta
			return false;
		}
		if (iF == iT && jF == jT) { // mismo lugar
			return false;
		}
		if (!belongsToRows(iF) || !belongsToRows(iT)
				|| !belongsToCols(jF) // celdas habilitadas
				|| !belongsToCols(iT) || getBox(iF, jF).isEmpty()
				|| !getBox(iT, jT).isEmpty()) {
			return false;
		}
		for (int i = iF; i < iT; i++) { // camino obstruído en la fila
			if (!getBox(i, jF).isEmpty()) {
				return false;
			}
		}
		for (int j = jF; j < jT; j++) { // camino obstruído en la columna
			if (!getBox(iT, j).isEmpty()) {
				return false;
			}
		}

		return true;
	}

	private void eat(int x, int y) throws Exception {
		Box aux = getBox(x, y);
		if (belongsToRows(x - 1) && getBox(x - 1, y).getSide() != aux.getSide()
				&& getBox(x - 1, y).getSide() != 0 && !isKing(x - 1, y)) {
			if (belongsToRows(x - 2)
					&& getBox(x - 2, y).getSide() == aux.getSide()) {
				eliminate(getBox(x - 1, y));
			}
		}
		if (belongsToRows(x + 1) && getBox(x + 1, y).getSide() != aux.getSide()
				&& getBox(x + 1, y).getSide() != 0 && !isKing(x + 1, y)) {
			if (belongsToRows(x + 2)
					&& getBox(x + 2, y).getSide() == aux.getSide()) {
				eliminate(getBox(x + 1, y));
			}
		}
		if (belongsToCols(y - 1) && getBox(x, y - 1).getSide() != aux.getSide()
				&& getBox(x, y - 1).getSide() != 0 && !isKing(x, y - 1)) {
			if (belongsToCols(y - 2)
					&& getBox(x, y - 2).getSide() == aux.getSide()) {
				eliminate(getBox(x, y - 1));
			}
		}
		if (belongsToCols(y + 1) && getBox(x, y + 1).getSide() != aux.getSide()
				&& getBox(x, y + 1).getSide() != 0 && !isKing(x, y + 1)) {
			if (belongsToCols(y + 2)
					&& getBox(x, y + 2).getSide() == aux.getSide()) {
				eliminate(getBox(x, y + 1));
			}
		}
	}

	private void eliminate(Box other) {
		other.setCharacter('-');
		other.setEmpty(true);
		other.setSide(0);
	}

	private boolean isKing(int x, int y) throws Exception {
		Box cell = getBox(x, y);
		if (cell.getCharacter() == 'K') {
			int count = 0;
			ArrayList<Box> neig = new ArrayList<>();
			neig.addAll(Arrays.asList(getBox(x - 1, y), getBox(x + 1, y),
					getBox(x, y - 1), getBox(x, y + 1)));
			for (Box box : neig) {
				if (box != null && box.getSide() != cell.getSide()
						&& box.getSide() != 0) {
					count++;
				}
			}
			if (count >= 3) {
				eliminate(cell);
				throw new EndGameException();
			}
		}
		return false;
	}
}
