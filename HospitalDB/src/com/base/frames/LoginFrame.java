package com.base.frames;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;

public class LoginFrame {

	private MainFrame mainFrame;

	private JFrame loginFrame;
	private JButton btnLogin;
	private JPasswordField pwfield;
	private JPasswordField passwordField;
	private JTextField textField;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	
	private JButton btnCancel;

	public LoginFrame() {

		this.mainFrame = mainFrame;

		loginFrame = new JFrame();
		loginFrame.setSize(new Dimension(200, 200));
		loginFrame.setLocationRelativeTo(null);
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.getContentPane().setLayout(
				new FlowLayout(FlowLayout.CENTER, 150, 20));

		panel = new JPanel();
		loginFrame.getContentPane().add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JLabel lblUserName = new JLabel("Username: ");
		panel.add(lblUserName);

		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);

		panel_1 = new JPanel();
		loginFrame.getContentPane().add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		JLabel lblPassword = new JLabel("Password: ");
		panel_1.add(lblPassword);

		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		panel_1.add(passwordField);

		panel_2 = new JPanel();
		loginFrame.getContentPane().add(panel_2);

		btnLogin = new JButton();

		panel_2.add(btnLogin);
		btnLogin.setText("Login");

		btnCancel = new JButton("Cancel");

		panel_2.add(btnCancel);

		loginFrame.setVisible(true);
	}
	
	public void login() {
		loginFrame.dispose();
	}
	
	public void dispose() {		
		System.exit(0);
	}
	
	public JButton getBtnCancel() {
		return this.btnCancel;
	}
	
	public JButton getBtnLogin() {
		return this.btnLogin;
	}

	public void addListenerToButton(ActionListener listen, JButton button) {
		button.addActionListener(listen);
	}
}
