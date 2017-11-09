package com.szachnowicz.graphs;

import java.util.LinkedList;
import java.util.List;

public class Vertex
{
    private int number;    //numer porzadkowy wierzcholka.
    private List<Edge> edge = new LinkedList<Edge>();    //lista krawędzi wychodzacych z wierzcholka

    private Vertex(){}

    /**
     * @param index numer porzadkowy tworzonego wierzcholka
     */
    public Vertex(int index)
    {
        number = index;
    }

    public String toString()
    {
        if(edge.size() == 0)
            return "";

        String out = new String();
        for(Edge e : edge)
            out = out + e.toString() + "\n";
        return out;
    }

    /**
     * Zwraca numer porzadkowy wierzcholka
     */
    public int getNumber()
    {
        return number;
    }

    /**
     * Zwraca <b>kopie</b> listy krawedzi wychodzacych z wierzcholka
     */
    public LinkedList<Edge> getEdgeList()
    {
        return new LinkedList<Edge>(edge);
    }

    /**
     * Usuwa krawedzie biegnace do i-tego wierzcholka (jezeli istnieje)
     * @param i numer wierzcholka do ktorego biegnie usuwana krawedz
     */
    public void removeEdge(int i)
    {
        int e = edge.size()-1;    //liczba krawedzi do sprawdzenia
        while( e>=0 )
        {
            if(edge.get(e).getEnd().getNumber() == i)
                edge.remove(e);
            e--;
        }
    }

    /**
     * Usuwa krawedzie biegnace do wierzcholka v
     * @param v wierzcholek do ktorego biegnie kasowana krawedz
     */
    public void removeEdge(Vertex v)
    {
        removeEdge(v.getNumber());
    }

    /**
     * Dodaje nowa krawedz do wierzcholka
     * @param e dodawana krawedz
     */
    public void addEdge(Edge e)
    {
        edge.add(e);
    }

    /**
     * Zwraca krawedz biegnaca do wierzcholka o numerze porzadkowym n (jezeli istnieje)
     * @param n numer wierzcholka do ktorego biegnie szukana krawedz
     */
    public Edge getEdge(int n)
    {
        for(Edge e : edge)
            if(e.getEnd().getNumber() == n)
                return e;
        return null;
    }

    /**
     * Zwraca n-ta w kolejnosci krawedz wychodzaca z tego wierzcholka (jezeli istnieje)
     * @param n numer szukanej krawedzi
     */
    public Edge getEdgeAt(int n)
    {
        if(n>=0 && n<edge.size())
            return edge.get(n);
        else
            return null;
    }
}
