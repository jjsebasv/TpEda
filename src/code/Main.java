package code;

import java.io.File;

import javax.naming.directory.InvalidAttributesException;

public class Main {

	public static void main(String[] args) throws Exception {
		

		String[] params = parser(args);
		Game game = new Game(params[0],Boolean.valueOf(params[3]),Integer.valueOf(params[1]), Integer.valueOf(params[2]), Boolean.valueOf(params[5]), Boolean.valueOf(params[6]) );
		game.saveGame();
		

		
		
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
}
