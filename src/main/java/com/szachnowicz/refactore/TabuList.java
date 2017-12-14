package com.szachnowicz.refactore;

public class TabuList {
    public int[][] tabuList;
    private int numOfCites ;

    public TabuList(int numCities) {
        this.numOfCites = numCities;
        tabuList = new int[numCities][numCities]; //city 0 is not used here, but left for simplicity
    }


    public void tabuMove(int city1, int city2){ //tabus the swap operation
        tabuList[city1][city2]+= numOfCites;
        tabuList[city2][city1]+= numOfCites;

    }
    public void clearTabu(){
        for(int i = 0; i<tabuList.length; i++){
            for(int j = 0; j<tabuList.length; j++)
                tabuList[i][j] = 0;
        }
    }
    public void decrementTabu(){
        for(int i = 0; i<tabuList.length; i++){
            for(int j = 0; j<tabuList.length; j++){
                tabuList[i][j]-=tabuList[i][j]<=0 ? 0 : 1;
            }
        }
    }
    public int[][] getTabuList() {
        return tabuList;
    }
}
