package com.szachnowicz;

import java.util.HashSet;

public class HeldKarp_SubSolution {

    public int last_visited_city;
    public HashSet<Integer> cities; /* we can use a TreeSet and then enumerate according to the ordering of numbers in S, but it doesn't really help, so better to use the faster hashSet */
    public int shortest_path;

    public HeldKarp_SubSolution(int x, HashSet<Integer> s, int shortest_path) {
        this.last_visited_city = x;
        this.cities = s;
        this.shortest_path = shortest_path;
    }
}