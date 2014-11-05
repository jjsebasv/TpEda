package view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container; 
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame; 
import javax.swing.JLabel; 
import javax.swing.JPanel; 

import code.Board;
import code.Box;
import code.Game;
import exceptions.EndGameException;
import exceptions.InvalidMoveException;
import exceptions.WinGameException;


public class VisualBoard extends JFrame { 

	private Container base; 
	private Container info;
	private static JPanel[][] casilla; 
	private Game game;
	private MyButton from;
	private MyButton to;
	private int dimention;
	private int counter;
	private JLabel label;

	public VisualBoard(Game game) 
	{ 
		
		this.game = game;
		this.dimention = game.getBoard().getDimention();
		base = getContentPane(); 
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);			
		base.setLayout(new GridLayout(dimention,dimention)); 	
		setSize(500,500); 
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		this.setCasilla(new JPanel[dimention][dimention]);
		crearCuadros(game.board); 
		
		
		label = new JLabel("");
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		label.setForeground(Color.RED);
		label.setBackground(Color.WHITE);
		label.setVisible(true);
		label.setText("TU TURNO");

		
	} 


	public void crearCuadros(Board board) { 

		setCasilla(new JPanel[dimention][dimention]); 
		ImageIcon image;

		for(int x = 0; x < dimention; x++) { 
			for(int y=0; y < dimention ; y++) { 
				getCasilla()[x][y] = new JPanel();
				MyButton button = new MyButton(x,y,board.getBoard()[x][y].getPiece().getC());
				switch (board.getBoard()[x][y].getPiece().getC()) {
					case 'K':
						image = new ImageIcon("./images/king.png");
						button.setIcon(image);
						getCasilla()[x][y].add(button);
						base.add(getCasilla()[x][y]);
						break;
					case 'N':
						image = new ImageIcon("./images/enemy.png");
						button.setIcon(image);
						getCasilla()[x][y].add(button);
						base.add(getCasilla()[x][y]);
						break;
					case 'G':
						image = new ImageIcon("./images/guard.png");
						button.setIcon(image);
						getCasilla()[x][y].add(button);
						base.add(getCasilla()[x][y]);
						break;
					case '0':	
						if ( x == board.getDimention() /2 && y == board.getDimention() / 2  ) {
							image = new ImageIcon("./images/throne.png");
							button.setIcon(image);
							getCasilla()[x][y].add(button);
							base.add(getCasilla()[x][y]);
						}else if ( (x == 0 || x == dimention - 1)  && ( y == 0 || y == dimention - 1 )){
							image = new ImageIcon("./images/castle.png");
	                    	button.setIcon(image);
	                    	getCasilla()[x][y].add(button);
	                    	base.add(getCasilla()[x][y]);
	                    } else{
	                    	image = new ImageIcon("./images/empty.png");
	                    	button.setIcon(image);
	                    	getCasilla()[x][y].add(button);
	                    	base.add(getCasilla()[x][y]);
	                    	
	                    }
	
				} 
				
				/* ACCCIONES */
				 button.addActionListener(new ActionListener() {
         			public void actionPerformed(ActionEvent e) {
         				if ( counter == 0 ){
         					//validatePiece();
         					from = (MyButton) e.getSource();
         					counter++;
         				}
         				else{
         					//validatePiece();
         					to = (MyButton) e.getSource();
         					try {
        						try {
        							game.move(from.getFil(),from.getCol(),to.getFil(),to.getCol());
								} catch (WinGameException e1) {
									label.setText("  GANASTE EL JUEGO ");
								} catch (InvalidMoveException e2) {
									label.setText("MOVIMIENTO INVALIDO");
								} catch (EndGameException e3) {
									label.setText("  PERDISTE EL JUEGO ");
								}
								
								repaint(game.board);
								counter = 0;
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
         				}
         			}
				 });
				}
				/* LISTO ACCIONES */
         
			}
	} 
	
	public static void repaint(Board board){
		Box casillero;
		MyButton boton;
		ImageIcon image = new ImageIcon();
		for (int i=0; i<board.getDimention(); i++){ 
	           for (int j=0; j < board.getDimention(); j++){ 
	        	   casillero  =board.getBoard()[i][j];
	        	   boton = (MyButton) getCasilla()[i][j].getComponent(0);
	        	   switch (casillero.getPiece().getC()) {
					case 'K':
						image = new ImageIcon("./images/king.png");
						boton.setIcon(image);
						break;
					case 'N':
						image = new ImageIcon("./images/enemy.png");
						boton.setIcon(image);
						break;
					case 'G':
						image = new ImageIcon("./images/guard.png");
						boton.setIcon(image);
						break;
					case '0':
						
						if ( i == board.getDimention() /2 && j == board.getDimention() / 2  ) {
							image = new ImageIcon("./images/throne.png");
							boton.setIcon(image);
						}else if ( (i != 0 && i != board.getDimention() - 1) || ( j != 0 && j != board.getDimention() - 1 ) ){
							image = new ImageIcon("./images/empty.png");
							boton.setIcon(image);;	
	                    }
						break;
					}
	        	   
	           }
		}
		
	}


	public static JPanel[][] getCasilla() {
		return casilla;
	}


	public static void setCasilla(JPanel[][] casilla) {
		VisualBoard.casilla = casilla;
	}
}