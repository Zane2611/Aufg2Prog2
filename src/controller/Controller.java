package controller;

import model.Model;
import view.View;

public class Controller {
	private Model model;
	private View view;

	public Controller() {
		this.model = new Model();
		this.view = new View(model);
	}

	public static void main(String[] args) {
		new Controller();
	}
}
