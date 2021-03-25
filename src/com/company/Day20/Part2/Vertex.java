package com.company.Day20.Part2;

import java.util.LinkedList;

public class Vertex {

    private final String id;

    private final LinkedList<Vertex> adj = new LinkedList<>();

    public Vertex(int x, int y) {
        this.id = x + ", " + y;
    }

    public Vertex(String s) {
        this.id = s;
    }


    public String getId() {
        return id;
    }

    public void addAdj(Vertex n) {
        adj.add(n);
    }

    public LinkedList<Vertex> getAdj() {
        return new LinkedList<>(adj);
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "id='" + id + '\'' +
                '}';
    }

    public static String toString(int x, int y) {
        return new Vertex(x, y).toString();
    }
}
