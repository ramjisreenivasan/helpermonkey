package com.helper.ws.model;

public class HardGC extends GridConfig {
	public HardGC() {
		this.GRID_LENGTH=15;
		this.GRID_WIDTH=15;
		this.GRID_DIFFICULTY=4;
		
		String words = "RAMJI,"
				+ "SRIDEVI,"
				+ "VASUPRADHA,"
				+ "ABHINANDAN,"
				+ "SREENIVASAN,"
				+ "SRIMATHY,"
				+ "RAMANAN,"
				+ "SRILEKHA,"
				+ "ABHILASH,"
				+ "AAKANSHA,"
				+ "SUBASREE"
				+ "";
		
		String[] wordList = words.split(",");
		
		String[][] configList = new String[wordList.length][];
		
		for(int idx=0; idx<wordList.length; idx++) {
			String wordItem = wordList[idx];
			
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
