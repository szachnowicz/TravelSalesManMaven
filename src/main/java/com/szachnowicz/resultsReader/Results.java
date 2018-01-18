package com.szachnowicz.resultsReader;

import java.util.Map;

public class Results {


    private final String city;
    private final int population;
    private final double avrageCost;
    private final double avrageTime;

    public String getCity() {
        return city;
    }

    public int getPopulation() {
        return population;
    }

    public double getAvrageCost() {
        return avrageCost;
    }

    public double getAvrageTime() {
        return avrageTime;
    }

    public Results(String city, int population, double avrageCost, double avrageTime) {
        this.city = city;
        this.population = population;
        this.avrageCost = avrageCost;
        this.avrageTime = avrageTime;
    }



}
