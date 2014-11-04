package exceptions;

public class IllegalPieceException extends Exception {

	public IllegalPieceException(){
		super("Pieza no permitida");
	}
}
