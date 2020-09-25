package com.helper.ws.orient;

import java.util.ArrayList;
import java.util.List;

import com.helper.ws.TableBorder;
import com.helper.ws.WordSearch1;
import com.helper.ws.model.GridLetter;
import com.helper.ws.model.GridLocation;
import com.helper.ws.model.GridWord;

public class OrientBLtoTR extends AbstractOrient {

//	public OrientBLtoTR() {
//		markTR=TableBorder.SLASH;
//		markBL=TableBorder.SLASH;
//		startTR=TableBorder.WORD_START;
//		startBL=TableBorder.WORD_START;
//		endTR=TableBorder.WORD_END;
//		endBL=TableBorder.WORD_END;
//	}
//	
	@Override
	public int nextHVal(int hVal) {
		// TODO Auto-generated method stub
		return hVal+1;
	}

	@Override
	public int nextVVal(int vVal) {
		// TODO Auto-generated method stub
		return vVal-1;
	}

	@Override
	public int getHMin(int len, int GL, int GW) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHMax(int len, int GL, int GW) {
		// TODO Auto-generated method stub
		return GL-len;
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
		gl.markTR = gl.markTR.equals(TableBorder.SPACE) && !(gl.markTR.equals(TableBorder.WORD_START) || gl.markTR.equals(TableBorder.WORD_END))
				? (end ? TableBorder.WORD_END : TableBorder.SLASH)
				: gl.markTR;
	}

	@Override
	public void populateFootMark(GridLetter gl, boolean start, boolean end) {
		gl.markBL = gl.markBL.equals(TableBorder.SPACE) && !(gl.markBL.equals(TableBorder.WORD_START) || gl.markBL.equals(TableBorder.WORD_END))
				? (start ? TableBorder.WORD_START : TableBorder.SLASH)
				: gl.markBL;
	}
}
