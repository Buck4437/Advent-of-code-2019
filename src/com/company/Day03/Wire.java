package com.company.Day03;

import java.util.ArrayList;

public class Wire {

    ArrayList<Line> lines = new ArrayList<>();
    String steps;

    public Wire(String vectors) {
        steps = vectors;
        int[] coors = {0, 0};
        int steps = 0;
        for (String vector : vectors.split(",")) {
            int mag = Integer.parseInt(vector.substring(1));
            int[] coor1 = coors.clone();
            switch (vector.charAt(0)) {
                case 'R' -> coors[0] += mag;
                case 'L' -> coors[0] -= mag;
                case 'U' -> coors[1] += mag;
                case 'D' -> coors[1] -= mag;
            }
            int[] coor2 = coors.clone();
            lines.add(new Line(coor1, coor2, steps));
            steps += mag;
        }
    }

    public int intersect(Wire wire2) {
        int distance = Integer.MAX_VALUE;
        for (Line line : lines) {
            for (Line line2 : wire2.lines) {
                if (line.intersection(line2) != null) {
                    int[] intersection = line.intersection(line2);
                    System.out.printf("{%s, %s}\n", intersection[0], intersection[1]);
                    int newD = Math.abs(intersection[0]) + Math.abs(intersection[1]);
                    if (newD < distance) {
                        distance = newD;
                    }
                }
            }
        }

        return distance;
    }

    public int steps(Wire wire2) {
        int steps = Integer.MAX_VALUE;
        for (Line line : lines) {
            for (Line line2 : wire2.lines) {
                if (line.intersection(line2) != null) {
                    int newS = line.steps(line2);
                    System.out.println(newS);
                    if (newS < steps) {
                        steps = newS;
                    }
                }
            }
        }

        return steps;
    }

}
