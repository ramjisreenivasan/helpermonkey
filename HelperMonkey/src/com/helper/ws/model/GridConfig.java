package com.helper.ws.model;

public abstract class GridConfig {
	public int GRID_LENGTH=0;
	public int GRID_WIDTH=0;
	public int GRID_DIFFICULTY=0;
	
	public String[][] WORDS = {};
	
	public abstract String getRandomLetter();
}
