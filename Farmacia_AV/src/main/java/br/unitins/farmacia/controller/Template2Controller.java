package br.unitins.farmacia.controller;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Template2Controller extends JFrame{
	ImageIcon imagem = new ImageIcon(getClass().getResource("java.png"));
	JLabel label = new JLabel(imagem);
	public Template2Controller() {
		add(label);
		setSize(500, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(this);
		setVisible(true);
	}


}