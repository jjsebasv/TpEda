package code;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

import javax.naming.directory.InvalidAttributesException;

import exceptions.EndGameException;
import exceptions.InvalidArgumentsException;
import exceptions.InvalidMoveException;
import exceptions.WinGameException;
import view.Table;
import view.VisualBoard;

public class Main {
	
	public static void main(String[] args) throws Exception {
		
		String[] params = parser(args);
		int turn = getTurn(params[0]);
		Game game = new Game(params[0],Boolean.valueOf(params[3]),Integer.valueOf(params[1]), Integer.valueOf(params[2]), Boolean.valueOf(params[5]), Boolean.valueOf(params[6]),turn  );
		boolean tree =  Boolean.valueOf(params[5]);

		DotGenerator graphviz = null;
		if ( tree ){
			graphviz = new DotGenerator();
		}

		
		if ( Boolean.valueOf(params[3]) ){
			VisualBoard gameView = new VisualBoard(game);
			gameView.setVisible(true);
		}else{
			System.out.println("******* LA FUGA DEL REY *******");
			System.out.println();
			game.board.printBoard();
			System.out.println();
			System.out.println("Ingrese Movimiento:");
			BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
			while ( !game.isFinished() ){
				try {
					game.parserMove(buffer.readLine());
					game.board.printBoard();
					System.out.println();
					System.out.println("Ingrese Movimiento:");
				} catch (InvalidMoveException e) {
					System.out.println("Movimiento invalido");
				} catch (WinGameException e2) {
					System.out.println("Ganaste el juego");
					graphviz.getFw().close();
				} catch (EndGameException e) {
					System.out.println("Perdiste el juego");
					graphviz.getFw().close();
				} catch (InvalidArgumentsException e) {
					System.out.println("Argumentos Invalidos. Ingresar (0,0)(0,0)");
				}
			}
		}
		
		
		
	}
	


	public static String[] parser(String[] args) throws InvalidAttributesException{
		int size = args.length;
		String[] params = new String[7];
		params[0] = args[4];
		// maxtime o depth
		switch (args[5]) {
		case "-maxtime":
			params[1] = args[6];
			params[2] = "0";
			break;
		case "-depth":
			params[2] = args[6];
			params[1] = "0";
			break;
		default:
			throw new InvalidAttributesException();
		}
		// graphic
		switch (args[7]) {
		case "-console":
			params[3] = "false";
			params[4] = "true";
			break;
		case "-visual":
			params[4] = "false";
			params[3] = "true";
			break;
		default:
			throw new InvalidAttributesException();
		}
		if ( size >= 9 ){
			switch (args[8]) {
			case "-prune":
				params[5] = "true";
				params[6] = "false";
				break;
			case "-tree":
				params[6] = "true";
				params[5] = "false";
				break;
			default:
				throw new InvalidAttributesException();
			}
		}else{
			params[5] = "false";
			params[6] = "false";
		}
		
		if ( size >= 10 ){
			switch (args[8]) {
			case "-prune":
				params[5] = "true";
				break;
			case "-tree":
				params[6] = "true";
				break;
			default:
				throw new InvalidAttributesException();
			}
		}
			
		return params;
	}
	
	private static int getTurn(String file) {
		
		int turn = 1;
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String l = br.readLine();
			turn = Integer.valueOf(l.toCharArray()[0]-'0');
			if ( turn < 1 && turn > 2 ){
				br.close();
				throw new InvalidAttributesException();
			}	
			br.close();
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return turn;
	}
}
