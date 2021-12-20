package com.company.Day20.Part2;

import java.util.HashMap;
import java.util.LinkedList;

public class Graph {

    private final HashMap<String, Vertex> graph = new HashMap<>();

    public Graph() {}

    public Vertex addVertex(String id) {
        Vertex vertex = new Vertex(id);
        graph.put(id, vertex);
        return vertex;
    }

    public Vertex getVertex(String id) {
        return graph.get(id);
    }

    public boolean hasVertex(String id) {
        return getVertex(id) != null;
    }

    public void addEdge(String id, String id2) {
        Vertex vertex = getVertex(id);
        Vertex vertex2 = getVertex(id2);

        vertex.addAdj(vertex2);
        vertex2.addAdj(vertex);
    }

    public LinkedList<Vertex> BFS(String start, String end) {
        LinkedList<Vertex> queue = new LinkedList<>();
        HashMap<Vertex, Vertex> visited = new HashMap<>();

        Vertex startVert = getVertex(start);
        Vertex endVert = getVertex(end);
        visited.put(startVert, null);

        Vertex current = startVert;

        while (current != endVert) {
            LinkedList<Vertex> adj = current.getAdj();

            for (Vertex v : adj) {
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
