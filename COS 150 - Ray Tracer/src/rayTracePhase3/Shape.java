package rayTracePhase3;

//Christopher Lenk for COS 150

import java.util.Scanner;

public abstract class Shape {
	
	public Shape(){
		super();
	}
	
	public Shape(Scanner sc) {
	}
	
	abstract public Intersection getIntersect(Point p1, Point p2);
	
	abstract public Color getAmbient(Point p);
	
	abstract public Color getDiffuse(Intersection isect);
	
	abstract public boolean hasDiffuse();
	
	abstract Point getNormal(Point spot);
	
	abstract public Color getSpecularWeight();
	
	abstract public boolean hasLighting();
}
