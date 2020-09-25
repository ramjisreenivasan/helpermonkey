package com.helper.ws;

public class TableBorder {
	public static final char[] EX_DEC = {9484, 9472, 9516, 9488, 9474, 9500, 9532, 9508, 9492, 9524, 9496};
	
	public static final String SINGLE_TOP_LEFT = new String(Character.toChars(EX_DEC[0]));
	public static final String SINGLE_HLINE = new String(Character.toChars(EX_DEC[1]));
	public static final String SINGLE_TOP_DIV = new String(Character.toChars(EX_DEC[2]));
	public static final String SINGLE_TOP_RIGHT = new String(Character.toChars(EX_DEC[3]));
	public static final String SINGLE_VLINE = new String(Character.toChars(EX_DEC[4]));
	public static final String SINGLE_LEFT_DIV = new String(Character.toChars(EX_DEC[5]));
	public static final String SINGLE_MID_DIV = new String(Character.toChars(EX_DEC[6]));
	public static final String SINGLE_RIGHT_DIV = new String(Character.toChars(EX_DEC[7]));
	public static final String SINGLE_BOTTOM_LEFT = new String(Character.toChars(EX_DEC[8]));
	public static final String SINGLE_BOTTOM_DIV = new String(Character.toChars(EX_DEC[9]));
	public static final String SINGLE_BOTTOM_RIGHT = new String(Character.toChars(EX_DEC[10]));
	public static final String SPACE = " ";
	public static final String WORD_START = "*";
	public static final String WORD_END = "*";
//	public static final String SLASH = new String(Character.toChars(0XFF0F));
//	public static final String BACKSLASH = new String(Character.toChars(0XFF3C));
	public static final String SLASH = "/";
	public static final String BACKSLASH = "\\";
}
