package com.company.Day18;

import java.util.HashSet;

public class Test {

    public static void main(String[] args) {
        HashSet<int[]> test = new HashSet<>();
        test.add(new int[]{1, 2});
        test.add(new int[]{1, 2});
        System.out.println(test.size());
    }

}
