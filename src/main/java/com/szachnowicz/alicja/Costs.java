package com.szachnowicz.alicja;

import java.io.File;
//import javax.swing.JFileChooser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Costs {

	public static String realFile = null;
	public static String best = null;
	public static double costsList[];

	public static void loadCost(String fileName) {
		try {

			File file = new File(fileName);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			if (ifFileExists(fileName)) {

			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();

			//System.out.println("Element g��wny: " + doc.getDocumentElement().getNodeName());
			realFile= doc.getElementsByTagName("name").item(0).getTextContent();
			best= doc.getElementsByTagName("best").item(0).getTextContent();


			NodeList nodeLst = doc.getElementsByTagName("edge");
			costsList=new double [nodeLst.getLength()];

			for (int s = 0; s < nodeLst.getLength(); s++) {

				Node fstNode = nodeLst.item(s);
				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
					Element fstElmnt = (Element) fstNode;
					costsList[s]= Double.parseDouble(fstElmnt.getAttribute("cost"));		//tutaj parse double
					}
				}
			} else {
				System.out.println("Nie ma pliku o nazwie: " + fileName);
				}
			} catch (Exception e) {
			e.printStackTrace();
			}
		}


	public static boolean ifFileExists(String nazwa) {
		File f = new File(nazwa);
		return f.exists() && f.isFile();
	}

}
