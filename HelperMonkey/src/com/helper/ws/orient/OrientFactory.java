package com.helper.ws.orient;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.helper.ws.model.GridOrientation;

public class OrientFactory {
	final static Map<GridOrientation, Supplier<IOrient>> map = new HashMap<>();
	static {
		map.put(GridOrientation.LEFT_TO_RIGHT, OrientLtoR::new);
		map.put(GridOrientation.RIGHT_TO_LEFT, OrientRtoL::new);
		map.put(GridOrientation.TOP_TO_BOTTOM, OrientTtoB::new);
		map.put(GridOrientation.BOTTOM_TO_TOP, OrientBtoT::new);
		map.put(GridOrientation.TL_TO_BR, OrientTLtoBR::new);
		map.put(GridOrientation.BR_TO_TL, OrientBRtoTL::new);
		map.put(GridOrientation.TR_TO_BL, OrientTRtoBL::new);
		map.put(GridOrientation.BL_TO_TR, OrientBLtoTR::new);
	}

	public IOrient getOrient(GridOrientation orientType) {
		Supplier<IOrient> orient = map.get(orientType);
		if (orient != null) {
			return orient.get();
		}
		throw new IllegalArgumentException("No such orientation " + orientType);
	}
}
