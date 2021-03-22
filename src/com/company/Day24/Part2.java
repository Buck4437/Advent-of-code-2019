package com.company.Day24;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;

public class Part2 {

    final static int[][] DIRS = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    public static void main(String[] args) {

        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 5; i++) {
                Point3D p = new Point3D(i, j);
                System.out.println("Point: " + p.toString());
                System.out.println("Neighbours: " + neighbours(p.toString()));
            }
        }

        String init = """
                #.#..
                .....
                .#.#.
                .##..
                .##.#""";

        ArrayList<String> bugs = new ArrayList<>();

        for (int y = 0; y < init.split("\n").length; y++) {
            String row = init.split("\n")[y];
            for (int x = 0; x < row.length(); x++) {
                char c = row.charAt(x);
                if (c == '#') {
                    bugs.add(Point3D.toString(x, y));
                }
            }
        }

        for (int i = 0; i < 200; i++) { // 10 mins for example
            Hashtable<String, Integer> neighbours = new Hashtable<>();
            ArrayList<String> mutate = new ArrayList<>();

            for (String bug : bugs) {
                ArrayList<Point3D> ns = neighbours(bug);
                for (Point3D n : ns) {
                    String id = n.toString();
                    Integer c = neighbours.get(id);
                    neighbours.put(id, (c == null ? 0 : c) + 1);
                }
            }

            Enumeration<String> enu = neighbours.keys();
            while (enu.hasMoreElements()) {
                String n = enu.nextElement();
                int count = neighbours.get(n);
                boolean hasBug = bugs.contains(n);
                if (hasBug && count == 1 || !hasBug && (count == 1 || count == 2)) {
                    mutate.add(n);
                }
            }

            bugs = mutate;
        }

        System.out.println(bugs.size());
    }

    public static ArrayList<Point3D> neighbours(String bug) { // layer 1 = the outer grid
        Point3D point = new Point3D(bug);
        ArrayList<Point3D> neighbours = new ArrayList<>();
        int x = point.getX(), y = point.getY(), z = point.getZ();


        for (int[] dir : DIRS) {
            int x2 = x + dir[0];
            int y2 = y + dir[1];
            if (x2 < 0) {
                neighbours.add(new Point3D(1, 2, z + 1));
            } else if (x2 > 4) {
                neighbours.add(new Point3D(3, 2, z + 1));
            } else if (y2 < 0) {
                neighbours.add(new Point3D(2, 1, z + 1));
            } else if (y2 > 4) {
                neighbours.add(new Point3D(2, 3, z + 1));
            } else if (x2 == 2 && y2 == 2){
                switch (x * 10 + y) {
                    case 21: // N
                        for (int i = 0; i < 5; i++) {
                            neighbours.add(new Point3D(i, 0, z - 1));
                        }
                        break;
                    case 12: // W
                        for (int i = 0; i < 5; i++) {
                            neighbours.add(new Point3D(0, i, z - 1));
                        }
                        break;
                    case 32: // E
                        for (int i = 0; i < 5; i++) {
                            neighbours.add(new Point3D(4, i, z - 1));
                        }
                        break;
                    case 23: // S
                        for (int i = 0; i < 5; i++) {
                            neighbours.add(new Point3D(i, 4, z - 1));
                        }
                        break;
                    default:
                        break;
                }
            } else {
                neighbours.add(new Point3D(x2, y2, z));
            }
        }

        return neighbours;
    }
}
