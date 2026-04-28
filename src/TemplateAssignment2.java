
/**
* First steps for implementing assignment 2 SoSe26 WIDual HS Mainz
* @author Frank Mehler
* ToDo: A Lot
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class TemplateAssignment2 extends JFrame {
	private static final long serialVersionUID = 1;
	private int max = 4; // dimension of game max x max
	private JPanel panelGameBoard;
	private JLabel[][] allLabels = new JLabel[max][max];

	public TemplateAssignment2() {
		super("Template");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final int HOR_SIZE = 400;
		final int VER_SIZE = 420;
		setSize(HOR_SIZE, VER_SIZE);
		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());
		createViewForGameBoard();
		setVisible(true);
	}

	/**
	 * Create the game grid with labels and register listener ControllerMouse
	 */
	private void createViewForGameBoard() {
		if (this.panelGameBoard != null)
			this.remove(panelGameBoard); // old game
		this.panelGameBoard = new JPanel();
		this.add(panelGameBoard, BorderLayout.CENTER);
		this.allLabels = new JLabel[max][max];
		this.panelGameBoard.setLayout(new GridLayout(max, max, 1, 1));
		//ControllerMouse controllerMouse = new ControllerMouse();
		for (int row = 0; row < max; row++) {
			for (int column = 0; column < max; column++) {
				JLabel label = new JLabel(column + ";" + row, SwingConstants.CENTER);
				label.setOpaque(true);
				label.setBackground(Color.LIGHT_GRAY);
				label.setFont(new Font("Arial", Font.BOLD, 30));
				allLabels[column][row] = label;
				//allLabels[column][row].addMouseListener(controllerMouse);
				allLabels[column][row].setName(column + ";" + row);
				this.panelGameBoard.add(allLabels[column][row]);
			}
		}
	}

	/**
	 * Inner class: Handles mouse events of user input
	 */
	

	public static void main(String[] args) {
		new TemplateAssignment2();
	}
}
