package com.helper.ws.orient;

import java.util.List;

import com.helper.ws.model.GridLetter;
import com.helper.ws.model.GridLocation;
import com.helper.ws.model.GridWord;

public interface IOrient {
	
	public int checkSpace(GridWord gw, int xVal, int yVal);
	public void populateGridWord(GridWord gw, int xVal, int yVal, boolean allowReplacements);
	public List<GridLocation> findWordLocations(GridWord gw, int GRID_LENGTH, int GRID_WIDTH);
	public void populateBodyMark(GridLetter gl, boolean start, boolean end);
	public void populateHeadMark(GridLetter gl, boolean start, boolean end);
	public void populateFootMark(GridLetter gl, boolean start, boolean end);
	public String getHeadMark();
	public String getFootMark();
	public String getMLMark();
	public String getMRMark();
}
