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

import exceptions.BoardOutOfBoundsException;
import exceptions.EndGameException;
import exceptions.IllegalPieceException;
import exceptions.InvalidMoveException;
import exceptions.WinGameException;

public class Board {

	private int dimention;
	private Box board[][];


	public Board(String file){
			if (file != null ){
				this.createBoard(file);
			}
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


	private void createBoard(String file) {
		try {
			
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
						board[i][j] = new Box(dimention*indexi + dimention * indexj,i,j,line[j]);
						break;
					case 'K':
						board[i][j] = new Box(dimention*indexi + dimention * indexj,i,j,line[j]);
						break;
					case 'G':
						board[i][j] = new Box(dimention*indexi + dimention * indexj,i,j,line[j]);
						break;
					default:
						board[i][j] = new Box(dimention*indexi + dimention * indexj,i,j,line[j]);
						break;
					}
				}
				l = br.readLine();
			}

			br.close();
			fr.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}


	public Board move( int iFrom, int jFrom, int iTo, int jTo) throws Exception{ 
		if ( validateMove(iFrom, jFrom, iTo, jTo)) {	
			swap(iFrom, jFrom, iTo, jTo);
			this.board[iFrom][jFrom].setPiece(new Piece('0'));
			if((iTo == 0 && jTo == 0) || (iTo == this.dimention -1 && jTo == 0) || (iTo == 0 && jTo == this.dimention - 1) || (iTo == this.dimention-1 && jTo == this.dimention-1)){
				throw new WinGameException();
			}
			eat(this, iTo, jTo);
			return this;
		}
		throw new InvalidMoveException();
	}
	
	private void swap(int iF, int jF, int iT, int jT) throws IllegalPieceException {
		Box from = getBox(iF, jF);
		Box to = getBox(iT, jT);
		to.setPiece(from.getPiece());
		from.setPiece(new Piece('0'));
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
				System.out.print(getBox(i, j).getPiece().getC() + " ");
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

			Box from = this.getBox(iF, jF);
			Box to = this.getBox(iT, jT);
			
			// casillo vacio
			if ( from.isEmpty()){
				System.out.println("- ORIGEN VACIO");
				return false;
			}
			
			//mismo lugar
			if ( iF == iT && jT == jF ){
				System.out.print("- MISMO LUGAR");
				return false;
			}else{
			}
			
			// no es dentro del tablero
			if ( iF > dimention || jF > dimention || iT > dimention || jT > dimention ||
					 iF < 0 || jF < 0|| iT < 0 || jT < 0) {
				System.out.print("- MALA DIMENSION");
				return false;
			}

			// no es en linea reacta
			if ( iF != iT && jF != jT) {
				System.out.println(" - NO EN LINEA R ");
				return false;
			}

			// destino castillo pieza no es rey
			if (from.getPiece().getC() != 'K'
					&& ( (iT == 0 && jT == 0 ) || (iT == 0 && jT == dimention-1 ) || (iT == dimention-1 && jT == 0 ) || (iT == dimention-1 && jT == dimention -1 ) )) {
				System.out.print(" - DESTINO CASTILLO ");
				return false;

			}

			// castilla trono -> solo rey
			if (from.getPiece().getC() != 'K' && (iT == dimention/2 && jT == dimention/2 ) ) {
				System.out.print("- CASILLA REY");
				return false;
			}


			// camino vacio
			
			if( iF < iT){ // verticalmente de arriba a abajo
				if( dimention >= 13){
					if(getBox(iF, jF).getPiece().getC() == 'G'){
						if( iF< dimention/2 && iT> dimention/2)
							return false;
					}
				}
				
				for(int fil = iF+1; fil <= iT; fil++ ){
					if(!getBox(fil, jF).isEmpty())
						return false;
				}
			}
			
			if( iF > iT){ // verticalmente de abajo a arriba
				if( dimention >= 13){
					if(getBox(iF, jF).getPiece().getC() == 'G'){
						if( iF> dimention/2 && iT< dimention/2)
							return false;
					}
				}
				
				for(int fil = iT-1; fil >= iF; fil-- ){
					if(!getBox(fil, jF).isEmpty())
						return false;
				}
			}
			
			if( jF < jT){ // horizontalmente de izquierda a derecha
				if( dimention >= 13){
					if(getBox(iF, jF).getPiece().getC() == 'G'){
						if( jF< dimention/2 && jT> dimention/2)
							return false;
					}
				}
				
				for(int col = jF+1; col <= jT; col++ ){
					if(!getBox(iF, col).isEmpty())
						return false;
				}
			}
			
			if( jF > jT){ // horizontalmente de derecha a izquierda
				if( dimention >= 13){
					if(getBox(iF, jF).getPiece().getC() == 'G'){
						if( jF> dimention/2 && jT< dimention/2)
							return false;
					}
				}
				
				for(int col = jF-1; col >= jT; col-- ){
					if(!getBox(iF, col).isEmpty())
						return false;
				}
			}
			

		return true;
	}

	private void eat(Board board, int x, int y) throws Exception {
		Box aux = board.getBox(x, y);
		if (board.belongsToRows(x - 1) && board.getBox(x - 1, y).getPiece().getPlayer().getTurn() != aux.getPiece().getPlayer().getTurn()
				&& board.getBox(x - 1, y).getPiece().getPlayer().getTurn() != 0 && !board.isKing(x - 1, y)) {
			if (board.belongsToRows(x - 2)
					&& board.getBox(x - 2, y).getPiece().getPlayer().getTurn() == aux.getPiece().getPlayer().getTurn()) {
				eliminate(board.getBox(x - 1, y));
			}
		}
		if (board.belongsToRows(x + 1) && board.getBox(x + 1, y).getPiece().getPlayer().getTurn() != aux.getPiece().getPlayer().getTurn()
				&& board.getBox(x + 1, y).getPiece().getPlayer().getTurn() != 0 && !board.isKing(x + 1, y)) {
			if (board.belongsToRows(x + 2)
					&& board.getBox(x + 2, y).getPiece().getPlayer().getTurn() == aux.getPiece().getPlayer().getTurn()) {
				eliminate(board.getBox(x + 1, y));
			}
		}
		if (board.belongsToCols(y - 1) && board.getBox(x, y - 1).getPiece().getPlayer().getTurn() != aux.getPiece().getPlayer().getTurn()
				&& board.getBox(x, y - 1).getPiece().getPlayer().getTurn() != 0 && !board.isKing(x, y - 1)) {
			if (board.belongsToCols(y - 2)
					&& board.getBox(x, y - 2).getPiece().getPlayer().getTurn() == aux.getPiece().getPlayer().getTurn()) {
				eliminate(board.getBox(x, y - 1));
			}
		}
		if (board.belongsToCols(y + 1) && board.getBox(x, y + 1).getPiece().getPlayer().getTurn() != aux.getPiece().getPlayer().getTurn()
				&& board.getBox(x, y + 1).getPiece().getPlayer().getTurn() != 0 && !board.isKing(x, y + 1)) {
			if (board.belongsToCols(y + 2)
					&& board.getBox(x, y + 2).getPiece().getPlayer().getTurn() == aux.getPiece().getPlayer().getTurn()) {
				eliminate(board.getBox(x, y + 1));
			}
		}
	}

	private void eliminate(Box other) throws IllegalPieceException {
		other.setPiece(new Piece('0'));;
	}

	private boolean isKing(int x, int y) throws Exception {
		Box cell = getBox(x, y);
		if ( cell.getPiece().getC() == 'K') {
			int count = 0;
			ArrayList<Box> neig = new ArrayList<>();
			neig.addAll(Arrays.asList(getBox(x - 1, y), getBox(x + 1, y),
					getBox(x, y - 1), getBox(x, y + 1)));
			for (Box box : neig) {
				if (box != null && box.getPiece().getPlayer().getTurn() != cell.getPiece().getPlayer().getTurn()
						&& box.getPiece().getPlayer().getTurn() != 0) {
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
			if (box != null && box.getPiece().getPlayer() != cell.getPiece().getPlayer()
					&& box.getPiece().getPlayer().getTurn() != 0) {
				acum++;
			}
		}
		
		return acum;
	}
	
	public List<Move> getMoves(int x, int y) {
		List<Move> l = new ArrayList<>();
		Board original = this;
		Board auxBoard = new Board(null);
		
		for(int i = 0; i < this.dimention; i++){
			for(int j = 0; j < this.dimention; j++){
				
				int value;
				//if( getBox(x, y).getPiece().getC() == 'K' )
				//	value = getBox(i, j).getValue();
				//else if( getBox(x, y).getPiece().getC() == 'G')
				//	value = enemies(i,j);
				//else 
					value = getBox(i,j).getValue();
					System.out.print("get move para: ("+x+","+y+")("+i+","+j+")");
				if( validateMove(x, y, i, j) ){
					System.out.println("- VALIDO EL MOVIMINETO");
					try {
						auxBoard = original;
						Box a = auxBoard.board[x][y];
						auxBoard.board[x][y] = auxBoard.board[i][j];
						auxBoard.board[i][j] = a;
						auxBoard.printBoard();
						l.add(new Move(auxBoard, value));
					} catch (Exception e) { // no se a que exception hace referencia
						System.out.println("invalid move");
					};
				}else{
					System.out.println(" - NO VALIDO");
				}
			}
		}
		return l;
	}
	
	public void printBoardValues() {
		for (int i = 0; i < this.dimention; i++) {
			for (int j = 0; j < this.dimention; j++) {
				System.out.print(getBox(i, j).getValue() + " ");
			}
			System.out.println();
		}

	}
	
	
	
}

