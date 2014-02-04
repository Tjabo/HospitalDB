package com.base;

public class Main {
	
	public Main() {
		Model model = new Model();
		View view = new View();
		Controller controller = new Controller(view, model);	
	}

	public static void main(String[] args) {
		Main main = new Main();
	}
}
