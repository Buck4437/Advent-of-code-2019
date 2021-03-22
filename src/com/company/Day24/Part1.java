package com.company.Day24;

import java.util.ArrayList;
import java.util.HashSet;

public class Part1 {

    public static void main(String[] args) {
        String init = """
                #.#..
                .....
                .#.#.
                .##..
                .##.#""";

        ArrayList<String> bugs = new ArrayList<>();
        HashSet<Long> prevStates = new HashSet<>();

        for (int y = 0; y < init.split("\n").length; y++) {
            String row = init.split("\n")[y];
            for (int x = 0; x < row.length(); x++) {
                char c = row.charAt(x);
                if (c == '#') {
                    bugs.add(Point.toString(x, y));
                }
            }
        }

        prevStates.add(bioDiversity(bugs));

        while (true) {
            ArrayList<String> mutate = new ArrayList<>();

            for (int y = 0; y < init.split("\n").length; y++) {
                String row = init.split("\n")[y];
                for (int x = 0; x < row.length(); x++) {
                    int count = 0;
                    for (int i = - 1; i <= 1; i++) {
                        for (int j = - 1; j <= 1; j++) {
                            if (Math.abs(i) + Math.abs(j) == 1) {
                                if (bugs.contains(Point.toString(x + i, y + j))) {
                                    count++;
                                }
                            }
                        }
                    }
                    boolean hasBug = bugs.contains(Point.toString(x, y));
                    if (hasBug && count == 1 || !hasBug && (count == 1 || count == 2)) {
                        mutate.add(Point.toString(x, y));
                    }
                }
            }

            long bioDiversity = bioDiversity(mutate);
            if (!prevStates.add(bioDiversity)) {
                System.out.println(bioDiversity);
                break;
            }

            bugs = mutate;
        }
    }

    public static long bioDiversity(ArrayList<String> bugs) {
        long count = 0;
        for (String bug : bugs) {
            int x = Integer.parseInt(bug.split(", ")[0]);
            int y = Integer.parseInt(bug.split(", ")[1]);
            count += Math.pow(2, x + y * 5);
        }
        return count;
    }
}
