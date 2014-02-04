package com.base;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.base.frames.LoginFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class View {

	private LoginFrame loginFrame;
	private JFrame frame;
	
	private JButton go;

	public View() {
	}

	private void initialize() {
		frame = new JFrame();
		frame.setSize(new Dimension(640, 480));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
