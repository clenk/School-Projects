package rayTracePhase3;

//Displays the picture on the screen 

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Image extends JFrame {
	private int width = 800;
	private int height = 600;
	private PicturePanel pp;
	
	//Getters
	public PicturePanel getPp() {
		return pp;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}

	//Setters
	public void setWidth(int width) {
		this.width = width;
	}
	public void setHeight(int height) {
		this.height = height;
	}

	//Constructor
	public Image() {
		pp = new PicturePanel(width, height);
	}
	public Image(int width, int height) {
		this.width = width;
		this.height = height;
		pp = new PicturePanel(width, height);
	}
	
	// Writes out the image into a file with the given filename
	public void write(String filename) {
		try {
			ImageIO.write(pp.getBufferedImage(), "jpg", new File("./"+filename+".jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Shows the picture
	public void display(Pixel pixels[][]) { 
		setTitle("Ray Tracer - Christopher Lenk");
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Loop through each pixel in the ouput picture and set its color values
		for (int i = 0; i < pixels.length; i++) {
			for (int j = 0; j < pixels[i].length; j++) {
				Pixel outPix = pp.getPixel(j, i);
				outPix.setRed(pixels[i][j].getRed());
				outPix.setGreen(pixels[i][j].getGreen());
				outPix.setBlue(pixels[i][j].getBlue());
			}
		}

		add(pp);
		pack();
		setVisible(true);
	}
}
