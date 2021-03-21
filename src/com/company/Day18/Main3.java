package com.company.Day18;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main3 {

    /* WARNING: UNFINISHED */

    /*
    idea 3:

    construct graph: consist of vertexes that represents player and keys

    edges consist of info such as distance and doors required

    transversing the graph stores info such as keys

    use depth-first to do shits

     */


    static final int[][] VECTORS = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}}; // N E S W

    static Hashtable<String, Character> maze = new Hashtable<>();
    static Hashtable<Character, int[]> keys = new Hashtable<>();

    static int TOTAL_KEYS = 0;

    public static void main(String[] args) throws FileNotFoundException {
        long time = System.nanoTime();
        int[] beginning = parse();

        System.out.printf("Time: %ss\n", (System.nanoTime() - time) / 1e9);
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
