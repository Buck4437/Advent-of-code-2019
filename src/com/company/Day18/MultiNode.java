package com.company.Day18;

import java.util.ArrayList;
import java.util.Arrays;

import static com.company.Day18.Part1.INFINITY;

public class MultiNode {

    private final char[] checkpoints;
    private final long keyNum;
    private boolean visited;
    private int dst;

    public MultiNode(char[] checkpoint, long keyNum) {
        this(checkpoint, keyNum, INFINITY);
    }

    public MultiNode(char[] checkpoint, long keyNum, int dst) {
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

    public char[] getCheckpoints() {
        return checkpoints;
    }

    @Override
    public String toString() {
        return "MultiNode{" +
                "checkpoints=" + Arrays.toString(checkpoints) +
                ", keyNum=" + keyNum +
                '}';
    }
}
