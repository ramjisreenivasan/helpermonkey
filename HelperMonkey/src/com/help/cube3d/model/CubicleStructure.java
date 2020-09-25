package com.help.cube3d.model;

import com.help.cube2d.model.BaseCubicle;
import com.help.cube2d.model.Directions;

public class CubicleStructure {

	int length;
	int width;
	int hieght;
	
	boolean showLengthConnections = true;
	boolean showWidthConnections = true;
	boolean showHieghtConnections = true;
	
	double CONNECT_THRESHOLD = 0.7;
	
	private int currentLabel;
	
	Cubicle3D[][][] cubicleSet = null;
	
	int[] pieceIndices; 
	
	public void initialize(int length, int width, int hieght) {
		this.length=length;
		this.width=width;
		this.hieght=hieght;
		
		pieceIndices = new int[length*width*hieght];
		
		cubicleSet = new Cubicle3D[length][width][hieght];
		
		// Initialize the cubicles in the structure set
		// Set the X-Y-Z co-ordinates for all the cubicles
		for(int kdx=0; kdx<hieght; kdx++) {
			for(int jdx=0; jdx<width; jdx++) {
				for(int idx=0; idx<length; idx++) {
					Cubicle3D baseCubicle = new Cubicle3D();
					baseCubicle.setXCoordinate(idx);
					baseCubicle.setYCoordinate(jdx);
					baseCubicle.setZCoordinate(kdx);
					
					if(idx==0) baseCubicle.setBlockConnectLeft(true);
					if(jdx==0) baseCubicle.setBlockConnectBack(true);
					if(kdx==0) baseCubicle.setBlockConnectDown(true);
					
					if(idx==length-1) baseCubicle.setBlockConnectRight(true);
					if(jdx==width-1) baseCubicle.setBlockConnectFront(true);
					if(kdx==hieght-1) baseCubicle.setBlockConnectDown(true);
					
					setRandomConnections(baseCubicle);
					
					cubicleSet[idx][jdx][kdx] = baseCubicle;
					
					//display();
				}
			}
		}
		
		// Optimize the piece labels and connections
//		for(int kdx=0; kdx<hieght; kdx++) {
//			for(int jdx=0; jdx<width; jdx++) {
//				for(int idx=0; idx<length; idx++) {
//					Cubicle3D cubicle = cubicleSet[idx][jdx][kdx];
//					try {
//					optimizeCubicle(cubicle);
//					display();
//					} catch (Exception ex) {
//						System.out.printf("idx=%d; jdx=%d, kdx=%d\n", idx, jdx, kdx);
//						ex.printStackTrace();
//						System.exit(-1);
//					}
//				}
//			}
//		}
		
	}
	
	private void optimizeCubicle(Cubicle3D c1) {
		// Make piece label optimizations
		if(!c1.isBlockConnectRight()) {
			Cubicle3D c2 = c1.getCubicle(Directions.RIGHT);
			Cubicle3D c3 = cubicleSet[c2.getXCoordinate()][c2.getYCoordinate()][c2.getZCoordinate()];
			
			if(c2.getPieceIndex()==c1.getPieceIndex()) {
				// Connect them both
				if(!c1.isConnectedRight()) c1.setConnectedRight(true);
				if(!c3.isConnectedLeft()) c3.setConnectedLeft(true);
			}
		}

		if(!c1.isBlockConnectBack()) {
			Cubicle3D c2 = c1.getCubicle(Directions.BACK);
			Cubicle3D c3 = cubicleSet[c2.getXCoordinate()][c2.getYCoordinate()][c2.getZCoordinate()];
			
			if(c2.getPieceIndex()==c1.getPieceIndex()) {
				// Connect them both
				if(!c3.isConnectedFront()) c3.setConnectedFront(true);
				if(!c1.isConnectedBack()) c1.setConnectedBack(true);
			}
		}

		if(!c1.isBlockConnectDown()) {
			Cubicle3D c2 = c1.getCubicle(Directions.DOWN);
			Cubicle3D c3 = cubicleSet[c2.getXCoordinate()][c2.getYCoordinate()][c2.getZCoordinate()];
			
			if(c2.getPieceIndex()==c1.getPieceIndex()) {
				// Connect them both
				if(!c3.isConnectedUp()) c3.setConnectedUp(true);
				if(!c1.isConnectedDown()) c1.setConnectedDown(true);
			}
		}
	}
	
	private void setRandomConnections(Cubicle3D baseCubicle) {
		// Connect them at random
		double connectedWt = 0.0;
		
		if(Math.random()>=CONNECT_THRESHOLD) {
			if(!baseCubicle.isBlockConnectLeft()) {
				baseCubicle.setConnectedLeft(true);
				Cubicle3D b1 = cubicleSet[baseCubicle.getCubicle(Directions.LEFT).getXCoordinate()]
						[baseCubicle.getYCoordinate()][baseCubicle.getZCoordinate()];
				if(b1!=null) b1.setConnectedRight(true);
				
				connectedWt += 0.2;
			}
		}

		if(Math.random()>=CONNECT_THRESHOLD+connectedWt) {
			if(!baseCubicle.isBlockConnectBack()) {
				baseCubicle.setConnectedBack(true);
				Cubicle3D b1 = cubicleSet[baseCubicle.getXCoordinate()]
						[baseCubicle.getCubicle(Directions.BACK).getYCoordinate()][baseCubicle.getZCoordinate()];
				if(b1!=null) b1.setConnectedFront(true);
				connectedWt += 0.2;
			}
		}

		if(Math.random()>=CONNECT_THRESHOLD+connectedWt) {
			if(!baseCubicle.isBlockConnectDown()) {
				baseCubicle.setConnectedDown(true);
				Cubicle3D b1 = cubicleSet[baseCubicle.getXCoordinate()]
						[baseCubicle.getYCoordinate()][baseCubicle.getCubicle(Directions.DOWN).getZCoordinate()];
				if(b1!=null) b1.setConnectedUp(true);
			}
		}
	}
	
	public void labelAllPieces() {
		for(int kdx=0; kdx<hieght; kdx++) {
			for(int jdx=0; jdx<width; jdx++) {
				for(int idx=0; idx<length; idx++) {
					labelPieceSet(idx, jdx, kdx, true, Directions.BACK);
				}
			}
		}		
//		// Consolidate subPieces
//		for(int jdx=0; jdx<width; jdx++) {
//			for(int idx=0; idx<length; idx++) {
//				BaseCubicle cubicle = cubicleSet[idx][jdx];
//				int origIndex = cubicle.getPieceIndex();
//				if(pieceIndices[origIndex] > 0) cubicle.setPieceIndex(pieceIndices[origIndex]);
//			}
//		}
	}
	
	private void labelPieceSet(int idx, int jdx, int kdx, boolean incr, Directions blockDir) {
		Cubicle3D cubicle = cubicleSet[idx][jdx][kdx];
		if(cubicle.getPieceIndex()==0) {
			if(incr) currentLabel++;
			cubicle.setPieceIndex(currentLabel);
			if(!blockDir.equals(Directions.RIGHT) && cubicle.isConnectedRight()) {
				labelPieceSet(idx+1, jdx, kdx, false, Directions.LEFT);
			}
			if(!blockDir.equals(Directions.FRONT) && cubicle.isConnectedFront()) {
				labelPieceSet(idx, jdx+1, kdx, false, Directions.BACK);
			}
			if(!blockDir.equals(Directions.UP) && cubicle.isConnectedUp()) {
				labelPieceSet(idx, jdx, kdx+1, false, Directions.DOWN);
			}
			if(!blockDir.equals(Directions.LEFT) && cubicle.isConnectedLeft()) {
				labelPieceSet(idx-1, jdx, kdx, false, Directions.RIGHT);
			}
			if(!blockDir.equals(Directions.BACK) && cubicle.isConnectedBack()) {
				labelPieceSet(idx, jdx-1, kdx, false, Directions.FRONT);
			}
			if(!blockDir.equals(Directions.DOWN) && cubicle.isConnectedDown()) {
				labelPieceSet(idx, jdx, kdx-1, false, Directions.UP);
			}
		} else {
			// Cubicle already grouped - preserve the label map
			pieceIndices[currentLabel] = cubicle.getPieceIndex();
		}
	}
	
	public void display() {
		for(int jdx=0; jdx<width; jdx++) {
			for(int kdx=0; kdx<hieght; kdx++) {
				for(int idx=0; idx<length; idx++) {
					Cubicle3D baseCubicle = cubicleSet[idx][jdx][kdx];
					if(baseCubicle==null) break;
					char chConnect =
							baseCubicle.isConnectedUp() && baseCubicle.isConnectedDown()?'@':
							(baseCubicle.isConnectedDown()?'D':
										(baseCubicle.isConnectedUp()?'U':'*'));
					if(showLengthConnections && baseCubicle.isConnectedRight()) {
						System.out.printf("%c---", chConnect);
					} else {
						System.out.printf("%c   ", chConnect);
					}
				}
				System.out.print("   ");
			}
			System.out.println();
			for(int kdx=0; kdx<hieght; kdx++) {
				for(int idx=0; idx<length; idx++) {
					Cubicle3D baseCubicle = cubicleSet[idx][jdx][kdx];
					if(baseCubicle==null) break;
					if(showWidthConnections && baseCubicle.isConnectedFront()) {
						System.out.print("|   ");
					} else {
						System.out.print("    ");
					}
				}
				System.out.print("   ");
			}
			System.out.println();
		}
	}

	public void prettyDisplay() {
		for(int kdx=0; kdx<hieght; kdx++) {
			for(int idx=0; idx<length; idx++) {
				System.out.print(" ---");
			}
			System.out.print("    ");
		}
		System.out.println();
		
		for(int jdx=0; jdx<width; jdx++) {
			//System.out.print("|");
			for(int kdx=0; kdx<hieght; kdx++) {
				System.out.print("|");
				for(int idx=0; idx<length; idx++) {
					Cubicle3D baseCubicle = cubicleSet[idx][jdx][kdx];
					char chConnect =
							baseCubicle.isConnectedUp() && baseCubicle.isConnectedDown()?'@':
							(baseCubicle.isConnectedDown()?'D':
										(baseCubicle.isConnectedUp()?'U':' '));
					if(baseCubicle.isConnectedRight()) {
						System.out.printf("%2d%c ", baseCubicle.getPieceIndex(), chConnect);
					} else {
						System.out.printf("%2d%c|", baseCubicle.getPieceIndex(), chConnect);
					}
				}
				System.out.print("   ");
			}
			System.out.println();
			for(int kdx=0; kdx<hieght; kdx++) {
				for(int idx=0; idx<length; idx++) {
					Cubicle3D baseCubicle = cubicleSet[idx][jdx][kdx];
					if(baseCubicle.isConnectedFront()) {
						System.out.print("    ");
					} else {
						System.out.print(" ---");
					}
				}
				System.out.print("    ");
			}
			System.out.println();
		}
	}

	public void prettyDisplayMini() {
		for(int kdx=0; kdx<hieght; kdx++) {
			for(int idx=0; idx<length; idx++) {
				System.out.print(" ---");
			}
			System.out.println();
			for(int jdx=0; jdx<width; jdx++) {
				System.out.print("|");
				for(int idx=0; idx<length; idx++) {
					Cubicle3D baseCubicle = cubicleSet[idx][jdx][kdx];
					char chConnect = baseCubicle.isConnectedDown()?'D':
						(baseCubicle.isConnectedUp()?'U':' ');
					if(baseCubicle.isConnectedRight()) {
						System.out.printf("%2d%c ", baseCubicle.getPieceIndex(), chConnect);
					} else {
						System.out.printf("%2d%c|", baseCubicle.getPieceIndex(), chConnect);
					}
				}
				System.out.println();
				for(int idx=0; idx<length; idx++) {
					BaseCubicle baseCubicle = cubicleSet[idx][jdx][kdx];
					if(baseCubicle.isConnectedFront()) {
						System.out.print("    ");
					} else {
						System.out.print(" ---");
					}
				}
				System.out.println();
			}
			System.out.println();
		}
	}
}
