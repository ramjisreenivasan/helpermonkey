package com.helper.ws.model;

import java.util.*;
import java.util.stream.Collectors;

import com.helper.ws.orient.IOrient;

public class GridWord implements Cloneable {
	public String[] word;
	public GridValidity valid;
	public GridLocation wordGL;
	public GridOrientation wordGO;
	public IOrient orientIntf;
	
	public int countFragments = 0;
	public int countMisspelled = 0;
	public int countDeformed = 0;
	
	//public Map<GridOrientation,List<GridLocation>> possibleLocations = new HashMap<GridOrientation, List<GridLocation>>();
	public List<GridFitment> possibleFitments = new ArrayList<GridFitment>();
	
	public List<GridWord> falseWords = new ArrayList<GridWord>();
	
	
	public GridWord(String[] word) {
		this(word, GridValidity.VALID);
	}

	public GridWord(String[] word, GridValidity valid) {
		this.word = word;
		this.valid = valid;
		
		//Populate locMap
//		for(GridOrientation go : Arrays.asList(GridOrientation.values())) {
//			List<GridLocation> listGL = new ArrayList<GridLocation>();
//			
//			possibleLocations.put(go, listGL);
//		}
	}
	
	public boolean isValid() {
		return valid.equals(GridValidity.VALID);
	}
	
	public List<GridFitment> getFitments(GridOrientation orient) {
		return possibleFitments.stream()
				.filter((o)-> o.orient==orient)
				.collect(Collectors.toList());
	}
}
