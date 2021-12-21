package com.company.Day18;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Part1 {

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
        char[][] input = parse("input_part1.txt");
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
        HashMap<String, Node> nodes = new HashMap<>();
        Queue<Node> unvisited = new PriorityQueue<>(new NodeComparator());

        Node beginning_node = new Node(BEGINNING_CHARACTER, 0, 0);
        unvisited.add(beginning_node);

        while (unvisited.size() > 0) {

            Node node = unvisited.poll();

            if (node.getKeyNum() == targetNumber) {
                return node.getDst();
            }

            node.markAsVisited();

            HashSet<Character> node_neighbours = neighbours.get(node.getCheckpoint());
//            System.out.printf("Neighbours of %s are: %s\n", node.getCheckpoint(), node_neighbours);

            for (char neighbour : node_neighbours) {
                long newKeyNum = node.getKeyNum();
                int type_of_neigh = getType(neighbour);

                // A door
                if (type_of_neigh == UPPER) {
                    if (!canOpen(newKeyNum, neighbour)) {
                        continue;
                    }
                    // A Key
                } else if (type_of_neigh == LOWER) {
                    newKeyNum = addKey(newKeyNum, neighbour);
                }

                Node neighbourNode = new Node(neighbour, newKeyNum);
                String neigh_key = neighbourNode.toString();

                Node prevNode = nodes.get(neigh_key);
                if (prevNode != null && prevNode.isVisited()) {
                    continue;
                }

                // This neighbour, after picking up the key, has not been visited before
                int newDst = node.getDst() + map.get(Character.toString(node.getCheckpoint()) + neighbour);
                neighbourNode.setDst(newDst);

                // Update distance
                if (prevNode == null || prevNode.getDst() > newDst) {
                    // Remove and reinsert a new element
                    unvisited.remove(prevNode);
                    unvisited.add(neighbourNode);
                    nodes.put(neigh_key, neighbourNode);
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
