package model;

import java.awt.Point;
import java.util.ArrayList;
import javax.swing.text.View;



public class Model {

	public ArrayList<Connection> allConnections;
	int currentConnectionIndex;
	public GameBoard gameBoard;
	View view;
	public boolean canAddPointsToCurrentConnection = true;
	
	public Model() {
		this.allConnections = new ArrayList<>();
		this.currentConnectionIndex = -1;
		this.gameBoard = new GameBoard();

	}

	public void startConnection(Point p) {
		
		if(isPointAlreadyUsed(p)) {
			canAddPointsToCurrentConnection = false;
		}
		this.allConnections.add(new Connection());
		currentConnectionIndex++;
		addPointToCurrentConnection(p);
	}

	public void mouseReleased() {

		canAddPointsToCurrentConnection = true;

		if(this.allConnections.size() > 0 && this.allConnections.get(currentConnectionIndex).conn.size() == 1) {
			this.allConnections.remove(currentConnectionIndex);
			currentConnectionIndex--;
		}
		
	}

	public void addPointToCurrentConnection(Point p) {
		if(!canAddPointsToCurrentConnection)
			return;
		if(isPointAlreadyUsed(p)) {
			canAddPointsToCurrentConnection = false;
			deleteCurrentConnection();
			deleteConnectionWithPoint(p);
			return;
		}

		Connection currentConnection = this.allConnections.get(currentConnectionIndex);
		if(!currentConnection.add(p)) {
			this.allConnections.add(new Connection());
			currentConnectionIndex++;
			this.allConnections.get(currentConnectionIndex).add(p);
		}
		
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

	public void printOutAllConnections() {
		for(Connection c : this.allConnections) {
			System.out.println("Connection with color " + c.color + " and points: ");
			for(Point p : c.conn) {
				System.out.println(p);
			}
		}
	}
   



	
	
	
	
	

	
}
