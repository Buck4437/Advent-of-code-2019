package com.company.Day18;

import java.util.ArrayList;
import java.util.HashSet;

public class Edge {

    private final int dst;
    private final HashSet<Character> doors;

    public Edge(int dst, HashSet<Character> doors) {
        this.dst = dst;
        this.doors = doors;
    }

    public Edge(int dst) {
        this(dst, new HashSet<>());
    }

    public HashSet<Character> getDoors() {
        return new HashSet<>(doors);
    }

    public int getDst() {
        return dst;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "dst=" + dst +
                ", doors=" + doors +
                '}';
    }
}
