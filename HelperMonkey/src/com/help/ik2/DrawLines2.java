package com.help.ik2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.help.ik.PathFollow;
import com.help.ik.PathFollow.TestPane;


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
	
	public LinePoint(int ri, int pi, int XOFFSET, int YOFFSET, int pOffset, boolean debugFlag, int rotateIdx) {

		rowIndex = ri;
		pointIndex = pi;
		double m = Math.tan(rowIndex*(Math.PI/(DrawLines2.ROWCOUNT/2.0)));
		double m1 = Math.sqrt(1/(1+m*m));
		
		//rotation
		double rotateAngle = (rotateIdx*Math.PI)/DrawLines2.ROWCOUNT;
		
		int xSegFactor = (rowIndex>DrawLines2.ROWCOUNT/4 && rowIndex<=(3*((double)DrawLines2.ROWCOUNT/4)))?-1:1;
		int ySegFactor = (rowIndex>DrawLines2.ROWCOUNT/4 && rowIndex<=(3*((double)DrawLines2.ROWCOUNT/4)))?-1:1; //rowIndex==6?-1:xSegFactor; //rowIndex>=4?1:-1;
		
		int xVal1 =  (int)(DrawLines2.GAP*(pointIndex+pOffset) * m1);
		int yVal1 =  (int)(m*m1*(pointIndex+pOffset)*DrawLines2.GAP);
		
		xVal = XOFFSET + xSegFactor*(int)(xVal1*Math.cos(rotateAngle)-yVal1*Math.sin(rotateAngle));
		yVal = YOFFSET + ySegFactor*(int)(xVal1*Math.sin(rotateAngle)+yVal1*Math.cos(rotateAngle));
		
		if(debugFlag)System.out.println("m: " + (m>=0?" ":"") + (Math.round(m*1000)/1000.0) + " :: xFac:" + xSegFactor + ", yFac:" + ySegFactor + " -- ("+xVal+","+yVal+")");
	}
	
	public String toString() {
		return (rowIndex+1) + ":" + (pointIndex+1) + "-("+xVal+","+yVal+")";
	}
}

class PicComponent extends JPanel {
	List<RowItem> listRows;
	List<LinePoint> listLPs;
    List<LineItem> linePoints;
    List<List<LineItem>> linesSets;
    
	
    int XOFFSET;
    int YOFFSET;
    //int pInterval;
    //int picOffset;
    int rotateIdx;
    boolean debugFlag;

    private Long startTime;

    private Shape pathShape;
    private List<Double> points;
    private Shape pointer;

    private int pos;
    private int index;

	PicComponent(int width, int height, boolean dFlag) {
        super();
        setPreferredSize(new Dimension(width,height));
        XOFFSET = width/2;
        YOFFSET = height/2;
        debugFlag = dFlag;

        linesSets = new ArrayList<List<LineItem>>();

        //pathShape = path1;

        pointer = new Rectangle(0, 0, 10, 10);

    	drawPic(0);
    	populateRows();
    	populateLPs();
    	
        //System.out.println("Point size: " + points.size());
        
        //super.paintComponent(g);
        //Graphics2D g2d = (Graphics2D) g.create();
        //g2d.draw(pathShape);

        //pathShape = path1;

        points = new ArrayList<>();
//        PathIterator pi = pathShape.getPathIterator(null, 0.01);
//        while (!pi.isDone()) {
//            double[] coords = new double[6];
//            switch (pi.currentSegment(coords)) {
//                case PathIterator.SEG_MOVETO:
//                case PathIterator.SEG_LINETO:
//                    points.add(new Point2D.Double(coords[0], coords[1]));
//                    break;
//            }
//            pi.next();
//        }
        
        System.out.println("Point size: " + points.size());

        Timer timer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (startTime == null) {
                    startTime = System.currentTimeMillis();
                }
                long playTime = System.currentTimeMillis() - startTime;
                double progress = playTime / DrawLines2.PLAY_TIME;
                if (progress >= 1.0) {
                    progress = 1d;
                    ((Timer) e.getSource()).stop();
                }

                index =  (int)(DrawLines2.POINTCOUNT * DrawLines2.ROWCOUNT * progress);

                pos = index;
                repaint();
            }
        });
        timer.start();

    	
	}
	
	public void drawPic(int rIdx) {
        rotateIdx = rIdx;
        
    	listRows = new ArrayList<RowItem>();
    	listLPs = new ArrayList<LinePoint>();
        linePoints = new ArrayList<LineItem>();
   }

	public void populateRows() {
		for(int iRow=0; iRow<DrawLines2.ROWCOUNT; iRow++) {
			RowItem ri = new RowItem(iRow, DrawLines2.POINTCOUNT);
			listRows.add(ri);
		}
	}
	
	public void populateLPs() {
		for(int iLP=0; iLP<=DrawLines2.ROWCOUNT*(DrawLines2.POINTCOUNT); iLP++) {
			int ri = (iLP*1)%DrawLines2.ROWCOUNT;
			int pi = debugFlag?1:(iLP*DrawLines2.PINTERVAL)%DrawLines2.POINTCOUNT;
			listLPs.add(new LinePoint(ri, pi, XOFFSET, YOFFSET, DrawLines2.PICOFFSET, debugFlag, rotateIdx));
			
			if(iLP>0 && iLP<=(debugFlag?DrawLines2.ROWCOUNT:DrawLines2.ROWCOUNT*(DrawLines2.POINTCOUNT))) {
				addLine(listLPs.get(iLP-1), listLPs.get(iLP));
			}
		}
		
		linesSets.add(linePoints);
	}

	public void printItem() {
		System.out.println("Rows:");
		for(int iPoint=0; iPoint<DrawLines2.ROWCOUNT; iPoint++) {
			listRows.get(iPoint).printItem();
		}
		System.out.println();
		
		System.out.println("LPs");
		for(int iLP=0; iLP<DrawLines2.ROWCOUNT*DrawLines2.POINTCOUNT; iLP++) {
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
    	
    	int idx=0;
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        Dimension d = getPreferredSize();
        g.setColor(Color.white);
        for(List<LineItem> lines1 : linesSets) {
	        for (LineItem line : lines1) {
	        	g.setColor(Color.getHSBColor((float)(idx*0.03), (float)0.9, (float)0.9));
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
	        		System.out.println("Eureka!!!" + xVal1 + "," + yVal1);
	        		yVal1 = (y2<YOFFSET?-30:30);
	        	}
	    		
	        	double cx1a = (y2<y1?(-1*xVal1):xVal1) + (x1 + (x2 - x1) / 2); // + (25*(y2>y1?1:-1));
	        	double cy1a = (y2<y1?(-1*yVal1):yVal1) + (y1 + (y2 - y1) / 2); // + 25*(x2>x1?1:-1);

	        	Path2D.Double path1 = new Path2D.Double();
	        	path1.moveTo(x1, y1);
	        	//System.out.println(g.getColor());
	        	g.setStroke(new BasicStroke(2));
	        	path1.curveTo(x1, y1, cx1a, cy1a, x2, y2);
	        	
	            PathIterator pi = path1.getPathIterator(null, 0.01);
	            int iCnt = 0;
	            
	            points = new ArrayList<Point2D.Double>();
	            
	            while (!pi.isDone()) {
	                double[] coords = new double[6];
	                switch (pi.currentSegment(coords)) {
	                    case PathIterator.SEG_MOVETO:
	                    case PathIterator.SEG_LINETO:
	                        
	                    	points.add(new Point2D.Double(coords[0], coords[1]));
	                        break;
	                }
	                pi.next();
	                iCnt++;
	            }
	            
	            Path2D.Double pathNew = new Path2D.Double();
	            pathNew.moveTo(x1, y1);
	            iCnt = 0;
	            Point2D.Double pPrev = new Point2D.Double();
	            pPrev.x = x1;
	            pPrev.y = y1;
	            
	            for(Point2D.Double pd1: points) {
	            	
		            pathNew.moveTo(pPrev.x, pPrev.y);
	            	pathNew.lineTo(pd1.x, pd1.y);
	            	pPrev = pd1;
	            	iCnt++;
	            }
	        	
	            //System.out.println("Path itr cnt: " + iCnt);
	        	if(idx++ > pos) break;
	            
	        	g.draw(pathNew);
	        }
        }
        
//	        AffineTransform at = new AffineTransform();

//	        if (pos != -1) {
//
//	            Rectangle bounds = pointer.getBounds();
//	            at.rotate(0, (bounds.width / 2), (bounds.width / 2));
//
//	            Path2D player = new Path2D.Double(pointer, at);
//
//	            g.translate(pos.getX() - (bounds.width / 2), pos.getY() - (bounds.height / 2));
//	            g.draw(player);
//
//	        }

	       // g.dispose();

    }
    
}

class DrawLines2 {

	int DIMX = 1000;
	int DIMY = 1000;
	
	 boolean DEBUG = false;
	
	 static int ROWCOUNT = 16;
	 static int ROWSTEP = 1;
	  static int POINTCOUNT = 23;
	  static int PINTERVAL = (int)(POINTCOUNT/2);

	  static int PICOFFSET = 3;
	  static int GAP = 15;

    static final double PLAY_TIME = 2400; // 5 seconds...


	public static void main(String[] args) {
	    DrawLines2 dl2 = new DrawLines2();
	}
	
	public DrawLines2() {
	    EventQueue.invokeLater(new Runnable() {
	        @Override
	        public void run() {
	            try {
	                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
	                ex.printStackTrace();
	            }
	
            	PicComponent picComponent = new PicComponent(DIMX, DIMY, DEBUG);
            	
	            
	            JFrame frame = new JFrame("Testing");
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.add(picComponent);
	            frame.pack();
	            frame.setLocationRelativeTo(null);
	            frame.setVisible(true);
	        }
	    });
	}
}
