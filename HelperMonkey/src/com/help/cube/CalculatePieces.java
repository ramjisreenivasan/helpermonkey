package com.help.cube;

import com.help.cube2d.model.CubicleStructure;

public class CalculatePieces {

	public static void main(String[] args) {
		CubicleStructure cubeStruct = new CubicleStructure();
		cubeStruct.initialize(5, 5);
		cubeStruct.labelAllPieces();
		cubeStruct.display();
		System.out.println("\n");
		cubeStruct.prettyDisplay();
	}

}
