package com.szachnowicz;


import com.szachnowicz.graphs.Edge;
import com.szachnowicz.graphs.Graph;
import com.szachnowicz.graphs.Vertex;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GraphService {
    private  Graph graph;

    public GraphService( final Graph graph) {
        this.graph = graph;
    }

    private List<int[]> allPremutation = new LinkedList<int[]>();

    public GraphService() {
    }

    public List<int[]> permute(int[] arr) {

        permuteHelper(arr, 0);
        return allPremutation;

    }

    private void permuteHelper(int[] arr, int index) {
        if (index >= arr.length - 1) { //If we are at the last element - nothing left to permute
            //System.out.println(Arrays.toString(arr));
            //Print the array
//            System.out.print("[");
            for (int i = 0; i < arr.length - 1; i++) {
                System.out.print(arr[i] + ", ");
            }
            if (arr.length > 0)
                System.out.print(arr[arr.length - 1]);
            System.out.println("]");
            allPremutation.add(arr.clone());
            return;
        }

        for (int i = index; i < arr.length; i++) { //For each index in the sub array arr[index...end]

            //Swap the elements at indices index and i
            int t = arr[index];
            arr[index] = arr[i];
            arr[i] = t;

            //Recurse on the sub array arr[index+1...end]
            permuteHelper(arr, index + 1);

            //Swap the elements back
            t = arr[index];
            arr[index] = arr[i];
            arr[i] = t;
        }

    }


    public double calculateDistane(int[] path) {
        double result = 0;

        for (int i = 0; i < path.length-1; i++) {
            if (graph.getVertex(path[i]).getEdge(path[i+1])!=null) {
                result+=graph.getVertex(path[i]).getEdge(path[i+1]).getWeight();
            }else {
                return -1;
            }
        }
        System.out.println( "wynik dla " + Arrays.toString(path) + " równy  " + result);
        return result;
    }


}
