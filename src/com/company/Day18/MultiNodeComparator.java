package com.company.Day18;
import java.util.Comparator;

public class MultiNodeComparator implements Comparator<MultiNode> {
    public int compare(MultiNode p1, MultiNode p2)  {
        return Integer.compare(p1.getDst(), p2.getDst());
    }
}
