package exceptions;

public class BoardOutOfBoundsException extends Exception {

	public BoardOutOfBoundsException(){
		super("Tablero fuera de los limites");
	}
}
