package view;

import controller.Controller;
import controller.ControllerMouse;
import java.awt.*;
import javax.swing.*;
import model.Connection;
import model.Model;

public class View extends JFrame {
	
	private static final long serialVersionUID = 1;
	public int max = 4; // dimension of game max x max
	public JPanel panelGameBoard;
	public JLabel[][] allLabels = new JLabel[max][max];
	private JButton loadButton;
	private JButton saveButton;
	private Model model;

public View(Model model, Controller controller) {	
		super("Game");
		this.model = model;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final int HOR_SIZE = 400;
		final int VER_SIZE = 420;
		setSize(HOR_SIZE, VER_SIZE);
		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());
		createViewForGameBoard();
		createMenuBar(controller);		
		setVisible(true);
		this.model = model;
	}
	
	public void createViewForGameBoard() {
		if (this.panelGameBoard != null)
			this.remove(panelGameBoard); // old game
		this.panelGameBoard = new JPanel();
		this.add(panelGameBoard, BorderLayout.CENTER);
		this.allLabels = new JLabel[max][max];
		this.panelGameBoard.setLayout(new GridLayout(max, max, 1, 1));
		ControllerMouse controllerMouse = new ControllerMouse(model, this);
		for (int row = 0; row < max; row++) {
			for (int column = 0; column < max; column++) {
				int boardValue = this.model.gameBoard.getValue(column, row);
				String labelText = (boardValue != 0) ? String.valueOf(boardValue) : "";
				JLabel label = new JLabel(labelText, SwingConstants.CENTER);
				label.putClientProperty("gridPoint", new Point(column, row));
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

	public void createMenuBar(controller.Controller controller) {

    JMenuBar menuBar = new JMenuBar();

    JMenu fileMenu = new JMenu("File");

    JMenuItem exportItem = new JMenuItem("JSON-Export");
    JMenuItem importItem = new JMenuItem("JSON-Import");

    exportItem.addActionListener(e -> controller.exportGame());
    importItem.addActionListener(e -> controller.importGame());

    fileMenu.add(exportItem);
    fileMenu.add(importItem);

    menuBar.add(fileMenu);

    this.setJMenuBar(menuBar);
}

	public void updateView() {
		for (int row = 0; row < max; row++) {
			for (int column = 0; column < max; column++) {
				this.allLabels[column][row].setBackground(Color.LIGHT_GRAY);
			}
		}
		for (Connection c : this.model.allConnections) {
			for (Point p : c.conn) {
				this.allLabels[p.x][p.y].setBackground(c.color.getColor());
			}
		}
	}
	
	public void setMax(int max) {
	    this.max = max;
	}

	
	
}
