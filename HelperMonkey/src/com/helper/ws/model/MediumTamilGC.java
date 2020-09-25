package com.helper.ws.model;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MediumTamilGC extends GridConfig {
	String[][] tamilLetters = {
			{"அ","ஆ","இ","ஈ","உ","ஊ","எ","ஏ","ஐ","ஒ","ஓ","ஔ"},
			{"க","ங","ச","ஜ","ஞ","ட","ண","த","ந","ன","ப","ம","ய","ர","ற","ல","ள","ழ","வ","ஶ","ஷ","ஸ","ஹ"},
			{"்","ா","ி","ீ","ு","ூ","ெ","ே","ை","ொ","ோ"}
	};

	public MediumTamilGC() {
		this.GRID_LENGTH=12;
		this.GRID_WIDTH=12;
		this.GRID_DIFFICULTY=4;
		
		String words = new String("ராம்ஜி,ஸ்ரீதேவி,வாசுப்பிரதா,அபிநந்தன்".getBytes(), Charset.forName("UTF-8"));
		
		String[] wordList = words.split(",");
		
		Arrays.asList(wordList).stream().forEach((item) -> {
			System.out.println("WORD: " + item);
			System.out.println("Length: " + item.length());
			System.out.println("Starts with: " + item.charAt(0) + item.codePointAt(0));
			System.out.println("next: " + item.charAt(1) + item.codePointAt(1));
			System.out.println("next: " + item.charAt(2) + item.codePointAt(2));
			System.out.println("Ends with: " + item.charAt(item.length()-1));
		});
		
		String[][] configList = new String[wordList.length][];
		
		for(int idx=0; idx<wordList.length; idx++) {
			String wordItem = wordList[idx];
			
			configList[idx] = new String[wordItem.length()];
			
			for(int jdx=0; jdx<wordItem.length(); jdx++) {
				configList[idx][jdx] = wordItem.charAt(jdx)+"";
			}
		}
		
		String[] tmp1 = {
				"ராம்ஜி",
				"ஸ்ரீதேவி",
				"வசுப்பிரதா",
				"அபிநந்தன்"
			};
		
		String[][] tmp2 = {
				{"ரா","ம்","ஜி"},
				{"ஸ்ரீ","தே","வி"},
				
			};

		String[][] tmp = new String[tmp1.length][];
		
		for(int idx=0; idx<tmp1.length; idx++) {
			tmp[idx] = getLetters(tmp1[idx]);
		}
		
		this.WORDS = tmp;
		
	}
	
	public String[] getLetters(String word) {
		List<String> listLetters = new ArrayList<String>();
		
		List<String> umLetters = Arrays.asList(tamilLetters[2]);
		
		int idx=0;
		while(idx<word.length()) {
			String lett = word.charAt(idx) +"";
			if(lett.equals(tamilLetters[1][21]+"") 
					&& (word.charAt(idx+1)+"").equals(tamilLetters[2][0])
					&& (word.charAt(idx+2)+"").equals(tamilLetters[1][13])
					&& umLetters.contains(word.charAt(idx+3)+"") ) {
				lett += word.charAt(++idx);
				lett += word.charAt(++idx);
				lett += word.charAt(++idx);
			} else if(umLetters.contains(word.charAt(idx+1)+"")) {
				lett += word.charAt(++idx);
			}
			listLetters.add(lett);
			idx++;
		}
		
		String[] str1 = new String[listLetters.size()];

		for(idx=0; idx<listLetters.size(); idx++) {
			str1[idx] = listLetters.get(idx);
		}
		
		return str1;
	}

	@Override
	public String getRandomLetter() {
		// TODO Auto-generated method stub

		String letter = null;
		int cat = (int)(Math.random()*4);
		if(cat == 0) {
			letter = tamilLetters[cat][(int)(Math.random()*(tamilLetters[cat].length))];
		} else if (cat == 2) {
			letter = tamilLetters[1][(int)(Math.random()*(tamilLetters[1].length))] 
					+ tamilLetters[cat][(int)(Math.random()*(tamilLetters[cat].length))];
		} else {
			letter = tamilLetters[1][(int)(Math.random()*(tamilLetters[1].length))]
							+ tamilLetters[2][0];
		}
		
		return letter;
	}
}
