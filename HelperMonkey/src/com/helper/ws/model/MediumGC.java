package com.helper.ws.model;

public class MediumGC extends GridConfig {
	public MediumGC() {
		this.GRID_LENGTH=12;
		this.GRID_WIDTH=12;
		this.GRID_DIFFICULTY=4;
		
		String words = "GIRRAFFE,ELEPHANT,MONKEY,ANTELOPE,TIGER";
		
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
