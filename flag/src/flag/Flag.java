// 8/16 ~ 8/24
// BY CAITLIN CAI (MISHA, left 8/20)

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JApplet;

public class Flag extends JApplet {
	private final int STRIPES = 13;

	// REMEMBER: These are scale factors.  They are not numbers of pixels.
	// You will use these and the width and height of the applet to figure
	// out how to draw the parts of the flag (stripes, stars, field)
	
	private final double A = 1.0;  // flag fly (width)
	private final double B = 1.9;  // flag hoist (height)
	private final double C = 7.0 / 13;  // union fly (width)
	private final double D = 0.76;  // union hoist (height)
	private final double E = 0.054;  // star y offset
	private final double F = 0.054;  // star y spacing
	private final double G = 0.063;  // star x offset
	private final double H = 0.063;  // star x spacing
	private final double K = 0.0616;  // star diameter
	private final double L = 1.0 / 13;  // stripe width
	
	private int STAR_POINTS; // used to draw polygon with 5 outer points and 5 inner points

	// set values for these in paint()
	private double flag_width;   // width of flag in pixels
	private double flag_height;  // height of flag in pixels
	private double stripe_height; // height of an individual stripe in pixels
	
	private int[] polygonX; // holds the x coordinates of the 10 vertices of the star
	private int[] polygonY; // holds the y coordinates of the 10 vertices of the star

	// init() automatically be called when an Applet is run
	public void init() {
		// Choice of width = 1.9 * height to start off
		// 760 : 400 is ratio of FLY (width) : HOIST (height)
		setSize(760, 400);
		repaint();
	}

	// paint() will be called every time a resizing of an Applet occurs
	public void paint(Graphics g) {
		double width = getWidth();
		double height = getHeight();
		
		flag_width = width;
		flag_height = height;
		
		// check if current width is > than expected width
		if ((B/A) * flag_height > flag_width) {  
			flag_height = (flag_width / (B / A)); // if true, flag_height is adjusted to maintain ratio
		} 
		else {                   
			flag_width = (flag_height * (B / A)); // otherwise, flag_width is adjusted
		}

		stripe_height = flag_height / STRIPES;
		STAR_POINTS = 10;
		polygonX = new int[STAR_POINTS];
		polygonY = new int[STAR_POINTS];
		
		drawBackground(g);
		drawStripes(g);
		drawUnion(g);
		drawStars(g);
	}

	// set background to black
	private void drawBackground(Graphics g) { 
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		
	}
	
	// draws stripes, color alternates
	public void drawStripes(Graphics g) { 
		for (int i = 0; i <= STRIPES; i++) {
			if (i % 2 == 1) {
				g.setColor(Color.white);
			}
			else {
				g.setColor(Color.red);
			}
		
			g.fillRect(0, (int)(stripe_height * i), (int)flag_width, (int)stripe_height);
		}
	}
	
	// draw union on top of stripes
	public void drawUnion(Graphics g) { 
		g.setColor(Color.blue);
		// height - 1 to fit union w/o overlapping 8th stripe
		g.fillRect(0, 0, (int)((D / B) * flag_width), (int)(stripe_height * 7 - 1)); 		
	}
	
	public void draw(Graphics g, int centerX, int centerY, double radius) {
		
		// distance from star center to inner vertex, dip thingy
		double innerRadius = radius * Math.sin(Math.toRadians(18) / Math.sin(Math.toRadians(54)));
		
		// both loops increment by 72 degrees bc each point/vertex is separated by 72 degrees 

		// calculates x and y of outer vertices
		for (int i = 18; i < 360; i += 72) { 
			// (i-18)/36 for correct index, coordinates adjusted by centerx/y
			polygonX[(i-18) / 36] = centerX + (int) (radius * Math.cos(Math.toRadians(i))); 
			polygonY[(i-18) / 36] = centerY - (int) (radius * Math.sin(Math.toRadians(i))); 
		}
		
		// calculates x and y of inner vertices
		for (int i = 54; i < 360; i += 72) { 
			polygonX[(i-18) / 36] = centerX + (int) (innerRadius * Math.cos(Math.toRadians(i)));
			polygonY[(i-18) / 36] = centerY - (int) (innerRadius * Math.sin(Math.toRadians(i))); 
		}
		g.fillPolygon(polygonX, polygonY, STAR_POINTS);
	}
	
	public void drawStars(Graphics g) {
		g.setColor(Color.white);
		
		// spacings for stars
		int xOffset = (int)(G / B * flag_width);
		int yOffset = (int)(E / A * flag_height);
		int xSpacer = (int)((H / B) * flag_width);
		int ySpacer = (int)((F / A) * flag_height);
		
		// separating longer rows and shorter rows of stars
		double radius = K / B / 2 * flag_width; // radius of stars proportionate to flag size
		int majorRows = 5;
		int minorRows = 4;
		int majorStars = 6;
		int minorStars = 5;

		for (int row = 0; row < majorRows; row++) {
			for (int col = 0; col < majorStars; col++) {
				// six stars
				// xOffset + col * 2 * xSpacer -> stars are spaced with xSpacer, * 2 to leave space
				draw(g, (int) (xOffset + col * 2 * xSpacer), (int) (yOffset + row * 2 * ySpacer), radius);
			}
		}

		for (int row = 0; row < minorRows; row++) {
			for (int col = 0; col < minorStars; col++) {
				// five stars
				// x/y spacer added so aligned between stars above and below
				draw(g, (int) (xOffset + xSpacer + col * 2 * xSpacer), 
						(int) (yOffset + ySpacer + row * 2 * ySpacer), radius);
			}
		}
	}
	
}
