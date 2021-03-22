package com.company.Day24;

public class Point3D {

    private static final String DELIMITER = "/";
    private final int x;
    private final int y;
    private final int z;

    public Point3D(String s) {
        String[] s2 = s.split(DELIMITER);
        x = Integer.parseInt(s2[0]);
        y = Integer.parseInt(s2[1]);
        z = Integer.parseInt(s2[2]);
    }

    public Point3D(int x, int y) {
        this(x, y, 0);
    }

    public Point3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public String toString() {
        return x + DELIMITER + y + DELIMITER + z;
    }

    public static String toString(int x, int y) {
        return toString(x, y, 0);
    }

    public static String toString(int x, int y, int z) {
        return new Point3D(x, y, z).toString();
    }

    public static String toString(Point3D point) {
        return point.toString();
    }
}
