package com.szachnowicz.refactore;

import com.szachnowicz.alicja.Costs;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {

    private BufferedWriter writer;
    private int fileNo;

    public static void main(String[] args) throws IOException {

    }

    public TpsData getDataFromStorage(int fileNo) {
        this.fileNo = fileNo;
        String choosenFile = showAvilableFiles();
        TpsData data = null;
        try {
            data = praseXmlFile(choosenFile,fileNo);
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    private TpsData praseXmlFile(String choosenFile, int fileNo) throws ParserConfigurationException, IOException, SAXException {
        TpsData tspData = new TpsData();


        File file = new File(choosenFile);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse(file);
        String realFile = doc.getElementsByTagName("name").item(0).getTextContent();
        tspData.setName(realFile);
        String best = doc.getElementsByTagName("best").item(0).getTextContent();
        tspData.setBest(Integer.valueOf(best));


        NodeList nodeLst = doc.getElementsByTagName("edge");
        double[] costsList = new double[nodeLst.getLength()];

        for (int s = 0; s < nodeLst.getLength(); s++) {
            Node fstNode = nodeLst.item(s);
            if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                Element fstElmnt = (Element) fstNode;
                costsList[s] = Double.parseDouble(fstElmnt.getAttribute("cost"));        //tutaj parse double
            }
        }

        tspData.setCostList(convertToMatrix(costsList, tspData.getNoOfCites()));
        return tspData;
    }

    private double[][] convertToMatrix(double[] costsList, int noOfCites) {
        int index = 0;
        double[][] matrix = new double[noOfCites][noOfCites];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {

                if (i == j) {
                    matrix[i][j] = -1;

                } else {
                    matrix[i][j] = costsList[index];
                    index++;
                }

                System.out.print((int) matrix[i][j] + " ");
            }
            System.out.println();
        }

        return matrix;
    }


    public FileReader() {
    }

    private String showAvilableFiles() {
        final String filePathToMainDirectory = getFilePathToMainDirectory();
        Scanner scanner = new Scanner(System.in);
        final String[] tempList = new File(filePathToMainDirectory).list();
        List<String> xmlFileList = new ArrayList<>();
        for (String file : tempList) {
            if (file.contains(".xml"))
                xmlFileList.add(file);
        }

        System.out.println("Wybierz plik z danymi");
        for (int i = 0; i < xmlFileList.size(); i++) {
            System.out.println(i + " : " + xmlFileList.get(i));
        }
        // FIXME: 2017-12-13
//        int choose = scanner.nextInt();
        int choose =fileNo;
//        System.out.println("Wybrano plik : ");
        final String filePath = filePathToMainDirectory + "\\" + xmlFileList.get(choose);
        System.out.println(filePath);
        return filePath;
    }

    private String getFilePathToMainDirectory() {
        File foo = new File("foo");
        // creating folder in project directory
        // system will find where this project  is so we don't have to  specifited it
        foo.mkdir();
        final String absolutePath = foo.getAbsolutePath();
        // after we get the path folder foo will be deleted
        foo.delete();
        // substring after last \ - result is we got the project path.
        return absolutePath.substring(0, absolutePath.lastIndexOf("\\"));
    }

    public void saveResult(String string,String fileName) throws IOException {
        File file = new File(fileName+".txt");
        writer = new BufferedWriter(new FileWriter(file, true));
        writer.append(string);
        writer.newLine();
        writer.flush();
        writer.close();

    }
}
