package view;

import java.awt.*;
import javax.swing.*;

import controller.ControllerMouse;
import model.Model;

public class View extends JFrame {
	
	private static final long serialVersionUID = 1;
	public int max = 4; // dimension of game max x max
	public JPanel panelGameBoard;
	public JLabel[][] allLabels = new JLabel[max][max];
	
	private Model model;

	public View(Model model) {
		super("Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final int HOR_SIZE = 400;
		final int VER_SIZE = 420;
		setSize(HOR_SIZE, VER_SIZE);
		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());
		createViewForGameBoard();
		setVisible(true);
	}
	
	public void createViewForGameBoard() {
		if (this.panelGameBoard != null)
			this.remove(panelGameBoard); // old game
		this.panelGameBoard = new JPanel();
		this.add(panelGameBoard, BorderLayout.CENTER);
		this.allLabels = new JLabel[max][max];
		this.panelGameBoard.setLayout(new GridLayout(max, max, 1, 1));
		ControllerMouse controllerMouse = new ControllerMouse(model);
		for (int row = 0; row < max; row++) {
			for (int column = 0; column < max; column++) {
				JLabel label = new JLabel(column + ";" + row, SwingConstants.CENTER);
				label.setOpaque(true);
				label.setBackground(Color.LIGHT_GRAY);
				label.setFont(new Font("Arial", Font.BOLD, 30));
				allLabels[column][row] = label;
				allLabels[column][row].addMouseListener(controllerMouse);
				allLabels[column][row].setName(column + ";" + row);
				this.panelGameBoard.add(allLabels[column][row]);
			}
		}
	}

	private int getColumnFromLabel(String labelName) {
		String[] partsOfLabelName = labelName.split(";");
		return Integer.parseInt(partsOfLabelName[0]);
	}

	private int getRowFromLabel(String labelName) {
		String[] partsOfLabelName = labelName.split(";");
		return Integer.parseInt(partsOfLabelName[1]);
	}
	
}
