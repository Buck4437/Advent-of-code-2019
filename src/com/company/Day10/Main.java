package com.company.Day10;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        System.out.println(angularPosition(new int[]{0, 0}, new int[]{0, -1}));
        System.out.println(angularPosition(new int[]{0, 0}, new int[]{1, -1}));
        System.out.println(angularPosition(new int[]{0, 0}, new int[]{1, 0}));
        System.out.println(angularPosition(new int[]{0, 0}, new int[]{1, 1}));
        System.out.println(angularPosition(new int[]{0, 0}, new int[]{0, 1}));
        System.out.println(angularPosition(new int[]{0, 0}, new int[]{-1, 1}));
        System.out.println(angularPosition(new int[]{0, 0}, new int[]{-1, 0}));
        System.out.println(angularPosition(new int[]{0, 0}, new int[]{-1, -1}));


        File file = new File("src\\com\\company\\Day10\\input.txt");
        Scanner sc = new Scanner(file);

        ArrayList<String> map = new ArrayList<>();
        while (sc.hasNextLine()) {
            map.add(sc.nextLine());
        }

        ArrayList<int[]> asteroids = new ArrayList<>();

        for (int i = 0; i < map.get(0).length(); i++) {
            for (int j = 0; j < map.size(); j++) {
                if (map.get(j).charAt(i) == '#') {
                    asteroids.add(new int[]{i, j});
                }
            }
        }

        int[] bestPos = new int[2];
        int max = 0;

        for (int[] asteroid : asteroids) {
            HashSet<String> ratios = new HashSet<>();
            for (int[] target : asteroids) {
                if (asteroid != target) {
                    int x = target[0] - asteroid[0], y = target[1] - asteroid[1];
                    int hcf = hcf(x, y);
                    String ratio = x/hcf + ":" + y/hcf;
                    ratios.add(ratio);
                }
            }
            int count = ratios.size();
            if (count > max) {
                bestPos = asteroid;
                max = count;
            }
        }

        System.out.printf("Best position: %s, asteroids: %s\n", Arrays.toString(bestPos), max);

        double angle = -1; //Dun set it to 0 or stuff breaks
        int[] destroyed = new int[0];
        for (int i = 0; i < 200; i++) {
            int[] target = null;
            double targetAngular = 999;
            while (true) {
                for (int[] asteroid : asteroids) {
                    if (Arrays.equals(asteroid, bestPos)) continue;
                    double astAngular = angularPosition(bestPos, asteroid);
                    if (astAngular > angle) {
                        if (astAngular > targetAngular) continue;
                        else if (astAngular == targetAngular) {
                            if (distance(bestPos, asteroid) > distance(bestPos, target)) continue;
                        }
                        target = asteroid;
                        targetAngular = astAngular;
                    }
                }
                if (target == null) {
                    angle = -1; //reset position
                } else {
                    break;
                }
            }
            asteroids.remove(target);
            destroyed = target;
            angle = targetAngular;
            System.out.printf("%s asteroid destroyed at: %s\n", i + 1, Arrays.toString(destroyed));
        }

        System.out.println(Arrays.toString(destroyed));
    }

    public static int hcf(int a, int b) {
        int hcf = 1;
        for (int i = 2; i <= Math.abs(a) || i <= Math.abs(b); i++) {
            if (a % i == 0 && b % i == 0) hcf = i;
        }
        return hcf;
    }

    public static double angularPosition(int[] base, int[] target) { // N = 0, E = 90 etc...
        float x = target[0] - base[0], y = target[1] - base[1];
        if (y == 0) {
            if (x > 0) return 90;
            return 270;
        }

        double degrees = Math.toDegrees(Math.atan(-x / y));

        if (y > 0) {
            return degrees + 180;
        } else if (x < 0) {
            return degrees + 360;
        }
        return degrees;
    }

    public static double distance(int[] a, int[] b) {
        return Math.sqrt(Math.pow(a[0] - b[0], 2) + Math.pow(a[1] - b[1], 2));
    }

}
