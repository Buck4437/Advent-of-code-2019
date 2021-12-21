package com.company.Day18.New;
import java.util.Comparator;

public class PrioNodeComparator implements Comparator<PrioNode> {
    public int compare(PrioNode p1, PrioNode p2)  {
        return Integer.compare(p1.dst, p2.dst);
    }
}
