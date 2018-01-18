package com.szachnowicz.genericAlgoritm;

import com.szachnowicz.FileService;
import com.szachnowicz.TpsData;

import java.io.IOException;
import java.util.*;

public class GenericAlgo {
    private TpsData data;
    private int bestSoFar;
    private int numOfGenerations=150; // warunek stopu wyra�ony jako liczba pokole�. Program konczy dzialanie po 300.
    private int crossProba = 80, mutatnioProbably = 20; // crossProba - prawdopodobienstwo zajscia krosowania, mutatnioProbably - prawdopodobienstwo zajscia mutacji
    private int popSize = 1000; // rozmiar populacji
    private int tourneySize = 2; // liczba osbonikow w turnieju

    List<Specimen> population, champions, elite;

    public GenericAlgo(int fileNo, int numOfGenerations) {
        data = new FileService().getDataFromStorage(fileNo);
        this.popSize = numOfGenerations;
        geneticAlgorithm();

    }


    // METODY DO POPULACJI:
    // generacja populacji
    private void generatePopulation(int amount) {
        population = new ArrayList<Specimen>();
        champions = new ArrayList<Specimen>();
        elite = new ArrayList<Specimen>();

        for (int i = 0; i < amount; i++)
            population.add(new Specimen(data));
    }

    public void geneticAlgorithm() {


        long actualTime = System.nanoTime();
        generatePopulation(popSize);

        int i = 0;
        while (i < numOfGenerations) {
            bestSoFar = evaluatePopulation();
            System.out.print(i+" : "+bestSoFar+",");

            if (bestSoFar == (data.getBest())) {
                System.out.println("znalezione rozwiazanie zgadza sie z optymalnym");
                break;
            }

            fillElite();
            tournamentSelection();
            population.clear();

            crossover();
            mutation();

            i++;
        }
        long executionTime = System.nanoTime() - actualTime;
        double ms = (double) executionTime / 1000000;
        System.out.println("\nCzas: " + ms + " [ms]");

        try {
            new FileService().saveResult(bestSoFar + " " + ms,data.getNoOfCites()+"-"+popSize);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void mutation() {
        for (int i = 0; i < population.size(); i++) {
            if (((int) (Math.random() * 101) < mutatnioProbably) && !population.get(i).isElite) mutateSpecimen(population.get(i));
        }
    }


    // MUTACJA:
    // mutacja pojedynczego osobnika (swap = zamiana dwoch miast w trasie miejscami)
    private void mutateSpecimen(Specimen s) {
        int numOfCities = data.getNoOfCites();
        int gene1 = (int) (Math.random() * (numOfCities - 1) + 1);
        int gene2 = (int) (Math.random() * (numOfCities - 1) + 1);
        while (gene1 == gene2)
            gene2 = (int) (Math.random() * (numOfCities - 1) + 1);

        s.mutateDNA(gene1, gene2);


    }
 // ta metoda z randomowo wygenerowanych rozwiązań zwraca najlepsze z nich
    private int evaluatePopulation() {
        int bestCost = Integer.MAX_VALUE;
        for (int i = 0; i < population.size(); i++) {
            population.get(i).score = getPathCost(population.get(i).tour);
            if (population.get(i).score < bestCost) {
                bestCost = population.get(i).score;
            }
        }
        return bestCost;
    }


    private int getPathCost(List<Integer> l) {
        int cost = 0;
        for (int i = 0; i < l.size() - 1; i++)
            cost += data.getCostList()[l.get(i)][l.get(i + 1)];
        return cost;
    }

    private void fillElite() {
        elite.clear();
        for (int i = 0; i < popSize / 20; i++)
            population.get(i).isElite = false;


        Collections.sort(population, new Comparator<Specimen>() {
            @Override
            public int compare(Specimen s1, Specimen s2) {
                return s1.score - s2.score;
            }
        });

        for (int i = 0; i < popSize / 20; i++) {
            population.get(i).isElite = true;
            elite.add(population.get(i));
            champions.add(population.get(i));
        }
    }


    // selekcja turniejowa, przeprowadza turnieje az osiagniecia pozadanego rozmiaru puli czempionow
    private void tournamentSelection() {
        Specimen champion = new Specimen();


        while (champions.size() < popSize / 2) {
            champion = tourney(population);
            population.remove(population.indexOf(champion));
            champions.add(champion);
        }
    }

    // SELEKCJA TURNIEJOWA:
    // przeprowadzenie pojedynczego turnieju, w ktorym wygrywa najlepszy osobnik
    private Specimen tourney(List<Specimen> p) {
        Specimen best = new Specimen();
        List<Specimen> pCopy = new ArrayList<Specimen>(p);
        List<Specimen> contestants = new ArrayList<Specimen>();

        for (int i = 0; i < tourneySize; i++) {
            int pos = (int) (Math.random() * pCopy.size());
            contestants.add(pCopy.get(pos));
            pCopy.remove(pos);
        }

        for (int i = 0; i < contestants.size(); i++) {
            if (contestants.get(i).score < best.score)
                best = contestants.get(i);
        }
        return best;
    }

    private void crossover() {
        Specimen p1;
        Specimen p2;
        List<Specimen> children = new ArrayList<Specimen>();

        population.addAll(elite);

        while (population.size() < popSize - 1) {
            int index1, index2;
            index1 = (int) (Math.random() * champions.size());
            do
                index2 = (int) (Math.random() * champions.size());
            while (index1 == index2);
            p1 = new Specimen(champions.get(index1));
            p2 = new Specimen(champions.get(index2));

            children = PMX(p1, p2);
            population.addAll(children);
        }
        champions.clear();
    }

    // KROSOWANIE:
    // krosowanie dwoch osobnikow metoda PMX
    private List<Specimen> PMX(Specimen parent1, Specimen parent2) {
        List<Specimen> ch = new ArrayList<Specimen>();

        if (((int) (Math.random() * 101)) < crossProba || parent1.isElite || parent2.isElite) {
            HashMap<Integer, Integer> map1 = new HashMap<Integer, Integer>();
            HashMap<Integer, Integer> map2 = new HashMap<Integer, Integer>();

            //losowanie fragmentu do zamiany
            int pos1, pos2;
            pos1 = 1 + (int) (Math.random() * (parent1.tour.size() - 2));
            do
                pos2 = 1 + (int) (Math.random() * (parent1.tour.size() - 2));
            while (pos1 == pos2);


            if (pos1 > pos2) {
                int tmp = pos1;
                pos1 = pos2;
                pos2 = tmp;
            }


            // zamiana wylosowanego fragmentu trasy pomiedzy rodzicami
            for (int i = pos1; i < pos2; i++) {
                int tmp = parent1.tour.get(i);
                parent1.tour.set(i, parent2.tour.get(i));
                parent2.tour.set(i, tmp);
            }


            // tworzenie mapy wartosci
            for (int i = pos1; i < pos2; i++) {
                map1.put(parent1.tour.get(i), parent2.tour.get(i));
                map2.put(parent2.tour.get(i), parent1.tour.get(i));
            }

            //displayPath(parent1.tour);
            //displayPath(parent2.tour);


            // naprawianie 1wszego dziecka
            for (int i = 1; i < pos2 - pos1 + 1; i++) {
                for (int j = 0; j < pos1; j++) {
                    Iterator it1 = map1.entrySet().iterator();
                    while (it1.hasNext()) {
                        Map.Entry mentry = (Map.Entry) it1.next();
                        if (parent1.tour.get(j) == mentry.getKey())
                            parent1.tour.set(j, (int) mentry.getValue());
                    }
                    //it1.remove();
                }

                for (int j = pos2; j < parent1.tour.size(); j++) {
                    Iterator it1 = map1.entrySet().iterator();
                    while (it1.hasNext()) {
                        Map.Entry mentry = (Map.Entry) it1.next();
                        if (parent1.tour.get(j) == mentry.getKey())
                            parent1.tour.set(j, (int) mentry.getValue());
                    }
                    //	it1.remove();
                }
            }

            //naprawianie drugiego dziecka
            for (int i = 1; i < pos2 - pos1 + 1; i++) {
                for (int j = 0; j < pos1; j++) {
                    Iterator it2 = map2.entrySet().iterator();
                    while (it2.hasNext()) {
                        Map.Entry mentry = (Map.Entry) it2.next();
                        if (parent2.tour.get(j) == mentry.getKey())
                            parent2.tour.set(j, (int) mentry.getValue());
                    }
                    //it2.remove();
                }


                for (int j = pos2; j < parent1.tour.size(); j++) {
                    Iterator it2 = map2.entrySet().iterator();
                    while (it2.hasNext()) {
                        Map.Entry mentry = (Map.Entry) it2.next();
                        if (parent2.tour.get(j) == mentry.getKey())
                            parent2.tour.set(j, (int) mentry.getValue());
                    }
                    //	it2.remove();
                }
            } // koniec naprawiania dzieci
        } // koniec ifa prawdopodobienstwa zajscia krzyzowania

        parent1.isElite = false;
        parent2.isElite = false;

        ch.add(parent1);
        ch.add(parent2);
        return ch;
    }

}
