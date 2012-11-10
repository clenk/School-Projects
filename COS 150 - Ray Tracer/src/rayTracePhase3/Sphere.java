package rayTracePhase3;

import java.util.Scanner;

//Christopher Lenk for COS 150
//Models a sphere object in 3d space

public class Sphere extends Shape {
	protected Point center;
	protected double radius;
	protected Color color; //color of the sphere
	protected Color specLight; //specular lighting

	//getters
	public Point getCenter() {
		return center;
	}
	public double getRadius() {
		return radius;
	}
	public Color getAmbient(Point p) {
		return color;
	}

	//setters
	public void setCenter(Point center) {
		this.center = center;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	//Constructors
	public Sphere() {
		super();
	}
	public Sphere(Point center, double radius, Color color, Color spec) {
		super();
		this.center = center;
		this.radius = radius;
		this.color = color;
		this.specLight = spec;
	}
	public Sphere(Scanner sc) {
		Scanner scan = new Scanner(sc.nextLine());
		
		if (scan.hasNext()) {
			String str = scan.next();
			if (str.equals("center")) { //Check that info was provided - if it was, skips over "center"
				Point centr = new Point(scan.nextDouble(), scan.nextDouble(), scan.nextDouble());
				scan = new Scanner(sc.nextLine());
				scan.next(); //skip over "radius"
				double rad = scan.nextDouble();
				scan = new Scanner(sc.nextLine());
				scan.next(); //skip over "color"
				Color colr = new Color(scan.nextDouble(), scan.nextDouble(), scan.nextDouble());
				scan = new Scanner(sc.nextLine());
				scan.next(); //skip over "specular"
				Color spec = new Color(scan.nextDouble(), scan.nextDouble(), scan.nextDouble());

				this.center = centr;
				this.radius = rad;
				this.color = colr;
				this.specLight = spec;
			} 
		}
	}

	@Override //returns the intersection between this sphere and the line between the two given points
	public Intersection getIntersect(Point p1, Point p2) {
		//get values we'll need
		Point endToStartDiff = p2.subtract(p1);
		Point startToCenterDiff = p1.subtract(this.center);
		double A = endToStartDiff.dotProduct(endToStartDiff);
		double B = 2.0 * (endToStartDiff.dotProduct(startToCenterDiff));
		double C = (startToCenterDiff.dotProduct(startToCenterDiff)) - this.radius * this.radius;
		double disc = B * B - 4.0 * A * C;
		
		//calculate the intersection distance
		if (disc < 0) { //no intersection
		    return new Intersection(new Point(0, 0, 0), this, Scene.MAX + 1);
		} else {
			double distance = (-B - Math.sqrt(disc)) / (2.0 * A);
			if (distance <= Scene.MIN) {
				distance = (-B + Math.sqrt(disc)) / (2.0 * A);
			}
			if (distance > Scene.MIN) { //make sure distance is positive
				double x = p1.getX() * (1-distance) + p2.getX()*distance;
				double y = p1.getY() * (1-distance) + p2.getY()*distance;
				double z = p1.getZ() * (1-distance) + p2.getZ()*distance;
				
				return new Intersection(new Point(x, y, z), this, distance);
			}
		}
		return new Intersection(new Point(0,0,0), this, Scene.MAX + 1);
	}
	
	//prints out results for testing
	public static void test(Shape s, Point p1, Point p2, int n) {
		Intersection isect = s.getIntersect(p1, p2);
		System.err.println("Intersection "+n+", x: " + isect.getPoint().getX());
		System.err.println("Intersection "+n+", y: " + isect.getPoint().getY());
		System.err.println("Intersection "+n+", z: " + isect.getPoint().getZ());
		System.err.println("Intersection "+n+", dist: " + isect.getDist() + "\n");
	}

	@Override
	public Color getDiffuse(Intersection isect) {
		return getAmbient( isect.getPoint() );
	}

	@Override
	public boolean hasDiffuse() {
		return true;
	}
	
	@Override
	Point getNormal(Point spot) {
		return center.getUnitVector(spot);
	}
	
	@Override //Returns true if this object reacts to lighting
	public boolean hasLighting() {
		return true;
	}
	
	@Override //Returns the weight that specular lighting has on this object
	public Color getSpecularWeight() {
		return specLight;
	}
}
