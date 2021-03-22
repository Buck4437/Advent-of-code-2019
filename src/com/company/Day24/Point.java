package com.company.Day24;

public class Point {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return x + ", " + y;
    }

    public static String toString(int x, int y) {
        return new Point(x, y).toString();
    }

    public static String toString(Point point) {
        return point.toString();
    }
}
