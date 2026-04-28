package model;

import java.awt.Color;

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

GameColor(Color color) {
	this.color = color;
}

public Color getColor() {
	return color;
}
}
