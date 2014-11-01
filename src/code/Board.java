package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.naming.directory.InvalidAttributesException;

import exceptions.EndGameException;
import exceptions.InvalidMoveException;
import exceptions.WinGameException;

public class Board {

	private int dimention;
	private Box board[][];


	public Board(String file){
		this.createBoard(file);
	}

	public Box getBox(int x, int y) {
		if (!belongsToRows(x) || !belongsToCols(y))
			return null;
		return board[x][y];	
	}
	
	
	public int getDimention() {
		return dimention;
	}

	public void setDimention(int dimention) {
		this.dimention = dimention;
	}

	public Box[][] getBoard() {
		return board;
	}

	public void setBoard(Box[][] board) {
		this.board = board;
	}

	public void addBoard(){
		
	}

	private void createBoard(String file) {
		try {
			int k = 0;
			int g = 0;
			int n = 0;
			
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String l = br.readLine();
			
			//creo el tablero
			l = br.readLine();
			char[] line = l.toCharArray();
			this.dimention = l.length();
			if ( dimention % 2 == 0 || dimention < 7 || dimention > 19){
				br.close();
				throw new InvalidAttributesException();
			}
			this.board = new Box[dimention][dimention];
			
			for (int i = 0; i < dimention; i++) {
					line = l.toCharArray();
				for (int j = 0; j < dimention; j++) {
					int indexi = ( i <= dimention/2 )? i : dimention-i-1;
					int indexj = ( j <= dimention/2 )? j : dimention-j-1;
					board[i][j] = new Box(dimention*indexi + dimention * indexj,line[j]);
					switch (line[j]) {
					case 'N':
						n++;
						break;
					case 'K':
						k++;
						break;
					case 'G':
						g++;
						break;
					}
				}
				l = br.readLine();
			}
			
			//valido el tablero
			validateBoard(k,n,g);
			br.close();
			fr.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	private void validateBoard(int k, int n, int g) throws InvalidAttributesException{	
		if ( k != 1 ){
			throw new InvalidAttributesException();
		}
		
		if ( (dimention <= 11 && ( g != 8 || n != 16))){
			throw new InvalidAttributesException();
		}
		else if ( dimention > 11 && (g != 12 || n != 24) ) {
			throw new InvalidAttributesException();
		}
	}

	public Board move(int iFrom, int jFrom, int iTo, int jTo) throws Exception{ 
		if ( validateMove(iFrom, jFrom, iTo, jTo)) {	
			System.out.println("move validado");
			swap(iFrom, jFrom, iTo, jTo);
			if((iTo == 0 && jTo == 0) || (iTo == this.dimention -1 && jTo == 0) || (iTo == 0 && jTo == this.dimention - 1) || (iTo == this.dimention-1 && jTo == this.dimention-1)){
				throw new WinGameException();
			}
			eat(iTo, jTo);
			return this;
		}
		throw new InvalidMoveException();
		
	}

	public void printBoard() {
		for (int i = 0; i < this.dimention; i++) {
			for (int j = 0; j < this.dimention; j++) {
				System.out.print(getBox(i, j).getCharacter() + " ");
			}
			System.out.println();
		}

	}

	private boolean belongsToRows(int x) {
		return (x < this.dimention && x >= 0);
	}

	private boolean belongsToCols(int y) {
		return (y < this.dimention && y >= 0);
	}

	private void swap(int iF, int jF, int iT, int jT) {
		Box from = getBox(iF, jF);
		Box to = getBox(iT, jT);

		to.setCharacter(from.getCharacter());
		to.setSide(from.getSide());
		to.setEmpty(false);
		from.setCharacter('0');
		from.setSide(0);
		from.setEmpty(true);

	}
	
	private boolean validateMove(int iF, int jF, int iT, int jT){
		if( iF != iT && jF != jT){// no son en linea recta
			System.out.println("no son en linea R");
			return false;
		}
		if (iF == iT && jF == jT) { // mismo lugar
			System.out.println("mismo lugar");
			return false;
		}
		if (!belongsToRows(iF) || !belongsToRows(iT)
				|| !belongsToCols(jF) // celdas habilitadas
				|| !belongsToCols(iT) || getBox(iF, jF).isEmpty()
				|| !getBox(iT, jT).isEmpty()) {
			System.out.println("fuera de las lineas o casillero ocupado");
			return false;
		}

		for (int i = iF; i < iT; i++) { // camino obstruido en la fila
			if ( i != iF && !getBox(i, jF).isEmpty()) {
				System.out.println("camino obstruido fila");
				return false;
			}
		}
		for (int j = jF; j <= jT; j++) { // camino obstruido en la columna
			if ( j != jF && !getBox(iT, j).isEmpty()) {
				System.out.println("camino obstruido col");
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
		other.setCharacter('0');
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
	
	
	public List<Move> getMoves(int x, int y){
		List<Move> l = new ArrayList<>();
		for(int i = 0; i < this.dimention; i++){
			for(int j = 0; j < this.dimention; j++){
				if( validateMove(x, y, i, j) ){
					try {
						Board auxBoard = move(x, y, i, j);
						l.add(new Move(auxBoard, getBox(i, j).getValue()));
					} catch (EndGameException eg) {
						// TODO Auto-generated catch block
						l.add(new Move(null, Integer.MAX_VALUE ) );
						continue;
					} catch (WinGameException wg) {
						// TODO Auto-generated catch block
						l.add(new Move(null, Integer.MIN_VALUE ) );
						continue;
					} catch (Exception e) { // no se a que exception hace referencia
						// TODO Auto-generated catch block
						e.printStackTrace();
						continue;
					};
				}
			}
		}
		return l;
	}
	
	
}

