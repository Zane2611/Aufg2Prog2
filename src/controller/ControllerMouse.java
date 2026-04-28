package controller;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import model.Model;
import view.View;

public class ControllerMouse extends MouseAdapter {
		private boolean isMousePressed;
		private Component previousEnteredComponent;
		Model model;
		View view;

		public ControllerMouse(Model model, View view) {
			this.isMousePressed = false;
			this.previousEnteredComponent = null;
			this.model = model;
			this.view = view;
		}

		public void mousePressed(MouseEvent e) {
			this.isMousePressed = true;
			System.out.println("mousePressed " + e.getComponent().getName());
			JLabel label = (JLabel) e.getSource();
			Point p = (Point) label.getClientProperty("gridPoint");
			String labelText = label.getText();
			
			model.startConnection(p, labelText);
			view.updateView();
		}

		public void mouseEntered(MouseEvent e) {
			previousEnteredComponent = e.getComponent();
			if (this.isMousePressed == true) {
				System.out.println("mouseEntered while mouse pressed " + e.getComponent().getName());
				JLabel label = (JLabel) e.getSource();
				Point p = (Point) label.getClientProperty("gridPoint");
				model.mouseEntered(p);
				view.updateView();
			}
		}

		public void mouseReleased(MouseEvent e) {
			this.isMousePressed = false;
			// use component of last entered mouse position
			System.out.println("mouseReleased " + previousEnteredComponent.getName());
			model.mouseReleased();
			view.updateView();
			if(model.detectWinning()) {
    JOptionPane.showMessageDialog(view, "You Win!");
}
		}
	}