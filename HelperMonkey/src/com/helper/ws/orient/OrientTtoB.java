package com.helper.ws.orient;

import java.util.ArrayList;
import java.util.List;

import com.helper.ws.TableBorder;
import com.helper.ws.WordSearch1;
import com.helper.ws.model.GridLetter;
import com.helper.ws.model.GridLocation;
import com.helper.ws.model.GridOrientation;
import com.helper.ws.model.GridWord;

public class OrientTtoB extends AbstractOrient {

//	public OrientTtoB() {
//		markTM=TableBorder.SINGLE_VLINE;
//		markBM=TableBorder.SINGLE_VLINE;
//		startTM=TableBorder.WORD_START;
//		startBM=TableBorder.WORD_START;
//		endTM=TableBorder.WORD_END;
//		endBM=TableBorder.WORD_END;
//	}
	
//	@Override
//	public String getHeadMark(GridLetter gl) {
//		String mrk = "";
//			mrk = gl.start?
//				(mrk.concat(TableBorder.SINGLE_TOP_LEFT).concat(TableBorder.SINGLE_HLINE)
//				.concat(TableBorder.SINGLE_HLINE).concat(TableBorder.SINGLE_HLINE)
//				.concat(TableBorder.SINGLE_TOP_RIGHT))
//				:
//					(mrk.concat(TableBorder.SINGLE_VLINE).concat(TableBorder.SPACE)
//							.concat(TableBorder.SPACE).concat(TableBorder.SPACE)
//							.concat(TableBorder.SINGLE_VLINE))
//					;
//		return mrk;
//	}
//	
//	@Override
//	public String getBodyMark(GridLetter gl) {
//		String mrk = TableBorder.SINGLE_VLINE;
//		mrk = mrk.concat(TableBorder.SPACE).concat(gl.letter).concat(TableBorder.SPACE)
//				.concat(TableBorder.SINGLE_VLINE);
//		return mrk;
//	}
//	
//	@Override
//	public String getFootMark(GridLetter gl) {
//		String mrk = "";
//		mrk = gl.end?
//			(mrk.concat(TableBorder.SINGLE_BOTTOM_LEFT).concat(TableBorder.SINGLE_HLINE)
//			.concat(TableBorder.SINGLE_HLINE).concat(TableBorder.SINGLE_HLINE)
//			.concat(TableBorder.SINGLE_BOTTOM_RIGHT))
//			:
//				(mrk.concat(TableBorder.SINGLE_VLINE).concat(TableBorder.SPACE)
//						.concat(TableBorder.SPACE).concat(TableBorder.SPACE)
//						.concat(TableBorder.SINGLE_VLINE));
//		return mrk;
//	}

	@Override
	public int nextHVal(int hVal) {
		// TODO Auto-generated method stub
		return hVal;
	}

	@Override
	public int nextVVal(int vVal) {
		// TODO Auto-generated method stub
		return vVal+1;
	}

	@Override
	public int getHMin(int len, int GL, int GW) {
		// TODO Auto-generated method stub
		return 0;
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
		return GW-len;
	}

	@Override
	public void populateHeadMark(GridLetter gl, boolean start, boolean end) {
		gl.markTM = gl.markTM.equals(TableBorder.SPACE) && !(gl.markTM.equals(TableBorder.WORD_START) || gl.markTM.equals(TableBorder.WORD_END))
				? (start ? TableBorder.WORD_START : TableBorder.SINGLE_VLINE)
				: gl.markTM;
	}

	@Override
	public void populateFootMark(GridLetter gl, boolean start, boolean end) {
		gl.markBM = gl.markBM.equals(TableBorder.SPACE) && !(gl.markBM.equals(TableBorder.WORD_START) || gl.markBM.equals(TableBorder.WORD_END))
				? (end ? TableBorder.WORD_END : TableBorder.SINGLE_VLINE)
				: gl.markBM;
	}
}
