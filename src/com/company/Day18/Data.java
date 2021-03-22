package com.company.Day18;

import java.util.ArrayList;
import java.util.Collections;

public class Data {

    private final int x;
    private final int y;
    private final ArrayList<Character> inv;

    public Data(int x, int y) {
        this(x, y, new ArrayList<>());
    }

    public Data(int x, int y, ArrayList<Character> inv) {
        this.x = x;
        this.y = y;
        Collections.sort(inv);
        this.inv = inv;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ArrayList<Character> getInv() {
        return inv;
    }

    public String toString() {
        return x + ", " + y + ", " + inv.toString();
    }

    public static String toString(Data data) {
        return data.toString();
    }
}
