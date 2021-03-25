package com.company.Day20.Part2;

import com.company.Day20.Part1.Vertex;

import java.util.HashMap;
import java.util.LinkedList;

public class Graph {

    private final HashMap<String, com.company.Day20.Part1.Vertex> graph = new HashMap<>();

    public Graph() {}

    public com.company.Day20.Part1.Vertex addVertex(String id) {
        com.company.Day20.Part1.Vertex vertex = new com.company.Day20.Part1.Vertex(id);
        graph.put(id, vertex);
        return vertex;
    }

    public com.company.Day20.Part1.Vertex getVertex(String id) {
        return graph.get(id);
    }

    public boolean hasVertex(String id) {
        return getVertex(id) != null;
    }

    public void addEdge(String id, String id2) {
        com.company.Day20.Part1.Vertex vertex = getVertex(id);
        com.company.Day20.Part1.Vertex vertex2 = getVertex(id2);

        vertex.addAdj(vertex2);
        vertex2.addAdj(vertex);
    }

    public LinkedList<com.company.Day20.Part1.Vertex> BFS(String start, String end) {
        LinkedList<com.company.Day20.Part1.Vertex> queue = new LinkedList<>();
        HashMap<com.company.Day20.Part1.Vertex, com.company.Day20.Part1.Vertex> visited = new HashMap<>();

        com.company.Day20.Part1.Vertex startVert = getVertex(start);
        com.company.Day20.Part1.Vertex endVert = getVertex(end);
        visited.put(startVert, null);

        com.company.Day20.Part1.Vertex current = startVert;

        while (current != endVert) {
            LinkedList<com.company.Day20.Part1.Vertex> adj = current.getAdj();

            for (com.company.Day20.Part1.Vertex v : adj) {
                if (!visited.containsKey(v)) {
                    visited.put(v, current);
                    queue.addLast(v);
                }
            }

            current = queue.removeFirst();
        }

        LinkedList<Vertex> path = new LinkedList<>();
        while (current != startVert) {
            path.addFirst(current);
            current = visited.get(current);
        }

        return path;
    }

}
