package com.help.cube2d.model;

public class BaseCubicle {
	protected int XCoordinate;
	protected int YCoordinate;
	
	protected boolean blockConnectRight;
	protected boolean blockConnectFront;
	protected boolean blockConnectLeft;
	protected boolean blockConnectBack;
	
	protected int pieceIndex;

	public int getXCoordinate() {
		return XCoordinate;
	}

	public void setXCoordinate(int xCoordinate) {
		XCoordinate = xCoordinate;
	}

	public int getYCoordinate() {
		return YCoordinate;
	}

	public void setYCoordinate(int yCoordinate) {
		YCoordinate = yCoordinate;
	}

	public boolean isConnectedLeft() {
		return connectedLeft;
	}

	public void setConnectedLeft(boolean connectedLeft) {
		this.connectedLeft = connectedLeft;
	}

	public boolean isConnectedRight() {
		return connectedRight;
	}

	public void setConnectedRight(boolean connectedRight) {
		if(!this.blockConnectRight) this.connectedRight = connectedRight;
	}

	public boolean isConnectedBack() {
		return connectedBack;
	}

	public void setConnectedBack(boolean connectedBack) {
		this.connectedBack = connectedBack;
	}

	public boolean isConnectedFront() {
		return connectedFront;
	}

	public void setConnectedFront(boolean connectedFront) {
		if(!this.blockConnectFront) this.connectedFront = connectedFront;
	}

	protected boolean connectedLeft;
	protected boolean connectedRight;
	protected boolean connectedBack;
	protected boolean connectedFront;
	
	public boolean equals(BaseCubicle c1) {
		return c1.XCoordinate == this.XCoordinate && c1.YCoordinate == this.YCoordinate;
	}

	public BaseCubicle getCubicle(Directions dir) {
		switch(dir)
		{
		case LEFT:
			if(connectedLeft) return new BaseCubicle(XCoordinate-1, YCoordinate);
			break;
		case RIGHT:
			if(connectedRight) return new BaseCubicle(XCoordinate+1, YCoordinate);
			break;
		case FRONT:
			if(connectedFront) return new BaseCubicle(XCoordinate, YCoordinate+1);
			break;
		case BACK:
			if(connectedBack) return new BaseCubicle(XCoordinate, YCoordinate-1);
			break;
		}
		return null;
	}

	public BaseCubicle(int idx, int jdx) {
		this.XCoordinate = idx;
		this.YCoordinate = jdx;
	}

	public BaseCubicle() {
		// TODO Auto-generated constructor stub
	}

	public boolean isBlockConnectRight() {
		return blockConnectRight;
	}

	public void setBlockConnectRight(boolean blockConnectRight) {
		this.blockConnectRight = blockConnectRight;
	}

	public boolean isBlockConnectFront() {
		return blockConnectFront;
	}

	public void setBlockConnectFront(boolean blockConnectFront) {
		this.blockConnectFront = blockConnectFront;
	}

	public int getPieceIndex() {
		return pieceIndex;
	}

	public void setPieceIndex(int pieceIndex) {
		this.pieceIndex = pieceIndex;
	}

	public boolean isBlockConnectLeft() {
		return blockConnectLeft;
	}

	public void setBlockConnectLeft(boolean blockConnectLeft) {
		this.blockConnectLeft = blockConnectLeft;
	}

	public boolean isBlockConnectBack() {
		return blockConnectBack;
	}

	public void setBlockConnectBack(boolean blockConnectBack) {
		this.blockConnectBack = blockConnectBack;
	}
}
