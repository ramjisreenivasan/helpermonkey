package com.help.cube3d.model;

import com.help.cube2d.model.BaseCubicle;
import com.help.cube2d.model.Directions;

public class Cubicle3D extends BaseCubicle {
	private int ZCoordinate;
	
	private boolean blockConnectUp;
	private boolean blockConnectDown;
	
	private boolean connectedUp;
	private boolean connectedDown;
	
	public int getZCoordinate() {
		return ZCoordinate;
	}
	public void setZCoordinate(int zCoordinate) {
		ZCoordinate = zCoordinate;
	}
	public boolean isBlockConnectUp() {
		return blockConnectUp;
	}
	public void setBlockConnectUp(boolean blockConnectUp) {
		this.blockConnectUp = blockConnectUp;
	}
	public boolean isBlockConnectDown() {
		return blockConnectDown;
	}
	public void setBlockConnectDown(boolean blockConnectDown) {
		this.blockConnectDown = blockConnectDown;
	}
	public boolean isConnectedUp() {
		return connectedUp;
	}
	public void setConnectedUp(boolean connectedUp) {
		if(!this.blockConnectUp) this.connectedUp = connectedUp;
	}
	public boolean isConnectedDown() {
		return connectedDown;
	}
	public void setConnectedDown(boolean connectedDown) {
		if(!this.blockConnectDown) this.connectedDown = connectedDown;
	}

	public Cubicle3D(int idx, int jdx, int kdx) {
		this.XCoordinate = idx;
		this.YCoordinate = jdx;
		this.ZCoordinate = kdx;
	}

	public Cubicle3D() {
		// TODO Auto-generated constructor stub
	}

	public Cubicle3D getCubicle(Directions dir) {
		switch(dir)
		{
		case LEFT:
			if(connectedLeft) return new Cubicle3D(XCoordinate-1, YCoordinate, ZCoordinate);
			break;
		case RIGHT:
			if(connectedRight) return new Cubicle3D(XCoordinate+1, YCoordinate, ZCoordinate);
			break;
		case FRONT:
			if(connectedFront) return new Cubicle3D(XCoordinate, YCoordinate+1, ZCoordinate);
			break;
		case BACK:
			if(connectedBack) return new Cubicle3D(XCoordinate, YCoordinate-1, ZCoordinate);
			break;
		case UP:
			if(connectedUp) return new Cubicle3D(XCoordinate, YCoordinate, ZCoordinate+1);
			break;
		case DOWN:
			if(connectedDown) return new Cubicle3D(XCoordinate, YCoordinate, ZCoordinate-1);
			break;
		}
		return null;
	}

	public boolean equals(Cubicle3D c1) {
		return c1.XCoordinate == this.XCoordinate && c1.YCoordinate == this.YCoordinate && c1.ZCoordinate == this.ZCoordinate;
	}
}
