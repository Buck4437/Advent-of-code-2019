package com.company.Day18.New;

import static com.company.Day18.New.MainNew3.INFINITY;

public class Node {

    private final char checkpoint;
    private final long keyNum;
    private boolean visited;
    private int dst;

    public Node(char checkpoint, long keyNum) {
        this(checkpoint, keyNum, INFINITY);
    }

    public Node(char checkpoint, long keyNum, int dst) {
        this.checkpoint = checkpoint;
        this.keyNum = keyNum;
        this.visited = false;
        this.dst = dst;
    }

    public void setDst(int dst) {
        this.dst = dst;
    }

    public int getDst() {
        return dst;
    }

    public void makeAsVisited() {
        visited = true;
    }

    public boolean isVisited() {
        return visited;
    }

    public long getKeyNum() {
        return keyNum;
    }

    public char getCheckpoint() {
        return checkpoint;
    }

    @Override
    public String toString() {
        return "Node{" +
                "checkpoint=" + checkpoint +
                ", keyNum=" + keyNum +
                '}';
    }
}
