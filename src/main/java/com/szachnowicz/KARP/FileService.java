package com.szachnowicz.KARP;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class FileService {

    public static int[][] readFile() throws IOException {


        /* ----------------------------- IO MANAGEMENT ----------------------------- */

        // The path to the files with the distances is asked
        Scanner input = new Scanner(System.in);
        System.out.println("Prosze podać nazwe pliku - musi on się znajdować w ktalogu z programem ");
        String file = input.nextLine();
        //= "gr17.txt"; //

        int size = 0;


        // The file in that location is opened
        FileReader f = new FileReader(file);
        BufferedReader b = new BufferedReader(f);

        String sizeLine = b.readLine();
        if (sizeLine.startsWith("size")) {
            final String[] split = sizeLine.split("size=");
            size = Integer.valueOf(split[split.length - 1]);
        }


        int[][] distances = new int[size][size];


        List<String> inputLines = new ArrayList<String>();
        String line;
        while ((line = b.readLine()) != null) {
            String[] values = line.trim().split(" ");
            Collections.addAll(inputLines, values);
            inputLines.removeIf(s -> s.equals(""));
        }


        int readCounnter = 0;
        for (int row = 0; row < size; row++) {

            for (int col = 0; col < size; col++) {
                int value = Integer.parseInt(inputLines.get(readCounnter++));
                distances[col][row] = value;
                if (distances[col][row] == 0)
                    break;
                distances[row][col] = value;
            }


        }

        // Closing file
        b.close();

        printMatrix(distances);
        return distances;
    }


    public static int[][] dummyData() {
        int size = 4;
        int[][] distances = new int[size][size];

        for (int i = 0; i < size; i++) {
            distances[i][i] = 0;
            if (i == 0) {
                distances[i][1] = 1;
                distances[1][i] = 1;

                distances[2][i] = 15;
                distances[i][2] = 15;

                distances[i][3] = 6;
                distances[3][i] = 6;

            }
            if (i == 1) {
                distances[i][0] = 1;
                distances[0][i] = 1;
                distances[i][2] = 7;
                distances[2][i] = 7;
                distances[i][3] = 3;
                distances[3][i] = 3;
            }
            if (i == 3) {
                distances[i][0] = 6;
                distances[0][i] = 6;
                distances[i][1] = 3;
                distances[1][i] = 3;
                distances[i][2] = 12;
                distances[2][i] = 12;
            }

        }


        printMatrix(distances);


        return distances;


    }

    public static void printMatrix(int[][] m) {
        try {
            int rows = m.length;
            int columns = m[0].length;
            String str = "|\t";

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    str += m[i][j] + "\t";
                }

                System.out.println(str + "|");
                str = "|\t";
            }

        } catch (Exception e) {
            System.out.println("Matrix is empty!!");
        }
    }

    public static void main(String[] args) {
        try {
            readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
