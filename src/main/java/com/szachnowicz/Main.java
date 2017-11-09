package com.szachnowicz;

import com.szachnowicz.graphs.Graph;

public class Main {

    public static void main(String[] args) {
        // write your code here


        Graph graph = new Graph(5);
        //A = 0
        graph.addEdge(0, 3, 14);
        graph.addEdge(0, 1, 16);
        graph.addEdge(0, 4, 22);
        // B = 1
        graph.addEdge(1, 4, 4);
        graph.addEdge(1, 2, 18);
        // C = 2
        graph.addEdge(2, 3, 19);
        graph.addEdge(2, 4, 23);
        // D = 3

        // E = 4
        graph.addEdge(3, 4, 7);

        System.out.println(graph);

         NNAlgorithm nnAlgorithm = new NNAlgorithm(graph);
         nnAlgorithm.calculateTheNearestPath(0);
         nnAlgorithm.getResult();


    }
}
