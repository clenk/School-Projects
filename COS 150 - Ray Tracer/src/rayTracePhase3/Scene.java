package rayTracePhase3;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

//Christopher Lenk for COS 150
//Models a 3D scene

public class Scene {
	//Demensions of the window
	private double width;
	private double height;
	//Scene maximum and minimum
	public static final double MAX = 60.0;
	public static final double MIN = 0.000001;
	//List of all shapes in the scene
	ArrayList<Shape> shapes = new ArrayList<Shape>();
	ArrayList<Light> lights = new ArrayList<Light>();
	
	//Getters
	public double getWidth() {
		return width;
	}
	public double getHeight() {
		return height;
	}

	//Setters
	public void setWidth(double width) {
		this.width = width;
	}
	public void setHeight(double height) {
		this.height = height;
	}

	//Constructor, builds the scene and adds all the objects to it
	public Scene(double width, double height, String filename) throws FileNotFoundException, ClassNotFoundException {
		this.width = width;
		this.height = height;
		
		//Read in scene details from a file
		File scenFil = new File(filename);
		Scanner sc = new Scanner(scenFil);

		while (sc.hasNextLine()) {
			String line = sc.nextLine();

			if (!line.equals("")) {
				try {
					Class<?> c = Class.forName("rayTracePhase3."+line);//Get the class from the name
					Class<?> params[] = {Scanner.class}; //Create a list of constructor parameters
					Object argList[] = {sc}; //Create argument list for the constructor
					Constructor con = c.getConstructor(params);
					Object obj = con.newInstance(argList);

					//If it's a light put it on the lights list, otherwise on the shapes list
					if (line.equals("Light")) {
						lights.add((Light) obj);
					} else {
						shapes.add((Shape) obj);
					}
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	//Loops through all shapes, checking their intersections
	//with the ray connecting the two given points, holds on to
	//only the closest one to the window and returns that intersection
	//Then check the lights
	public Intersection trace(Point p1, Point p2) {
		Intersection curr; //Current intersection
		Intersection near = new Intersection(null, null, MAX+2); //Nearest intersection; start with a large value so it'll be overridden
		
		for (Shape s: shapes) {
			curr = s.getIntersect(p1, p2);
			if (curr.getDist() < near.getDist()) {
				near = curr;
			}
		}
		for (Shape s: lights) {
			curr = s.getIntersect(p1, p2);
			if (curr.getDist() < near.getDist()) {
				near = curr;
			}
		}
		return near;
	}
	
	//Creates and returns a 3D Point with the given position in the 2D window
	public Point get3DCoordinate(int col, int row, Image img) {
		double x = ((((double)col) / (img.getWidth()-1)) * this.width) - (this.width / 2);
		double y = -1 * (((((double)row) / (img.getHeight()-1)) * this.height) - (this.height / 2));
		return new Point(x, y, 0.0);
	}

	//Returns a list of lights that are visible from the given point
	public ArrayList<Light> getVisibleLights(Point p) {
		ArrayList<Light> visLights = new ArrayList<Light>();
		for (Light lit: lights) {
			Shape s = trace(p, lit.getCenter()).getS();
			//If light is visible add it to list to be returned
			if (s == lit) {
				visLights.add((Light)s);
			}
		}
		return visLights;
	}
}
