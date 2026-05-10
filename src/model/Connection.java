package model;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

/**
 * Represents a single connection drawn by the player.
 * A connection consists of a list of grid points and a color.
 * Colors rotate automatically through all GameColor values.
 *
 * @author Philipp Palm
 */
public class Connection {

    /** Shared index tracking which color the next connection should receive. */
    private static int currentColorIndex = 0;

    private ArrayList<Point> conn;
    private GameColor color;

    /**
     * Creates a new connection and assigns the next available color.
     * The color index advances automatically and wraps around when all
     * colors have been used.
     */
    public Connection() {
        conn = new ArrayList<>();
        color = GameColor.values()[currentColorIndex];
        currentColorIndex = (currentColorIndex + 1) % GameColor.values().length;
    }

    /**
     * Attempts to add a point to the connection.
     * The point is only added if it is horizontally or vertically adjacent
     * to the last point and not already contained in this connection.
     *
     * @param p the point to add
     * @return true if the point was added, false if the move was invalid
     */
    public boolean add(Point p) {
        if (isPointHorizontalOrVertical(p) && !conn.contains(p)) {
            conn.add(p);
            return true;
        }
        return false;
    }

    /**
     * Resets the shared color index so that new connections start
     * again from the first color. Should be called when a new game begins.
     */
    public static void resetColorIndex() {
        currentColorIndex = 0;
    }

    
    /**
     * Returns the list of grid points in this connection.
     *
     * @return the ordered list of points from start to end
     */
    public ArrayList<Point> getConn() {
        return conn;
    }

    /**
     * Returns the AWT color assigned to this connection.
     *
     * @return the color used to render this connection
     */
    public Color getColor() {
        return color.getColor();
    }

   
    /**
     * Checks whether the given point is horizontally or vertically adjacent
     * to the last point in the connection.
     * Returns true unconditionally if the connection is still empty.
     *
     * @param p the point to check
     * @return true if the point is a valid next step, false otherwise
     */
    private boolean isPointHorizontalOrVertical(Point p) {
        if (conn.isEmpty())
            return true;

        Point last = conn.getLast();

        int xDiff = Math.abs(last.x - p.x);
        int yDiff = Math.abs(last.y - p.y);

        return (xDiff == 1 && yDiff == 0) || (xDiff == 0 && yDiff == 1);
    }
}