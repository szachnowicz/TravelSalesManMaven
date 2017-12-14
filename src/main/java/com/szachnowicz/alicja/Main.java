package com.szachnowicz.alicja;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("Podaj nazwe pliku: ");
		String name = input.nextLine();
		Costs.loadCost(name);
		
		TSP instance = new TSP();

		instance.engine();


		input.close();
	}
	
}
