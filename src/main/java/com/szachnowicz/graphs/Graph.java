package com.szachnowicz.graphs;

import java.util.LinkedList;
import java.util.List;

public class Graph {
    private List<Vertex> vertex = new LinkedList<Vertex>();   //lista wierzcholkow grafu
    private int vertexCount;  //wskazuje ile wierzcholkow ma graf (null to tez wierzcholek, tyle ze pusty.
    //Co za tym idzie - vertexCount nigdy nie zmaleje, nawet po usunieciu wszystkich wierzcholkow, gdyz usuwanie
    //to nic innego jak zastapienie istniejacego wierzcholka w liscie przez null)


    public Graph() {
        vertexCount = 0;
    }


    /**
     * Tworzy graf z n wierzcholkami
     *
     * @param nodes ilosc wierzcholkow do utworzenia
     */
    public Graph(int nodes) {
        for (int i = 0; i < nodes; i++)
            vertex.add(new Vertex(i));

        vertexCount = nodes;
    }

    public String toString() {
        String out = new String();
        for (Vertex v : vertex)
            if (v != null && v.toString() != "")
                out = out + v.toString() + "\n";
        return out;
    }

    /**
     * Dodaje kolejny wierzcholek do grafu
     */
    public void addVertex() {
        vertex.add(new Vertex(vertexCount));
        vertexCount++;
    }

    /**
     * Usuwa wierzcholek o liczbie porzadkowej rownej n
     * Razem z usunietym wierzcholkiem wszystkie polaczenia biegnace w jego strone zostaja przerwane
     *
     * @param n liczba porzadkowa wierzcholka do usuniecia
     */
    public void removeVertex(int n) {
        if (n < vertexCount) {
            vertex.set(n, null);
            for (Vertex v : vertex)
                if (v != null)
                    v.removeEdge(n);
        }
    }

    /**
     * Usuwa wierzcholek v z grafu.
     * Razem z usunietym wierzcholkiem wszystkie polaczenia biegnace w jego strone zostaja przerwane
     */
    public void removeVertex(Vertex v) {
        removeVertex(v.getNumber());
    }

    /**
     * Dodaje do grafu krawedz edge
     *
     * @param edge krawedz ktora ma byc dodana do grafu
     */
    public void addEdge(Edge edge) {

        vertex.get(edge.getBegin().getNumber()).addEdge(edge);

    }

    /**
     * Dodaje do grafu krawedz o wadze w rozpieta miedzy wierzcholkami o liczbach porzadkowych b --> e.
     * Jeżeli wprowadzone numery wierzcholkow wskazuja na elementy nieistniejace to krawedz nie zostaje utworzona.
     *
     * @param b numer porzadkowy poczatkowego wierzcholka
     * @param e numer porzadkowy wierzcholka koncowego
     * @param w waga dodawanej krawedzi
     */
    public void addEdge(int b, int e, double w) {

        if (b >= 0 && b <= vertexCount && e >= 0 && e <= vertexCount && vertex.get(b) != null && vertex.get(e) != null) {
            addEdge(new Edge(vertex.get(b), vertex.get(e), w));
            addEdge(new Edge(vertex.get(e), vertex.get(b), w));

        }
    }

    /**
     * Usuwa krawedz e jezeli istnieje. Waga jest pomijana
     *
     * @param e krawedz do usuniecia
     */
    public void removeEdge(Edge e) {
        if (e.getBegin() == null) return;

        int b = e.getBegin().getNumber();
        if (b >= 0 && b <= vertexCount && vertex.get(b) != null)
            vertex.get(b).removeEdge(e.getEnd().getNumber());
    }

    /**
     * Zwraca wierzcholek o liczbie porzadkowej n
     *
     * @param n liczba porzadkowa szukanego wierzcholka
     */
    public Vertex getVertex(int n) {
        return vertex.get(n);
    }

    /**
     * Zwraca <b>kopie</b> listy wierzcholkow tego grafu
     */
    public LinkedList<Vertex> getVertexList() {
        return new LinkedList<Vertex>(vertex);
    }


}
