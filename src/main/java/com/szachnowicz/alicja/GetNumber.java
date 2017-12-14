package com.szachnowicz.alicja;

import java.util.ArrayList;
import java.util.List;


public class GetNumber {

	private static List<Integer> getNumbers(String s) {
		List<Integer> l = new ArrayList<Integer>();
		char[] chars = s.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (Character.isDigit(chars[i])) {	//sprawdza czy jest cyfra
				String num = "";
				while (i < chars.length && Character.isDigit(chars[i])) {
					num += chars[i++];
				}
				l.add(Integer.parseInt(num));
			}
		}
		return l;
	}
	
	public static int returnNumOfCities(){
		return getNumbers(Costs.realFile).get(0);
	}
}
