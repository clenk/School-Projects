package rayTracePhase3;

import java.io.FileNotFoundException;
import java.util.ArrayList;

//Christopher Lenk for COS 150 - Spring 2011
//Ray Tracer Phase 3
//Creates a 3D scene and displays it

public class RayTracer {
	private Point eye;
	private Scene scene;
	private Image img;
	private Pixel pxls[][]; //Array of the pixels in the window/image
	public static final double dimensionX = 4.0;
	public static final double dimensionY = 3.0;
	public static double DISTANCE_WEIGHT = 0.6;
	
	//Pixel getter
	public Pixel getPixel(int row, int col) { //gets a specific pixel
		return pxls[row][col];
	}
	
	//Constructors
	public RayTracer() throws FileNotFoundException, ClassNotFoundException {
		this.eye = new Point(0, 0.8, -5);
		this.scene = new Scene(dimensionX, dimensionY, "sceneLenk.ssf");
		
		this.img = new Image(); 
		this.pxls = new Pixel[img.getHeight()][img.getWidth()];
		//Fill array with Pixel objects 
		for (int i = 0; i < pxls.length; i++) {
			for (int j = 0; j < pxls[i].length; j++) {
				pxls[i][j] = new Pixel(img.getPp(), j, i);
			}
		}
		trace(); //Assign color values to all pixels
		img.display(pxls); //Show the picture
	}
	public RayTracer(int width, int height) throws FileNotFoundException, ClassNotFoundException {
		this.eye = new Point(0, 0.5, -4);
		this.scene = new Scene(dimensionX, dimensionY, "scene.ssf");
		
		this.img = new Image(width, height); 
		this.pxls = new Pixel[img.getHeight()][img.getWidth()];
		//Fill array with Pixel objects 
		for (int i = 0; i < pxls.length; i++) {
			for (int j = 0; j < pxls[i].length; j++) {
				pxls[i][j] = new Pixel(img.getPp(), j, i);
			}
		}
		trace(); //Assign color values to all pixels
		img.display(pxls); //Show the picture
	}
	public RayTracer(int width, int height, String filename, String ssf) throws FileNotFoundException, ClassNotFoundException {
		this.eye = new Point(0, 0.5, -4);
		this.scene = new Scene(dimensionX, dimensionY, ssf);
		
		this.img = new Image(width, height); 
		this.pxls = new Pixel[img.getHeight()][img.getWidth()];
		//Fill array with Pixel objects 
		for (int i = 0; i < pxls.length; i++) {
			for (int j = 0; j < pxls[i].length; j++) {
				pxls[i][j] = new Pixel(img.getPp(), j, i);
			}
		}
		trace(); //Assign color values to all pixels
		img.write(filename); //Print the picture to a file
		img.display(pxls); //Show the picture
	}
	
	//Loops through all the pixels in the window and gets their color from their intersection
	public void trace() {
		for (int row = 0; row < img.getHeight(); row++) {
			for (int col = 0; col < img.getWidth(); col++) {
				Point p = scene.get3DCoordinate(col, row, img);
				Color c = buildColor(eye, p); //Get color of pixel
				//Convert colors from 0-1 scale to 0-255 scale
				double red = c.getRed();
				double green = c.getGreen();
				double blue = c.getBlue();
				//Set pixel colors
				Pixel pix = getPixel(row, col);
				pix.setRed((int)(red*255));
				pix.setGreen((int)(green*255));
				pix.setBlue((int)(blue*255));
			}
		}
	}
	
	//Applies shadow effect to the color
	public Color buildColor(Point eye, Point p) {
		Intersection isect = scene.trace(eye, p); //intersection point on closest shape
		Shape s = isect.getS();
		Color c;
		//If shape of intersection is diffuse, change the color based on all the lights
		if (s.hasDiffuse()) {
			c = rayTrace(eye, p, eye.getDistance(p));
			//Decrease light based on distance
			double distTravld = isect.getDist();
			c = c.divide(distTravld * DISTANCE_WEIGHT);
		} else { //Otherwise just get the unmodified color
			c = new Color(0, 0, 0);
			Point pnt = isect.getPoint();
			c.addTo(s.getAmbient(pnt));
			//At this point, c is always equal to the correct sky color 
		}
		return c;
	}
	
	//Calculates the color resulting from diffuse lighting 
	public Color calcDiffuse(Intersection isect) {
		Color c = new Color(0, 0, 0);
		Point p = isect.getPoint();
		Shape s = isect.getS();
		c.addTo(s.getAmbient(p));
		
		ArrayList<Light> lights = scene.getVisibleLights(p);
		for (Light lit: lights) {
			//Apply Diffuse lighting
			Point cent = lit.getCenter();
			Point unitDir = p.getUnitVector(cent);
			double lightDist = p.getDistance(cent);
			double lightAngle = isect.getS().getNormal(isect.getPoint()).dotProduct(unitDir);
			double lightStr = lightAngle / lightDist;
			c.addTo(lit.getDiffuse(isect).multiply(lightStr));
		}
		return c;
	}
	
	//Calculates the color resulting from specular lighting 
	public Color calcSpecular(Intersection isect, Point start, double dist) {
		Point p = isect.getPoint();
		Point inPoint = p.getUnitVector(start);
		//the bounce of first’s normal on the incoming point
		Point outPoint = inPoint.bounce(isect.getS().getNormal(p));
		outPoint.addTo(p);
		
		return rayTrace(p, outPoint, dist);
	}
	
	//Raytrace method
	private Color rayTrace(Point start, Point end, double dist) {
		if (dist > Scene.MAX) {
			return new Color(0, 0, 0);
		}
		
		Intersection firstIsect = scene.trace(start, end);
		Point ipoint = firstIsect.getPoint();
		//Make a copy of the color from the first intersection
		Color pixel = new Color(firstIsect.getS().getAmbient(ipoint));
		//Add distance from the start to the first intersection to the distance traveled
		dist += start.getDistance(ipoint);
		
		if (firstIsect.getS().hasLighting()) {
			pixel.addTo(calcDiffuse(firstIsect));
			pixel.divide(DISTANCE_WEIGHT * dist); 
			
			Color specClr = calcSpecular(firstIsect, start, dist);
			specClr.multiplyBy(firstIsect.getS().getSpecularWeight());
			pixel.multiplyBy(firstIsect.getS().getSpecularWeight().getNegative());
			pixel.addTo(specClr);			
		}
		return pixel;
	}

	//Main Method
	public static void main(String args[]) throws FileNotFoundException, ClassNotFoundException {
		new RayTracer();
	}
	
}
