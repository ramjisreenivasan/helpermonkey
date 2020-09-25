package com.helper.ws.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum GridOrientation {
	LEFT_TO_RIGHT (1, 1, 0, 0),
	RIGHT_TO_LEFT (2,-1, 0, 1),
	TOP_TO_BOTTOM (1, 0, 1, 2),
	BOTTOM_TO_TOP (2, 0,-1, 3),
	TL_TO_BR (3, 1, 1, 4),
	BL_TO_TR (3, 1,-1, 5),
	TR_TO_BL (3,-1, 1, 6),
	BR_TO_TL (4,-1,-1, 7);
	
	public final int difficulty;
	public final int horizontal;
	public final int vertical;
	public final int order;
	
	GridOrientation(int diff, int h, int v, int o) {
		this.difficulty = diff;
		this.horizontal = h;
		this.vertical = v;
		this.order = o;
	}
	
	public static GridOrientation getRandom(int diff) {
		return getRandom(diff, true);
	}

	public static GridOrientation getRandom(int diff, boolean valid) {
		List<GridOrientation> l1 = Arrays.asList(GridOrientation.values());
		l1 = l1.stream()
				.filter(o -> o.difficulty <= diff)
				.filter(o -> (valid && (o.horizontal==-1 || o.vertical==-1)) || (!valid && (o.horizontal==1 || o.vertical==1)))
				.collect(Collectors.toList());
		return l1.get((int) (Math.random()*l1.size()));
	}

	public static List<GridOrientation> getDifficultyList(int diff) {
		List<GridOrientation> l1 = Arrays.asList(GridOrientation.values());
		l1 = l1.stream()
				.filter(o -> o.difficulty <= diff)
				.sorted((o1, o2) -> o1.order-o2.order)
				.collect(Collectors.toList());
		return l1;
	}
}
