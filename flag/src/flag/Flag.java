// Flag starter kit

/*
 * CAITLIN
 */

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JApplet;

public class Flag extends JApplet {
	private final int STRIPES = 13;

	// SCALE FACTORS (A through L)
	//
	// Note: Constants in Java should always be ALL_CAPS, even
	// if we are using single letters to represent them
	//
	// NOTE 2: Do not delete or change the names of any of the
	// variables given here
	//
	// Set the constants to exactly what is specified in the documentation
	// REMEMBER: These are scale factors.  They are not numbers of pixels.
	// You will use these and the width and height of the Applet to figure
	// out how to draw the parts of the flag (stripes, stars, field).
	
	private final double A = 1.0;  // Hoist (width) of flag
	private final double B = 1.9;  // Fly (length) of flag
	private final double C = 7.0 / 13;  // Hoist of Union
	private final double D = 0.76;  // Fly of Union
	private final double E = 0.054;  // See flag specification
	private final double F = 0.054;  // See flag specification
	private final double G = 0.063;  // See flag specification
	private final double H = 0.063;  // See flag specification
	private final double K = 0.0616;  // Diameter of star
	private final double L = 1.0 / 13;  // Width of stripe

	// You will need to set values for these in paint()
	private double flag_width;   // width of flag in pixels
	private double flag_height;  // height of flag in pixels
	private double stripe_height; // height of an individual stripe in pixels
	
	private int STAR_POINTS;
	private int[] polygonX;
	private int[] polygonY;

	// init() will automatically be called when an applet is run
	public void init() {
		// Choice of width = 1.9 * height to start off
		// 760 : 400 is ratio of FLY : HOIST
		setSize(760, 400);
		repaint();
	}

	// paint() will be called every time a resizing of an applet occurs
	public void paint(Graphics g) {
		double width = getWidth();
		double height = getHeight();
		
		flag_width = width;
		flag_height = height;
		
		if ((B/A) * flag_height > flag_width) {  
			flag_height = (flag_width / (B / A));
		} 
		else {                   
			flag_width = (flag_height * (B / A));
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

	private void drawBackground(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		
	}
	
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

	public void drawUnion(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(0, 0, (int)((D / B) * flag_width), (int)(stripe_height * 7 - 1));
		
	}
	
	public void draw(Graphics g, int centerX, int centerY, double radius) {
		double innerRadius = radius*Math.sin(Math.toRadians(18)/Math.sin(Math.toRadians(54)));
		
		for (int i = 18; i < 360; i += 72) {
			polygonX[(i-18)/36] = centerX + (int) (radius * Math.cos(Math.toRadians(i)));
			polygonY[(i-18)/36] = centerY - (int) (radius * Math.sin(Math.toRadians(i))); 
		}

		for (int i = 54; i < 360; i += 72) {
			polygonX[(i-18)/36] = centerX + (int) (innerRadius * Math.cos(Math.toRadians(i)));
			polygonY[(i-18)/36] = centerY - (int) (innerRadius * Math.sin(Math.toRadians(i))); 
		}
		g.fillPolygon(polygonX, polygonY, STAR_POINTS);
	}
	
	public void drawStars(Graphics g) {
		g.setColor(Color.white);
		
		//star positions
		int xOffset = (int)(G / B * flag_width);
		int yOffset = (int)(E / A * flag_height);
		int xSpacer = (int)((H / B) * flag_width);
		int ySpacer = (int)((F / A) * flag_height);
		
		//separating longer rows and shorter rows of stars
		double radius = K / B / 2 * flag_width;
		int majorRows = 5;
		int minorRows = 4;
		int majorStars = 6;
		int minorStars = 5;

		for (int row = 0; row < majorRows; row++) {
			for (int col = 0; col < majorStars; col++) {
				// six stars
				draw(g, (int) (xOffset + col * 2 * xSpacer),
						(int) (yOffset + row * 2 * ySpacer), radius);
			}
		}

		for (int row = 0; row < minorRows; row++) {
			for (int col = 0; col < minorStars; col++) {
				// five stars
				draw(g, (int) (xOffset + xSpacer + col * 2 * xSpacer),
						(int) (yOffset + ySpacer + row * 2 * ySpacer), radius);
			}
		}
	}
	
}
