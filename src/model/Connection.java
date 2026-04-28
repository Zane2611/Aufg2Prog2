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
    if (this.conn.size() == 0)
        return true;

    Point last = this.conn.getLast();

    int xDiff = Math.abs(last.x - p.x);
    int yDiff = Math.abs(last.y - p.y);

    return (xDiff == 1 && yDiff == 0) ||
           (xDiff == 0 && yDiff == 1);
}

public boolean add(Point p) {
	if(isPointHorizontalOrVertical(p) && !this.conn.contains(p)) {
		this.conn.add(p);
		return true;
	}
	return false;
}
}
