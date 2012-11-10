package rayTracePhase3;

//Christopher Lenk for COS 150
//Models a point in 3D space

public class Point {
	//Coordinate values
	private double x;
	private double y;
	private double z;
	
	//Getters
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getZ() {
		return z;
	}
	
	//Setters
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public void setZ(double z) {
		this.z = z;
	}
	
	//Constructor
	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	//Prints out the xyz coordinates separated by commas
	public void print() {
		System.out.println(getX() + ", " + getY() + ", " + getZ());
	}
	
	//Returns a point by subtracting given point from this point
	public Point subtract(Point point) {
		double x = this.getX() - point.getX();
		double y = this.getY() - point.getY();
		double z = this.getZ() - point.getZ();
		return new Point(x, y, z);
	}
	
	//Calculates the dot product of the given point and this point
	public double dotProduct(Point point) {
		double x = this.getX() * point.getX();
		double y = this.getY() * point.getY();
		double z = this.getZ() * point.getZ();
		return x + y + z;
	}
	
	public Point getUnitVector(Point direction) {
		double dist = getDistance(direction);
		if (dist < Scene.MIN) {
			return new Point (0, 0, 0);
		} else {
			Point diff = direction.subtract(this);
			return diff.divide(dist);
		}
	}

	//Returns a point by multiplying this point by given value
	public Point multiply(double multiplier) {
		double x = this.getX() * multiplier;
		double y = this.getY() * multiplier;
		double z = this.getZ() * multiplier;
		return new Point(x, y, z);
	}
	
	//Returns a point by dividing this point by given divisor
	public Point divide(double divisor) {
		double x = this.getX() / divisor;
		double y = this.getY() / divisor;
		double z = this.getZ() / divisor;
		return new Point(x, y, z);
	}
	
	//Returns the distance between this point and the point passed in
	public double getDistance(Point point) {
		//Point newPoint = subtract(point);
		/*double x = this.x - point.getX();
		double y = this.y - point.getY();
		double z = this.z - point.getZ();
		return Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2)+Math.pow(z, 2)); */

		Point diff = subtract(point);
        return Math.sqrt (diff.dotProduct(diff));
	}
	
	//Returns an equal but opposite angle to the point passed in
	public Point bounce(Point normal) {
		Point bounced = normal.multiply(2.0 * (this.dotProduct(normal)) );
		bounced = bounced.subtract(this);
		return bounced;
	}
	
	//Adds another point to this one
	public void addTo(Point p) {
		this.x = this.x + p.getX();
		this.y = this.y + p.getY();
		this.z = this.z + p.getZ();
	}
	
	//Main; tests methods to ensure accuracy
	public static void main(String args[]) {
		//Create a couple points
		Point p1 = new Point(.2,.4,0);
		Point p2 = new Point(3.9,1.9,4);
		//Subtract them
		Point p3 = p1.subtract(p2);
		Point p4 = p2.subtract(p1);
		//Print the subtractions
		System.out.println("p1-p2: " + p3.getX() + ", " + p3.getY() + ", " + p3.getZ());
		System.out.println("p2-p1: " + p4.getX() + ", " + p4.getY() + ", " + p4.getZ());
		//Print the dot products
		System.out.println("p1*p2: " + p1.dotProduct(p2));
		System.out.println("p2*p1: " + p2.dotProduct(p1));
	}
}
