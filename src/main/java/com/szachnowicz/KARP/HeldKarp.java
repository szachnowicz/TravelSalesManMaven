package com.szachnowicz.KARP;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

/**
 * The Held Karp algorithm:
 * <p>
 * There are 2 possible cases in each iteration:
 * <p>
 * A) A base case where we already know the answer. (Stopping condition)
 * B) Decreasing the number of considered vertices and calling our algorithm again. (Recursion)
 * <p>
 * Explanation of every case:
 * <p>
 * A) If the list of vertices is empty, return the distance between starting point and vertex.
 * B) If the list of vertices is not empty, lets decrease our problem space:
 * <p>
 * 1) Consider each vertex in vertices as a starting point ("initial")
 * 2) As "initial" is the starting point, we have to remove it from the list of vertices
 * 3) Calculate the cost of visiting "initial" (costCurrentNode) + cost of visiting the rest from it ("costChildren")
 * 4) Return the minimum result from step 3
 */

public class HeldKarp {

    /* ----------------------------- GLOBAL VARIABLES ------------------------------ */
    private static int[][] distances;
    private static int optimalDistance = Integer.MAX_VALUE;
    private static String optimalPath = "";
    private static int size;


    public static void main(String args[]) throws IOException {


        distances = FileService.readFile();

        String path = "";
        size = distances.length;

        int[] vertices = new int[size - 1];

        for (int i = 1; i < size; i++) {
            vertices[i - 1] = i;
        }

        long startTime = System.nanoTime();

        procedure(0, vertices, path, 0);

        long endTime = System.nanoTime();


        long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
        System.out.println(" " + (duration / 1000000));

        System.out.print("Path: " + optimalPath + ". Distance = " + optimalDistance);
    }


    /* ------------------------------- RECURSIVE FUNCTION ---------------------------- */

    private static int procedure(int initial, int vertices[], String path, int costUntilHere) {
        // wraz z każdym wywołanie fukcji rekurencyjne przebyta scieżka zostaje nadpisana
        path = path + Integer.toString(initial) + " - ";
        int length = vertices.length;
        int newCostUntilHere;
        // Warunek końca funkcji  gdy nie ma już więcej wezłów do odwiedzenia
        if (length == 0) {
            newCostUntilHere = costUntilHere + distances[initial][0];
            // Jeżeli nowy wynik jest lepszy od obecnie optymalnego  nowy wynik staje się optymalnym
            if (newCostUntilHere < optimalDistance) {
                optimalDistance = newCostUntilHere;
                optimalPath = path + "0";
            }
            return (distances[initial][0]);
        }
        //  Jeżeli koszt aktulanego przejścia jest wiekszy niż obecnie optymalny wynik : przerwanie fukcji
        else if (costUntilHere > optimalDistance) {
            return 0;
        }

       // Gdy jest więcej możliwości przejscia po wezłach
        else {
            int[][] newVertices = new int[length][(length - 1)];
            int costCurrentNode, costChild;
            int bestCost = Integer.MAX_VALUE;
        // Sprawdzanie każdego wezłą z koleji
            for (int i = 0; i < length; i++) {
                //tworznie fukcji rekurencyjnej dla każdego z elementu nowej listy
                for (int j = 0, k = 0; j < length; j++, k++) {
                    // The current child is not stored in the new vertices array
                    if (j == i) {
                        k--;
                        continue;
                    }
                    newVertices[i][k] = vertices[j];
                }

                // kosz dotarcia do obecnego węzła z jego poprzednika
                costCurrentNode = distances[initial][vertices[i]];

                // suma dotarcia z poprzedniej rekurecji plus
                newCostUntilHere = costCurrentNode + costUntilHere;

                // wywołąni
                costChild = procedure(vertices[i], newVertices[i], path, newCostUntilHere);

                // The cost of every child + the current node cost is computed
                int totalCost = costChild + costCurrentNode;

                // Finally we select from the minimum from all possible children costs
                if (totalCost < bestCost) {
                    bestCost = totalCost;
                }
            }

            return (bestCost);
        }
    }
}