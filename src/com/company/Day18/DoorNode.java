package com.company.Day18;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class DoorNode {

    private final int[] coord;
    private final HashSet<Character> doors;

    public DoorNode(int[] coord, HashSet<Character> doors) {
        this.coord = coord;
        this.doors = doors;
    }

    public DoorNode(int[] coord) {
        this(coord, new HashSet<>());
    }

    public HashSet<Character> getDoors() {
        return new HashSet<>(doors);
    }

    public int[] getCoord() {
        return coord;
    }

    public void addNode(char c) {
        doors.add(c);
    }

    @Override
    public String toString() {
        return "RestrictedNode{" +
                "coord=" + Arrays.toString(coord) +
                '}';
    }
}
