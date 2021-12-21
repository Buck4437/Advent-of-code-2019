package com.company.Day18;
import java.util.Comparator;

public class GroupComparator implements Comparator<Group> {
    public int compare(Group p1, Group p2)  {
        return Integer.compare(p1.getTotalDst(), p2.getTotalDst());
    }
}
