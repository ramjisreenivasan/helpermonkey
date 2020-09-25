package com.helper.ws.orient;

import java.util.ArrayList;
import java.util.List;

import com.helper.ws.TableBorder;
import com.helper.ws.WordSearch1;
import com.helper.ws.model.GridLetter;
import com.helper.ws.model.GridLocation;
import com.helper.ws.model.GridWord;

public class OrientRtoL extends AbstractOrient {

//	public OrientRtoL() {
//		markTL=TableBorder.SPACE;
//		markTM=TableBorder.SPACE;
//		markTR=TableBorder.SPACE;
//		markML=TableBorder.SINGLE_HLINE;
//		markMR=TableBorder.SINGLE_HLINE;
//		markBL=TableBorder.SPACE;
//		markBM=TableBorder.SPACE;
//		markBR=TableBorder.SPACE;		
//		startML=TableBorder.WORD_START;
//		startMR=TableBorder.WORD_START;
//		endML=TableBorder.WORD_END;
//		endMR=TableBorder.WORD_END;
//	}
	
//	@Override
//	public String getHeadMark(GridLetter gl) {
//		String mrk = gl.end?TableBorder.SINGLE_TOP_LEFT:TableBorder.SINGLE_HLINE;
//		mrk = mrk.concat(TableBorder.SINGLE_HLINE).concat(TableBorder.SINGLE_HLINE).concat(TableBorder.SINGLE_HLINE)
//				.concat(gl.start?TableBorder.SINGLE_TOP_RIGHT:TableBorder.SINGLE_HLINE);
//		return mrk;
//	}
//	
//	@Override
//	public String getBodyMark(GridLetter gl) {
//		String mrk = gl.end?TableBorder.SINGLE_VLINE:TableBorder.SINGLE_HLINE;
//		mrk = mrk.concat(" ").concat(gl.letter).concat(" ")
//				.concat(gl.start?TableBorder.SINGLE_VLINE:TableBorder.SINGLE_HLINE);
//		return mrk;
//	}
//	
//	@Override
//	public String getFootMark(GridLetter gl) {
//		String mrk = gl.end?TableBorder.SINGLE_BOTTOM_LEFT:TableBorder.SINGLE_HLINE;
//		mrk = mrk.concat(TableBorder.SINGLE_HLINE).concat(TableBorder.SINGLE_HLINE).concat(TableBorder.SINGLE_HLINE)
//				.concat(gl.start?TableBorder.SINGLE_BOTTOM_RIGHT:TableBorder.SINGLE_HLINE);
//		return mrk;
//	}

	@Override
	public int nextHVal(int hVal) {
		// TODO Auto-generated method stub
		return hVal-1;
	}

	@Override
	public int nextVVal(int vVal) {
		// TODO Auto-generated method stub
		return vVal;
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
		return 0;
	}

	@Override
	public int getVMax(int len, int GL, int GW) {
		// TODO Auto-generated method stub
		return GW-1;
	}

	@Override
	public void populateBodyMark(GridLetter gl, boolean start, boolean end) {
		// TODO Auto-generated method stub
		gl.markML = gl.markML.equals(TableBorder.SPACE) && !(gl.markML.equals(TableBorder.WORD_START) || gl.markML.equals(TableBorder.WORD_END))
				? (end ? TableBorder.WORD_END : TableBorder.SINGLE_HLINE)
				: gl.markML;
		gl.markMR = gl.markMR.equals(TableBorder.SPACE) && !(gl.markMR.equals(TableBorder.WORD_START) || gl.markMR.equals(TableBorder.WORD_END))
				? (start ? TableBorder.WORD_START : TableBorder.SINGLE_HLINE)
				: gl.markMR;
	}
}
