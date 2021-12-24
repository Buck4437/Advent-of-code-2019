package com.company.Day20.Part2v2;

import java.util.ArrayList;
import java.util.Arrays;

import static com.company.Day20.Part2v2.Main.INFINITY;

public class Node {

    private final String portal;
    private final int layer;
    private boolean visited;
    private int dst;
    private ArrayList<String> prev = new ArrayList<>();

    public Node(String portal, int layer) {
        this(portal, layer, INFINITY);
    }

    public Node(String portal, int layer, int dst) {
        this.portal = portal;
        this.layer = layer;
        this.dst = dst;
        this.visited = false;
    }

    public void setPrev(ArrayList<String> prev) {
        this.prev = new ArrayList<>(prev);
    }

    public void addPrev(String s) {
        this.prev.add(s);
    }

    public ArrayList<String> getPrev() {
        return prev;
    }

    public void setDst(int dst) {
        this.dst = dst;
    }

    public int getDst() {
        return dst;
    }

    public void markAsVisited() {
        visited = true;
    }

    public boolean isVisited() {
        return visited;
    }

    public boolean equalPos(Node n2) {
        return portal.equals(n2.getPortal()) && layer == n2.getLayer();
    }

    public int getLayer() {
        return layer;
    }

    public String getPortal() {
        return portal;
    }

    @Override
    public String toString() {
        return "Node{" +
                "portal=" + portal +
                ", layer=" + layer +
                '}';
    }
}
