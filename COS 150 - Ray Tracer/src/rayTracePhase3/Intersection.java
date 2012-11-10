package rayTracePhase3;
//Christopher Lenk for COS 150

public class Intersection {
	private Point point; //point where intersection occurs
	private Shape s; //shape that was intersected
	private double dist; //distance to the intersection point
	
	//getters
	public Point getPoint() {
		return point;
	}
	public Shape getS() {
		return s;
	}
	public double getDist() {
		return dist;
	}
	
	//setters
	public void setPoint(Point point) {
		this.point = point;
	}
	public void setS(Shape s) {
		this.s = s;
	}
	public void setDist(double dist) {
		this.dist = dist;
	}
	
	//constructor
	public Intersection(Point point, Shape s, double dist) {
		super();
		this.point = point;
		this.s = s;
		this.dist = dist;
	}
}
