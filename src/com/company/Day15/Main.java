package com.company.Day15;

import com.company.Intcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

    static int minX = 0;
    static int maxX = 5;
    static int minY = 0;
    static int maxY = 5;

    static int[] pos = {2, 2};
    static int[][] DIRS = {{0, 0}, {0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    static int[] LEFT = {0, 4, 3, 1, 2};
    static int[] RIGHT = {0, 3, 4, 2, 1};
    static int[] REVERSE = {0, 2, 1, 4, 3};

    static ArrayList<String> steps = new ArrayList<>();

    static HashSet<String> walls = new HashSet<>();

    static ArrayList<String> steps2 = new ArrayList<>();
    static HashSet<String> oxygen = new HashSet<>();
    static int maxStep = 0;

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("src\\com\\company\\Day15\\input.txt");
        Scanner sc = new Scanner(file);
        Scanner sc2 = new Scanner(System.in);

        String[] numS = sc.nextLine().split(",");
        long[] instructions = new long[numS.length];
        for (int i = 0; i < numS.length; i++) {
            instructions[i] = Long.parseLong(numS[i]);
        }

        Intcode intcode = new Intcode(instructions);
//        intcode.showLog(true);
        int code = intcode.run();

        boolean found = false;

        int dir = 1;
        do {
            printMaze(coordToString(pos[0], pos[1]));

//            int in = sc2.nextInt();
//            intcode.input(in);

            int[] spins = {LEFT[dir], dir, RIGHT[dir], REVERSE[dir]};
            int in = 0, status = -1;

            for (int spin : spins) {
                in = spin;
                intcode.input(in);
                status = (int) intcode.getOutput(-1);
                if (status != 0) break;
                addWall(in);
            }

            dir = in;

            if (steps.size() != 0) {
                if (Integer.parseInt(steps.get(steps.size() - 1)) == REVERSE[in]) {
                    steps.remove(steps.size() - 1);
                } else {
                    steps.add(Integer.toString(in));
                }
            } else {
                steps.add(Integer.toString(in));
            }

            if (found) {
                oxygen.add(coordToString(pos[0], pos[1]));
                if (steps2.size() != 0) {
                    if (Integer.parseInt(steps2.get(steps2.size() - 1)) == REVERSE[in]) {
                        steps2.remove(steps2.size() - 1);
                    } else {
                        steps2.add(Integer.toString(in));
                    }
                } else {
                    steps2.add(Integer.toString(in));
                }

                maxStep = Math.max(maxStep, steps2.size());
            }

            int newX = pos[0] + DIRS[in][0];
            int newY = pos[1] + DIRS[in][1];

            if (newX < minX) minX = newX;
            if (newX > maxX) maxX = newX;
            if (newY < minY) minY = newY;
            if (newY > maxY) maxY = newY;

            switch (status) {
                case 2:
                    found = true;
                case 1:
                    pos[0] = newX;
                    pos[1] = newY;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + status);
            }

            System.out.println(coordToString(pos[0], pos[1]));
            System.out.printf("Steps: %s (%s)\n", steps.size(), steps.toString());
            System.out.printf("Max steps for oxygen: %s\n", maxStep);

        } while (true);

//        System.out.println("Program terminated with the code: " + code);

    }

    public static void printMaze(String current) {
        String str = "";
        for (int j = maxY; j >= minY; j--) { // top to bottom
            for (int i = minX; i <= maxX; i++) {
                String c = coordToString(i, j);
                if (current.equals(c)) {
                    str += "T";
                } else {
                    str += walls.contains(c) ? "#" : oxygen.contains(c) ? "O" : " ";
                }
            }
            str += "\n";
        }
        System.out.println(str);
    }

    public static String coordToString(int x, int y) {
        return x + ", " + y;
    }

    public static void addWall(int in) {
        int newX = pos[0] + DIRS[in][0];
        int newY = pos[1] + DIRS[in][1];

        if (newX < minX) minX = newX;
        if (newX > maxX) maxX = newX;
        if (newY < minY) minY = newY;
        if (newY > maxY) maxY = newY;

        walls.add(coordToString(newX, newY));
    }
}
