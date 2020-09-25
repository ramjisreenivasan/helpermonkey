package com.help.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoolFeature {
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int len = 10000;
/*
		a: [13, 90, 49, 57, 47, 67, 35, 6, 4, 97]
				b: [56, 96, 100, 0, 50, 54, 14, 24, 81, 50]
				query:
				[[1,18], 
				 [1,148], 
				 [1,147], 
				 [1,153], 
				 [1,123], 
				 [1,103], 
				 [1,130], 
				 [1,151], 
				 [1,148], 
				 [1,85]]
*/						 
		CoolFeature cf = new CoolFeature();
		int[] a = new int[len];
		int[] b = new int[len]; //{56, 96, 100, 0, 50, 54, 14, 24, 81, 50};
		for(int idx=0; idx<len; idx++) {
			a[idx] = idx + 1;
		}
		for(int idx=0; idx<len; idx++) {
			b[idx] = idx*2 + 1;
		}

		int[][] q = new int[len][]; 
		
		for(int idx=0; idx<len; idx++) {
			int[] tmp1 = new int[2];
			tmp1[0] = 1;
			tmp1[1] = len/2 + idx;
			q[idx] = tmp1;
		}
		
		long st = System.currentTimeMillis();
		int[] rVal = cf.coolFeature(a,b,q);
		long ed = System.currentTimeMillis();
		
		System.out.println("Time : " + (ed - st));
		
		System.out.println("Result: " + rVal.length);
		for(int iVal:rVal) {
			//System.out.print(iVal + ", ");
		}
	}

	Map<Integer, List<Integer>> pupulateQueryMap(int[] a, int[] b) {
		Map<Integer, List<Integer>> queryMap = new HashMap<Integer, List<Integer>>();
		for(int aItem : a) {
			for (int bItem: b) {
				Integer sum = new Integer(aItem+bItem);
				if(queryMap.containsKey(sum)) {
					queryMap.get(sum).add(sum);
				} else {
					List<Integer> l1 = new ArrayList<Integer>();
					l1.add(sum);
					queryMap.put(sum, l1);
				}
			}
		}
		return queryMap;
	}
	
	int[] coolFeature(int[] a, int[] b, int[][] query) {
		
		int actRetLen  = 0;
		for(int[] item : query) {
			if(item[0]==1) actRetLen++;
		}
		
		int[] returnVal = new int[actRetLen];

		Map<Integer, List<Integer>> queryMap = pupulateQueryMap(a, b);
		
		int rCnt = 0;
		for(int[] item : query) {
			if(item[0]==1) {
				int cnt = 0;
//				for(int aItem : a) {
//					for (int bItem: b) {
//						if(aItem + bItem == item[1]) {
//							cnt++;
//						}
//					}
//				}
				Integer i1 = new Integer(item[1]);
				if(queryMap.containsKey(i1)) {
					cnt = queryMap.get(i1).size();
				}
				returnVal[rCnt++] = cnt;
			}else if (item[0]==0){
				b[item[1]] = item[2];
				queryMap = pupulateQueryMap(a, b);
			}
		}

		return returnVal;
	}
	
}
