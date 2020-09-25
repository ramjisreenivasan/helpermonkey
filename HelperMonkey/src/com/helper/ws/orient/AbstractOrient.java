package com.helper.ws.orient;

import java.util.ArrayList;
import java.util.List;

import com.helper.ws.TableBorder;
import com.helper.ws.WordSearch1;
import com.helper.ws.model.GridLetter;
import com.helper.ws.model.GridLocation;
import com.helper.ws.model.GridWord;
import com.helper.ws.model.WordLetter;

public abstract class AbstractOrient implements IOrient {
	
	public abstract int nextHVal(int hVal);
	public abstract int nextVVal(int vVal);
	
	public abstract int getHMin(int len, int GL, int GW);
	public abstract int getHMax(int len, int GL, int GW);
	public abstract int getVMin(int len, int GL, int GW);
	public abstract int getVMax(int len, int GL, int GW);
	
	public String markTL=TableBorder.SPACE;
	public String markTM=TableBorder.SPACE;
	public String markTR=TableBorder.SPACE;
	public String markML=TableBorder.SPACE;
	public String markMR=TableBorder.SPACE;
	public String markBL=TableBorder.SPACE;
	public String markBM=TableBorder.SPACE;
	public String markBR=TableBorder.SPACE;
	public String startTL=TableBorder.SPACE;
	public String startTM=TableBorder.SPACE;
	public String startTR=TableBorder.SPACE;
	public String startML=TableBorder.SPACE;
	public String startMR=TableBorder.SPACE;
	public String startBL=TableBorder.SPACE;
	public String startBM=TableBorder.SPACE;
	public String startBR=TableBorder.SPACE;
	public String endTL=TableBorder.SPACE;
	public String endTM=TableBorder.SPACE;
	public String endTR=TableBorder.SPACE;
	public String endML=TableBorder.SPACE;
	public String endMR=TableBorder.SPACE;
	public String endBL=TableBorder.SPACE;
	public String endBM=TableBorder.SPACE;
	public String endBR=TableBorder.SPACE;
	
	@Override
	public void populateHeadMark(GridLetter gl, boolean start, boolean end) {
	}
	
	@Override
	public void populateBodyMark(GridLetter gl, boolean start, boolean end) {
	}
	
	@Override
	public void populateFootMark(GridLetter gl, boolean start, boolean end) {
	}
	
	@Override
	public String getMLMark() {
		return this.markML;
	}

	@Override
	public String getMRMark() {
		return this.markMR;
	}

	@Override
	public String getHeadMark() {
		return markTL.concat(TableBorder.SPACE).concat(markTM).concat(TableBorder.SPACE).concat(markTR);
	}

	@Override
	public String getFootMark() {
		return markBL.concat(TableBorder.SPACE).concat(markBM).concat(TableBorder.SPACE).concat(markBR);
	}

	@Override
	public int checkSpace(GridWord gw, int xVal, int yVal) {
		int pScore = 1;
		int nScore = 0;
		for(int idx=0; idx<gw.word.length; idx++) {
			String str1 = WordSearch1.GRID_ITEMS[yVal][xVal].letter;
			xVal = nextHVal(xVal);
			yVal = nextVVal(yVal);
			
			if(str1.equals(gw.word[idx]+"")) pScore+=5;
			
			if(!str1.equals("-") && !str1.equals(gw.word[idx]+"")) {
				nScore-=idx==0?5:1;
			}
		}
		return nScore==0?pScore:nScore;
	}

	@Override
	public void populateGridWord(GridWord gw, int xVal, int yVal, boolean allowReplacements) {
		for(int idx=0; idx<gw.word.length; idx++) {
			
			GridLetter gLetter = WordSearch1.GRID_ITEMS[yVal][xVal];
			
			if(gw.isValid() || 
					allowReplacements || gLetter.letter.equals(gLetter.DEFAULT)) {
				gLetter.letter = gw.word[idx]+"";
			}
			
			gw.orientIntf = this;
			
			WordLetter wl = new WordLetter(gw);
			if(idx==0) wl.start = true;
			if(idx==gw.word.length-1) wl.end = true;
			
			gLetter.listWL.add(wl);
			gLetter.populateMarkers();
			
			WordSearch1.GRID_ITEMS[yVal][xVal] = gLetter;
			xVal = nextHVal(xVal);
			yVal = nextVVal(yVal);
		}
	}

	@Override
	public List<GridLocation> findWordLocations(GridWord gw, int GRID_LENGTH, int GRID_WIDTH) {
		int xLow = getHMin(gw.word.length, GRID_LENGTH, GRID_WIDTH);
		int xHigh = getHMax(gw.word.length, GRID_LENGTH, GRID_WIDTH);
		int yLow = getVMin(gw.word.length, GRID_LENGTH, GRID_WIDTH);
		int yHigh = getVMax(gw.word.length, GRID_LENGTH, GRID_WIDTH);
		
		List<GridLocation> listGL = new ArrayList<GridLocation>();
		
		for(int jdx=yLow; jdx<=yHigh; jdx++) {
			for(int idx=xLow; idx<=xHigh; idx++) {
				int score = checkSpace(gw, idx, jdx);
				if(gw.isValid()) {
					if(score>0) {
						GridLocation gl = new GridLocation(idx, jdx);
						gl.score = score;
						listGL.add(gl);
					}
				} else {
					GridLocation gl = new GridLocation(idx, jdx);
					gl.score = score;
					listGL.add(gl);
				}
			}
		}
		
		return listGL;
	}
}
