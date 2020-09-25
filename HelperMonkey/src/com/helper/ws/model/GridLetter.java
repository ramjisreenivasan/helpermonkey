package com.helper.ws.model;

import java.util.ArrayList;
import java.util.List;

import com.helper.ws.TableBorder;
import com.helper.ws.model.*;

public class GridLetter {
	public String letter;
	
	public List<WordLetter> listWL = new ArrayList<WordLetter>();

	public String headMark;
	public String bodyMark;
	public String footMark;
	
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
	
	public static String DEFAULT = "-";
	
	public GridLetter(String letter) {
		this.letter = letter;
		
		this.headMark = "     ";
		this.bodyMark = "     ";
		this.footMark = "     ";
	}

	public GridLetter(String letter, GridWord gw) {
		WordLetter wl = new WordLetter(gw);
		this.listWL.add(wl);
		this.letter = letter;
	}
	
	public void populateMarkers() {
		listWL.stream().filter((item)-> item.gw.isValid())
		.forEach((item) -> {
			item.gw.orientIntf.populateHeadMark(this, item.start, item.end);
			item.gw.orientIntf.populateBodyMark(this, item.start, item.end);
			item.gw.orientIntf.populateFootMark(this, item.start, item.end);
		}
		);
		
		this.headMark = markTL.concat(TableBorder.SPACE).concat(markTM).concat(TableBorder.SPACE).concat(markTR);
		this.footMark = markBL.concat(TableBorder.SPACE).concat(markBM).concat(TableBorder.SPACE).concat(markBR);
		this.bodyMark = markML.concat(TableBorder.SPACE).concat(letter).concat(TableBorder.SPACE).concat(markMR);
	}
}
