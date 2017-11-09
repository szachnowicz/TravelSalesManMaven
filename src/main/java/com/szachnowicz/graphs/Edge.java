package com.szachnowicz.graphs;


public class Edge {
    private Vertex begin, end; // wierzochołki pomiędzy którmy rozpieta jest krawędz
    private double weight; // jej waga

    public Edge() {
    }

    public Edge(Vertex begin, Vertex end, double weight) {
        this.begin = begin;
        this.end = end;
        this.weight = weight;
    }

    public String toString() {
        return Integer.toString(begin.getNumber()) + " ---( "
                + Double.toString(weight) + " )---> "
                + Integer.toString(end.getNumber());
    }

    public Vertex getBegin() {
        return begin;
    }

    public Vertex getEnd() {
        return end;
    }

    public double getWeight() {
        return weight;
    }
}

