package com.help.ik;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


class PointItem {
	int pointIndex;
	int xVal;
	int yVal;
	
	public PointItem(int pi) {
		pointIndex = pi;
	}
}

class RowItem {
	int rowIndex;
	int itemCount;
	List<PointItem> listPoints = new ArrayList<PointItem>(itemCount);
	
	public RowItem(int ri, int cnt) {
		rowIndex = ri;
		itemCount = cnt;
	
		populatePoints();
	}
	
	public void populatePoints() {
		for(int iPoint=0; iPoint<itemCount; iPoint++) {
			addPoint(new PointItem(iPoint));
		}
	}
	
	public void addPoint(PointItem pi) {
		listPoints.add(pi);
	}
	
	public void printItem() {
		System.out.print("RowIndex: "+rowIndex+", Points:");
		for(int iPoint=0; iPoint<itemCount; iPoint++) {
			System.out.print(listPoints.get(iPoint).pointIndex+ ", ");
		}
		System.out.println();
	}
}

class LineItem {
	LinePoint lp1;
	LinePoint lp2;
}

class LinePoint {
	int rowIndex;
	int pointIndex;
	int xVal;
	int yVal;
	
	public LinePoint(int ri, int pi, int XOFFSET, int YOFFSET, int ROWCOUNT, int GAP, int pOffset, boolean debugFlag, int rotateIdx) {

		rowIndex = ri;
		pointIndex = pi;
		double m = Math.tan(rowIndex*(Math.PI/(ROWCOUNT/2.0)));
		double m1 = Math.sqrt(1/(1+m*m));
		
		//rotation
		double rotateAngle = (rotateIdx*Math.PI)/ROWCOUNT;
		
		int xSegFactor = (rowIndex>ROWCOUNT/4 && rowIndex<=(3*((double)ROWCOUNT/4)))?-1:1;
		int ySegFactor = (rowIndex>ROWCOUNT/4 && rowIndex<=(3*((double)ROWCOUNT/4)))?-1:1; //rowIndex==6?-1:xSegFactor; //rowIndex>=4?1:-1;
		
		int xVal1 =  (int)(GAP*(pointIndex+pOffset) * m1);
		int yVal1 =  (int)(m*m1*(pointIndex+pOffset)*GAP);
		
		xVal = XOFFSET + xSegFactor*(int)(xVal1*Math.cos(rotateAngle)-yVal1*Math.sin(rotateAngle));
		yVal = YOFFSET + ySegFactor*(int)(xVal1*Math.sin(rotateAngle)+yVal1*Math.cos(rotateAngle));
		
		if(debugFlag)System.out.println("m: " + (m>=0?" ":"") + (Math.round(m*1000)/1000.0) + " :: xFac:" + xSegFactor + ", yFac:" + ySegFactor + " -- ("+xVal+","+yVal+")");
	}
	
	public String toString() {
		return (rowIndex+1) + ":" + (pointIndex+1) + "-("+xVal+","+yVal+")";
	}
}

class PicComponent extends JComponent {
	List<RowItem> listRows;
	List<LinePoint> listLPs;
    List<LineItem> linePoints;
    List<List<LineItem>> linesSets;
    
    int XOFFSET;
    int YOFFSET;
    int ROWCOUNT;
    int POINTCOUNT;
    int GAP;
    int pInterval;
    int picOffset;
    int rotateIdx;
    boolean debugFlag;
	
	PicComponent(int width, int height, boolean dFlag) {
        super();
        setPreferredSize(new Dimension(width,height));
        XOFFSET = width/2;
        YOFFSET = height/2;
        debugFlag = dFlag;

        linesSets = new ArrayList<List<LineItem>>();
	}
	
	public void drawPic(int rowCount, int pointCount, int gap, int pInt, int pOffset, int rIdx) {
        ROWCOUNT = rowCount;
        POINTCOUNT = pointCount;
        GAP = gap;
        
        pInterval = pInt;
        picOffset = pOffset;
        rotateIdx = rIdx;
        
    	listRows = new ArrayList<RowItem>();
    	listLPs = new ArrayList<LinePoint>();
        linePoints = new ArrayList<LineItem>();
   }

	public void populateRows() {
		for(int iRow=0; iRow<ROWCOUNT; iRow++) {
			RowItem ri = new RowItem(iRow, POINTCOUNT);
			listRows.add(ri);
		}
	}
	
	public void populateLPs() {
		for(int iLP=0; iLP<=ROWCOUNT*(POINTCOUNT); iLP++) {
			int ri = iLP%ROWCOUNT;
			int pi = debugFlag?1:(iLP*pInterval)%POINTCOUNT;
			listLPs.add(new LinePoint(ri, pi, XOFFSET, YOFFSET, ROWCOUNT, GAP, 
					picOffset, debugFlag, rotateIdx));
			
			if(iLP>0 && iLP<=(debugFlag?ROWCOUNT:ROWCOUNT*(POINTCOUNT))) {
				addLine(listLPs.get(iLP-1), listLPs.get(iLP));
			}
		}
		
		linesSets.add(linePoints);
	}

	public void printItem() {
		System.out.println("Rows:");
		for(int iPoint=0; iPoint<ROWCOUNT; iPoint++) {
			listRows.get(iPoint).printItem();
		}
		System.out.println();
		
		System.out.println("LPs");
		for(int iLP=0; iLP<ROWCOUNT*POINTCOUNT; iLP++) {
			System.out.println(listLPs.get(iLP));
		}
	}

    public void addLine(LinePoint lp1, LinePoint lp2) {
        int width = (int)getPreferredSize().getWidth();
        int height = (int)getPreferredSize().getHeight();
        Line2D.Double line = new Line2D.Double(
            lp1.xVal,
            lp1.yVal,
            lp2.xVal,
            lp2.yVal
            );
        LineItem li = new LineItem();
        li.lp1 = lp1;
        li.lp2 = lp2;
        
        linePoints.add(li);
        //repaint();
    }

    public void paintComponent(Graphics g0) {
    	Graphics2D g = (Graphics2D)g0;
    	
    	int idx=3;
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        Dimension d = getPreferredSize();
        g.setColor(Color.black);
        for(List<LineItem> lines1 : linesSets) {
	        for (LineItem line : lines1) {
	        	g.setColor(Color.getHSBColor((float)(idx++*0.03), (float)0.9, (float)0.9));
	        	System.out.print("colorHue("+ (int)(idx*3)/100.0 +"), ");
	//            g.drawLine(
	//                (int)line.getX1(),
	//                (int)line.getY1(),
	//                (int)line.getX2(),
	//                (int)line.getY2()
	//                //,0,0
	//                );
	
	        	double x1 = line.lp1.xVal;
	        	double y1 = line.lp1.yVal;
	        	double x2 = line.lp2.xVal;
	        	double y2 = line.lp2.yVal;

	       // Find control point
	        	//if(y2==y1) System.out.println("Eureka!!!" + x1 + "," + y1 + " - " + x2 + "," + y2);
	        	double mp = -1*((x2-x1)/(y2-y1));
	        	double mp1 = Math.sqrt(1/(1+mp*mp));
	    		int xVal1 =  (int)(30*mp1);
	    		int yVal1 =  (int)(mp*mp1*30);
	    		
	        	if(y2==y1) {
	        		//System.out.println("Eureka!!!" + xVal1 + "," + yVal1);
	        		yVal1 = (y2<YOFFSET?-30:30);
	        	}
	    		
	        	double cx1a = (y2<y1?(-1*xVal1):xVal1) + (x1 + (x2 - x1) / 2); // + (25*(y2>y1?1:-1));
	        	double cy1a = (y2<y1?(-1*yVal1):yVal1) + (y1 + (y2 - y1) / 2); // + 25*(x2>x1?1:-1);
	        	
	        	Path2D.Double path1 = new Path2D.Double();
	        	g.setStroke(new BasicStroke(2));
	        	path1.moveTo(x1, y1);
	        	path1.curveTo(x1, y1, cx1a, cy1a, x2, y2);

	        	//System.out.println("locate("+x1+","+y1+")");
	        	System.out.println("bezierCurve("+getPlot(x1)+","+getPlot(y1)+","+getPlot(cx1a)+","+getPlot(cy1a)
	        	+","+getPlot(x2)+","+getPlot(y2)+")");
	        	
	        	g.draw(path1);
	        }
        }
    }
    
    private int getPlot(double val) {
    	return (int) Math.round(val-XOFFSET);
    }
}


class DrawLines {

	static int GAP = 20;

	static int DIMX = 800;
	static int DIMY = 800;
	
	static int ROWCOUNT = 12;
	static int POINTCOUNT = 13;
	static int PINTERVAL = 6;
	
	static int PICOFFSET = 2;
	
	static boolean DEBUG = false;
	
    public static void main(String[] args) {
    	
        Runnable r = new Runnable() {
            public void run() {
            	PicComponent picComponent = new PicComponent(DIMX, DIMY, DEBUG);
            	
            	picComponent.drawPic(ROWCOUNT, POINTCOUNT, GAP, PINTERVAL, PICOFFSET, 0);
            	picComponent.populateRows();
            	picComponent.populateLPs();

//            	picComponent.drawPic(ROWCOUNT/2, POINTCOUNT, GAP, PINTERVAL, 13, 0);
//            	picComponent.populateRows();
//            	picComponent.populateLPs();
//
//            	picComponent.drawPic(ROWCOUNT/4, POINTCOUNT, GAP, PINTERVAL, 1, 0);
//            	picComponent.populateRows();
//            	picComponent.populateLPs();
            	//picComponent.printItem();

//            	picComponent.drawPic(12, 29, 10, 17, 10, 0);
//            	picComponent.populateRows();
//            	picComponent.populateLPs();
//            	
//            	picComponent.drawPic(6, 13, 10, 6, 2, 0);
//            	picComponent.populateRows();
//            	picComponent.populateLPs();
            	
                JOptionPane.showMessageDialog(null, picComponent);

            	
//            	LineComponent lc = new LineComponent(400, 400);
//            	lc.addLine(0,0,100,0);
//            	lc.addLine(100,0,100,100);
//            	lc.addLine(100,100,-100,100);
//            	lc.addLine(-100,100,-100,-100);
//            	lc.addLine(-100, -100, 100, -100);
//            	JOptionPane.showMessageDialog(null, lc);
            }
        };
        SwingUtilities.invokeLater(r);
    }
}

class LineComponent extends JComponent {

    ArrayList<Line2D.Double> lines;
    Random random;

    LineComponent(int width, int height) {
        super();
        setPreferredSize(new Dimension(width,height));
        lines = new ArrayList<Line2D.Double>();
        random = new Random();
    }

    public void addLine(int x1, int y1, int x2, int y2) {
        int width = (int)getPreferredSize().getWidth();
        int height = (int)getPreferredSize().getHeight();
        Line2D.Double line = new Line2D.Double(x1,y1,x2,y2);
        lines.add(line);
        repaint();
    }

    public void paintComponent(Graphics g) {
    	int OFFSET = 200;
    	int idx = 0;
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        Dimension d = getPreferredSize();
        g.setColor(Color.black);
        for (Line2D.Double line : lines) {
        	g.setColor(new Color(255-(50*(idx++)),100,100));
            g.drawLine(
                (int)line.getX1()+OFFSET,
                (int)line.getY1()+OFFSET,
                (int)line.getX2()+OFFSET,
                (int)line.getY2()+OFFSET
                );
        }
    }
}