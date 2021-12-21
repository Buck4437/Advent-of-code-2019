package com.company.Day18;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Part2 {

    public static void main(String[] args) throws FileNotFoundException {
        long time = System.nanoTime();
        System.out.printf("Minimum step: %s\n", execute());
        System.out.printf("Time: %ss\n", (System.nanoTime() - time) / 1e9);
    }

    static HashSet<Character> keys = new HashSet<>();
    static HashSet<Character> doors = new HashSet<>();
    static HashMap<Character, int[]> checkpoints = new HashMap<>();
    static ArrayList<int[]> beginngings = new ArrayList<>();
    static char BEGINNING_CHARACTER = '@';
    static char BEGINNING_INIT_CHARACTER = '0';

    public static long execute() throws FileNotFoundException {
        char[][] input = parse("input_part2.txt");
        HashMap<String, Integer> map = constructMap(input);
        System.out.println("Map constructed.");
//        return -1;
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
                    if (getType(c) == PLAYER) {
                        // Beginning characters use 0-9
                        char replacement = (char) (BEGINNING_INIT_CHARACTER + beginngings.size());
                        beginngings.add(coord);
                        checkpoints.put(replacement, coord);
                    } else {
                        checkpoints.put(c, coord);
                    }
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
//                System.out.printf("Travelling from %s to %s requires %s steps.\n", start, end, dst);
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
        HashMap<String, Group> groups = new HashMap<>();
        HashMap<String, Boolean> visited = new HashMap<>();
        Queue<Group> unvisited = new PriorityQueue<>(new GroupComparator());

        Group beginning_group = new Group();
        for (int i = 0; i < beginngings.size(); i++) {
            Node node = new Node((char) (BEGINNING_INIT_CHARACTER + i), 0, 0);
            beginning_group.addNode(node);
        }
        unvisited.add(beginning_group);

        int iter = 0;

        while (unvisited.size() > 0) {
            Group group = unvisited.poll();

            iter ++;
            if (iter % 10000 == 0) {
                System.out.println(iter);
                System.out.println(group.getTotalDst());
//                System.out.println(groups.size());
            }

            if (group.getTotalKeyNum() == targetNumber) {
                return group.getTotalDst();
            }

//            System.out.println(group.getTotalDst());

//            group.markAsVisited();
            visited.put(group.toString(), true);
            groups.remove(group.toString());

            ArrayList<Node> nodes = group.getNodes();
            for (int i = 0; i < nodes.size(); i++) {
                Node node = nodes.get(i);
                HashSet<Character> node_neighbours = neighbours.get(node.getCheckpoint());

                for (char neighbour : node_neighbours) {
                    long newKeyNum = node.getKeyNum();
                    int type_of_neigh = getType(neighbour);

                    Group neighbour_group = new Group();

                    // It's a key!
                    if (type_of_neigh == LOWER) {
                        newKeyNum = addKey(newKeyNum, neighbour);
                    }

                    long newTotalKeyNum = newKeyNum | group.getTotalKeyNum();

                    // A door
                    if (type_of_neigh == UPPER) {
                        if (!canOpen(newTotalKeyNum, neighbour)) {
                            continue;
                        }
                    }

                    // This node will replace the current node in a new group
                    Node neighbourNode = new Node(neighbour, newKeyNum);

                    for (int j = 0; j < nodes.size(); j++) {
                        if (i == j) {
                            neighbour_group.addNode(neighbourNode);
                        } else {
                            neighbour_group.addNode(nodes.get(j).clone_node());
                        }
                    }

                    String neigh_key = neighbour_group.toString();

                    if (visited.get(neigh_key) != null) {
                        continue;
                    }

                    Group prevGroup = groups.get(neigh_key);
//                    if (prevGroup != null && prevGroup.isVisited()) {
//                        continue;
//                    }

                    // This neighbouring group, after picking up the key, has not been visited before
                    int newDst = node.getDst() + map.get(Character.toString(node.getCheckpoint()) + neighbour);
                    neighbourNode.setDst(newDst);

                    // Update distance
                    if (prevGroup == null || prevGroup.getTotalDst() > neighbour_group.getTotalDst()) {
                        // Remove and reinsert a new element
                        unvisited.remove(prevGroup);
                        unvisited.add(neighbour_group);
                        groups.put(neigh_key, neighbour_group);
                    }
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
}
