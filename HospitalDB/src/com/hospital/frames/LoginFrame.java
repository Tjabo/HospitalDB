package com.hospital.frames;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class LoginFrame {
	
	private String TITLE = "Login";
		
	private JFrame frame;
	private JPanel dbselectPanel;
	private JPanel loginPanel;
	private JLabel lblIP;
	private JLabel lblPort;
	private JLabel lblUser;
	private JLabel lblPassword;
	private JTextField textFieldIP;
	private JTextField textFieldPort;
	private JTextField textFieldUser;
	private JPasswordField passwordField;
	private JButton btnConnect;
	private JButton btnCancel;
	private JButton btnUseDB;
	private JComboBox<Object> comboBox;
	
	public LoginFrame() {
		frame = new JFrame();
		
		ImageIcon imgIcon = new ImageIcon("res/icon.gif");
		frame.setIconImage(imgIcon.getImage());
		
		frame.setTitle(TITLE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(320, 200));
		
		createLoginPanel();	
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void createLoginPanel() {
		loginPanel = new JPanel();
		frame.getContentPane().add(loginPanel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {128, 128};
		gbl_panel.columnWeights = new double[]{1.0, 1.0};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
		loginPanel.setLayout(gbl_panel);
		
		lblIP = new JLabel("IP:");
		GridBagConstraints gbc_lblIP = new GridBagConstraints();
		gbc_lblIP.insets = new Insets(10, 10, 10, 10);
		gbc_lblIP.gridx = 0;
		gbc_lblIP.gridy = 0;
		loginPanel.add(lblIP, gbc_lblIP);
		
		textFieldIP = new JTextField();
		GridBagConstraints gbc_textFieldIP = new GridBagConstraints();
		gbc_textFieldIP.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldIP.insets = new Insets(10, 10, 10, 10);
		gbc_textFieldIP.gridx = 1;
		gbc_textFieldIP.gridy = 0;
		loginPanel.add(textFieldIP, gbc_textFieldIP);
		textFieldIP.setColumns(10);
		
		lblPort = new JLabel("Port:");
		GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.insets = new Insets(10, 10, 10, 10);
		gbc_lblPort.gridx = 0;
		gbc_lblPort.gridy = 1;
		loginPanel.add(lblPort, gbc_lblPort);
		
		textFieldPort = new JTextField();
		GridBagConstraints gbc_textFieldPort = new GridBagConstraints();
		gbc_textFieldPort.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPort.insets = new Insets(10, 10, 10, 10);
		gbc_textFieldPort.gridx = 1;
		gbc_textFieldPort.gridy = 1;
		loginPanel.add(textFieldPort, gbc_textFieldPort);
		textFieldPort.setColumns(10);
		
		lblUser = new JLabel("Username:");
		GridBagConstraints gbc_lblUser = new GridBagConstraints();
		gbc_lblUser.insets = new Insets(10, 10, 10, 10);
		gbc_lblUser.gridx = 0;
		gbc_lblUser.gridy = 2;
		loginPanel.add(lblUser, gbc_lblUser);
		
		textFieldUser = new JTextField();
		GridBagConstraints gbc_textFieldUser = new GridBagConstraints();
		gbc_textFieldUser.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldUser.insets = new Insets(10, 10, 10, 10);
		gbc_textFieldUser.gridx = 1;
		gbc_textFieldUser.gridy = 2;
		loginPanel.add(textFieldUser, gbc_textFieldUser);
		textFieldUser.setColumns(10);
		
		lblPassword = new JLabel("Password:");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.insets = new Insets(10, 10, 10, 10);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 3;
		loginPanel.add(lblPassword, gbc_lblPassword);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(10, 10, 10, 10);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 3;
		loginPanel.add(passwordField, gbc_passwordField);
		
		btnConnect = new JButton("Connect");
		GridBagConstraints gbc_btnConnect = new GridBagConstraints();
		gbc_btnConnect.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnConnect.insets = new Insets(10, 10, 10, 10);
		gbc_btnConnect.gridx = 0;
		gbc_btnConnect.gridy = 4;
		loginPanel.add(btnConnect, gbc_btnConnect);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(10, 10, 10, 10);
		gbc_btnCancel.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancel.gridx = 1;
		gbc_btnCancel.gridy = 4;
		loginPanel.add(btnCancel, gbc_btnCancel);
	}
	
	
	public void createDBSelectPanel(ArrayList<String> dbList) {
		
		dbselectPanel = new JPanel();
		frame.getContentPane().add(dbselectPanel, BorderLayout.SOUTH);
		dbselectPanel.setLayout(new BoxLayout(dbselectPanel, BoxLayout.Y_AXIS));
		
		frame.getContentPane().add(loginPanel, BorderLayout.CENTER);
		GridBagLayout gbl_dbpanel = new GridBagLayout();
		gbl_dbpanel.columnWidths = new int[] {128, 128};
		gbl_dbpanel.columnWeights = new double[]{1.0, 1.0};
		gbl_dbpanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
		dbselectPanel.setLayout(gbl_dbpanel);
		
		comboBox = new JComboBox<Object>(dbList.toArray());
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(10, 10, 10, 10);
		dbselectPanel.add(comboBox);
		
		btnUseDB = new JButton("Select...");
		GridBagConstraints gbc_btnUse = new GridBagConstraints();
		gbc_btnUse.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnUse.insets = new Insets(10, 10, 10, 10);
		dbselectPanel.add(btnUseDB);
		
		frame.revalidate();
		frame.pack();
		frame.repaint();
	}
	
	public void disconnect() {
		for (Component c : loginPanel.getComponents()) {
			c.setEnabled(true);
		}
		btnConnect.setEnabled(true);
		btnConnect.setText("Connect");
		frame.remove(dbselectPanel);
	}
	
	public void connect() {
		for (Component c : loginPanel.getComponents()) {
			c.setEnabled(false);
		}
		btnConnect.setEnabled(true);
		btnConnect.setText("Disconnect");		
	}
	
	public void dispose() {
		frame.dispose();
	}
	
	public String getIP(){
		return textFieldIP.getText();
	}
	
	public int getPort() {
		return Integer.parseInt(textFieldPort.getText());
	}
	
	public String getUser() {
		return textFieldUser.getText();
	}
	
	public String getPassword() {		
		return passwordField.getText();		
	}
	
	public void addListenerToButton(ActionListener listen, JButton button) {
		button.addActionListener(listen);
	}
	
	public void addListenerToCombobox(ItemListener listen, JComboBox<?> box) {
		box.addItemListener(listen);
	}

	public JButton getConnectBtn() {
		return this.btnConnect;
	}
	
	public JButton getUseBtn() {
		return this.btnUseDB;
	}
	
	public JComboBox<Object> getBox() {
		return this.comboBox;
	}
}
