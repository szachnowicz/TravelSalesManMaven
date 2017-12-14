package com.szachnowicz.alicja;

import java.util.ArrayList;
import java.util.List;

public class TSP{
	
	private int numOfCities = GetNumber.returnNumOfCities();
	private int pow= (int)Math.pow(2, numOfCities);
	private double distances[][]=new double [numOfCities][numOfCities];
	private int solutions[][]= new int [numOfCities][pow];
	private int optTourArray[][]= new int [numOfCities][pow];
	private int Dooooooone;
	List<Integer> optTour = new ArrayList<Integer>();
	
	public void fillDistances()
	{
		int index=0;
		for(int i=0; i<distances.length; i++) {
			for(int j=0; j<distances[i].length; j++) {
				
				if(i==j) {
					distances[i][j]=-1;
					
				}else {
					distances[i][j]=Costs.costsList[index];
					index++;
				}				
				
				System.out.print((int)distances[i][j]+" ");		//uzupelnienie macierzy odleglosciami
			}
			System.out.println();
		}		
	}
	
	
	public void engine() {
		this.	fillDistances();
		for(int i=0; i<numOfCities; i++) {
			for(int j=0; j<pow; j++) {
				
				optTourArray[i][j]=-1; //ta cala w -1
				
				
				if(j==0) {
					solutions[i][0]=(int)distances[i][0];
					
				} else {
					solutions[i][j]=-1;
				}				
			}
			
		}
		long actualTime = System.nanoTime();
		Dooooooone= findMinPath(0, pow-2);	
		
		System.out.println("\nRozwiazanie optymalne(wg heidelberg): " + Costs.best);
		
		long executionTime = System.nanoTime() - actualTime;
		System.out.println("Rozwiazanie optymalne: " + Dooooooone);
		
		if((int)Dooooooone==Integer.parseInt(Costs.best)) {
			System.out.println("Rozwiazanie jest ok!" );
		} else {
			System.out.println("Rozwiazanie nie jest ok!" );
		}
		
		System.out.println("\nCzas: "+ (double)executionTime/1000000 + " [ms]");

		
		findOptTour(0, pow-2);
		
		
		System.out.print("\n0 -> ");
		for (int i =0; i<optTour.size(); i++)
		{
			System.out.print(optTour.get(i)+" -> ");
		}
		System.out.print("0");
		
	}
	
	private int findMinPath(int start, int set) {
		
		int result=-1;
		int mask, masked, tmp;
		
		if(solutions[start][set] !=-1) { 	//jesli droga przez wszystkie miasta zostanie policzona np. cel dla 4 miast-> D[1][{2,3,4}]
			return solutions[start][set];	//to wychodzimy z programu
		} else {
			for(int i=0; i<numOfCities; i++) {
				
				mask=pow-1-(int)Math.pow(2, i);	//mask- ktory wierzcholek usuwamy
				masked=set&mask; //AND zestaw miast do przejscia ->> wychodzi z tego masked czyli zestaw miast do przejscia bez 1
				
				if(masked != set) {
					tmp = (int) distances[start][i]+ findMinPath(i, masked);	//liczy permutacje 
					
					//jesli nic nie jest wpisane lub jezeli jest gorszy niz wczesniej znaleziony w tmp to:
					if(result==-1 || result>tmp) {
						result=tmp;
						
						
						optTourArray[start][set]=i;
					}
				}
			}
		}
		solutions[start][set]=result;
		
		return result;
	}
	
	private void findOptTour(int start, int set) {
		if(optTourArray[start][set]!=-1) {
			int currentNode = optTourArray[start][set];
			int mask = pow - 1 - (int)Math.pow(2, currentNode);	 //set na setOfCities, mask newSetOfCities, masked removingOneCity
			int masked = set & mask;

			
			optTour.add(currentNode);
			findOptTour(currentNode, masked);
			
		}
	}
}