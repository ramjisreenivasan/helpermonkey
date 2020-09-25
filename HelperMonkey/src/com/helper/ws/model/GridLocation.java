package com.helper.ws.model;

public class GridLocation {
	public int xCoord;
	public int yCoord;
	public int score;
	
	public GridLocation(int x, int y) {
		this.xCoord = x;
		this.yCoord = y;
	}
	
	@Override
	public boolean equals (Object o) {
		GridLocation gl = (GridLocation)o;
		return (gl.xCoord == this.xCoord && gl.yCoord == this.yCoord);
	}
}
