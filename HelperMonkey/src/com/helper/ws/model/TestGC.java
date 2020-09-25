package com.helper.ws.model;

import java.util.Arrays;
import java.util.stream.IntStream;

public class TestGC extends GridConfig {
	public TestGC() {
		this.GRID_LENGTH=5;
		this.GRID_WIDTH=5;
		this.GRID_DIFFICULTY=1;
		
		String words = "TIGER,LION,WOLF,GOOSE";
		
		String[] wordList = words.split(",");
		
		String[][] configList = new String[wordList.length][];
		
		for(int idx=0; idx<wordList.length; idx++) {
			String wordItem = wordList[idx];
			
			configList[idx] = new String[wordItem.length()];
			
			for(int jdx=0; jdx<wordItem.length(); jdx++) {
				configList[idx][jdx] = wordItem.charAt(jdx)+"";
			}
		}
		
		this.WORDS = configList;
		
	}

	@Override
	public String getRandomLetter() {
		// TODO Auto-generated method stub
		return (char)(65 + (int)(Math.random()*26)) + "";
	}
}
