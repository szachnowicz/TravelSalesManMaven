package com.szachnowicz.refactore;

public class TpsData {

    private String name;
    private int best;
    private double[][] costList;
    private int noOfCites;

    public TpsData() {
    }

    public String getName() {

        return name;
    }

    public int getNoOfCites() {
        return noOfCites;
    }

    public void setNoOfCites(int noOfCites) {
        this.noOfCites = noOfCites;
    }

    public void setName(String name) {
        this.name = name;
        String numOfCites = "";

        final char[] chars = name.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isDigit(chars[i])) {
                numOfCites += chars[i];
            }
        }
        setNoOfCites(Integer.parseInt(numOfCites));

    }

    public int getBest() {
        return best;
    }

    public void setBest(int best) {
        this.best = best;
    }

    public double[][] getCostList() {
        return costList;
    }

    public void setCostList(double[][] costList) {
        this.costList = costList;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        stringBuilder.append("City No  : " + noOfCites);
        stringBuilder.append("Best : " + best);
        stringBuilder.append("\n");

        return stringBuilder.toString();

    }
}
