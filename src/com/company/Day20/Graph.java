package com.company.Day20;

import java.util.HashMap;

public class Graph {

    private HashMap<String, Vertex> graph = new HashMap<>();

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

    @Override
    public String toString() {
        return "Graph{" +
                "graph=" + graph +
                '}';
    }
}
