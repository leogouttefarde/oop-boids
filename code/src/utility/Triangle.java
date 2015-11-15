package utility;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import gui.GraphicalElement;

/**
 * Classe de l'élément graphique Triangle
 * @author Ilyes Kacher, Léo Gouttefarde, Nejmeddine Douma
 *
 */
public class Triangle implements GraphicalElement {

	private Color drawColor;
	private Color fillColor;
	private int xPoints[];
	private int yPoints[];
	private static int nPoints = 3;

	/**
	 * Crée un Triangle
	 * @param xPoints les coordonnées x des points du triangle
	 * @param yPoints les coordonnées y des points du triangle
	 * @param drawColor la couleur du tracé
	 * @param fillColor la couleur du remplisage
	 */
	public Triangle(int xPoints[], int yPoints[], Color drawColor, Color fillColor)
	{
		this.drawColor = drawColor;
		this.fillColor = fillColor;
		this.xPoints = xPoints;
		this.yPoints = yPoints;
	}

	/**
	 * Dessine le triangle dans le Graphics2D g2d
	 * @inheritDoc
	 */
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
	
	/**
	 * @return Retourne une String contenant la couleur du triangle
	 */
	public String toString()
	{
		return this.drawColor.toString() + " triangle";
	}
}
