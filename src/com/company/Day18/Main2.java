package com.company.Day18;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main2 {
    
    /*
    idea:
    - Separate into "Key locator" and "Steps calculator" to prevent stackoverflow
    - Key locator: calculate the minimum steps required to get X keys
    - Step calculator: calculate steps required to go from 1 place to another
     */

    /*
    idea2:

    revert back to old code (Works better (?))
     */


    static final int[][] VECTORS = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}}; // N E S W

    static Hashtable<String, Character> maze = new Hashtable<>();
    static Hashtable<Character, int[]> keys = new Hashtable<>();

    static int TOTAL_KEYS = 0;

    public static void main(String[] args) throws FileNotFoundException {
        long time = System.nanoTime();
        int[] beginning = parse();
        System.out.println(steps(beginning[0], beginning[1]));

//        System.out.println(calcSteps(beginning[0], beginning[1], keys.get('a')[0], keys.get('a')[1], new ArrayList<>()));

        System.out.printf("Time: %ss\n", (System.nanoTime() - time) / 1e9);
    }

    static Hashtable<String, Integer> stepsCache = new Hashtable<>();

    public static int steps(int x, int y) {
        return steps(x, y, new ArrayList<>());
    }

    public static int steps(int x, int y, ArrayList<Character> inv) {
        if (inv.size() >= TOTAL_KEYS) return 0;
        String id = stateToStr(x, y, inv);
        if (stepsCache.containsKey(id)) {
            return stepsCache.get(id);
        }

        int init = 1000000;

        int steps = init;

        Enumeration<Character> enums = keys.keys();

        while (enums.hasMoreElements()) {
            char key = enums.nextElement();
            if (inv.contains(key)) continue;

            int[] keyCoord = keys.get(key);
            int base = calcSteps(x, y, keyCoord[0], keyCoord[1], inv);
            ArrayList<Character> newInv = new ArrayList<>(inv);
            newInv.add(key);
            steps = Math.min(steps, base + steps(keyCoord[0], keyCoord[1], newInv));
        }

        if (steps != init) { // Has possible move
            Main2.stepsCache.put(id, steps);
        }

        return steps;
    }

    static Hashtable<String, Integer> calcStepsCache = new Hashtable<>();

    public static int calcSteps(int x1, int y1, int x2, int y2, ArrayList<Character> inv) {
        return calcSteps(x1, y1, x2, y2, inv, new ArrayList<>());
    }

    public static int calcSteps(int x1, int y1, int x2, int y2, ArrayList<Character> inv, ArrayList<String> prev) {
        if (x1 == x2 && y1 == y2) return 0;

        Collections.sort(inv);

        String id = lineToStr(x1, y1, x2, y2) + " " + inv;
        if (calcStepsCache.containsKey(id)) {
            return calcStepsCache.get(id);
        }

        int init = 1000000;

        int steps = init;


        ArrayList<String> newPrev = new ArrayList<>(prev);
        newPrev.add(pointToStr(x1, y1));

        for (int[] v : VECTORS) {
            int newX = x1 + v[0], newY = y1 + v[1];
            if (!prev.contains(pointToStr(newX, newY))) {
                char c = getLayout(newX, newY);
                if (c == '.' || isKey(c)) { // Space and Key
                    steps = Math.min(steps, calcSteps(newX, newY, x2, y2, new ArrayList<>(inv), newPrev) + 1);
                } else if (c != '#') { // Door
                    if (inv.contains(Character.toLowerCase(c))) { // Can open
                        steps = Math.min(steps, calcSteps(newX, newY, x2, y2, new ArrayList<>(inv), newPrev) + 1);
                    }
                } // Wall is excluded
            }
        }

        if (steps != init) { // Has possible move
            calcStepsCache.put(id, steps);
        }

        return steps;
    }

    public static char getLayout(int x, int y) {
        return maze.get(pointToStr(x, y));
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
                } else if (isKey(c)) {
                    keys.put(c, new int[]{x, y});
                    TOTAL_KEYS ++;
                }
                maze.put(pointToStr(x, y), c);
            }
        }

        return beginning;
    }

    private static boolean isKey(char c) {
        return Character.toLowerCase(c) == c && c != '#' && c != '.';
    }

    public static String lineToStr(int x1, int y1, int x2, int y2) {
        return pointToStr(x1, y1) + " " + pointToStr(x2, y2);
    }


    public static String pointToStr(int x, int y) {
        return x + ", " + y;
    }

    public static String stateToStr(int x, int y, ArrayList<Character> inv) {
        Collections.sort(inv);
        return x + ", " + y + ", " + inv.toString();
    }

}
