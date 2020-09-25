package com.helper.ws;

public class ExtendedAscii {

	/*
		ASCII code 179 = │ ( Box drawing character single vertical line )
		ASCII code 180 = ┤ ( Box drawing character single vertical and left line )
		ASCII code 191 = ┐ ( Box drawing character single line upper right corner )
		ASCII code 192 = └ ( Box drawing character single line lower left corner )
		ASCII code 193 = ┴ ( Box drawing character single line horizontal and up )
		ASCII code 194 = ┬ ( Box drawing character single line horizontal down )
		ASCII code 195 = ├ ( Box drawing character single line vertical and right )
		ASCII code 196 = ─ ( Box drawing character single horizontal line )
		ASCII code 197 = ┼ ( Box drawing character single line horizontal vertical )
		ASCII code 217 = ┘ ( Box drawing character single line lower right corner )
		ASCII code 218 = ┌ ( Box drawing character single line upper left corner )
		*/
	
			public static final char[] EXTENDED = { 
            0x00E4, 0x00E0, 0x00E5, 0x00E7, 0x00EA, 0x00EB, 0x00E8, 0x00EF,
            0x00EE, 0x00EC, 0x00C4, 0x00C5, 0x00C9, 0x00E6, 0x00C6, 0x00F4,
            0x00F6, 0x00F2, 0x00FB, 0x00F9, 0x00FF, 0x00D6, 0x00DC, 0x00A2,
            0x00A3, 0x00A5, 0x20A7, 0x0192, 0x00E1, 0x00ED, 0x00F3, 0x00FA,
            0x00F1, 0x00D1, 0x00AA, 0x00BA, 0x00BF, 0x2310, 0x00AC, 0x00BD,
            0x00BC, 0x00A1, 0x00AB, 0x00BB, 0x2591, 0x2592, 0x2593, 0x2502,
            0x2524, 0x2561, 0x2562, 0x2556, 0x2555, 0x2563, 0x2551, 0x2557,
            0x255D, 0x255C, 0x255B, 0x2510, 0x2514, 0x2534, 0x252C, 0x251C,
            0x2500, 0x253C, 0x255E, 0x255F, 0x255A, 0x2554, 0x2569, 0x2566,
            0x2560, 0x2550, 0x256C, 0x2567, 0x2568, 0x2564, 0x2565, 0x2559,
            0x2558, 0x2552, 0x2553, 0x256B, 0x256A, 0x2518, 0x250C, 0x2588,
            0x2584, 0x258C, 0x2590, 0x2580, 0x03B1, 0x00DF, 0x0393, 0x03C0,
            0x03A3, 0x03C3, 0x00B5, 0x03C4, 0x03A6, 0x0398, 0x03A9, 0x03B4,
            0x221E, 0x03C6, 0x03B5, 0x2229, 0x2261, 0x00B1, 0x2265, 0x2264,
            0x2320, 0x2321, 0x00F7, 0x2248, 0x00B0, 0x2219, 0x00B7, 0x221A,
            0x207F, 0x00B2, 0x25A0, 0x00A0 };
			
			public static final char[] EX_DEC = {9484, 9472, 9516, 9488, 9474, 9500, 9532, 9508, 9492, 9524, 9496};

    public static void main(String[] args) {
    	int idx=0;
        for (char c : EX_DEC) {
            System.out.printf("%d = %s,", (int)c, new String(Character.toChars(c)));
            idx++;
            if(idx%8==0) System.out.println();
        }
        System.out.println();
        
        System.out.println((new String(Character.toChars(EX_DEC[0])))+
        		(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+
        				(new String(Character.toChars(EX_DEC[2])))+
        				(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+
        								(new String(Character.toChars(EX_DEC[3]))));
        System.out.println((new String(Character.toChars(EX_DEC[4])))+
        		(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+
        				(new String(Character.toChars(EX_DEC[4])))+
        				(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+
        								(new String(Character.toChars(EX_DEC[4]))));
        System.out.println((new String(Character.toChars(EX_DEC[5])))+
        		(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+
        				(new String(Character.toChars(EX_DEC[6])))+
        				(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+
        								(new String(Character.toChars(EX_DEC[7]))));
        System.out.println((new String(Character.toChars(EX_DEC[4])))+
        		(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+
        				(new String(Character.toChars(EX_DEC[4])))+
        				(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+
        								(new String(Character.toChars(EX_DEC[4]))));
        System.out.println((new String(Character.toChars(EX_DEC[8])))+
        		(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+
        				(new String(Character.toChars(EX_DEC[9])))+
        				(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+
        								(new String(Character.toChars(EX_DEC[10]))));

        System.out.println("\033[0m BLACK");
        System.out.println("\033[31m RED");
        System.out.println("\033[32m GREEN");
        System.out.println("\033[33m YELLOW");
        System.out.println("\033[34m BLUE");
        System.out.println("\033[35m MAGENTA");
        System.out.println("\033[36m CYAN");
        System.out.println("\033[37m WHITE");  
        
        System.out.println((char)27 + "[31mThis text would show up red" + (char)27 + "[0m");
    }
}