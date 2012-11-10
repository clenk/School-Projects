package rayTracePhase3;

//Christopher Lenk for COS 150
//Holds RGB values that together constitute a color 
//RGB values are in a 0 to 1 scale

public class Color {
	private double red;
	private double green;
	private double blue;
	
	//Getters
	public double getRed() {
		return red;
	}
	public double getGreen() {
		return green;
	}
	public double getBlue() {
		return blue;
	}
	
	//Constructor
	public Color(double red, double green, double blue) {
		super();
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	//Copy Constructor
	public Color(Color c) {
		this.red = c.red;
		this.green = c.green;
		this.blue = c.blue;
	}
	
	//Adds another color to this one
	public void addTo(Color c) {
		this.red = this.red + c.red;
		this.green = this.green + c.green;
		this.blue = this.blue + c.blue;
	}

	//Mulitplies this color by the value passed in, returns new Color object
	public Color multiply(double multiplier) {
		return new Color((red*multiplier), (green*multiplier), (blue*multiplier));
	}
	
	//Mulitplies this color by the color passed in, modifies current Color object
	public void multiplyBy(Color c) {
		this.red = this.red * c.red;
		this.green = this.green * c.green;
		this.blue = this.blue * c.blue;
	}

	//Divides this color by the value passed in
	public Color divide(double divisor) {
		return new Color((red/divisor), (green/divisor), (blue/divisor));
	}
	
	//Returns a new Color object with the negative values of this Color
	public Color getNegative() {
		return new Color((1 - red), (1 - green), (1 - blue));
	}

	public String toString(){
		return red + " "+green+" "+blue;
	}
}
