package com.szachnowicz;

import com.szachnowicz.resultsReader.Results;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ResultReader {

    public static void main(String[] args) {

        new ResultReader().getAvrageResultForCity("14");
//        new ResultReader().getAvrageResultForCity("17");
        new ResultReader().getAvrageResultForCity("17");
        new ResultReader().getAvrageResultForCity("38");
        new ResultReader().getAvrageResultForCity("150");
        new ResultReader().getAvrageResultForCity("170");
        new ResultReader().getAvrageResultForCity("442");

    }

    public List<Results> getAvrageResultForCity(String city) {
        List<Results> resultsList = new LinkedList<>();
        for (int i = 100; i <= 1000; i += 50) {


            String s = city + "-" + i;
            File file = new File("results\\" + s + ".txt");
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                List<Double> cost = new LinkedList<>();
                List<Double> time = new LinkedList<>();
                try {
                    while ((line = br.readLine()) != null) {
                        // process the line.
                        String[] split = line.split(" ");
                        cost.add(Double.valueOf(split[0]));
                        time.add(Double.valueOf(split[1]));


                    }

                    double avrageCost = cost.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
                    double avrageTime = time.stream().mapToDouble(Double::doubleValue).average().getAsDouble();

                    resultsList.add(new Results(city, i, avrageCost, avrageTime));

                } catch (IOException e) {

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        saveAveResultToFile(resultsList, city);


        return null;
    }

    private void saveAveResultToFile(List<Results> resultsList, String city) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Population Sredni-Koszt Sredni-czas  \n");
        for (Results results : resultsList) {
            stringBuilder.append(results.getPopulation() + " " + results.getAvrageCost() + " " + results.getAvrageTime() + "\n");

        }

        try {
            new FileService().saveResult(stringBuilder.toString(), city + "-ALL");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
