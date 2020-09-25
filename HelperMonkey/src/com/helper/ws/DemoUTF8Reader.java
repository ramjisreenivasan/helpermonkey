package com.helper.ws;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class DemoUTF8Reader {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		File f = new File("C:\\Ramji\\Tamil-Story.txt");
		Reader decoded = new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8);
		//bufferedWriter = new BufferedWriter (new OutputStreamWriter(System.out, "UTF8"));
		//BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8));
		char[] buffer = new char[100];
		int n = 0;
		StringBuilder build = new StringBuilder();
		int itr = 0;
		while (true) {
			try {
				n = decoded.read(buffer);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (n < 0) {
				break;
			}
			itr++;
			build.append(buffer, 0, n);
//			System.out.println(buffer);
		}
		decoded.close();
		
//		System.out.println("Itr::" + itr);
//		System.out.println(build.toString());
		
		String str1 = "ஒரு விறகு வெட்டி இருந்தான்";
		
		
//		System.out.println("New string ::: " + str1 + "Len: " + str1.length());
//		System.out.println(str1.charAt(0) + "-");
//		System.out.println(str1.charAt(1) + "-");
//		System.out.println(str1.charAt(2) + "-");
//		System.out.println(str1.substring(1, 3) + "-");
		
		String[][] tamilLetters = {
				{"அ","ஆ","இ","ஈ","உ","ஊ","எ","ஏ","ஐ","ஒ","ஓ","ஔ"},
				{"க","ங","ச","ஜ","ஞ","ட","ண","த","ந","ன","ப","ம","ய","ர","ற","ல","ள","ழ","வ","ஶ","ஷ","ஸ","ஹ"},
				{"்","ா","ி","ீ","ு","ூ","ெ","ே","ை","ொ","ோ","ௌ"}
		};
		
		int idx=200;
		while(idx-->0) {
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
			
			System.out.print(letter+"\t");
			
			if(idx%20==0) {
				System.out.println();
			}
		}
		
		
	}

}
