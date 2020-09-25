package com.help.cube2d.model;

public class CubicleStructure {

	int length;
	int width;
	
	boolean connectSingles = true;
	
	double CONNECT_THRESHOLD = 0.7;
	
	private int currentLabel;
	
	BaseCubicle[][] cubicleSet = null;
	
	int[] pieceIndices; 
	
	public void initialize(int length, int width) {
		this.length=length;
		this.width=width;
		pieceIndices = new int[length*width];
		
		cubicleSet = new BaseCubicle[length][width];
		
		// Initialize the cubicles in the structure set
		// Set the X-Y co-ordinates for all the cubicles
		for(int jdx=0; jdx<width; jdx++) {
			for(int idx=0; idx<length; idx++) {
				BaseCubicle baseCubicle = new BaseCubicle();
				baseCubicle.setXCoordinate(idx);
				baseCubicle.setYCoordinate(jdx);
				
				if(idx==0) baseCubicle.setBlockConnectLeft(true);
				if(jdx==0) baseCubicle.setBlockConnectBack(true);
				
				if(idx==length-1) baseCubicle.setBlockConnectRight(true);
				if(jdx==width-1) baseCubicle.setBlockConnectFront(true);
				
				setRandomConnections(baseCubicle);
				
				cubicleSet[idx][jdx] = baseCubicle;
			}
		}
	}
	
	private void setRandomConnections(BaseCubicle baseCubicle) {
		// Connect them at random
		if(Math.random()>=CONNECT_THRESHOLD) {
			if(!baseCubicle.isBlockConnectLeft()) {
				baseCubicle.setConnectedLeft(true);
				cubicleSet[baseCubicle.getCubicle(Directions.LEFT).getXCoordinate()][baseCubicle.getYCoordinate()].setConnectedRight(true);
			}
		}

		if(Math.random()>=CONNECT_THRESHOLD) {
			if(!baseCubicle.isBlockConnectBack()) {
					baseCubicle.setConnectedBack(true);
					BaseCubicle b1 = cubicleSet[baseCubicle.getXCoordinate()][baseCubicle.getCubicle(Directions.BACK).getYCoordinate()];
					if(b1!=null) b1.setConnectedFront(true);
			}
		}
	}
	
	public void labelAllPieces() {
		for(int jdx=0; jdx<width; jdx++) {
			for(int idx=0; idx<length; idx++) {
				labelPieceSet(idx, jdx, true, Directions.BACK);
			}
		}
		
//		// Consolidate subPieces by connecting singles
//		if(connectSingles) {
//			for(int jdx=0; jdx<width; jdx++) {
//				for(int idx=0; idx<length; idx++) {
//					BaseCubicle cubicle = cubicleSet[idx][jdx];
//					connectPieces(cubicle, Directions.RIGHT);
//				}
//			}
//		}
	}
	
	private int getConnectedWt(BaseCubicle cubicle) {
		int connectWt = 0;

		if(cubicle.isConnectedRight()) connectWt++;
		if(cubicle.isConnectedLeft()) connectWt++;
		if(cubicle.isConnectedFront()) connectWt++;
		if(cubicle.isConnectedBack()) connectWt++;
		
		return connectWt;
	}
	
	private void labelPieceSet(int idx, int jdx, boolean incr, Directions blockDir) {
		BaseCubicle cubicle = cubicleSet[idx][jdx];
		if(cubicle.getPieceIndex()==0) {
			if(incr) currentLabel++;
			cubicle.setPieceIndex(currentLabel);
			if(!blockDir.equals(Directions.RIGHT) && cubicle.isConnectedRight()) {
				labelPieceSet(idx+1, jdx, false, Directions.LEFT);
			}
			if(!blockDir.equals(Directions.FRONT) && cubicle.isConnectedFront()) {
				labelPieceSet(idx, jdx+1, false, Directions.BACK);
			}
			if(!blockDir.equals(Directions.LEFT) && cubicle.isConnectedLeft()) {
				labelPieceSet(idx-1, jdx, false, Directions.RIGHT);
			}
			if(!blockDir.equals(Directions.BACK) && cubicle.isConnectedBack()) {
				labelPieceSet(idx, jdx-1, false, Directions.FRONT);
			}
		} else {
			// Cubicle already grouped - preserve the label map
			pieceIndices[currentLabel] = cubicle.getPieceIndex();
		}
	}
	
	public void display() {
		for(int jdx=0; jdx<width; jdx++) {
			for(int idx=0; idx<length; idx++) {
				BaseCubicle baseCubicle = cubicleSet[idx][jdx];
				if(baseCubicle.isConnectedRight()) {
					System.out.print("*---");
				} else {
					System.out.print("*   ");
				}
			}
			System.out.println();
			for(int idx=0; idx<length; idx++) {
				BaseCubicle baseCubicle = cubicleSet[idx][jdx];
				if(baseCubicle.isConnectedFront()) {
					System.out.print("|   ");
				} else {
					System.out.print("    ");
				}
			}
			System.out.println();
		}
	}

	public void prettyDisplay() {
		for(int idx=0; idx<length; idx++) {
			System.out.print(" ---");
		}
		System.out.println();
		for(int jdx=0; jdx<width; jdx++) {
			System.out.print("|");
			for(int idx=0; idx<length; idx++) {
				BaseCubicle baseCubicle = cubicleSet[idx][jdx];
				if(baseCubicle.isConnectedRight()) {
					System.out.printf("%2d  ", baseCubicle.getPieceIndex());
				} else {
					System.out.printf("%2d |", baseCubicle.getPieceIndex());
				}
			}
			System.out.println();
			for(int idx=0; idx<length; idx++) {
				BaseCubicle baseCubicle = cubicleSet[idx][jdx];
				if(baseCubicle.isConnectedFront()) {
					System.out.print("    ");
				} else {
					System.out.print(" ---");
				}
			}
			System.out.println();
		}
	}
}
