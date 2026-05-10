package view;

import controller.Controller;
import controller.ControllerMouse;
import java.awt.*;
import javax.swing.*;
import model.Connection;
import model.Model;

/**
 * Main view class responsible for rendering the game board, handling UI layout
 * and updating the visual representation of all connections stored in the
 * model.
 *
 * @author Philipp Palm
 */
public class View extends JFrame {

	private static final long serialVersionUID = 1;

	private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 30);

	private JPanel panelGameBoard;

	private JLabel[][] allLabels;

	private final Model model;

	private static final int WINDOW_WIDTH = 400;

	private static final int WINDOW_HEIGHT = 420;

	/**
	 * Creates the main game window, initializes layout, builds the game board and
	 * menu bar.
	 *
	 * @param model      the game model
	 * @param controller the main controller
	 */
	public View(Model model, Controller controller) {
		super("Game");
		this.model = model;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

		getContentPane().setLayout(new BorderLayout());

		createViewForGameBoard();
		createMenuBar(controller);

		setVisible(true);
	}

	/**
	 * Creates or rebuilds the game board panel based on the current max value. Each
	 * cell is represented by a JLabel with mouse interaction enabled.
	 */
	public void createViewForGameBoard() {

		int max = model.getGameBoard().getMax();

		if (panelGameBoard != null) {
			remove(panelGameBoard);
		}

		panelGameBoard = new JPanel();
		add(panelGameBoard, BorderLayout.CENTER);

		allLabels = new JLabel[max][max];
		panelGameBoard.setLayout(new GridLayout(max, max, 1, 1));

		ControllerMouse controllerMouse = new ControllerMouse(model, this);

		for (int row = 0; row < max; row++) {
			for (int column = 0; column < max; column++) {

				int boardValue = model.getGameBoard().getValue(column, row);
				String labelText = (boardValue != 0) ? String.valueOf(boardValue) : "";

				JLabel label = new JLabel(labelText, SwingConstants.CENTER);
				label.putClientProperty("gridPoint", new Point(column, row));
				label.setOpaque(true);
				label.setBackground(Color.LIGHT_GRAY);
				label.setFont(LABEL_FONT);
				label.setName(column + ";" + row);
				label.addMouseListener(controllerMouse);

				allLabels[column][row] = label;
				panelGameBoard.add(allLabels[column][row]);
			}
		}

		revalidate();
		repaint();
		updateView();
	}

	/**
	 * Creates the menu bar containing JSON import and export options.
	 *
	 * @param controller the main controller handling menu actions
	 */
	public void createMenuBar(Controller controller) {

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");

		JMenuItem exportItem = new JMenuItem("JSON-Export");
		JMenuItem importItem = new JMenuItem("JSON-Import");

		exportItem.addActionListener(e -> controller.exportGame());
		importItem.addActionListener(e -> controller.importGame());

		fileMenu.add(exportItem);
		fileMenu.add(importItem);
		menuBar.add(fileMenu);

		setJMenuBar(menuBar);
	}

	/**
	 * Updates the entire view by resetting all cell backgrounds and repainting all
	 * active connections.
	 */
	public void updateView() {
		resetView();
		repaintView();
	}

	/**
	 * Resets all grid cell backgrounds to the default color.
	 */
	private void resetView() {
		for (int row = 0; row < model.getGameBoard().getMax(); row++) {
			for (int column = 0; column < model.getGameBoard().getMax(); column++) {
				allLabels[column][row].setBackground(Color.LIGHT_GRAY);
			}
		}
	}

	/**
	 * Colors all cells belonging to active connections using their assigned color.
	 */
	private void repaintView() {
		for (Connection c : model.getAllConnections()) {
			for (Point p : c.getConn()) {
				allLabels[p.x][p.y].setBackground(c.getColor());
			}
		}
	}

}