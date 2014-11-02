package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.naming.directory.InvalidAttributesException;

import view.Table;

public class Main {
	
	public static void main(String[] args) throws Exception {
		

		String[] params = parser(args);
		int turn = getTurn(params[0]);
		System.out.println("TURN: MAIN: " + turn);
		Game game = new Game(params[0],Boolean.valueOf(params[3]),Integer.valueOf(params[1]), Integer.valueOf(params[2]), Boolean.valueOf(params[5]), Boolean.valueOf(params[6]),turn  );
		game.board.printBoard();
		if ( Boolean.valueOf(params[3]) ){
			Table gameView = new Table(game);
			gameView.setVisible(true);
		}
		
		// leer hasta que se acabe el juego
		if ( !Boolean.valueOf(params[3]) ) {
		Exception e = new Exception();
			while ( e == null ){
				try {
					readMove(game);
				} catch (Exception e2) {
					e = e2;
				}
			}
		}
		
	}
	
	private static void readMove(Game game) throws Exception {
		BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
		String line=buffer.readLine();
		char[] array = line.toCharArray();
		game.move(Integer.valueOf(array[1]), Integer.valueOf(array[2]), Integer.valueOf(array[5]), Integer.valueOf(array[6]));
	}

	public static String[] parser(String[] args) throws InvalidAttributesException{
		int size = args.length;
		String[] params = new String[7];
		params[0] = args[4];
		// maxtime o depth
		switch (args[5]) {
		case "-maxtime":
			params[1] = args[6];
			params[2] = "10";
			break;
		case "-depth":
			params[2] = args[6];
			params[1] = "10";
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
				System.out.println("f");
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
