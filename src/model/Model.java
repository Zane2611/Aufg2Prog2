package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Model {

	public ArrayList<Connection> allConnections;
	private int currentConnectionIndex;
	private Point startPoint;
	private String startFieldNumber;
	private String currentFieldNumber;
	public GameBoard gameBoard;
	private DrawState state;

	public Model() {
		this.allConnections = new ArrayList<>();
		this.currentConnectionIndex = -1;
		this.gameBoard = new GameBoard();
		startPoint = null;
		this.state = DrawState.IDLE;
	}

	public void setGameBoard(GameBoard newBoard) {
		this.gameBoard = newBoard;
		this.allConnections.clear();
		this.currentConnectionIndex = -1;
	}

	public void mousePressed(Point point, String fieldNumber) {
		startPoint = point;
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

	private void startNewConnection(Point point) {
		addConnection();
		addPointToCurrentConnection(point);
	}

	public void mouseEntered(Point point, String fieldNumber) {
		currentFieldNumber = fieldNumber;
		if (state != DrawState.DRAWING || currentConnectionIndex < 0) {
			if (state == DrawState.SELECTED) {
				state = DrawState.IDLE;
			}
			return;
		}

		if (isPointInCurrentConnection(point)) {
			while (getCurrentConnList().getLast() != point)
				getCurrentConnList().removeLast();
			return;
		}

		if (isPointAlreadyInAnyConnection(point) || !isFieldNumberEqualOrEmpty(currentFieldNumber)) {
//			state = DrawState.INVALID;
//			deleteCurrentConnection();
//			deleteConnectionContainingPoint(point);
			return;
		}

		addPointToCurrentConnection(point);
	}

	private boolean isFieldNumberEqualOrEmpty(String fieldNumber) {
		if (fieldNumber == "" || fieldNumber.equals(startFieldNumber))
			return true;
		return false;
	}

	public void mouseReleased() {
		if (state != DrawState.INVALID && (state == DrawState.SELECTED || isConnectionInvalid())) {
			deleteConnectionContainingPoint(startPoint);
		}
		state = DrawState.IDLE;
		startPoint = null;
	}

	private boolean isConnectionInvalid() {
		if(getCurrentConnList().size() <= 1)
			return true;
		if(!startFieldNumber.equals(currentFieldNumber))
			return true;
		return false;
	}
	
	private void addPointToCurrentConnection(Point p) {
		Connection currentConnection = this.allConnections.get(currentConnectionIndex);
		currentConnection.add(p);

	}

	private ArrayList<Point> getCurrentConnList() {
		return allConnections.get(currentConnectionIndex).conn;
	}

	private boolean isPointInCurrentConnection(Point p) {
		Connection currentConnection = this.allConnections.get(currentConnectionIndex);
		for (Point point : currentConnection.conn) {
			if (point.equals(p))
				return true;
		}
		return false;
	}

	private boolean isPointAlreadyInAnyConnection(Point p) {
		for (Connection c : this.allConnections) {
			for (Point point : c.conn) {
				if (point.equals(p))
					return true;
			}
		}
		return false;
	}

	private void addConnection() {
		this.allConnections.add(new Connection());
		currentConnectionIndex++;
	}

	public void deleteCurrentConnection() {
		if (this.allConnections.size() > 0) {
			this.allConnections.remove(currentConnectionIndex);
			currentConnectionIndex--;
		}
	}

	public void deleteConnectionContainingPoint(Point wantedPoint) {
		for (Connection c : this.allConnections) {
			for (Point point : c.conn) {
				if (point.equals(wantedPoint)) {
					this.allConnections.remove(c);
					currentConnectionIndex--;
					return;
				}
			}
		}
	}

	public boolean detectWinning() {

		Set<Integer> solvedNumbers = getSolvedNumbersFromConnections();

		Set<Integer> boardNumbers = getBoardNumbers();

		return solvedNumbers.equals(boardNumbers);
	}

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

	private Integer getConnectionNumberIfValid(Connection c) {

		Set<Integer> numbersInConnection = new HashSet<>();

		for (Point p : c.conn) {
			int value = gameBoard.getValue(p.x, p.y);
			if (value != 0) {
				numbersInConnection.add(value);
			}
		}

		if (numbersInConnection.size() != 1)
			return null;

		int number = numbersInConnection.iterator().next();

		int count = 0;
		for (Point p : c.conn) {
			if (gameBoard.getValue(p.x, p.y) == number)
				count++;
		}

		if (count != 2)
			return null;

		return number;
	}

	private Set<Integer> getBoardNumbers() {

		Set<Integer> boardNumbers = new HashSet<>();

		int size = gameBoard.getFld().length;

		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {

				int value = gameBoard.getValue(x, y);

				if (value != 0) {
					boardNumbers.add(value);
				}
			}
		}

		return boardNumbers;
	}

}
