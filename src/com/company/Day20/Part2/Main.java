package com.company.Day20.Part2;

import com.company.Day20.Part1.Graph;
import com.company.Day20.Part1.Vertex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    static ArrayList<String> input = new ArrayList<>();
    static final int[][] DIR = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    static final int[][] POSITIVE_DIR = {{1, 0}, {0, 1}};

    public static void main(String[] args) throws FileNotFoundException {

        long time = System.nanoTime();

        File file = new File("src\\com\\company\\Day20\\input.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            String str = sc.nextLine();
            input.add(str);
            System.out.println(str);
        }

        com.company.Day20.Part1.Graph graph = new Graph();

        Hashtable<String, String> portal = new Hashtable<>();
        String start = "";
        String end = "";

        for (int y = 0; y < input.size(); y++) {
            String str = input.get(y);
            for (int x = 0; x < str.length(); x++) {
                char c = str.charAt(x);
                if (c == '#' || c == ' ') continue;

                if (c == '.') {
                    String id = com.company.Day20.Part1.Vertex.toString(x, y);
                    if (!graph.hasVertex(id)) {
                        graph.addVertex(id);
                    }
                    for (int[] dir : DIR) {
                        int x2 = x + dir[0], y2 = y + dir[1];
                        String id2 = com.company.Day20.Part1.Vertex.toString(x2, y2);
                        if (graph.hasVertex(id2)) {
                            graph.addEdge(id, id2);
                        }
                    }

                } else { // Portal
                    for (int[] dir : POSITIVE_DIR) {
                        int x2 = x + dir[0], y2 = y + dir[1];
                        char c2 = getInput(x2, y2);
                        if (Character.isLetter(c2)) { // The portal is horizontal or vertical, with current position at top / left
                            String portalId = c + Character.toString(c2);
                            int x3 = x2 + dir[0], y3 = y2 + dir[1];
                            char c3 = getInput(x3, y3);
                            String id;
                            if (c3 == '.') {
                                id = com.company.Day20.Part1.Vertex.toString(x3, y3);
                            } else {
                                id = com.company.Day20.Part1.Vertex.toString(x - dir[0], y - dir[1]);
                            }

                            if (!graph.hasVertex(id)) {
                                graph.addVertex(id);
                            }

                            if (portalId.equals("AA")) {
                                start = id;
                            } else if (portalId.equals("ZZ")) {
                                end = id;
                            } else if (portal.containsKey(portalId)) {
                                String id2 = portal.remove(portalId);
                                graph.addEdge(id, id2);
                            } else {
                                portal.put(portalId, id);
                            }
                        }
                    }
                }
            }
        }

        LinkedList<Vertex> path = graph.BFS(start, end);
        System.out.printf("Best path (%s steps):\n%s\n", path.size(), path.toString());
        System.out.printf("Time taken: %sms", (System.nanoTime() - time) / 1e6);
    }

    public static char getInput(int x, int y) {
        if (y >= input.size() || y < 0) {
            return ' ';
        } else {
            String str = input.get(y);
            if (x >= str.length() || x < 0) {
                return ' ';
            }
            return str.charAt(x);
        }
    }

}
