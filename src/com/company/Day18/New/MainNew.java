package com.company.Day18.New;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MainNew {

    public static void main(String[] args) throws FileNotFoundException {
        long time = System.nanoTime();
        System.out.printf("Minimum step: %s\n", execute());
        System.out.printf("Time: %ss\n", (System.nanoTime() - time) / 1e9);
    }

    static HashSet<Character> keys = new HashSet<>();
    static HashSet<Character> doors = new HashSet<>();
    static HashMap<Character, int[]> checkpoints = new HashMap<>();
    static char BEGINNING_CHARACTER = '@';

    public static long execute() throws FileNotFoundException {
        char[][] input = parse("test.txt");
        HashMap<String, Integer> map = constructMap(input);
        System.out.println("Map constructed.");
        return path_find(map);
    }

    public static char[][] parse(String name) throws FileNotFoundException {
        File file = new File("src\\com\\company\\Day18\\" + name);
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

    public static HashMap<String, Integer> constructMap(char[][] input) {
        HashMap<String, Character> grid = new HashMap<>();

        for (int x = 0; x < input.length; x++) {
            char[] row = input[x];
            for (int y = 0; y < row.length; y++) {
                char c = row[y];
                int[] coord = new int[]{x, y};

                grid.put(pointToStr(coord), c);
                switch (getType(c)) {
                    case LOWER -> keys.add(c);
                    case UPPER -> doors.add(c);
                }
                if (getType(c) != NONE) {
                    checkpoints.put(c, coord);
                }
            }
        }

        HashMap<String, Integer> map = new HashMap<>();
        for (char start: checkpoints.keySet()) {
            for (char end: checkpoints.keySet()) {
                if (start == end) {
                    continue;
                }
                String key = Character.toString(start) + end;
                if (map.get(key) != null) {
                    continue;
                }
                String key2 = Character.toString(end) + start;
                int dst = findShortest(grid, checkpoints.get(start), checkpoints.get(end));
                map.put(key, dst);
                map.put(key2, dst);
            }
        }
        return map;
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
                    // This coordinate is a door and not the beginning / end (filtered by return)
                } else if (getType(grid.get(pointToStr(coord))) == UPPER && !Arrays.equals(coord, start)) {
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

    public static Hashtable<Character, HashSet<Character>> gen_neighbour_checkpoints(HashMap<String, Integer> map) {
        Hashtable<Character, HashSet<Character>> table = new Hashtable<>();
        for (String key: map.keySet()) {
            if (map.get(key) == INFINITY) {
                continue;
            }
            char c1 = key.charAt(0);
            char c2 = key.charAt(1);

            if (table.get(c1) == null) {
                table.put(c1, new HashSet<>(c2));
            } else {
                table.get(c1).add(c2);
            }
            if (table.get(c2) == null) {
                table.put(c2, new HashSet<>(c1));
            } else {
                table.get(c2).add(c1);
            }
        }
        return table;
    }

    public static int path_find(HashMap<String, Integer> map) {
        long targetNumber = keySetToKeyNumber(keys);
        System.out.println("Target key number: " + targetNumber);

        Hashtable<Character, HashSet<Character>> neighbours = gen_neighbour_checkpoints(map);
        HashMap<String, Integer> distances = new HashMap<>();
        HashMap<String, Boolean> visited = new HashMap<>();
        HashSet<long[]> unvisited = new HashSet<>();

        long[] beginning_node = {BEGINNING_CHARACTER, 0};
        unvisited.add(beginning_node);
        distances.put(pointToStr(beginning_node), 0);
        long iterations = 0;
        long distance = 0;

        while (unvisited.size() > 0) {
            iterations += 1;
            if (iterations % 1000 == 1) {
                System.out.printf("Iteration %s, distance %s\n", iterations, distance);
                System.out.println(unvisited.size());
            }
            long[] min_dst_node = new long[2];
            int min_dst = INFINITY;
            for (long[] node : unvisited) {
                int dst = distances.get(pointToStr(node));

                if (dst < min_dst) {
                    min_dst_node = node;
                    min_dst = dst;
                }
            }

            distance = min_dst;

            char cur_node_char = (char) min_dst_node[0];
            long key_num = min_dst_node[1];

            if (key_num == targetNumber) {
                return min_dst;
            }

            unvisited.remove(min_dst_node);
            visited.put(pointToStr(min_dst_node), true);

            HashSet<Character> node_neighbours = neighbours.get(cur_node_char);
//            System.out.printf("Neighbours of %s are: %s\n", cur_node_char, node_neighbours);
            for (char neighbour : node_neighbours) {
                long new_key_num = key_num;
                int type_of_neigh = getType(neighbour);

                // A door
                if (type_of_neigh == UPPER) {
                    if (!canOpen(key_num, neighbour)) {
                        continue;
                    }
                    // A Key
                } else if (type_of_neigh == LOWER) {
                    new_key_num = addKey(key_num, neighbour);
                }

                long[] neigh_as_node = {neighbour, new_key_num};
                String neigh_key = pointToStr(neigh_as_node);

                if (visited.get(neigh_key) != null) {
                    continue;
                }

                // This neighbour, after picking up the key, has not been visited before

                // Update distance
                int new_dst = min_dst + map.get(Character.toString(cur_node_char) + neighbour);
                if (distances.get(neigh_key) == null || distances.get(neigh_key) > new_dst) {
                    distances.put(neigh_key, new_dst);
                }

                // Add to unvisited, if it isn't in the set
                boolean contains = unvisited.stream().anyMatch(c -> Arrays.equals(c, neigh_as_node));
                if (!contains) {
                    unvisited.add(neigh_as_node);
                }

            }
        }
        return INFINITY;
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

    public static final int LOWER = 5;
    public static final int UPPER = 10;
    public static final int PLAYER = -7;
    public static final int NONE = -1;
    public static final int INFINITY = 999999999;

    public static int getType(char c) {
        if ('a' <= c && c <= 'z') return LOWER;
        if ('A' <= c && c <= 'Z') return UPPER;
        if (c == '@') return PLAYER;
        return NONE;
    }

    public static boolean canOpen(long keyNum, char door) {
        return (keyNum & (long) Math.pow(2, door - 'A')) != 0;
    }

    public static long addKey(long keyNum, char key) {
        return keyNum | (long) Math.pow(2, key - 'a');
    }

    public static long keySetToKeyNumber(HashSet<Character> keys) {
        long keyNum = 0;
        for (char key : keys) {
            keyNum += Math.pow(2, key - 'a');
        }
        return keyNum;
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

    public static String pointToStr(long[] pt) {
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
