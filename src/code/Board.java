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
	
	public Board(int d ){
		this.dimention = d;
		this.board = new Box[dimention][dimention];
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
					switch (line[j]) {
					case 'N':
						board[i][j] = new Box(dimention*indexi + dimention * indexj,line[j],2,false);
						n++;
						break;
					case 'K':
						board[i][j] = new Box(dimention*indexi + dimention * indexj,line[j], 1, false);
						k++;
						break;
					case 'G':
						board[i][j] = new Box(dimention*indexi + dimention * indexj,line[j],1,false);
						g++;
						break;
					default:
						board[i][j] = new Box(dimention*indexi + dimention * indexj,line[j],0,true);
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

	public Board move( int iFrom, int jFrom, int iTo, int jTo) throws Exception{ 
		if ( validateMove(iFrom, jFrom, iTo, jTo)) {	
			swap(this, iFrom, jFrom, iTo, jTo);
			this.board[iFrom][jFrom].setEmpty(true);
			if((iTo == 0 && jTo == 0) || (iTo == this.dimention -1 && jTo == 0) || (iTo == 0 && jTo == this.dimention - 1) || (iTo == this.dimention-1 && jTo == this.dimention-1)){
				throw new WinGameException();
			}
			eat(this, iTo, jTo);
			return this;
		}
		throw new InvalidMoveException();
	}
	
	private void swap(Board board, int iF, int jF, int iT, int jT) {
		if ( board != null ) {
		Box from = board.getBox(iF, jF);
		Box to = board.getBox(iT, jT);
		to.setCharacter(from.getCharacter());
		to.setSide(from.getSide());
		to.setEmpty(false);
		from.setCharacter('0');
		from.setSide(0);
		from.setEmpty(true);
		}
	}
	
	public Board duplicate(){
		
		
		Box[][] aux = new Box[dimention][dimention];
		for (int i = 0; i < this.getDimention(); i++) {
			for (int j = 0; j < this.getDimention(); j++) {
				aux[i][j] = getBox(i, j);
			}
		}
		Board auxBoard = new Board(this.getDimention());
		auxBoard.setBoard(aux);
		
		System.out.println("-------- duplicate ---------");
		System.out.println("auxBoard");
		auxBoard.printBoard();
		System.out.println();
		System.out.println("------------------------------");
		
		
		return auxBoard;
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


	
	private boolean validateMove(int iF, int jF, int iT, int jT){
		
		//System.out.println("-----------------");
		//System.out.println("FROM ("+iF+","+jF+") TO ("+iT+","+jT+")");
		//printBoard();
		
		if( iF != iT && jF != jT){// no son en linea recta
			//System.out.println("no son en linea r");
			return false;
		}
		if (iF == iT && jF == jT) { // mismo lugar
			//System.out.println("mismo lugar");
			return false;
		}
		if (!belongsToRows(iF) || !belongsToRows(iT)
				|| !belongsToCols(jF) // celdas habilitadas
				|| !belongsToCols(iT) || getBox(iF, jF).isEmpty()
				|| !getBox(iT, jT).isEmpty()) {
			return false;
		}
		if ( board[iF][jF].getCharacter() == 'N' && (iT == 0 || iT == dimention - 1)  && ( jT == 0 || jT == dimention - 1 ) ){
			//System.out.println("castillo");
			return false;
		}

		
		if( iF < iT){ // significa que se mueve verticalmente de arriba hacia abajo
			for(int i = iF+1; i <= iT; i++){
				if(!getBox(i, jF).isEmpty()) //camino obstruido en columna
					return false;
			}
		}
		
		if( iF > iT){ // significa que se mueve verticalmente de abajo hacia arriba
			for(int i = iF-1; i >= iT; i--){
				if(!getBox(i,jF).isEmpty()) //camino obstruido en columna
					return false;
			}	
		}
		
		if( jF < jT){ // significa que se mueve horizontalmente de izquierda a derecha
			for(int j = jF+1; j <= jT; j++){
				if(!getBox(iF, j).isEmpty()) //camino obstruido en fila
					return false;
			}
		}
		
		if( jF > jT){ // significa que se mueve horizontalmente de derecha a izquieda
			for(int j = jF-1; j >= jT; j--){
				if(!getBox(iF,j).isEmpty()) //camino obstruido en fila
					return false;
			}	
		}
		
		return true;
	}

	private void eat(Board board, int x, int y) throws Exception {
		Box aux = board.getBox(x, y);
		if (board.belongsToRows(x - 1) && board.getBox(x - 1, y).getSide() != aux.getSide()
				&& board.getBox(x - 1, y).getSide() != 0 && !board.isKing(x - 1, y)) {
			if (board.belongsToRows(x - 2)
					&& board.getBox(x - 2, y).getSide() == aux.getSide()) {
				eliminate(board.getBox(x - 1, y));
			}
		}
		if (board.belongsToRows(x + 1) && board.getBox(x + 1, y).getSide() != aux.getSide()
				&& board.getBox(x + 1, y).getSide() != 0 && !board.isKing(x + 1, y)) {
			if (board.belongsToRows(x + 2)
					&& board.getBox(x + 2, y).getSide() == aux.getSide()) {
				eliminate(board.getBox(x + 1, y));
			}
		}
		if (board.belongsToCols(y - 1) && board.getBox(x, y - 1).getSide() != aux.getSide()
				&& board.getBox(x, y - 1).getSide() != 0 && !board.isKing(x, y - 1)) {
			if (board.belongsToCols(y - 2)
					&& board.getBox(x, y - 2).getSide() == aux.getSide()) {
				eliminate(board.getBox(x, y - 1));
			}
		}
		if (board.belongsToCols(y + 1) && board.getBox(x, y + 1).getSide() != aux.getSide()
				&& board.getBox(x, y + 1).getSide() != 0 && !board.isKing(x, y + 1)) {
			if (board.belongsToCols(y + 2)
					&& board.getBox(x, y + 2).getSide() == aux.getSide()) {
				eliminate(board.getBox(x, y + 1));
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
	
	private int enemies(int x, int y){
		int acum = 0;
		Box cell = getBox(x, y);
		ArrayList<Box> neig = new ArrayList<>();
		neig.addAll(Arrays.asList(getBox(x - 1, y), getBox(x + 1, y),
				getBox(x, y - 1), getBox(x, y + 1)));
		for (Box box : neig) {
			if (box != null && box.getSide() != cell.getSide()
					&& box.getSide() != 0) {
				acum++;
			}
		}
		
		return acum;
	}
	
	public List<Move> getMoves(int x, int y){
		List<Move> l = new ArrayList<>();
		Board auxBoard = null;
		for(int i = 0; i < this.dimention; i++){
			for(int j = 0; j < this.dimention; j++){
				int value;
				if( getBox(x, y).getCharacter() == 'K' )
					value = getBox(i, j).getValue();
				else if( getBox(x,y).getCharacter() == 'G')
					value = enemies(i,j);
				else 
					value = getBox(i,i).getValue();
				if( validateMove(x, y, i, j) ){
					System.out.println("VALIDO EL MOVIMINETO");
					try {
						auxBoard = move( x, y, i, j);
						System.out.println(auxBoard == null);
						l.add(new Move(auxBoard, value));
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
						//e.printStackTrace();
						System.out.println("invalid move");
						continue;
					};
				}
			}
		}
		//System.out.println("posibles movimientos");
		//System.out.println(l);
		//System.out.println("--------");
		return l;
	}
	
	
}

