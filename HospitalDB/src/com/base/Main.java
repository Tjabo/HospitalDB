package com.base;

import com.base.frames.MainFrame;

public class Main {
	
	public Main() {
		Model model = new Model();
		MainFrame view = new MainFrame();
		Controller controller = new Controller(view, model);	
	}

	public static void main(String[] args) {
		Main main = new Main();
	}
}
