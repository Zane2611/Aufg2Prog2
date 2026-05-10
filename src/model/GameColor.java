package model;

import java.awt.Color;

/**
 * Defines the available colors for connections.
 * Colors can be extended without requiring changes elsewhere.
 *
 * @author Philipp Palm
 */
public enum GameColor {

    ORANGE(Color.ORANGE),
    YELLOW(Color.YELLOW),
    GREEN(Color.GREEN),
    BLUE(Color.BLUE),
    PURPLE(new Color(128, 0, 128)),
    PINK(Color.PINK),
    RED(Color.RED),
    CYAN(Color.CYAN);

    private final Color color;

    /**
     * Creates a GameColor constant with the given AWT color.
     *
     * @param color the AWT color to associate with this constant
     */
    GameColor(Color color) {
        this.color = color;
    }

    /**
     * Returns the AWT color associated with this enum constant.
     *
     * @return the AWT Color object
     */
    public Color getColor() {
        return color;
    }
}