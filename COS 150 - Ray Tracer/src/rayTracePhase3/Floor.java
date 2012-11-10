package rayTracePhase3;

import java.util.Scanner;

public class Floor extends Shape {
	private double y; //height of the floor in the scene
	private Color color1;
	private Color color2;
	private final double checkSize = 1.33; //size of squares in the checkerboard
	private Color specLight; //specular lighting

	//Constructor
	public Floor(double y, Color color1, Color color2, Color spec) {
		this.y = y;
		this.color1 = color1;
		this.color2 = color2;
		this.specLight = spec;
	}
	public Floor(Scanner sc) {
		//Default values
		double floorHeight = (-(RayTracer.dimensionY/2));
		Color floorC1 = new Color(0.4, 0.4, 0.4);
		Color floorC2 = new Color(0.1, 0.1, 0.1);
		Color floorSpec = new Color(0, 0, 0);
		
		Scanner scan = new Scanner(sc.nextLine());

		if (scan.hasNext()) {
			String line = scan.next();
			if (line.equals("height")) { //Check that info was provided - if it was, skips over "height"
				floorHeight = scan.nextDouble();
				scan = new Scanner(sc.nextLine());
				scan.next(); //skip over "color1"
				floorC1 = new Color(scan.nextDouble(), scan.nextDouble(), scan.nextDouble());
				scan = new Scanner(sc.nextLine());
				scan.next(); //skip over "color2"
				floorC2 = new Color(scan.nextDouble(), scan.nextDouble(), scan.nextDouble());
				scan = new Scanner(sc.nextLine());
				scan.next(); //skip over "specular"
				floorSpec = new Color(scan.nextDouble(), scan.nextDouble(), scan.nextDouble());
			}
		}
		
		this.y = floorHeight;
		this.color1 = floorC1;
		this.color2 = floorC2;
		this.specLight = floorSpec;
	}

	@Override
	public Intersection getIntersect(Point p1, Point p2) {
		if (Math.abs(p2.getY() - p1.getY()) < Scene.MIN) { //Check for a flat line (no intersection)
			return new Intersection(new Point(0, 0, 0), this, Scene.MAX + 1); //empty intersection
		}
		double distance = (y - p1.getY()) / (p2.getY() - p1.getY()); //get distance to floor
		if (distance < Scene.MIN) { //Check for intersect behind window (line slopes up)
			return new Intersection(new Point(0, 0, 0), this, Scene.MAX + 1); //empty intersection
		}
		//otherwise return the real intersection
		double x = p1.getX() * (1.0 - distance) + p2.getX() * distance;
		double y=this.y;
		double z= p1.getZ() * (1.0 - distance) + p2.getZ() * distance;
		return new Intersection(new Point(x, y, z), this, distance);
	}

	@Override
	public Color getAmbient(Point p) {
		double x1 = p.getX() + (Scene.MAX);
		double z1 = p.getZ() + (Scene.MAX);
		
		int x2 = (int)(x1 / checkSize);
		int z2 = (int)(z1 / checkSize);
		
		if (x2 % 2 == 0) {
			if (z2 % 2 == 0) {
				return color1;
			} else {
				return color2;
			}
		} else {
			if (z2 % 2 == 0) {
				return color2;
			} else {
				return color1;
			}
		}
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
		return new Point(0, 1, 0);
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
