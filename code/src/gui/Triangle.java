package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class Triangle extends CenteredGraphicalElement {

	  private Color drawColor;
	  private Color fillColor;
	  private int size;
	  private int xPoints[];
	  private int yPoints[];
	  private static int nPoints = 3;

	  public Triangle(int x, int y, Color drawColor, Color fillColor, int size)
	  {
	    super(x, y);
	    this.drawColor = drawColor;
	    this.fillColor = fillColor;
	    this.size = size;
	    xPoints = new int[nPoints];
	    yPoints = new int[nPoints];
	    
	    initPoints(x, y);
	  }
	  
	  private void initPoints(int x, int y){
		    xPoints[0] = x + this.size;
		    xPoints[1] = x;
		    xPoints[2] = x - this.size;
		    
		    yPoints[0] = y + this.size;
		    yPoints[1] = y - this.size;
		    yPoints[2] = y;
	  }

	  public void paint(Graphics2D g2d)
	  {
	    Stroke currentStroke = g2d.getStroke();
	    g2d.setStroke(new BasicStroke(2.0F));
	    Color current = g2d.getColor();
	    if (this.fillColor != null) {
	      g2d.setColor(this.fillColor);
	      g2d.fillPolygon(xPoints, yPoints, nPoints);
	    }
	    g2d.setColor(this.drawColor);
	    g2d.drawPolygon(xPoints, yPoints, nPoints);
	    g2d.setColor(current);
	    g2d.setStroke(currentStroke);
	  }

	  public String toString()
	  {
	    return this.drawColor.toString() + " rectangle";
	  }
	
}
