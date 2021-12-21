package com.company.Day18;
import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {
    public int compare(Node p1, Node p2)  {
        return Integer.compare(p1.getDst(), p2.getDst());
    }
}
