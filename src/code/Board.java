package code;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.naming.directory.InvalidAttributesException;

import exceptions.EndGameException;
import exceptions.IllegalPieceException;
import exceptions.InvalidMoveException;
import exceptions.WinGameException;

public class Board {

	public int value = 0;
	private int dimention;
	private Box board[][];

	// ------------------------------------ CONSTRUCTORES ------------------------------------ // 
	
	public Board(int dimention){
		this.board = new Box[dimention][dimention];
		this.dimention = dimention;
	}

	public Board(String file){
			if (file != null ){
				this.createBoard(file);
			}
	}


	// ------------------------------------ METODOS  ------------------------------------ // 

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


	public Board move( int iFrom, int jFrom, int iTo, int jTo) throws WinGameException, InvalidMoveException, IllegalPieceException, EndGameException { 
		if ( validateMove(iFrom, jFrom, iTo, jTo)) {	
			swap(iFrom, jFrom, iTo, jTo);
			this.board[iFrom][jFrom].setPiece(new Piece('0'));
			if((iTo == 0 && jTo == 0) || (iTo == this.dimention -1 && jTo == 0) || (iTo == 0 && jTo == this.dimention - 1) || (iTo == this.dimention-1 && jTo == this.dimention-1)){
				if(getBox(iTo, jTo).getPiece().getPlayer().getTurn() == 2){
					value = Integer.MIN_VALUE;
				}
				else
					value = Integer.MAX_VALUE;
				throw new WinGameException();
			}
			int num = eat(this, iTo, jTo);
			int num2 = enemies(iTo,jTo);
			if(getBox(iTo, jTo).getPiece().getPlayer().getTurn() == 1){
				num2 = (-num2);
				num = (-num);
			}
			value += num;
			value += num2;
			
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
		
		return auxBoard;
	}

	public void printBoard() {
		for (int i = 0; i < this.dimention; i++) {
			if ( i == 0 ){
				System.out.print("   ");
				for (int k = 0; k < this.dimention; k++) {
					System.out.print(k+" ");
				}
				System.out.println();
				System.out.println();
			}
			for (int j = 0; j < this.dimention; j++) {
				if (j == 0) {
					System.out.print(i+"  ");
				}
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

			if ( from.isEmpty() ){
				return false;
			}
			
			// destino ocupado
			if ( !to.isEmpty()){
				return false;
			}
			
			//mismo lugar
			if ( iF == iT && jT == jF ){
				return false;
			}else{
			}
			
			// no es dentro del tablero
			if ( iF > dimention || jF > dimention || iT > dimention || jT > dimention ||
					 iF < 0 || jF < 0|| iT < 0 || jT < 0) {
				return false;
			}

			// no es en linea reacta
			if ( iF != iT && jF != jT) {
				return false;
			}

			// destino castillo pieza no es rey
			if (from.getPiece().getC() != 'K'
					&& ( (iT == 0 && jT == 0 ) || (iT == 0 && jT == dimention-1 ) || (iT == dimention-1 && jT == 0 ) || (iT == dimention-1 && jT == dimention -1 ) )) {
				return false;

			}

			// castilla trono -> solo rey
			if (from.getPiece().getC() != 'K' && (iT == dimention/2 && jT == dimention/2 ) ) {
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

	private int eat(Board board, int x, int y) throws IllegalPieceException, EndGameException {
		Box aux = board.getBox(x, y);
		int acum = 0;
		if (board.belongsToRows(x - 1) && board.getBox(x - 1, y).getPiece().getPlayer().getTurn() != aux.getPiece().getPlayer().getTurn()
				&& board.getBox(x - 1, y).getPiece().getPlayer().getTurn() != 0 && !board.isKing(x - 1, y)) {
			if (board.belongsToRows(x - 2)
					&& board.getBox(x - 2, y).getPiece().getPlayer().getTurn() == aux.getPiece().getPlayer().getTurn()) {
				eliminate(board.getBox(x - 1, y));
				acum ++;
			}
		}
		if (board.belongsToRows(x + 1) && board.getBox(x + 1, y).getPiece().getPlayer().getTurn() != aux.getPiece().getPlayer().getTurn()
				&& board.getBox(x + 1, y).getPiece().getPlayer().getTurn() != 0 && !board.isKing(x + 1, y)) {
			if (board.belongsToRows(x + 2)
					&& board.getBox(x + 2, y).getPiece().getPlayer().getTurn() == aux.getPiece().getPlayer().getTurn()) {
				eliminate(board.getBox(x + 1, y));
				acum ++;
			}
		}
		if (board.belongsToCols(y - 1) && board.getBox(x, y - 1).getPiece().getPlayer().getTurn() != aux.getPiece().getPlayer().getTurn()
				&& board.getBox(x, y - 1).getPiece().getPlayer().getTurn() != 0 && !board.isKing(x, y - 1)) {
			if (board.belongsToCols(y - 2)
					&& board.getBox(x, y - 2).getPiece().getPlayer().getTurn() == aux.getPiece().getPlayer().getTurn()) {
				eliminate(board.getBox(x, y - 1));
				acum ++;
			}
		}
		if (board.belongsToCols(y + 1) && board.getBox(x, y + 1).getPiece().getPlayer().getTurn() != aux.getPiece().getPlayer().getTurn()
				&& board.getBox(x, y + 1).getPiece().getPlayer().getTurn() != 0 && !board.isKing(x, y + 1)) {
			if (board.belongsToCols(y + 2)
					&& board.getBox(x, y + 2).getPiece().getPlayer().getTurn() == aux.getPiece().getPlayer().getTurn()) {
				eliminate(board.getBox(x, y + 1));
				acum ++;
			}
		}
		return acum;
	}

	private void eliminate(Box other) throws IllegalPieceException, EndGameException {
		if(other.getPiece().getC() == 'K')
			throw new EndGameException();
		other.setPiece(new Piece('0'));;
	}

	private boolean isKing(int x, int y) throws EndGameException, IllegalPieceException  {
		Box cell = getBox(x, y);
		ArrayList<Box> neig = new ArrayList<>();
		if ( cell.getPiece().getC() == 'K') {
			int countEnemy = 0;
			int countSpecial = 0;
			int countGuard = 0;
			int countOut = 0;
			neig.addAll(Arrays.asList(getBox(x - 1, y), getBox(x + 1, y),
					getBox(x, y - 1), getBox(x, y + 1)));
			for (Box box : neig) {
				if(box == null)
					countOut++;
				else if(box.getFila() == dimention/2 && box.getColumna() == dimention/2)
					countSpecial++;
				else if( (box.getFila() == 0 && (box.getColumna() == 0 || box.getColumna() == dimention-1) ) || (box.getFila() == dimention-1 && (box.getColumna() == 0 || box.getColumna() == dimention-1) ) )
					countSpecial++;
				else if( box.getPiece().getPlayer().getTurn() == 1)
					countGuard++;
				else if( box.getPiece().getPlayer().getTurn() == 2)
					countEnemy++;
						
			}
			if ( (countEnemy == 3 && (countSpecial == 1 || countOut == 1 || countGuard == 1)) || (countEnemy == 4) || (countEnemy == 2 && countOut == 1 && countSpecial == 1)  ) {
				eliminate(cell);
				value = Integer.MAX_VALUE;
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
				if(box.getPiece().getC() == 'K'){
					acum += 10;
				}
				if((box.getFila() == 0 && (box.getColumna() == 0 || box.getColumna() == dimention-1))||(box.getFila() == dimention-1 && (box.getColumna() == 0 || box.getColumna() == dimention-1))){
					acum -= 10;
				}
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
				
				
				if(validateMove(x, y, i, j) ){
					try {
						auxBoard = copyBoard(original);
						auxBoard.move(x,y, i, j);
						int newturn = (getBox(i,j).getPiece().getPlayer().getTurn()==1)? 2 : 1; 
						l.add(new Move(auxBoard, auxBoard.value, x,y,i,j,newturn));
					} catch (Exception e) { 
						//do nothing
					};
				}
			}
		}
		return l;
	}


	
	public boolean myPiece(int i, int j, int turn) {
		return getBox(i,j).getPiece().getPlayer().getTurn() == turn;
	}

	public Board copyBoard(Board original) throws IllegalPieceException {
		Board answer = new Board(original.getDimention());
		for (int i = 0; i < original.getDimention(); i++) {
			for (int j = 0; j < original.getDimention(); j++) {
				Box a = new Box(original.board[i][j].getValue(),i,j,original.board[i][j].getPiece().getC());
				answer.board[i][j] = a;
			}
		}
		return answer;
	}

	public void printBoardValues() {
		for (int i = 0; i < this.dimention; i++) {
			for (int j = 0; j < this.dimention; j++) {
				System.out.print(getBox(i, j).getValue() + " ");
			}
			System.out.println();
		}

	}
	
	
	// ------------------------------------ GETTERS Y SETTERS ------------------------------------ // 
	

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
}

