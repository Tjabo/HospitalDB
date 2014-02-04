package com.base;

import com.base.frames.LoginFrame;



public class Controller {
	
	private LoginFrame loginFrame;
	private View view;
	private Model model;

	public Controller(View view, Model model) {
		this.view = view;
		this.model = model;
		
		showLoginFrame();
	}
	
	public void showLoginFrame() {
		loginFrame = new LoginFrame();
		
	}
	
	public void init() {		
				
	}
}
