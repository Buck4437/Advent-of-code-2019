package com.company.Day18;

import java.util.ArrayList;

import static com.company.Day18.Part1.INFINITY;

public class MultiNode {

    private final ArrayList<Character> checkpoints;
    private final long keyNum;
    private boolean visited;
    private int dst;

    public MultiNode(ArrayList<Character> checkpoint, long keyNum) {
        this(checkpoint, keyNum, INFINITY);
    }

    public MultiNode(ArrayList<Character> checkpoint, long keyNum, int dst) {
        this.checkpoints = checkpoint;
        this.keyNum = keyNum;
        this.dst = dst;
        this.visited = false;
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

    public long getKeyNum() {
        return keyNum;
    }

    public ArrayList<Character> getCheckpoints() {
        return checkpoints;
    }

    @Override
    public String toString() {
        return "MultiNode{" +
                "checkpoints=" + checkpoints.toString() +
                ", keyNum=" + keyNum +
                '}';
    }
}
