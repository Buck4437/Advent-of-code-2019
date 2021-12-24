package com.company.Day20.Part2v2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static final int INFINITY = 999999999;
    public static void main(String[] args) throws FileNotFoundException {
        long time = System.nanoTime();
        System.out.printf("Minimum step: %s\n", execute());
        System.out.printf("Time: %ss\n", (System.nanoTime() - time) / 1e9);
    }

    public static int execute() throws FileNotFoundException {
        char[][] maze_layout = parse("input.txt");
        HashMap<String, int[]> portals = formPortal(maze_layout);
        HashMap<String, Character> grid = formGrid(maze_layout);
        HashMap<String, Integer> map = constructMap(grid, portals);
        System.out.println("Map constructed.");
        Hashtable<String, HashSet<String>> nb = gen_neighbour_checkpoints(map);
        return path_find(map);
//        return INFINITY;
    }

    // Reminder: Going between same portal takes 1 step

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

    public static Hashtable<String, HashSet<String>> gen_neighbour_checkpoints(HashMap<String, Integer> map) {
        Hashtable<String, HashSet<String>> neighbours = new Hashtable<>();
        for (String key : map.keySet()) {
            String start = key.substring(0, 3);
            String end = key.substring(3);
            if (neighbours.containsKey(start)) {
                neighbours.get(start).add(end);
            } else {
                HashSet<String> set = new HashSet<>();
                set.add(end);
                neighbours.put(start, set);
            }
        }
        return neighbours;
    }

    public static int path_find(HashMap<String, Integer> map) {
        String start = "AAO", end = "ZZO";
        System.out.println("Finding path...");

        Hashtable<String, HashSet<String>> neighbours = gen_neighbour_checkpoints(map);
        HashMap<String, Node> nodes = new HashMap<>();
        Queue<Node> unvisited = new PriorityQueue<>(new NodeComparator());

        Node beginningNode = new Node("AAO", 0, 0);
        unvisited.add(beginningNode);

        Node targetNode = new Node("ZZO", 0);

        while (unvisited.size() > 0) {

            Node node = unvisited.poll();

            if (node.equalPos(targetNode)) {
                System.out.println(node.getPrev());
                return node.getDst();
            }

            node.markAsVisited();

            int nodeLayer = node.getLayer();
            String portal = node.getPortal();

//            System.out.println(portal);

            HashSet<String> node_neighbours = neighbours.get(portal);

            if (!portal.equals("AAO") && !portal.equals("ZZO")) {
                node_neighbours.add(complement(portal));
            }

            // Nodes in the same layer
            for (String neighbour : node_neighbours) {
                Node neighbourNode;
                boolean pair = is_pair(portal, neighbour);
                if (pair) {
                    if (nodeLayer + (getState(portal) == OUTER ? -1 : 1) < 0) {
                        continue;
                    }
                    neighbourNode = new Node(neighbour, nodeLayer + (getState(portal) == OUTER ? -1 : 1));
                } else {
                    neighbourNode = new Node(neighbour, nodeLayer);
                }
                neighbourNode.setPrev(node.getPrev());
                neighbourNode.addPrev(node.toString());

                String neigh_key = neighbourNode.toString();

                Node prevNode = nodes.get(neigh_key);
                if (prevNode != null && prevNode.isVisited()) {
                    continue;
                }

                // This neighbour, after picking up the key, has not been visited before
                int newDst = node.getDst() + (pair ? 1 : map.get(node.getPortal() + neighbour));
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

    public static HashMap<String, Integer> constructMap(HashMap<String, Character> grid, HashMap<String, int[]> portals) {
        HashMap<String, Integer> map = new HashMap<>();
        HashSet<String> visited = new HashSet<>();
        for (String start: portals.keySet()) {
            for (String end: portals.keySet()) {
                if (start.equals(end)) {
                    continue;
                }
                String key = start + end;
                if (visited.contains(key)) {
                    continue;
                }
                String key2 = end + start;
                int dst = findShortest(grid, portals.get(start), portals.get(end));
                if (dst != INFINITY) {
                    map.put(key, dst);
                    map.put(key2, dst);
                }
                visited.add(key);
                visited.add(key2);
            }
        }
        return map;
    }

    static final int OUTER = 1298;
    static final int INNER = 2348;

    public static int getState(String portal) {
        return portal.charAt(2) == 'O' ? OUTER : INNER;
    }

    public static String complement(String portal) {
        return portal.substring(0, 2) + (getState(portal) == OUTER ? "I" : "O");
    }

    public static boolean is_pair(String p1, String p2) {
        return p1.substring(0, 2).equals(p2.substring(0, 2)) && getState(p1) != getState(p2);
    }


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
                }

                int[][] neighbours = gen_neighbour(coord);
                for (int[] neighbour : neighbours) {
                    String key = pointToStr(neighbour);
                    if (grid.get(key) == null || grid.get(key) != '.' || visited.contains(key)) {
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

//
//    public static int find_path(HashMap<String, int[]> portals, HashMap<String, Character> maze_map) {
//        int[] base_start = portals.get("AAO");
//        int[] base_end = portals.get("ZZO");
//        Node start = new Node(base_start);
//        Node end = new Node(base_end);
//    }

    public static char[][] parse(String fileName) throws FileNotFoundException {
        File file = new File("src\\com\\company\\Day20\\" + fileName);
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

    public static HashMap<String, Character> formGrid(char[][] maze) {
        HashMap<String, Character> map = new HashMap<>();
        for (int y = 0; y < maze.length; y++) {
            char[] row = maze[y];
            for (int x = 0; x < row.length; x++) {
                map.put(pointToStr(new int[]{x, y}), row[x]);
            }
        }
        return map;
    }

//    public static HashMap<String, String> reversePortalMap(HashMap<String, int[]> portal) {
//        HashMap<String, String> nm = new HashMap<>();
//        for (String ky : portal.keySet()) {
//            nm.put(pointToStr(portal.get(ky)), ky);
//        }
//        return nm;
//    }

    public static HashMap<String, int[]> formPortal(char[][] maze) {
        HashMap<String, int[]> portals = new HashMap<>();
        for (int y = 1; y < maze.length - 1; y++) {
            char[] row = maze[y];
            for (int x = 1; x < row.length - 1; x++) {
                char c = row[x];
                int[] currentCoord = {x, y};
                if (!isPortal(c)) continue;
                String portalKey;
                // Outer portal
                if (y == 1) {
                    portalKey = Character.toString(maze[0][x]) + c + "O";
                    portals.put(portalKey, new int[]{x, 2});
                } else if (y == maze.length - 2) {
                    portalKey = c + Character.toString(maze[y+1][x])+ "O";
                    portals.put(portalKey, new int[]{x, maze.length-3});
                } else if (x == 1) {
                    portalKey = Character.toString(maze[y][0]) + c + "O";
                    portals.put(portalKey, new int[]{2, y});
                } else if (x == row.length - 2) {
                    portalKey = c + Character.toString(maze[y][x + 1]) + "O";
                    portals.put(portalKey, new int[]{row.length-3, y});
                } else {
                    int[] r = vectorSum(currentCoord, new int[]{1, 0});
                    char r_c = maze[r[1]][r[0]];
                    int[] l = vectorSum(currentCoord, new int[]{-1, 0});
                    char l_c = maze[l[1]][l[0]];
                    if (isPortal(r_c) || isPortal(l_c)) {
                        // Horizontal
                        if (r_c == ' ' || l_c == ' ') {
                            continue;
                        }
                        if (r_c == '.') {
                            portalKey = l_c + Character.toString(c) + "I";
                            portals.put(portalKey, new int[]{x+1, y});
                        } else {
                            portalKey = Character.toString(c) + r_c + "I";
                            portals.put(portalKey, new int[]{x-1, y});
                        }

                    } else {
                        // Vertical
                        int[] u = vectorSum(currentCoord, new int[]{0, -1});
                        char u_c = maze[u[1]][u[0]];
                        int[] d = vectorSum(currentCoord, new int[]{0, 1});
                        char d_c = maze[d[1]][d[0]];

                        if (u_c == ' ' || d_c == ' ') {
                            continue;
                        }
                        // Portal at top
                        if (u_c == '.') {
                            portalKey = Character.toString(c) + d_c + "I";
                            portals.put(portalKey, new int[]{x, y-1});
                        } else {
                            portalKey = u_c + Character.toString(c) + "I";
                            portals.put(portalKey, new int[]{x, y+1});
                        }
                    }
                }
            }
        }
        return portals;
    }

    public static int[] vectorSum(int[] v1, int[] v2) {
        int[] sum = new int[v1.length];
        for (int i = 0; i < v1.length; i++) {
            sum[i] = v1[i] + v2[i];
        }
        return sum;
    }

    public static boolean isPortal(char c) {
        return Character.isLetter(c);
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
