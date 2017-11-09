package com.szachnowicz;

import com.szachnowicz.graphs.Edge;
import com.szachnowicz.graphs.Graph;
import com.szachnowicz.graphs.Vertex;

import java.util.*;

public class NNAlgorithm {
    Set<Integer> visitedNotes = new LinkedHashSet();
    double pathLenght = 0;
    final private Graph graph;


    public NNAlgorithm(Graph graph) {
        this.graph = graph;
    }

    public void calculateTheNearestPath(int startNode) {
        visitedNotes.add(startNode);
        double closest = Double.MAX_VALUE;
        int visited = -1;
        Vertex vertex = graph.getVertex(startNode);

        for (int i = 0; i < vertex.getEdgeList().size(); i++) {
            Edge edge = vertex.getEdgeList().get(i);
            if (closest > edge.getWeight() && checkNotIfVisited(edge)) {
                closest = edge.getWeight();
                visited = edge.getEnd().getNumber();
            }
        }

        if (visited > -1 && !visitedNotes.contains(visited)) {
            visitedNotes.add(visited);
            pathLenght += closest;
            calculateTheNearestPath(visited);
        }
    }

    private boolean checkNotIfVisited(Edge vertex) {
        boolean visited = !visitedNotes.contains(vertex.getEnd().getNumber());

        return visited;


    }


    public double getResult() {
        if (visitedNotes.size() <= graph.getVertexList().size())
            return -1;

        System.out.println("Algorytm najbliższego sąsiada  oblicz najkrótszą scieżke  o długości " + pathLenght);
        for (Integer visitedNote : visitedNotes)
            System.out.print("->" + visitedNote);
        return pathLenght;


    }

    public void clear() {

        pathLenght = 0;
        visitedNotes = new LinkedHashSet();
    }


}









