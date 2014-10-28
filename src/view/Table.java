package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;

import code.Game;

import java.awt.SystemColor;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;

public class Table extends JFrame {

	private JPanel contentPane;
	private Game game;



	public Table(Game game) {
		this.game = game;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JButton btnSaveGame = new JButton("GUARDAR PARTIDA");
		btnSaveGame.setForeground(new Color(0, 0, 0));
		btnSaveGame.setBackground(Color.ORANGE);
		contentPane.add(btnSaveGame, BorderLayout.SOUTH);
		
		JLabel lblLaFugaDel = new JLabel("LA FUGA DEL REY");
		lblLaFugaDel.setFont(new Font("Cambria", Font.PLAIN, 25));
		lblLaFugaDel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblLaFugaDel, BorderLayout.NORTH);
		
		
	}
}
