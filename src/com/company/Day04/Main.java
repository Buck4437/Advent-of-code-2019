package com.company.Day04;

import java.util.*;

public class Main {


    public static void main(String[] args) {
        int min = 307237;
        int max = 769058;

        int count = 0;

        for (int i = min; i <= max; i++) {
            ArrayList<Integer> numArray = toIntArray(i);

            //Ascending order
            Collections.sort(numArray);
            if (!numArray.toString().equals(toIntArray(i).toString())) {
                continue;
            }

            //At least 1 x2
            Hashtable<Integer, Integer> table = new Hashtable<>();
            for (int j : numArray) {
                Integer c = table.get(j);
                if (c == null) {
                    table.put(j, 1);
                } else {
                    table.put(j, c+1);
                }
            }

            Set<Map.Entry<Integer, Integer>> entrySet = table.entrySet();
            for (Map.Entry<Integer, Integer> entry : entrySet) {
                if (entry.getValue() == 2) {
                    System.out.println(i + " is valid");
                    count++;
                    break;
                }
            }

        }

        System.out.printf("Total: %s", count);
    }

    public static ArrayList<Integer> toIntArray(int i) {
        ArrayList<Integer> array = new ArrayList<>();
        while (i > 0) {
            array.add(0, i % 10);
            i = (i - i % 10) / 10;
        }
        return array;
    }

}
