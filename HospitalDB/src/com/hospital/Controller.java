package com.hospital;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import com.hospital.dbconn.DBConnection;
import com.hospital.frames.GUI_test;
import com.hospital.frames.LoginFrame;


public class Controller {
	private DBConnection dbc;
	private LoginFrame loginFrame;
	private GUI_test gt;
	
	public Controller() {
		initLoginFrame();
		
		dbc = DBConnection.getInstance();
		
		
	}
	
	private void initLoginFrame() {
		loginFrame = new LoginFrame();
		loginFrame.addListenerToButton(new connectBtnListener(), loginFrame.getConnectBtn());		
		loginFrame.lazy(); // Debug call - method just sets properties for login.
	}
	
	private void initMainFrame() {
		DBConnection.getInstance().selectDB(loginFrame.getBox().getSelectedItem().toString());			
		loginFrame.dispose();			
		gt = new GUI_test();
		gt.init();
	}
	
	class useDBBtnListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			initMainFrame();
		}
	}
	
	class comboBoxSelectionListener implements ItemListener {
	    @Override
	    public void itemStateChanged(ItemEvent event) {
	       if (event.getStateChange() == ItemEvent.SELECTED) {
	          Object item = event.getItem();
	          item.toString();
	       }
	    }
	}
	
	class connectBtnListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			if (dbc.getFlag() == 0) {
				dbc.setIP(loginFrame.getIP());
				dbc.setPort(loginFrame.getPort());
				dbc.setUser(loginFrame.getUser());
				dbc.setPassword(loginFrame.getPassword());
				dbc.connect();
				dbc.createStatement();
				
				loginFrame.createDBSelectPanel(dbc.getDBNamesAsArrayList());
				loginFrame.connect();
				loginFrame.addListenerToCombobox(new comboBoxSelectionListener(), loginFrame.getBox());
				loginFrame.addListenerToButton(new useDBBtnListener(), loginFrame.getUseBtn());
			}
			else {
				loginFrame.disconnect();
				dbc.close();
			}
		}
	}
	
	public void getStatus() {
		System.out.println("Running...");
	}
}
