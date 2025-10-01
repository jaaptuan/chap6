package se233.chapter6.model;

import javafx.geometry.Point2D;

public enum Direction {
    RIGHT(new Point2D(1, 0)),
    LEFT(new Point2D(-1, 0)),
    UP(new Point2D(0, -1)),
    DOWN(new Point2D(0, 1));

    public final Point2D current;

    Direction(Point2D current) {
        this.current = current;
    }

    public Direction opposite() { // กันไม่ให้งูย้อนกลับ
        switch (this) {
            case UP: return DOWN;
            case DOWN: return UP;
            case LEFT: return RIGHT;
            case RIGHT: return LEFT;
            default: return null;
        }
    }
}