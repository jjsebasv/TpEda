package code;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		Board b = new Board(7, 7);
		b.printBoard();
		
		System.out.println();
		
		try {
			b.move(2, 2, 1, 2);
			b.move(2, 4, 1, 4);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		b.printBoard();
		b.exportBoard();
		
	}

}
