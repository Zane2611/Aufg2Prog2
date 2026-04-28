package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;



public class Model {

	public ArrayList<Connection> allConnections;
	int currentConnectionIndex;
	boolean hasMovedOffStartPoint;
	Point startPoint;
	public GameBoard gameBoard;

	public boolean canAddPointsToCurrentConnection;
	
	public Model() {
		this.allConnections = new ArrayList<>();
		this.currentConnectionIndex = -1;
		this.hasMovedOffStartPoint = false;
		this.canAddPointsToCurrentConnection = true;
		this.gameBoard = new GameBoard();
		startPoint = null;
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

    int size = gameBoard.getBoard().length;

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


	
	public void startConnection(Point p, String labelText) {

    canAddPointsToCurrentConnection = true;
    hasMovedOffStartPoint = false;
 	startPoint = p;
    if (isPointAlreadyUsed(p) || labelText.isEmpty()) {
        canAddPointsToCurrentConnection = false;
        return;
    }

    this.allConnections.add(new Connection());
    currentConnectionIndex++;
    addPointToCurrentConnection(p);
}

	public void mouseEntered(Point p) {
 if(startPoint != null && !p.equals(startPoint)) {
        hasMovedOffStartPoint = true;
    }
    if (!canAddPointsToCurrentConnection)
        return;

    if (currentConnectionIndex < 0)
        return;

    if (isPointAlreadyUsed(p) && !isPointInCurrentConnection(p)) {
        canAddPointsToCurrentConnection = false;
        deleteCurrentConnection();
        deleteConnectionWithPoint(p);
        return;
    }

    addPointToCurrentConnection(p);
}

	public void mouseReleased() {

    canAddPointsToCurrentConnection = true;

   if(!hasMovedOffStartPoint) {
	   deleteConnectionWithPoint(startPoint);
	   
   }

   startPoint = null;

	
}

	public void addPointToCurrentConnection(Point p) {
		Connection currentConnection = this.allConnections.get(currentConnectionIndex);
		currentConnection.add(p); 
		
	}

	public boolean isPointInCurrentConnection(Point p) {
		Connection currentConnection = this.allConnections.get(currentConnectionIndex);
		for(Point point : currentConnection.conn) {
			if(point.equals(p))
				return true;
		}
		return false;
	}

	public boolean isPointAlreadyUsed(Point p) {
		for(Connection c : this.allConnections) {
			for(Point point : c.conn) {
				if(point.equals(p))
					return true;
			}
		}
		return false;
	}

	public void addConnection() {
		this.allConnections.add(new Connection());
		currentConnectionIndex++;
	}

	public void deleteCurrentConnection() {
		if(this.allConnections.size() > 0) {
			this.allConnections.remove(currentConnectionIndex);
			currentConnectionIndex--;
		}
	}
	
	public void deleteConnectionWithPoint(Point p) {
		for(Connection c : this.allConnections) {
			for(Point point : c.conn) {
				if(point.equals(p)) {
					this.allConnections.remove(c);
					currentConnectionIndex--;
					return;
				}
			}
		}
	}

	



	
	
	
	
	

	
}
