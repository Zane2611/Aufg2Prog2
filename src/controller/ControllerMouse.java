package controller;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import model.Model;
import view.View;

/**
 * Mouse controller responsible for handling all mouse interactions on the grid.
 * Forwards mouse events to the model and triggers updates in the view. Manages
 * the logic for starting, extending and finishing connections, and checks for a
 * winning condition after each release.
 *
 * @author Philipp Palm
 */
public class ControllerMouse extends MouseAdapter {

	/** Indicates whether the mouse button is currently held down. */
	private boolean isMousePressed;

	/** Reference to the game model. */
	private final Model model;

	/** Reference to the game view. */
	private final View view;

	/**
	 * Creates a new mouse controller for the given model and view.
	 *
	 * @param model the game model
	 * @param view  the game view
	 */
	public ControllerMouse(Model model, View view) {
		this.isMousePressed = false;
		this.model = model;
		this.view = view;
	}

	/**
	 * Handles the start of a mouse interaction. Notifies the model and updates the
	 * view. May start a new connection or mark an existing one for deletion.
	 *
	 * @param e the mouse event
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		isMousePressed = true;

		JLabel label = (JLabel) e.getSource();
		Point p = (Point) label.getClientProperty("gridPoint");

		model.mousePressed(p, label.getText());
		view.updateView();
	}

	/**
	 * Handles the mouse entering a cell while the button is held down. Each entered
	 * cell is forwarded to the model to extend the active connection.
	 *
	 * @param e the mouse event
	 */
	@Override
	public void mouseEntered(MouseEvent e) {

		if (isMousePressed) {
			JLabel label = (JLabel) e.getSource();
			Point p = (Point) label.getClientProperty("gridPoint");

			model.mouseEntered(p, label.getText());
			view.updateView();
		}
	}

	/**
	 * Handles the end of a mouse interaction. Finalises the connection in the
	 * model, updates the view and displays a win message if all numbers are
	 * correctly connected.
	 *
	 * @param e the mouse event
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		isMousePressed = false;
		JLabel label = (JLabel) e.getSource();
		Point p = (Point) label.getClientProperty("gridPoint");
		model.mouseReleased(p);
		view.updateView();

		if (model.detectWinning()) {
			JOptionPane.showMessageDialog(view, "You won!");
		}
	}
}