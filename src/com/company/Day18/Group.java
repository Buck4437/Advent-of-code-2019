package com.company.Day18;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Group {

    private final ArrayList<Node> nodes = new ArrayList<>();
    private boolean visited = false;

    public Group() {}

    public void addNode(Node n) {
        nodes.add(n);
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public boolean isVisited() {
        return visited;
    }

    public void markAsVisited() {
        visited = true;
    }

    public int getTotalDst() {
        int tot = 0;
        for (Node node : nodes) {
            tot += node.getDst();
        }
        return tot;
    }

    public int getTotalKeyNum() {
        int keyNum = 0;
        for (Node node : nodes) {
            keyNum |= node.getKeyNum();
        }
        return keyNum;
    }

    @Override
    public String toString() {
        StringBuilder nodes_str = new StringBuilder();
        for (Node node : nodes) {
            nodes_str.append(node.toString());
        }
        return "Group{" +
                "nodes=" + nodes_str +
                '}';
    }
}
