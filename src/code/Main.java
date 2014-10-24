package code;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Board b = new Board(7, 7);
		b.printBorad();
		
		System.out.println();
		
		b.move(3, 1, 0, 1);
		b.printBorad();
		
	}

}
