package com.base.frames;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
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

	private JFrame loginFrame;
	private JButton btnLogin;
	private JPasswordField pwfield;
	private JPasswordField passwordField;
	private JTextField textField;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;

	public LoginFrame() {

		loginFrame = new JFrame();
		loginFrame.setSize(new Dimension(200, 200));
		loginFrame.setLocationRelativeTo(null);
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 150, 20));
		
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
				
						JButton btnCancel = new JButton("Cancel");
						panel_2.add(btnCancel);

		loginFrame.setVisible(true);
	}

	public void addListenerToButton() {

	}
}
