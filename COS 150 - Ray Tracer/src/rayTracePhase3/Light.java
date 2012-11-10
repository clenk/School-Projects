package rayTracePhase3;

import java.util.Scanner;

public class Light extends Sphere {

	//Constructors
	public Light(Point center, double radius, Color color) {
		super(center, radius, color, new Color(0, 0, 0));
		// TODO Auto-generated constructor stub
	}
	public Light(Scanner sc) { //Overrides Sphere's Scanner Constructor

		Scanner scan = new Scanner(sc.nextLine());
		
		if (scan.hasNext()) {
			String line = scan.next();
			if (line.equals("center")) { //Check that info was provided - if it was, skips over "center"
				Point centr = new Point(scan.nextDouble(), scan.nextDouble(), scan.nextDouble());
				scan = new Scanner(sc.nextLine());
				scan.next(); //skip over "radius"
				double rad = scan.nextDouble();

				this.center = centr;
				this.radius = rad;
				this.color = new Color(0.3, 0.3, 0.3);
				this.specLight = new Color(0, 0, 0);
			}
		}
	}
	
	//Overrides Sphere's hasDiffuse 
	public boolean hasDiffuse(){
		return false;
	}
	
	@Override //Returns true if this object reacts to lighting
	public boolean hasLighting() {
		return false;
	}

}
