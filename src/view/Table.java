package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;

import code.Board;
import code.Box;
import code.Game;

import java.awt.SystemColor;

import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;

import exceptions.EndGameException;
import exceptions.InvalidMoveException;
import exceptions.WinGameException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;
import java.awt.Dimension;

public class Table extends JFrame {

	private JPanel contentPane;
	private Game game;
	private int dimention;
	private MyButton from;
	private MyButton to;
	private static MyButton visualBoard[][];
	private int counter;
	private JLabel label;
	
	public Table(final Game game) {
		
		visualBoard = new MyButton[game.board.getDimention()][game.board.getDimention()];
		this.counter = 0;
		dimention = game.board.getDimention();
		this.game = game;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		/*
		JButton btnSaveGame = new JButton("GUARDAR PARTIDA");
		btnSaveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.saveGame();
				System.exit(0);
			}
		});
		btnSaveGame.setForeground(new Color(0, 0, 0));
		btnSaveGame.setBackground(Color.WHITE);
		contentPane.add(btnSaveGame, BorderLayout.EAST);
		*/
		
		label = new JLabel("");
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		label.setForeground(Color.RED);
		label.setBackground(Color.WHITE);
		label.setVisible(true);
		label.setText("TU TURNO");
		contentPane.add(label,BorderLayout.SOUTH);
		
		
		from = new MyButton(0,0,' ');
		to = new MyButton(0,0,' ');
		
		JLabel lblLaFugaDel = new JLabel("LA FUGA DEL REY");
		lblLaFugaDel.setFont(new Font("Cambria", Font.PLAIN, 25));
		lblLaFugaDel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblLaFugaDel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
	       
		panel.setLayout(new GridLayout(8,8,1,1)); 
		       
		for (int i=0; i< game.board.getDimention(); i++){ 
		       
		           for (int j=0; j < game.board.getDimention(); j++){ 
		          
		                   	MyButton button = new MyButton(i,j,' '); 	                   	
		                    button.setBackground(Color.WHITE);
		             
		                    char c = game.board.getBoard()[i][j].getPiece().getC() ;
		                    ImageIcon image = new ImageIcon();
		                    switch (c) {
							case 'K':
								image = new ImageIcon("./images/king.png");
								break;
							case 'N':
								image = new ImageIcon("./images/enemy.png");
								break;
							case 'G':
								image = new ImageIcon("./images/guard.png");
								break;
							}
		                    button.setIcon(image);
		                    if ( (i == 0 || i == dimention - 1)  && ( j== 0 || j == dimention - 1 )){
		                    	button.setIcon(new ImageIcon("./images/castle.png"));
		                    	button.setBackground(Color.BLUE);
		                    }
		                    visualBoard[i][j] = button;
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
		            						System.out.print("ACA ("+from.getFil()+","+from.getCol()+")("+to.getFil()+","+to.getCol()+")");
		            						try {
		            							game.move(from.getFil(),from.getCol(),to.getFil(),to.getCol());
		            							label.setText("TU TURNO");
												//game.board.printBoard();
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
		                    panel.add(button);
		              
		           } 
		           		getContentPane().add(panel,BorderLayout.CENTER); 
		       }
	
	}
	
	
	
	public static void repaint(Board board){
		Box casilla;
		MyButton boton;
		ImageIcon image = new ImageIcon();
		for (int i=0; i<board.getDimention(); i++){ 
	           for (int j=0; j < board.getDimention(); j++){ 
	        	   casilla =board.getBoard()[i][j];
	        	   boton = visualBoard[i][j];
	        	   switch (casilla.getPiece().getC()) {
					case 'K':
						image = new ImageIcon("./images/king.png");
						visualBoard[i][j].setIcon(image);
						break;
					case 'N':
						image = new ImageIcon("./images/enemy.png");
						visualBoard[i][j].setIcon(image);
						break;
					case 'G':
						image = new ImageIcon("./images/guard.png");
						visualBoard[i][j].setIcon(image);
						break;
					case '0':
						
						if ( i == board.getDimention() /2 && j == board.getDimention() / 2  ) {
							image = new ImageIcon("./images/throne.png");
							visualBoard[i][j].setIcon(image);
						}else if ( (i != 0 && i != board.getDimention() - 1) || ( j != 0 && j != board.getDimention() - 1 ) ){
							visualBoard[i][j].setIcon(null);	
	                    }
						break;
					}
	        	   
	           }
		}
		
	}

}
