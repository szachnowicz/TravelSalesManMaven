package com.szachnowicz;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TabuSearchAlgo {
    private final FileService fileService;
    private boolean dywers = false;
    private TpsData data;
    private int[] currSolution;


    public int getObjectiveFunctionValue(int solution[]) { //returns the path cost		//getPathCost
        //the first and the last cities'
        //  positions do not change.
        // example solution : {0, 1, 3, 4, 2, 0}

        int cost = 0;

        for (int i = 0; i < solution.length - 1; i++) {
            cost += data.getCostList()[solution[i]][solution[i + 1]];
        }

        return cost;

    }

    public TabuSearchAlgo(int fileNo) throws IOException {

        // reading form files and creating the matrix
        data = new FileService().getDataFromStorage(fileNo);
        fileService = new FileService();

        currSolution = new int[data.getNoOfCites() + 1];

        TabuList tabuList = new TabuList(data.getNoOfCites());

        currSolution = newRandSolution();

        int[] bestSol = new int[currSolution.length]; //this is the best Solution So Far
        System.arraycopy(currSolution, 0, bestSol, 0, bestSol.length);
        int bestCost = getObjectiveFunctionValue(bestSol);
        long startingTime = System.nanoTime();
        long endingTime = 0;

        int iter = 0;
        double iterLimit = 150;
        int timeLimit = 300000;


        while (endingTime < timeLimit) {

            if (dywers && iter == (int) iterLimit) //dywersyfikacja
            {
                iterLimit = iterLimit * (1 + 0.10 * (1 - (endingTime / timeLimit)));
                iter = 0;
                //clear of current solution
                for (int i = 1; i < data.getNoOfCites(); i++) {
                    currSolution[i] = 0;
                }
                currSolution = newRandSolution();
                tabuList.clearTabu();
                System.out.println("Losowanko, limit = " + (int) iterLimit);

            }
            currSolution = getBestNeighbour(tabuList, currSolution, bestCost);
            int currCost = getObjectiveFunctionValue(currSolution);

            if (currCost < bestCost) {
                iter = 0;
                System.out.println("Znaleziono lepsze rozwiazanie: " + currCost);
                System.arraycopy(currSolution, 0, bestSol, 0, bestSol.length);
                bestCost = currCost;
            }

            if (bestCost == data.getBest())
                break;

            endingTime = TimeUnit.MILLISECONDS.convert(System.nanoTime() - startingTime, TimeUnit.NANOSECONDS);
            iter++;


        }
        System.out.println("Optymalne rozwiazanie (dla porownania): " + data.getBest());
        System.out.println("Search done! \nBest Solution cost found = " + bestCost + "\nBest Solution :");
        System.out.println("Zakonczono po " + endingTime + " sekundach.");
        printSolution(bestSol);

        fileService.saveResult(bestCost + " " + endingTime,data.getName());


    }


    public int[] newRandSolution() {
        List<Integer> citiesArray = new LinkedList<>();

        for (int i = 1; i < data.getNoOfCites(); i++)
            citiesArray.add(i);

        int tmp = data.getNoOfCites();
        currSolution[0] = 0;

        for (int i = 0; i < data.getNoOfCites() - 1; i++) {
            int iterator = (int) (Math.random() * (tmp - 1));
            currSolution[i + 1] = citiesArray.get(iterator);
            citiesArray.remove(iterator);
            tmp--;
        }
        currSolution[data.getNoOfCites()] = 0;
        return currSolution;
    }

    public int[] getBestNeighbour(TabuList tabuList, int[] initSolution, int globalBestCost) {

        String wynik = "";


        int[] bestSol = new int[initSolution.length]; //this is the best Solution So Far
        System.arraycopy(initSolution, 0, bestSol, 0, bestSol.length);
        int bestCost = getObjectiveFunctionValue(initSolution);
        int city1 = 0;
        int city2 = 0;
        boolean firstNeighbor = true;

        for (int i = 1; i < bestSol.length - 1; i++) {
            for (int j = 2; j < bestSol.length - 1; j++) {
                if (i == j) {
                    continue;
                }

                int[] newBestSol = new int[bestSol.length]; //this is the best Solution So Far
                System.arraycopy(bestSol, 0, newBestSol, 0, newBestSol.length);

                newBestSol = swapOperator(i, j, initSolution); //Try swapping cities i and j
                // , maybe we get a bettersolution
                int newBestCost = getObjectiveFunctionValue(newBestSol);

                if ((newBestCost < bestCost || firstNeighbor) && (tabuList.tabuList[i][j] == 0 || newBestCost < 0.80 * bestCost || newBestCost < globalBestCost)) { //if better move found, store it

                    if (tabuList.tabuList[i][j] != 0 && (newBestCost < 0.80 * bestCost || newBestCost < globalBestCost)) {
                        if (newBestCost < 0.80 * bestCost)
                            wynik = "Zlamano lokalne tabu (" + newBestCost + ")\n";
                        if (newBestCost < globalBestCost)
                            wynik = "Zlamano tabu globalnie (" + newBestCost + ")\n";
                    } else if (tabuList.tabuList[i][j] == 0)
                        wynik = "";
                    firstNeighbor = false;
                    city1 = i;
                    city2 = j;
                    System.arraycopy(newBestSol, 0, bestSol, 0, newBestSol.length);
                    bestCost = newBestCost;
                }


            }
        }

        if (city1 != 0) {
            System.out.print(wynik);
            tabuList.decrementTabu();
            tabuList.tabuMove(city1, city2);
        }
        return bestSol;


    }

    public int[] swapOperator(int city1, int city2, int[] solution) {
        int temp = solution[city1];
        solution[city1] = solution[city2];
        solution[city2] = temp;
        return solution;
    }

    public void printSolution(int[] solution) {
        for (int i = 0; i < solution.length; i++) {
            System.out.print(solution[i] + " ");
        }
        System.out.println();
    }

}
