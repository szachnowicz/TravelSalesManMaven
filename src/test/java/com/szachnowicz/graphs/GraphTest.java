//package com.szachnowicz.graphs;
//
//import com.szachnowicz.GraphService;
//import com.szachnowicz.NNAlgorithm;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.lang.reflect.Array;
//import java.util.Arrays;
//import java.util.List;
//
//public class GraphTest {
//    Graph graph;
//    int grapSize = 6;
//
//    @Before
//    public void setUp() {
//
//        graph = new Graph(grapSize);
//    }
//
//
//    @Test
//    public void testMEthod() {
//        graph.addEdge(0, 1, 2);
//        graph.addEdge(1, 2, 8);
//        graph.addEdge(0, 5, 9);
//        graph.addEdge(1, 3, 15);
//        graph.addEdge(1, 5, 6);
//        graph.addEdge(4, 3, 3);
//        graph.addEdge(2, 3, 1);
//        graph.addEdge(4, 2, 7);
//        graph.addEdge(5, 4, 3);
//
//        System.out.println(graph.toString());
//
//
//    }
//
//    @Test
//    public void testAccrunacy() throws Exception {
//
//        graph = new Graph(7);
//        graph.addEdge(0, 5, 4);
//        graph.addEdge(0, 4, 3);
//        graph.addEdge(0, 2, 7);
//        graph.addEdge(2, 4, 7);
//        graph.addEdge(1, 4, 6);
//        graph.addEdge(2, 1, 5);
//        graph.addEdge(3, 1, 9);
//        graph.addEdge(3, 4, 7);
//        graph.addEdge(3, 5, 12);
//        graph.addEdge(4, 5, 6);
//        graph.addEdge(3, 6, 7);
//        graph.addEdge(6, 5, 2);
//        graph.addEdge(6, 4, 6);
//
//
//
//        final GraphService graphService = new GraphService(graph);
//        int tab[] = {0, 1, 2, 3, 4, 5,6};
//        double best = Double.MAX_VALUE;
//        int bestPath[] = null;
//        final List<int[]> permute = graphService.permute(tab);
//        for (int[] ints : permute) {
//            double result = graphService.calculateDistane(ints);
//            if (result < best && result !=-1) {
//                best = result;
//                bestPath = ints.clone();
//            }
//        }
//
//
//        System.out.println("najlepszy wynik " + best + "   po scieżce " + Arrays.toString(bestPath));
//
//
//        NNAlgorithm nnAlgorithm = new NNAlgorithm(graph);
////        for (int i : tab) {
//            nnAlgorithm.calculateTheNearestPath(2);
//            nnAlgorithm.getResult();
//            nnAlgorithm.clear();
////        }
//
//    }
//
//    @Test
//    public void testCalculateDistanceMethod() {
//
//
//        graph.addEdge(0, 1, 2);
//        graph.addEdge(1, 2, 8);
//        graph.addEdge(0, 5, 9);
//        graph.addEdge(1, 3, 15);
//        graph.addEdge(1, 5, 6);
//        graph.addEdge(4, 3, 3);
//        graph.addEdge(2, 3, 1);
//        graph.addEdge(4, 2, 7);
//        graph.addEdge(5, 4, 3);
//
//        System.out.println(graph.toString());
//
//        final GraphService graphService = new GraphService(graph);
//
//        int tab[] = {1, 2, 3, 4, 5};
//
//        final List<int[]> permute = graphService.permute(tab);
//        for (int[] ints : permute) {
//            graphService.calculateDistane(ints);
//
//        }
//
//
//    }
//
//    @Test
//    public void accurancyTest() throws Exception {
//
//        graph.addEdge(0, 1, 9);
//        graph.addEdge(0, 2, 4);
//        graph.addEdge(0, 5, 2);
//        graph.addEdge(1, 2, 8);
//        graph.addEdge(1, 4, 6);
//        graph.addEdge(4, 3, 7);
//        graph.addEdge(2, 3, 2);
//        graph.addEdge(3, 5, 15);
//
//        NNAlgorithm nnAlgorithm = new NNAlgorithm(graph);
//        nnAlgorithm.calculateTheNearestPath(0);
//        nnAlgorithm.getResult();
//
//
//    }
//
//    @Test
//    public void testHeldKarpDistanceMethod() {
//graph.addEdge(0,1,1);
//graph.addEdge(0,2,15);
//graph.addEdge(0,3,6);
////graph.addEdge(1,0,1);
//graph.addEdge(1,2,7);
//graph.addEdge(1,3,3);
//graph.addEdge(2,0,15);
//graph.addEdge(2,1,7);
//graph.addEdge(2,3,12);
//graph.addEdge(3,0,6);
//graph.addEdge(3,1,3);
//graph.addEdge(3,12,0);
//
//
//        System.out.println(graph.toString());
//
//        final GraphService graphService = new GraphService(graph);
//
//        int tab[] = {0,1, 2, 3};
//
//        final List<int[]> permute = graphService.permute(tab);
//        for (int[] ints : permute) {
//            graphService.calculateDistane(ints);
//
//        }
//
//
//    }
//}