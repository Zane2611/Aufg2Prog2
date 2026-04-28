package model;

import java.awt.Point;
import java.util.ArrayList;

public class Connection {
public static int currentColorIndex = 0;
public ArrayList<Point> conn;
public GameColor color;

public Connection() {
	conn = new ArrayList<Point>();
	color = GameColor.values()[currentColorIndex];
	currentColorIndex = (currentColorIndex + 1) % GameColor.values().length;
}


private boolean isPointHorizontalOrVertical(Point p) {
	if(this.conn.size() == 0)
		return true;
	Point currentLastPoint = this.conn.getLast();
	
	int xDiff = Math.abs(currentLastPoint.x -p.x);
	int yDiff = Math.abs(currentLastPoint.y -p.y);
	
	if(xDiff == 1 || yDiff == 1)
		return true;
	return false;

}
}
