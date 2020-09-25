package com.help.project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CamelCase {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		CamelCase cc = new CamelCase();
		
//		String[] words = {"is", "valid", "right"};
//		String variable1 = "isValid";
		
		String[] words = {"a", "b", "c", "d", "e"};
		String variable1 = "ABCDE";
		
		System.out.println(cc.camelCaseSeparation(words, variable1));

		Scanner sc1 = new Scanner(System.in);
	}
	
	boolean camelCaseSeparation(String[] words, String variableName) {

		List<String> varSplits = new ArrayList<String>();
		String s = variableName;
	    int startIndex = 0;
	    int endIndex = 0;
		
		for (int i = 0; i < s.length(); i++) {
	        if (Character.isUpperCase(s.charAt(i)) || i == s.length() - 1) {
	            startIndex = endIndex;
	            endIndex = i != s.length() - 1 ? i : i;
	            if(endIndex > startIndex) {
	            	varSplits.add(s.substring(startIndex, endIndex));
	            }
	        }
	    }
		
		List<String> wordListTmp = Arrays.asList(words);
		List<String> capWords = new ArrayList<String>();
		capWords.addAll(wordListTmp);
		
		for(String w1 : wordListTmp) {
			String new1 = new String(Character.toUpperCase(w1.charAt(0)) + w1.substring(1));
			capWords.add(new1);
		}
		
		//List<String> wordList = Arrays.asList(capWords);
		
		
		for(String splitItem : varSplits) {
			if(!capWords.contains(splitItem)) {
				return false;
			}
		}
		return true;

	}	
	
	
	
	
	
}
