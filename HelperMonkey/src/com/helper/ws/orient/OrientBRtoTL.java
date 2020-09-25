package com.helper.ws.orient;

import java.util.ArrayList;
import java.util.List;

import com.helper.ws.TableBorder;
import com.helper.ws.WordSearch1;
import com.helper.ws.model.GridLetter;
import com.helper.ws.model.GridLocation;
import com.helper.ws.model.GridWord;

public class OrientBRtoTL extends AbstractOrient {

//	public OrientBRtoTL() {
//		markTL=TableBorder.BACKSLASH;
//		markTM=TableBorder.SPACE;
//		markTR=TableBorder.SPACE;
//		markML=TableBorder.SPACE;
//		markMR=TableBorder.SPACE;
//		markBL=TableBorder.SPACE;
//		markBM=TableBorder.SPACE;
//		markBR=TableBorder.BACKSLASH;		
//		markTL=TableBorder.BACKSLASH;
//		markBR=TableBorder.BACKSLASH;		
//		startTL=TableBorder.WORD_START;
//		startBR=TableBorder.WORD_START;
//		endTL=TableBorder.WORD_END;
//		endBR=TableBorder.WORD_END;
//	}
	
	
	@Override
	public int nextHVal(int hVal) {
		// TODO Auto-generated method stub
		return hVal-1;
	}

	@Override
	public int nextVVal(int vVal) {
		// TODO Auto-generated method stub
		return vVal-1;
	}

	@Override
	public int getHMin(int len, int GL, int GW) {
		// TODO Auto-generated method stub
		return len-1;
	}

	@Override
	public int getHMax(int len, int GL, int GW) {
		// TODO Auto-generated method stub
		return GL-1;
	}

	@Override
	public int getVMin(int len, int GL, int GW) {
		// TODO Auto-generated method stub
		return len-1;
	}

	@Override
	public int getVMax(int len, int GL, int GW) {
		// TODO Auto-generated method stub
		return GW-1;
	}

	@Override
	public void populateHeadMark(GridLetter gl, boolean start, boolean end) {
		gl.markTL = gl.markTL.equals(TableBorder.SPACE) && !(gl.markTL.equals(TableBorder.WORD_START) || gl.markTL.equals(TableBorder.WORD_END))
				? (end ? TableBorder.WORD_END : TableBorder.BACKSLASH)
				: gl.markTL;
	}

	@Override
	public void populateFootMark(GridLetter gl, boolean start, boolean end) {
		gl.markBR = gl.markBR.equals(TableBorder.SPACE) && !(gl.markBR.equals(TableBorder.WORD_START) || gl.markBR.equals(TableBorder.WORD_END))
				? (start ? TableBorder.WORD_START : TableBorder.BACKSLASH)
				: gl.markBR;
	}

}
