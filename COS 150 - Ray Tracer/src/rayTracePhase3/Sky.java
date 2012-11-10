package rayTracePhase3;

import java.util.Scanner;

public class Sky extends Shape {
	private Color color;
	private double z;

	//Constructors
	public Sky(double z, Color c) {
		this.z = z;
		this.color = c;
	}
	public Sky(Scanner sc) {
		Color sky = new Color(0, 0.7, 0.9); //Default value

		Scanner scan = new Scanner(sc.nextLine());

		if (scan.hasNext()) {
			String str = scan.next();
			if (str.equals("color")) { //Check that info was provided - if it was, skips over "color"
				double red = scan.nextDouble();
				double green = scan.nextDouble();
				double blue = scan.nextDouble();
				sky = new Color(red, green, blue);
			}
		}

		this.z = Scene.MAX;
		this.color = sky;
	}

	@Override
	public Intersection getIntersect(Point p1, Point p2) {
		Point newPoint = p1.getUnitVector(p2);
		//Point newPoint = new Point(p2.getX(), p2.getY(), this.z);
		return new Intersection(newPoint, this, this.z);
	}

	@Override
	public Color getAmbient(Point p) {
		return color;
	}

	@Override
	public Color getDiffuse(Intersection isect) {
		return new Color(0, 0, 0);
	}

	@Override
	public boolean hasDiffuse() {
		return false;
	}

	@Override
	Point getNormal(Point spot) {
		return new Point(0, 0, 0);
	}
	
	@Override //Returns true if this object reacts to lighting
	public boolean hasLighting() {
		return false;
	}
	
	@Override //Returns the weight that specular lighting has on this object
	public Color getSpecularWeight() {
		return new Color(0, 0, 0);
	}
}
