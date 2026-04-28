package controller;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import model.Model;

public class ControllerMouse extends MouseAdapter {
		private boolean isMousePressed;
		private Component previousEnteredComponent;
		Model model;

		public ControllerMouse(Model model) {
			this.isMousePressed = false;
			this.previousEnteredComponent = null;
			this.model = model;
		}

		/**
		 * Parsing of label names consisting of x and y coordinate separated by
		 * semicolon
		 * 
		 * @param labelName is internal name of the label as a unique identifier
		 * @return first coordinate 0, i.e. x-coordinate
		 */
		private int getColumnFromLabel(String labelName) {
			String[] partsOfLabelName = labelName.split(";");
			return Integer.parseInt(partsOfLabelName[0]);
		}

		private int getRowFromLabel(String labelName) {
			String[] partsOfLabelName = labelName.split(";");
			return Integer.parseInt(partsOfLabelName[1]);
		}

		public void mousePressed(MouseEvent e) {
			this.isMousePressed = true;
			System.out.println("mousePressed " + e.getComponent().getName());
			String labelName = e.getComponent().getName();
			int column = getColumnFromLabel(labelName);
			int row = getRowFromLabel(labelName);
			//getAllLabels()[column][row].setBackground(Color.ORANGE);
		}

		public void mouseEntered(MouseEvent e) {
			previousEnteredComponent = e.getComponent();
			if (this.isMousePressed == true) {
				System.out.println("mouseEntered while mouse pressed " + e.getComponent().getName());
				String labelName = e.getComponent().getName();
				int column = getColumnFromLabel(labelName);
				int row = getRowFromLabel(labelName);
				//model.allLabels[column][row].setBackground(Color.ORANGE);
				
			}
		}

		public void mouseReleased(MouseEvent e) {
			this.isMousePressed = false;
			// use component of last entered mouse position
			System.out.println("mouseReleased " + previousEnteredComponent.getName());
		}
	}