package com.helper.ws;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.helper.ws.model.GridConfig;
import com.helper.ws.model.GridFitment;
import com.helper.ws.model.GridLetter;
import com.helper.ws.model.GridLocation;
import com.helper.ws.model.GridOrientation;
import com.helper.ws.model.GridValidity;
import com.helper.ws.model.GridWord;
import com.helper.ws.model.HardGC;
import com.helper.ws.model.MediumGC;
import com.helper.ws.model.MediumTamilGC;
import com.helper.ws.model.TestGC;
import com.helper.ws.model.WordLetter;
import com.helper.ws.orient.OrientFactory;

class WSLabel extends JLabel {
	GridLetter gl;
	public WSLabel(GridLetter gl) {
		super(gl.letter, JLabel.CENTER);
		
		setFont(new Font("Courier", Font.PLAIN, 20));

		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		this.gl = gl;
		setSize(200, 200);
		//setBorder(border);
		setVerticalAlignment(JLabel.CENTER);
		setMinimumSize(new Dimension(50, 50));
		setMaximumSize(new Dimension(100, 100));
		setPreferredSize(new Dimension(80, 80));
	}
}

class WSInput extends JPanel {
	public WSInput() {
		super();
        setMinimumSize(new Dimension(500, 250));
        setMaximumSize(new Dimension(600, 300));
		
        setLayout(new GridLayout(10,1));
        
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		setBorder(border);
		
		Arrays.asList(WordSearch1.WORDS).stream()
			.forEach((item) -> {
				JLabel labelWord = new JLabel(item[0]);
				labelWord.setSize(600, 50);
				labelWord.setBorder(border);
				//labelWord.setBorder(new EmptyBorder(0,10,0,0));
				
				labelWord.addMouseListener(new MouseAdapter(){
				    @Override
				    public void mouseEntered(MouseEvent e) {
				    	labelWord.setForeground(Color.GREEN);
				    }
				    @Override
				    public void mouseExited(MouseEvent e) {
				    	labelWord.setForeground(Color.BLUE);
				    }
				});
				
				add(labelWord);
			});
	}
}

class WSComponent extends JPanel {

	public WSComponent(int gRID_LENGTH, int gRID_WIDTH) {
		super();
        setMinimumSize(new Dimension(gRID_LENGTH*100,gRID_WIDTH*100));
        setMaximumSize(new Dimension(gRID_LENGTH*100,gRID_WIDTH*100));
		// Setting Title to the JFrame GUI
		setName("Grid Layout Test Java Code");
		// Setting grid  layout to JFrame GUI
		setLayout(new GridLayout(gRID_LENGTH, gRID_WIDTH));
		// Adding buttons using Grid Layout

		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		setBorder(border);
		
		Arrays.asList(WordSearch1.GRID_ITEMS).stream()
			.forEach((item) -> {
				Arrays.asList(item).stream()
						.filter((o) -> o!=null && o.letter!=null)
						.forEach((obj) -> {
							System.out.print(obj.letter);
							
							WSLabel label = new WSLabel(obj);
							label.setVisible(true);
							add(label);
						});
			});
		
	}

}

public class WordSearch1  {
	
	public int GRID_LENGTH;
	public int GRID_WIDTH;
	public int GRID_DIFFICULTY;
	public int ITER_CHECKSPACE;
	
	public static int GRID_ID;
	
	public GridConfig currGC;
	
	public static String[][] WORDS;
	
	public static GridLetter[][] GRID_ITEMS = new GridLetter[50][50];
	
	public static final char[] EX_DEC = {9484, 9472, 9516, 9488, 9474, 9500, 9532, 9508, 9492, 9524, 9496};
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		WordSearch1 ws1 = new WordSearch1();
	}
	
	public WordSearch1() {

		//String inputWordList = "TIGER,LION,WOLF,GOOSE";
		
		//PopulateConfig
		try {
			populateConfigs(new TestGC());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			populateConfigs(new MediumTamilGC());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//ws1.populateConfigs(new HardGC());
		
		String[][] listWords = WORDS;
		GRID_ID = 100000 + (int)(100 * Math.random());
		
		// Initialize the grid
		initGrid();
		
		//Populate the grid
		List<GridWord> gwList = new ArrayList<GridWord>();
		for(String[] word : listWords) {
			GridWord gw = new GridWord(word);
			gwList.add(gw);

			// Add deformed words
			int len = word.length;
			//String word1 = (len>6?word.substring(0,len-((int)len/3)):shuffleWord(word));
			//GridWord gw1 = new GridWord(word1, GridValidity.FRAGMENTED);
			//gwList.add(gw1);
			
		}
		
		gwList = gwList.stream()
				.sorted(Comparator.comparing(GridWord::isValid,Comparator.reverseOrder()))
				.sorted((o1,o2)-> o2.word.length-o1.word.length)
						.collect(Collectors.toList());
		
		populateGrid(gwList);
		
		printWords(listWords);

		// Populate the remaining places
		populateRest();
		
		System.out.println();
		System.out.println();
		
		printGrid5();
		System.out.println("\n\n\n\n\n");
		//printGrid();
		
		System.out.println();
		
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
//            ex.printStackTrace();
//        }
//
//    	WSComponent wsComponent = new WSComponent(GRID_LENGTH, GRID_WIDTH);
//    	
//		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
//    	
//        JFrame frame = new JFrame("Testing");
//        frame.setLayout(new GridLayout(2, 1));
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(600, 800);
//        frame.add(new WSInput());
//        frame.add(wsComponent);
//        //frame.pack();
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);

	}
	
	private String shuffleWord(String word) {
		String s = word.substring(word.length()-2);
		return word.substring(0,word.length()-2).concat(s.charAt(1)+"").concat(s.charAt(0)+"");
	}
	
	public void populateConfigs(GridConfig gc) throws UnsupportedEncodingException {
		this.GRID_LENGTH = gc.GRID_LENGTH;
		this.GRID_WIDTH = gc.GRID_WIDTH;
		this.GRID_DIFFICULTY = gc.GRID_DIFFICULTY;
		this.WORDS = gc.WORDS;
		
		this.currGC = gc;
		
		//new String(gc.WORDS.getBytes(Charset.forName("UTF-8")), Charset.forName("UTF-8")); // 
	}

	public void populateRest() {
		
		for(int idx=0; idx<this.GRID_WIDTH; idx++) {
			for(int jdx=0; jdx<this.GRID_LENGTH; jdx++) {
				if(WordSearch1.GRID_ITEMS[idx][jdx].letter.equals(GridLetter.DEFAULT)) {
					// Populate with any random letter
					
					GridLetter gLetter = WordSearch1.GRID_ITEMS[idx][jdx];
					
					gLetter.letter = this.currGC.getRandomLetter();
					
					WordSearch1.GRID_ITEMS[idx][jdx] = gLetter;
					
				}
			}
		}
	}	
	
	public void printWords(String[][] listWords) {
		System.out.println();
		System.out.println();
		System.out.println("GRID: " + GRID_ID);
		Arrays.asList(listWords).stream().forEachOrdered((word) -> {
			Arrays.asList(word).stream().forEachOrdered((letter -> {
				System.out.print(letter);
			}));
			System.out.print(", ");
		});
		System.out.println();
		System.out.println();
	}
	
	public void populateGrid(List<GridWord> listWords) {
		listWords.forEach(gw -> {
			populateWord(gw, false, false);
		});
		
		printGrid4();

		//Populate jumbled words
		listWords.forEach(gw -> {
			GridWord gw1 = new GridWord(getFalseWord(gw.word.clone()), GridValidity.JUMBLED);
			populateWord(gw1, true, false);
			gw.falseWords.add(gw1);
			gw.countMisspelled++;
		});

		//Populate fragments words
		listWords.forEach(gw -> {
			IntStream.iterate(0, i -> i + 1).limit(2).forEach(i -> {
				// Populate FRAGMENTED word
				String[] frag1 = new String[gw.word.length-1]; //.substring(0, gw.word.length() - 1);
				for(int idx=0; idx<gw.word.length-1; idx++) {
					frag1[idx] = gw.word[idx];
				}
				
				GridWord gw2 = new GridWord(frag1, GridValidity.FRAGMENTED);
				populateWord(gw2, true, true);
				gw.countFragments++;
				gw.falseWords.add(gw2);
			});
		});
	
	}
	
	public String[] getFalseWord(String[] word) {
//		String str1 = word.substring(0,  word.length()-2);
//		String str2 = word.substring(word.length()-2);
//		String str3 = str1.concat(str2.charAt(1)+"").concat(str2.charAt(0)+"");
//		return str3;
		String tmp = word[word.length-1];
		word[word.length-1] = word[word.length-2];
		word[word.length-2] = tmp;
		return word;
	}
	
	public String printWord(String[] item) {
		String retVal = "";
		for(String s : item) {
			retVal += s;
		}
		
		return retVal;
	}
	
	public void populateWord(GridWord gw, boolean nested, boolean allowReplacements) {
			//GridWord gw = new GridWord(word);
			//if(gw.isValid()) 
			//System.out.println("Populating :: " + printWord(gw.word) + " - nested: " + nested);
			int gridDiff = GRID_DIFFICULTY;
			
			findWordLocations(gw);
			//markLocations(gw);
			
			// Populate the word
			GridOrientation orient = GridOrientation.getRandom(gridDiff, gw.isValid());
			int itr = orient.order;
			List<GridOrientation> orientList = GridOrientation.getDifficultyList(gridDiff);
			int olSize = orientList.size();
			int olLimit = itr+olSize;
			while(!checkLocs(gw, orient)) {
				orient = orientList.get(itr++%olSize);
				if(itr>olLimit) {
					System.err.println("Not populated - orient: "+ printWord(gw.word));
					if(gw.isValid()) return;
					break;
				}
			}
			
			List<GridLocation> listGL = gw.getFitments(orient).stream()
					.map((item)-> item.gl).collect(Collectors.toList());

			//int iSize = listGL.size();
			//int posIdx = new Random().nextInt(iSize);
			
			// Get a weighted random
			GridLocation gl;
			RandomCollection<GridLocation> collGL = new RandomCollection<GridLocation>();
			listGL.stream().filter((o) -> o.score > 0)
					.forEach(item -> {
					collGL.add(gw.isValid()?item.score:100, item);
				});
				
			if(!collGL.isEmpty()) {	
				gl = collGL.next();
				
				gw.wordGL = gl;
				gw.wordGO = orient;
				
				Supplier<OrientFactory> orientFactory =  OrientFactory::new;
				orientFactory.get().getOrient(orient).populateGridWord(gw, gl.xCoord, gl.yCoord, allowReplacements);
				//printGrid3();
				
//				if (!nested) {
//					IntStream.iterate(0, i -> i + 1).limit(2).forEach(i -> {
//						// Populate FRAGMENTED word
//						String[] frag1 = new String[gw.word.length-1]; //.substring(0, gw.word.length() - 1);
//						for(int idx=0; idx<gw.word.length-1; idx++) {
//							frag1[idx] = gw.word[idx];
//						}
//						
//						GridWord gw2 = new GridWord(frag1, GridValidity.FRAGMENTED);
//						populateWord(gw2, true, allowReplacements);
//						gw.countFragments++;
//						gw.falseWords.add(gw2);
//					});
//				}
			} else {
				System.err.println("Not populated: "+ printWord(gw.word));
			}
	}
	
	public boolean checkLocs(GridWord gw, GridOrientation orient) {
		return gw.getFitments(orient).size() > 0;
	}
	
	public void populateGridWord1(String word, int xVal, int yVal, GridOrientation orient) {
//		//Populate grid
//		if(orient.equals(GridOrientation.LEFT_TO_RIGHT)) {
//			for(int idx=0; idx<word.length(); idx++) {
//				WordSearch1.GRID_ITEMS[yVal][xVal++] = word.charAt(idx)+"";
//			}
//		} else if(orient.equals(GridOrientation.RIGHT_TO_LEFT)) {
//			for(int idx=0; idx<word.length(); idx++) {
//				WordSearch1.GRID_ITEMS[yVal][xVal--] = word.charAt(idx)+"";
//			}
//		} else if(orient.equals(GridOrientation.TOP_TO_BOTTOM)) {
//			for(int idx=0; idx<word.length(); idx++) {
//				WordSearch1.GRID_ITEMS[yVal++][xVal] = word.charAt(idx)+"";
//			}
//		} else if(orient.equals(GridOrientation.BOTTOM_TO_TOP)) {
//			for(int idx=0; idx<word.length(); idx++) {
//				WordSearch1.GRID_ITEMS[yVal--][xVal] = word.charAt(idx)+"";
//			}
//		} else {
//			System.out.println("UNKONWN");
//		}
	}
	
	public void markLocations(GridWord gw) {
		//for(GridOrientation orient : gw.possibleLocations.keySet()) {
			//List<GridLocation> listGL = gw.possibleLocations.get(orient);
			List<GridLocation> listGL = gw.possibleFitments.stream()
					.map((item) -> item.gl).collect(Collectors.toList());
			
			for(int idx=0; idx<this.GRID_WIDTH; idx++) {
				for(int jdx=0; jdx<this.GRID_LENGTH; jdx++) {
					if(listGL.contains(new GridLocation(jdx, idx))) {
						System.out.print(" X ");
					} else {
						System.out.print(" - ");
					}
				}
				System.out.println();
			}
			System.out.println();
		//}
	}

	public void findWordLocations(GridWord gw) {
		//Populate locMap
		for(GridOrientation orient : Arrays.asList(GridOrientation.values())) {
			//System.out.println("Orient:"+orient);
			Supplier<OrientFactory> orientFactory =  OrientFactory::new;
			
			orientFactory.get().getOrient(orient)
				.findWordLocations(gw, GRID_LENGTH, GRID_WIDTH)
				.forEach((gl) -> {
					//gw.possibleLocations.get(orient).add(gl);
					gw.possibleFitments.add(new GridFitment(gl, orient));
				});
		}		
	}
	
	public void populateGrid1(List<String> listWords) {
//		listWords.forEach(word -> {
//			// Find a orientation
//			GridOrientation orient = GridOrientation.getRandom(GRID_DIFFICULTY);
//			
//			while(!checkOrient(orient, word)) {
//				orient = GridOrientation.getRandom(GRID_DIFFICULTY);
//			}
//
//			// Find X,Y position
//			int xLow = orient.horizontal == -1?word.length():1;
//			int yLow = orient.vertical == -1?word.length():1;
//			int xHigh = orient.horizontal == 1?this.GRID_LENGTH - word.length()+1:this.GRID_LENGTH;
//			int yHigh = orient.vertical == 1?this.GRID_WIDTH - word.length()+1:this.GRID_WIDTH;
//
//			System.out.println("WORD:: " + word + "-" + orient);
//			System.out.println(xLow + " to " + xHigh);
//			System.out.println(yLow + " to " + yHigh);
//			System.out.println();
//
//			int xVal = (xHigh == xLow? xLow: xLow + new Random().nextInt(xHigh-xLow))-1;
//			int yVal = (yHigh == yLow? yLow: yLow + new Random().nextInt(yHigh-yLow))-1;
//			
//			int itr = 0;
//			while(!checkSpace(orient, word, xVal, yVal, itr++)) {
//				//orient = GridOrientation.getRandom(GRID_DIFFICULTY);
//				
//				// Find X,Y position
//				xLow = orient.horizontal == -1?word.length():1;
//				yLow = orient.vertical == -1?word.length():1;
//				xHigh = orient.horizontal == 1?this.GRID_LENGTH - word.length()+1:this.GRID_LENGTH;
//				yHigh = orient.vertical == 1?this.GRID_WIDTH - word.length()+1:this.GRID_WIDTH;
//				
//				xVal = (xHigh == xLow? xLow: xLow + new Random().nextInt(xHigh-xLow))-1;
//				yVal = (yHigh == yLow? yLow: yLow + new Random().nextInt(yHigh-yLow))-1;
//			}
//			
//			if(itr <= ITER_CHECKSPACE) {
//				System.out.println("****** FIXED ********");
//				System.out.println("WORD:: " + word + "-" + orient);
//				System.out.println(xVal + "::"+ xLow + " to " + xHigh);
//				System.out.println(yVal + "::"+ yLow + " to " + yHigh);
//				System.out.println();
//	
//				//Populate grid
//				if(orient.equals(GridOrientation.LEFT_TO_RIGHT)) {
//					for(int idx=0; idx<word.length(); idx++) {
//						WordSearch1.GRID_ITEMS[yVal][xVal++] = word.charAt(idx)+"";
//					}
//				} else if(orient.equals(GridOrientation.RIGHT_TO_LEFT)) {
//					for(int idx=0; idx<word.length(); idx++) {
//						WordSearch1.GRID_ITEMS[yVal][xVal--] = word.charAt(idx)+"";
//					}
//				} else if(orient.equals(GridOrientation.UP_TO_DOWN)) {
//					for(int idx=0; idx<word.length(); idx++) {
//						WordSearch1.GRID_ITEMS[yVal++][xVal] = word.charAt(idx)+"";
//					}
//				} else if(orient.equals(GridOrientation.DOWN_TO_UP)) {
//					for(int idx=0; idx<word.length(); idx++) {
//						WordSearch1.GRID_ITEMS[yVal--][xVal] = word.charAt(idx)+"";
//					}
//				} else {
//					System.out.println("UNKONWN");
//				}
//			}
//		}
//		);
	}
	
	public boolean checkOrient(GridOrientation orient, String word) {
		
		return true;
	}
	
	public boolean checkSpace1(GridOrientation orient, String word, int xVal, int yVal, int itr) {
//		if(itr > ITER_CHECKSPACE) {
//			System.out.println(word + " :: NOT Done");
//			return true;
//		}
//		
//		try {
//			if(orient.equals(GridOrientation.LEFT_TO_RIGHT)) {
//				for(int idx=0; idx<word.length(); idx++) {
//					String str1 = WordSearch1.GRID_ITEMS[yVal][xVal++];
//					if(!str1.equals("-") && !str1.equals(word.charAt(idx)+"")) return false;
//				}
//			} else if(orient.equals(GridOrientation.RIGHT_TO_LEFT)) {
//				for(int idx=0; idx<word.length(); idx++) {
//					String str1 = WordSearch1.GRID_ITEMS[yVal][xVal--];
//					if(!str1.equals("-") && !str1.equals(word.charAt(idx)+"")) return false;
//				}
//			} else if(orient.equals(GridOrientation.TOP_TO_BOTTOM)) {
//				for(int idx=0; idx<word.length(); idx++) {
//					String str1 = WordSearch1.GRID_ITEMS[yVal++][xVal];
//					if(!str1.equals("-") && !str1.equals(word.charAt(idx)+"")) return false;
//				}
//			} else if(orient.equals(GridOrientation.BOTTOM_TO_TOP)) {
//				for(int idx=0; idx<word.length(); idx++) {
//					String str1 = WordSearch1.GRID_ITEMS[yVal--][xVal];
//					if(!str1.equals("-") && !str1.equals(word.charAt(idx)+"")) return false;
//				}
//			}
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
		return true;
	}

	public void printGrid1() {
		for(int idx=0; idx<this.GRID_WIDTH; idx++) {
			Arrays.stream(WordSearch1.GRID_ITEMS[idx]).filter((o1) -> o1 != null).forEach(item -> System.out.print(item+" "));
			System.out.println();
		}
	}

	public void printGrid2() {
//		for(int val=150; val<225; val++) {
//			System.out.println(val+"="+(char)val);
//		}
//		System.out.println();
		
		for(int idx=0; idx<this.GRID_WIDTH; idx++) {
			for(int jdx=0; jdx<GRID_LENGTH; jdx++) {
				System.out.print(" --- ");
			}
			System.out.println();
			Arrays.stream(WordSearch1.GRID_ITEMS[idx]).filter((o1) -> o1 != null)
				.forEach(item -> System.out.print("| " + item + " |"));
			System.out.println();
			for(int jdx=0; jdx<GRID_LENGTH; jdx++) {
				System.out.print(" --- ");
			}
			System.out.println();
		}
	}

	public void printGrid3() {
		System.out.println("Grid occupancy: " + getGridOccupancy() + "  :: Valid: " + getGridOccupancy(true));
		System.out.print((new String(Character.toChars(EX_DEC[0]))));
		for(int jdx=0; jdx<GRID_LENGTH; jdx++) {
			if (jdx == GRID_LENGTH-1){
				System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
				System.out.print((new String(Character.toChars(EX_DEC[3]))));
			} else {
				System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
				System.out.print((new String(Character.toChars(EX_DEC[2]))));
			}
		}
		System.out.println();
		for(int idx=0; idx<this.GRID_WIDTH; idx++) {
			List<String> list1 = Arrays.stream(WordSearch1.GRID_ITEMS[idx])
					.filter((o1) -> o1 != null)
					.map((item) -> item.letter)
					.collect(Collectors.toList());
			
			System.out.print((new String(Character.toChars(EX_DEC[4]))));
			for(int jdx=0; jdx<GRID_LENGTH; jdx++) {
				System.out.print(" " + (list1.get(jdx)=="-"?" ":list1.get(jdx)) + " " + (new String(Character.toChars(EX_DEC[4]))));
				//temp1++;
			}
			System.out.println();
			if(idx<this.GRID_WIDTH-1) {
				System.out.print((new String(Character.toChars(EX_DEC[5]))));
				for(int jdx=0; jdx<GRID_LENGTH; jdx++) {
					if (jdx == GRID_LENGTH-1){
						System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
						System.out.print((new String(Character.toChars(EX_DEC[7]))));
					} else {
						System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
						System.out.print((new String(Character.toChars(EX_DEC[6]))));
					}
				}
				System.out.println();
			}
		}
		System.out.print((new String(Character.toChars(EX_DEC[8]))));
		for(int jdx=0; jdx<GRID_LENGTH; jdx++) {
			if (jdx == GRID_LENGTH-1){
				System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
				System.out.print((new String(Character.toChars(EX_DEC[10]))));
			} else {
				System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
				System.out.print((new String(Character.toChars(EX_DEC[9]))));
			}
		}
		System.out.println();
	}

	public void printGrid41() {
		System.out.println("Grid occupancy: " + getGridOccupancy() + "  :: Valid: " + getGridOccupancy(true));
		System.out.println();
		for(int idx=0; idx<this.GRID_WIDTH; idx++) {
			List<String> list1 = Arrays.stream(WordSearch1.GRID_ITEMS[idx])
					.filter((o1) -> o1 != null)
					.map((item) -> item.letter)
					.collect(Collectors.toList());
			
			for(int jdx=0; jdx<GRID_LENGTH; jdx++) {
				System.out.print((list1.get(jdx)=="-"?" ":list1.get(jdx)) + "\t");
				//temp1++;
			}
			System.out.println();
			System.out.println();
		}
		System.out.println();
		System.out.println();
		System.out.println();
		printGrid5();
	}

	public void printGrid4() {
		System.out.println("GRID: " + GRID_ID);
		
		//System.out.println("Grid occupancy: " + getGridOccupancy() + "  :: Valid: " + getGridOccupancy(true));
		System.out.println();
		for(int idx=0; idx<this.GRID_WIDTH; idx++) {
			List<String> list1 = Arrays.stream(WordSearch1.GRID_ITEMS[idx])
					.filter((o1) -> o1 != null)
					.map((item) -> {
						boolean startWord = false;
						boolean endWord = false;
						for(WordLetter wordLetter : item.listWL) {
							if (!wordLetter.gw.isValid()) continue;
//							
							startWord = startWord || wordLetter.start;
							endWord = endWord || wordLetter.end;
						}
						String retVal = item.letter;
						if(startWord) {
							retVal = "."+retVal;
						}
						if(endWord) {
							retVal = retVal + ".";
						}
						return retVal;
					})
					.collect(Collectors.toList());
			
			for(int jdx=0; jdx<GRID_LENGTH; jdx++) {
				System.out.print((list1.get(jdx)=="-"?" ":list1.get(jdx)) + "\t");
				//temp1++;
			}
			System.out.println();
			System.out.println();
		}
		System.out.println();
		System.out.println();
		//printGrid5();
	}

	public void printGrid5() {
		System.out.println();
		System.out.println();
		System.out.println("GRID: " + GRID_ID);
		System.out.println();

		//System.out.println("Grid occupancy: " + getGridOccupancy() + "  :: Valid: " + getGridOccupancy(true));
		System.out.print((new String(Character.toChars(EX_DEC[0]))));
		System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
		for(int jdx=0; jdx<GRID_LENGTH; jdx++) {
			if (jdx == GRID_LENGTH-1){
				System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
				System.out.print((new String(Character.toChars(EX_DEC[3]))));
				//System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
			} else {
				System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
				System.out.print((new String(Character.toChars(EX_DEC[2]))));
				System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
			}
		}
		System.out.println();
		for(int idx=0; idx<this.GRID_WIDTH; idx++) {
			List<String> list1 = Arrays.stream(WordSearch1.GRID_ITEMS[idx])
					.filter((o1) -> o1 != null)
					.map((item) -> item.letter)
					.collect(Collectors.toList());
			
			System.out.print((new String(Character.toChars(EX_DEC[4]))));
			//System.out.print(" ");
			for(int jdx=0; jdx<GRID_LENGTH; jdx++) {
				System.out.print((list1.get(jdx)=="-"?" ": list1.get(jdx)) + "\t" + (new String(Character.toChars(EX_DEC[4]))));
				//System.out.print(" " + (list1.get(jdx)=="-"?" ":list1.get(jdx)) + " " + (new String(Character.toChars(EX_DEC[4]))));
				//temp1++;
			}
			System.out.println();
			if(idx<this.GRID_WIDTH-1) {
				System.out.print((new String(Character.toChars(EX_DEC[5]))));
				System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
				for(int jdx=0; jdx<GRID_LENGTH; jdx++) {
					if (jdx == GRID_LENGTH-1){
						System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
						System.out.print((new String(Character.toChars(EX_DEC[7]))));
//						System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
					} else {
						System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
						System.out.print((new String(Character.toChars(EX_DEC[6]))));
						System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
					}
				}
				System.out.println();
			}
		}
		System.out.print((new String(Character.toChars(EX_DEC[8]))));
		System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
		for(int jdx=0; jdx<GRID_LENGTH; jdx++) {
			if (jdx == GRID_LENGTH-1){
				System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
				System.out.print((new String(Character.toChars(EX_DEC[10]))));
				//System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
			} else {
				System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
				System.out.print((new String(Character.toChars(EX_DEC[9]))));
				System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
			}
		}
		System.out.println();
	}

	public void printGrid() {
		System.out.print((new String(Character.toChars(EX_DEC[0]))));
		for(int jdx=0; jdx<GRID_LENGTH; jdx++) {
			if (jdx == GRID_LENGTH-1){
				System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
				System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
				System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
				System.out.print((new String(Character.toChars(EX_DEC[3]))));
			} else {
				System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
				System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
				System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
				System.out.print((new String(Character.toChars(EX_DEC[2]))));
			}
		}
		System.out.println();
		for(int idx=0; idx<this.GRID_WIDTH; idx++) {
			List<GridLetter> list1 = Arrays.stream(WordSearch1.GRID_ITEMS[idx])
					.filter((o1) -> o1 != null)
					//.map((item) -> item.letter)
					.collect(Collectors.toList());
			
			System.out.print((new String(Character.toChars(EX_DEC[4]))));
			for(int jdx=0; jdx<GRID_LENGTH; jdx++) {
				System.out.print("  "+list1.get(jdx).headMark+"  " + (new String(Character.toChars(EX_DEC[4]))));
				//temp1++;
			}
			System.out.println();

			System.out.print((new String(Character.toChars(EX_DEC[4]))));
			for(int jdx=0; jdx<GRID_LENGTH; jdx++) {
				System.out.print("  " + list1.get(jdx).bodyMark + "  " + (new String(Character.toChars(EX_DEC[4]))));
				//temp1++;
			}
			System.out.println();

			System.out.print((new String(Character.toChars(EX_DEC[4]))));
			for(int jdx=0; jdx<GRID_LENGTH; jdx++) {
				System.out.print("  "+list1.get(jdx).footMark+"  " + (new String(Character.toChars(EX_DEC[4]))));
				//temp1++;
			}
			System.out.println();

			if(idx<this.GRID_WIDTH-1) {
				System.out.print((new String(Character.toChars(EX_DEC[5]))));
				for(int jdx=0; jdx<GRID_LENGTH; jdx++) {
					if (jdx == GRID_LENGTH-1){
						System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
						System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
						System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
						System.out.print((new String(Character.toChars(EX_DEC[7]))));
					} else {
						System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
						System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
						System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
						System.out.print((new String(Character.toChars(EX_DEC[6]))));
					}
				}
				System.out.println();
			}
		}
		System.out.print((new String(Character.toChars(EX_DEC[8]))));
		for(int jdx=0; jdx<GRID_LENGTH; jdx++) {
			if (jdx == GRID_LENGTH-1){
				System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
				System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
				System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
				System.out.print((new String(Character.toChars(EX_DEC[10]))));
			} else {
				System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
				System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
				System.out.print((new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1])))+(new String(Character.toChars(EX_DEC[1]))));
				System.out.print((new String(Character.toChars(EX_DEC[9]))));
			}
		}
		System.out.println();
	}

	public int getGridOccupancy() {
		int score=0;
		for(int idx=0; idx<this.GRID_WIDTH; idx++) {
			for(int jdx=0; jdx<this.GRID_LENGTH; jdx++) {
				if(!(WordSearch1.GRID_ITEMS[idx][jdx].letter.equals(GridLetter.DEFAULT))) score++; 
			}
		}
		return (int)(score*100/(this.GRID_LENGTH*this.GRID_WIDTH));
	}
	
	public int getGridOccupancy(boolean valid) {
		int score=0;
		for(int idx=0; idx<this.GRID_WIDTH; idx++) {
			for(int jdx=0; jdx<this.GRID_LENGTH; jdx++) {
				if(!(WordSearch1.GRID_ITEMS[idx][jdx].letter.equals(GridLetter.DEFAULT))) {
					long validCount = WordSearch1.GRID_ITEMS[idx][jdx].listWL.stream()
						.filter((o) -> o.gw.isValid()).count();
					if(validCount>0) score++; 
				}
			}
		}
		return (int)(score*100/(this.GRID_LENGTH*this.GRID_WIDTH));
	}
	
	public void initGrid() {
		for(int idx=0; idx<this.GRID_WIDTH; idx++) {
			for(int jdx=0; jdx<this.GRID_LENGTH; jdx++) {
				WordSearch1.GRID_ITEMS[idx][jdx] = new GridLetter(GridLetter.DEFAULT);
			}
		}
	}
}
