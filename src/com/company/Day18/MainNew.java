package com.company.Day18;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;
public class MainNew {

    public static void main(String[] args) throws FileNotFoundException {
        long time = System.nanoTime();
        System.out.println(execute());
        System.out.printf("Time: %ss\n", (System.nanoTime() - time) / 1e9);
    }

    public static long execute() throws FileNotFoundException {
        char[][] input = parse();
        constructMap(input);
        return -1;
    }

    // Gonna add 1 for each transversal to a node
    public static void constructMap(char[][] input) {
        HashMap<String, Character> grid = new HashMap<>();
        HashMap<Character, int[]> keys = new HashMap<>();
        HashMap<Character, int[]> doors = new HashMap<>();
        int[] beginning = new int[2];
        for (int x = 0; x < input.length; x++) {
            char[] row = input[x];
            for (int y = 0; y < row.length; y++) {
                char c = row[y];
                int[] coord = new int[]{x, y};

                grid.put(pointToStr(coord), c);
                switch (getType(c)) {
                    case LOWER -> keys.put(c, coord);
                    case UPPER -> doors.put(c, coord);
                    case PLAYER -> beginning = coord;
                }
            }
        }
//        System.out.println(Arrays.toString(beginning));
//        System.out.println(Arrays.toString(keys.get('b')));
        System.out.println(Arrays.toString(beginning));
        int dst = findShortest(grid, beginning, doors.get('E'));
        System.out.println(dst);
    }

    public static int[][] gen_neighbour(int[] coord) {
        int x = coord[0];
        int y = coord[1];
        int[][] neighbours = new int[4][];
        neighbours[0] = new int[]{x-1, y};
        neighbours[1] = new int[]{x+1, y};
        neighbours[2] = new int[]{x, y-1};
        neighbours[3] = new int[]{x, y+1};
        return neighbours;
    }

    // No wall, no door (unless you reach the destination)
    public static int findShortest(HashMap<String, Character> grid, int[] start, int[] end) {
        ArrayDeque<int[]> queue = new ArrayDeque<>();
        HashSet<String> visited = new HashSet<>();
        queue.add(start);
        int steps = 0;
        while (true) {
            ArrayDeque<int[]> unvisited = new ArrayDeque<>();
            while (!queue.isEmpty()) {
                int[] coord = queue.removeFirst();
                visited.add(pointToStr(coord));

                if (Arrays.equals(coord, end)) {
                    return steps;
                } else if (getType(grid.get(pointToStr(coord))) == UPPER) {
                    continue;
                }

                int[][] neighbours = gen_neighbour(coord);
                for (int[] neighbour : neighbours) {
                    String key = pointToStr(neighbour);
                    // Non-existent neighbour / Neighbour is a wall / Neighbour has been visited
                    // Doors are still allowed because the destination might be a door
                    if (grid.get(key) == null || grid.get(key) == '#' || visited.contains(key)) {
                        continue;
                    }
                    unvisited.add(neighbour);
                }
            }

            if (unvisited.isEmpty()) {
                return INFINITY;
            } else {
                queue = unvisited;
                ++steps;
            }
        }
    }

    public static final int LOWER = 5;
    public static final int UPPER = 10;
    public static final int PLAYER = -7;
    public static final int NONE = -1;
    public static final int INFINITY = -3;

    public static int getType(char c) {
        if ('a' <= c && c <= 'z') return LOWER;
        if ('A' <= c && c <= 'Z') return UPPER;
        if (c == '@') return PLAYER;
        return NONE;
    }

    public static char[][] parse() throws FileNotFoundException {
        File file = new File("src\\com\\company\\Day18\\input.txt");
        Scanner sc = new Scanner(file);

        ArrayList<char[]> str = new ArrayList<>();
        while (sc.hasNextLine()) {
            str.add(sc.nextLine().toCharArray());
        }

        char[][] out = new char[str.size()][];
        for (int i = 0; i < str.size(); i++) {
            out[i] = str.get(i);
        }
        return out;
    }

    public static String pointToStr(int[] pt) {
        StringBuilder out = new StringBuilder();
        out.append("(");
        for (int i = 0; i < pt.length; i++) {
            out.append(pt[i]);
            if (i != pt.length - 1) {
                out.append(", ");
            }
        }
        out.append(")");
        return out.toString();
    }
}
