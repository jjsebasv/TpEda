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

import code.Box;
import code.Game;

import java.awt.SystemColor;

import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Table extends JFrame {

	private JPanel contentPane;
	private Game game;
	private JButton visualBoard[][];
	private int dimention;

	public Table(final Game game) {
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
		
		JButton btnSaveGame = new JButton("GUARDAR PARTIDA");
		btnSaveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.saveGame();
				System.exit(0);
			}
		});
		btnSaveGame.setForeground(new Color(0, 0, 0));
		btnSaveGame.setBackground(Color.WHITE);
		contentPane.add(btnSaveGame, BorderLayout.SOUTH);
		
		JLabel lblLaFugaDel = new JLabel("LA FUGA DEL REY");
		lblLaFugaDel.setFont(new Font("Cambria", Font.PLAIN, 25));
		lblLaFugaDel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblLaFugaDel, BorderLayout.NORTH);
		
		this.visualBoard = new JButton[game.board.getDimention()][game.board.getDimention()];
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
	       
		      panel.setLayout(new GridLayout(8,8,1,1)); 
		       
		       for (int i=0; i< game.board.getDimention(); i++){ 
		       
		           for (int j=0; j < game.board.getDimention(); j++){ 
		          
		                    JButton button = new JButton(" "); 
		                    button.setBackground(Color.WHITE);
		             
		                    char c = game.board.getBoard()[i][j].getCharacter() ;
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
		                    button.addActionListener(new ActionListener() {
		            			public void actionPerformed(ActionEvent e) {
		            				//game.board.move(iFrom, jFrom, iTo, jTo);
		            			}
		            		});
		                    visualBoard[i][j] = button;
		                    panel.add(button);
		              
		           } 
		            
		           getContentPane().add(panel,BorderLayout.CENTER); 
		            
		             }
		
		
	}
}
