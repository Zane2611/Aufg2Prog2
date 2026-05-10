package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Central game model managing the game board, connections, drawing state and
 * win detection.
 *
 * @author Philipp Palm
 */
public class Model {

	/** List of all connections currently drawn on the board. */
	private ArrayList<Connection> allConnections;

	/** The active game board containing the grid layout and number positions. */
	private GameBoard gameBoard;

	/** The current drawing state of the interaction. */
	private DrawState state;

	/** Index of the connection currently being drawn, or -1 if none is active. */
	private int currentConnectionIndex;

	/** The grid point where the current mouse interaction started. */
	private Point startPoint;

	/** The field number of the cell where the current connection was started. */
	private String startFieldNumber;
	
	/**
	 * The field number of the last cell entered during the current drawing
	 * interaction.
	 */
	private String currentFieldNumber;

	/**
	 * Creates a new model with a default game board and empty connection list.
	 */
	public Model() {
		allConnections = new ArrayList<>();
		currentConnectionIndex = -1;
		gameBoard = new GameBoard();
		startPoint = null;
		state = DrawState.IDLE;
	}

	/**
	 * Handles a mouse press on the given grid point. Starts a new connection if the
	 * point holds a number and is not yet used, or marks the point as selected if
	 * it already belongs to a connection.
	 *
	 * @param point       the grid coordinates of the pressed cell
	 * @param fieldNumber the number displayed in the cell, or an empty string if
	 *                    the cell is empty
	 */
	public void mousePressed(Point point, String fieldNumber) {
		startPoint = point;
		startFieldNumber = "";

		if (isPointAlreadyInAnyConnection(point)) {
			state = DrawState.SELECTED;
			return;
		}
		if (fieldNumber.isEmpty()) {
			state = DrawState.INVALID;
			startPoint = null;
			return;
		}
		startFieldNumber = fieldNumber;
		state = DrawState.DRAWING;
		startNewConnection(point);
	}

	/**
	 * Handles the mouse entering a cell while the mouse button is held down.
	 * Extends the current connection, performs backtracking if the cell was already
	 * visited, or blocks the move if it would violate the game rules.
	 *
	 * @param point       the grid coordinates of the entered cell
	 * @param fieldNumber the number displayed in the cell, or an empty string if
	 *                    the cell is empty
	 */
	public void mouseEntered(Point point, String fieldNumber) {
		currentFieldNumber = fieldNumber;

		if (state != DrawState.DRAWING || currentConnectionIndex < 0) {
			if (state == DrawState.SELECTED) {
				state = DrawState.IDLE;
			}
			return;
		}

		if (handleBacktracking(point))
			return;

		if (handleInvalidMove(point, fieldNumber))
			return;

		addPointToCurrentConnection(point);
	}

	/**
	 * Handles the mouse button being released. Finalises or discards the current
	 * connection depending on its validity, or deletes an existing connection when
	 * the user only clicked without moving.
	 *
	 * @param releasePoint the grid coordinates of the cell where the mouse was
	 *                     released
	 */
	public void mouseReleased(Point releasePoint) {
		boolean singlePointClick = releasePoint.equals(startPoint);

		if (state == DrawState.SELECTED && singlePointClick) {
			deleteConnectionContainingPoint(releasePoint);
		} else if (state == DrawState.DRAWING && isConnectionInvalid()) {
			deleteCurrentConnection();
		}
		state = DrawState.IDLE;
		startPoint = null;
	}

	/**
	 * Replaces the current game board with a new one and resets all connections.
	 *
	 * @param newBoard the new game board to use
	 */
	public void setGameBoard(GameBoard newBoard) {
		gameBoard = newBoard;
		allConnections.clear();
		currentConnectionIndex = -1;
	}

	/**
	 * Checks whether all numbers on the board are correctly connected. A connection
	 * is valid if it links exactly two cells sharing the same number with no other
	 * numbered cells in between.
	 *
	 * @return true if every number on the board is part of a valid connection
	 */
	public boolean detectWinning() {
		Set<Integer> solvedNumbers = getSolvedNumbersFromConnections();
		Set<Integer> boardNumbers = getBoardNumbers();
		return solvedNumbers.equals(boardNumbers);
	}

	/**
	 * Deletes the currently active connection.
	 */
	public void deleteCurrentConnection() {
		if (!allConnections.isEmpty()) {
			allConnections.remove(currentConnectionIndex);
			currentConnectionIndex--;
		}
	}

	/**
	 * Deletes the connection that contains the given point.
	 *
	 * @param wantedPoint the point whose containing connection should be removed
	 */
	public void deleteConnectionContainingPoint(Point wantedPoint) {
		Iterator<Connection> iterator = allConnections.iterator();

		while (iterator.hasNext()) {
			Connection c = iterator.next();

			for (Point point : c.getConn()) {
				if (point.equals(wantedPoint)) {
					iterator.remove();
					currentConnectionIndex--;
					return;
				}
			}
		}
	}

	/**
	 * Returns the current game board.
	 *
	 * @return the active GameBoard instance
	 */
	public GameBoard getGameBoard() {
		return gameBoard;
	}

	/**
	 * Returns all existing connections.
	 *
	 * @return list of all Connection objects
	 */
	public ArrayList<Connection> getAllConnections() {
		return allConnections;
	}

	/**
	 * Starts a new connection by adding a fresh Connection object and recording the
	 * starting point.
	 *
	 * @param point the first point of the new connection
	 */
	private void startNewConnection(Point point) {
		addConnection();
		addPointToCurrentConnection(point);
	}

	/**
	 * Performs backtracking if the given point is already part of the current
	 * connection, removing all points that were added after it.
	 *
	 * @param point the point to backtrack to
	 * @return true if backtracking was performed, false otherwise
	 */
	private boolean handleBacktracking(Point point) {
		if (!isPointInCurrentConnection(point))
			return false;

		ArrayList<Point> conn = getCurrentConnList();
		while (!conn.get(conn.size() - 1).equals(point)) {
			conn.remove(conn.size() - 1);
		}
		return true;
	}

	/**
	 * Checks whether adding the given point to the current connection would violate
	 * the game rules (point already used, or wrong field number).
	 *
	 * @param point       the candidate point
	 * @param fieldNumber the number of the candidate cell
	 * @return true if the move is invalid and should be blocked
	 */
	private boolean handleInvalidMove(Point point, String fieldNumber) {
		if (isPointAlreadyInAnyConnection(point))
			return true;

		if (!isFieldNumberEqualOrEmpty(fieldNumber))
			return true;

		return false;
	}

	/**
	 * Checks whether a connection that has just been drawn is invalid (too short,
	 * or the end field number does not match the start).
	 *
	 * @return true if the connection should be discarded
	 */
	private boolean isConnectionInvalid() {
		if (getCurrentConnList().size() <= 1)
			return true;
		if (!startFieldNumber.equals(currentFieldNumber))
			return true;
		return false;
	}

	/**
	 * Creates a new empty Connection and appends it to the connection list.
	 */
	private void addConnection() {
		allConnections.add(new Connection());
		currentConnectionIndex++;
	}

	/**
	 * Appends a point to the currently active connection.
	 *
	 * @param p the point to add
	 */
	private void addPointToCurrentConnection(Point p) {
		allConnections.get(currentConnectionIndex).add(p);
	}

	/**
	 * Returns the point list of the currently active connection.
	 *
	 * @return the list of points in the current connection
	 */
	private ArrayList<Point> getCurrentConnList() {
		return allConnections.get(currentConnectionIndex).getConn();
	}

	/**
	 * Checks whether a field number is either empty or matches the start field
	 * number. Empty cells and the matching end cell are the only valid targets
	 * while drawing.
	 *
	 * @param fieldNumber the field number to check
	 * @return true if the cell may be entered
	 */
	private boolean isFieldNumberEqualOrEmpty(String fieldNumber) {
		return fieldNumber.isEmpty() || fieldNumber.equals(startFieldNumber);
	}

	/**
	 * Checks whether the given point is already part of the current connection.
	 *
	 * @param p the point to search for
	 * @return true if the point is contained in the current connection
	 */
	private boolean isPointInCurrentConnection(Point p) {
		for (Point point : allConnections.get(currentConnectionIndex).getConn()) {
			if (point.equals(p))
				return true;
		}
		return false;
	}

	/**
	 * Checks whether the given point is already part of any existing connection.
	 *
	 * @param p the point to search for
	 * @return true if the point is used in any connection
	 */
	private boolean isPointAlreadyInAnyConnection(Point p) {
		for (Connection c : allConnections) {
			for (Point point : c.getConn()) {
				if (point.equals(p))
					return true;
			}
		}
		return false;
	}

	/**
	 * Collects all numbers that are currently solved by a valid connection. Returns
	 * an empty set if any connection is invalid.
	 *
	 * @return set of solved numbers, or an empty set if validation fails
	 */
	private Set<Integer> getSolvedNumbersFromConnections() {
		Set<Integer> solvedNumbers = new HashSet<>();

		for (Connection c : allConnections) {
			Integer number = getConnectionNumberIfValid(c);

			if (number == null)
				return Collections.emptySet();

			solvedNumbers.add(number);
		}
		return solvedNumbers;
	}

	/**
	 * Validates a single connection and returns the number it connects, or null if
	 * the connection is invalid. A valid connection contains exactly two cells with
	 * the same number and no other numbered cells.
	 *
	 * @param c the connection to validate
	 * @return the connected number, or null if the connection is invalid
	 */
	private Integer getConnectionNumberIfValid(Connection c) {
		Set<Integer> numbersInConnection = new HashSet<>();

		for (Point p : c.getConn()) {
			int value = gameBoard.getValue(p.x, p.y);
			if (value != 0)
				numbersInConnection.add(value);
		}

		if (numbersInConnection.size() != 1)
			return null;

		int number = numbersInConnection.iterator().next();
		int count = 0;

		for (Point p : c.getConn()) {
			if (gameBoard.getValue(p.x, p.y) == number)
				count++;
		}

		if (count != 2)
			return null;

		return number;
	}

	/**
	 * Collects all distinct numbers present on the game board.
	 *
	 * @return set of all non-zero values on the board
	 */
	private Set<Integer> getBoardNumbers() {
		Set<Integer> boardNumbers = new HashSet<>();
		int size = gameBoard.getFld().length;

		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				int value = gameBoard.getValue(x, y);
				if (value != 0)
					boardNumbers.add(value);
			}
		}
		return boardNumbers;
	}
}