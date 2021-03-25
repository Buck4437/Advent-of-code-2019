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

        Hashtable<String, Vertex> layout = new Hashtable<>();
        Hashtable<String, Vertex> portal = new Hashtable<>();
        Vertex start;
        Vertex end;

        for (int y = 0; y < input.size(); y++) {
            String str = input.get(y);
            for (int x = 0; x < str.length(); x++) {
                char c = str.charAt(x);
                if (c == '#' || c == ' ') continue;

                if (c == '.') {
                    String id = Vertex.toString(x, y);

                    Vertex vertex = new Vertex(id);
                    if (layout.containsKey(id)) {
                        vertex = layout.get(id);
                    }

                    for (int[] dir : DIR) {
                        int x2 = x + dir[0], y2 = y + dir[1];
                        String id2 = Vertex.toString(x2, y2);
                        if (layout.containsKey(id2)) {
                            Vertex vertex2 = layout.get(id2);
                            vertex2.addAdj(vertex);
                            vertex.addAdj(vertex2);
                        }
                    }

                    layout.put(id, vertex);

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

                            Vertex vertex = new Vertex(id);
                            if (layout.containsKey(id)) {
                                vertex = layout.get(id);
                            }

                            if (portalId.equals("AA")) {
                                start = vertex;
                            } else if (portalId.equals("ZZ")) {
                                end = vertex;
                            } else if (portal.containsKey(portalId)) {
                                Vertex vertex2 = portal.remove(portalId);
                                vertex2.addAdj(vertex);
                                vertex.addAdj(vertex2);
                            } else {
                                portal.put(portalId, vertex);
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i <= 30; i++) {
            for (int j = 0; j <= 30; j++) {
                System.out.print(layout.containsKey(Vertex.toString(j, i)) ? "." : "#");
            }
            System.out.print("\n");
        }

        for (int i = 0; i <= 30; i++) {
            for (int j = 0; j <= 30; j++) {
                if (layout.containsKey(Vertex.toString(j, i))) {
                    System.out.print(layout.get(Vertex.toString(j, i)) + "  =>  ");
                    System.out.println(layout.get(Vertex.toString(j, i)).getAdj().toString());
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
