package com.company.Day20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class Main {

    static ArrayList<String> input = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        final int[][] DIR = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        final int[][] POSITIVE_DIR = {{1, 0}, {0, 1}};

        File file = new File("src\\com\\company\\Day20\\input.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            input.add(sc.nextLine());
        }

        Graph graph = new Graph();

        Hashtable<String, String> portal = new Hashtable<>();
        String start;
        String end;

        for (int y = 0; y < input.size(); y++) {
            String str = input.get(y);
            for (int x = 0; x < str.length(); x++) {
                char c = str.charAt(x);
                if (c == '#' || c == ' ') continue;

                if (c == '.') {
                    String id = Vertex.toString(x, y);
                    if (!graph.hasVertex(id)) {
                        graph.addVertex(id);
                    }
                    for (int[] dir : DIR) {
                        int x2 = x + dir[0], y2 = y + dir[1];
                        String id2 = Vertex.toString(x2, y2);
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
                                id = Vertex.toString(x3, y3);
                            } else {
                                id = Vertex.toString(x - dir[0], y - dir[1]);
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

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                System.out.print(graph.hasVertex(Vertex.toString(j, i)) ? "." : "#");
            }
            System.out.print("\n");
        }

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (graph.hasVertex(Vertex.toString(j, i))) {
                    System.out.print(graph.getVertex(Vertex.toString(j, i)) + "  =>  ");
                    System.out.println(graph.getVertex(Vertex.toString(j, i)).getAdj().toString());
                }
            }
            System.out.print("\n");
        }
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
