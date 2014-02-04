package com.base;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.base.frames.LoginFrame;
import com.base.frames.MainFrame;



public class Controller {
	
	private LoginFrame loginFrame;
	private MainFrame mainFrame;
	private Model model;

	public Controller(MainFrame mainFrame, Model model) {
		this.mainFrame = mainFrame;
		this.model = model;
		
		showLoginFrame();
	}
	
	public void showLoginFrame() {
		loginFrame = new LoginFrame();
		this.loginFrame.addListenerToButton(new addCancelBtnListener(), this.loginFrame.getBtnCancel());
		this.loginFrame.addListenerToButton(new addLoginBtnListener(), this.loginFrame.getBtnLogin());
		
	}
	
	class addCancelBtnListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			loginFrame.dispose();			
		}
	}
	
	class addLoginBtnListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			loginFrame.login();
		}
	}
	
	public void init() {		
				
	}
}
