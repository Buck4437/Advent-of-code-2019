package com.company.Day18;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    static final int[][] VECTORS = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}}; // N E S W

    static Hashtable<String, Character> maze = new Hashtable<>();

    static Hashtable<String, Integer> prevRuns = new Hashtable<>();

    public static void main(String[] args) throws FileNotFoundException {
        long time = System.nanoTime();
        int[] beginning = parse();
        System.out.println(transverse(beginning));
        System.out.printf("Time: %ss\n", (System.nanoTime() - time) / 1e9);
    }

    public static int transverse(int[] start) {
        return transverse(start, new ArrayList<>(), true);
    }

    public static int transverse(int[] start, ArrayList<Character> inv) {
        return transverse(start, inv, false);
    }

    private static int transverse(int[] start, ArrayList<Character> inv, boolean initial) {
        Collections.sort(inv);
        String id = Arrays.toString(start) + inv.toString();
        if (prevRuns.containsKey(id)) {
            return prevRuns.get(id);
        }

        int[] cur = start.clone();
        int dir = 0; // North
        HashSet<String> prev = new HashSet<>();
        boolean end = false;

        ArrayList<Integer> steps = new ArrayList<>();
        int min = Integer.MAX_VALUE;
        boolean hasNewKey = false;
        char newKey = '0';

        while (!end) {
            int[] newPos = new int[2];
            int newDir = 0;
            for (int i = 0; i < 4; i++) {
                newDir = Math.floorMod(dir - 1 + i, 4);
                newPos = new int[]{cur[0] + VECTORS[newDir][0], cur[1] + VECTORS[newDir][1]};
                char c = maze.get(pointToStr(newPos[0], newPos[1]));
                if (c == '#') continue;
                if (c == '.') break;
                if (isDoor(c)) {
                    if (inv.contains(Character.toLowerCase(c))) break;
                } else {
                    if (!inv.contains(c)) { // Key that you don't own
                        hasNewKey = true;
                        newKey = c;
                    }
                    break;
                }
            }

            cur = newPos;
            dir = newDir;

            if (steps.size() != 0) {
                if (Math.abs(steps.get(steps.size() - 1) - newDir) == 2) {
                    steps.remove(steps.size() - 1);
                } else {
                    steps.add(newDir);
                }
            } else {
                steps.add(newDir);
            }

            if (initial) { // fuck the 3x3 square
                dir = 0;
                initial = false;
            }

            if (hasNewKey) {
//                System.out.printf("You found a \"%s\" key!\n", newKey);
//                System.out.printf("Steps to key: %s\n", steps.toString());

                ArrayList<Character> newInv = new ArrayList<>(inv);
                newInv.add(newKey);
                int minMove = transverse(cur, newInv) + steps.size();
                if (minMove < min) {
                    min = minMove;
                }

                hasNewKey = false;
            }

            end = !prev.add(vectorToStr(cur[0], cur[1], dir));
        }

        int result = newKey == '0' ? 0 : min;

        prevRuns.put(id, result);

        return result;
    }

    public static int[] parse() throws FileNotFoundException {
        File file = new File("src\\com\\company\\Day18\\input.txt");
        Scanner sc = new Scanner(file);

        ArrayList<String> str = new ArrayList<>();
        while (sc.hasNextLine()) {
            str.add(sc.nextLine());
        }

        int[] beginning = new int[]{0, 0};

        for (int y = 0; y < str.size(); y++) { // top to bottom
            String row = str.get(y);
            for (int x = 0; x < row.length(); x++) { // left to right
                char c = row.charAt(x);
                if (c == '@') {
                    beginning = new int[]{x, y};
                    c = '.';
                }
                maze.put(pointToStr(x, y), c);
            }
        }

        return beginning;
    }

    private static boolean isDoor(char c) {
        return Character.toUpperCase(c) == c;
    }

    public static String pointToStr(int x, int y) {
        return x + ", " + y;
    }

    public static String vectorToStr(int x, int y, int dir) {
        return x + ", " + y + ", " + dir;
    }

}
