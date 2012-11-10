package rayTracePhase3;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * This class modifies the SimplePicture class of GT Media Project
 * so that it extends JPanel, thus allowing the flexiblity to use it
 * with other swing components
 * 
 * SimplePicture is a class that represents a simple picture.  A simple picture may have
 * an associated file name and a title.  A simple picture has pixels, 
 * width, and height.  A simple picture uses a BufferedImage to 
 * hold the pixels.  You can show a simple picture in a 
 * PictureFrame (a JFrame).
 * 
 * SimplePicture is part of the GT Media Projct
 * Copyright Georgia Institute of Technology 2004
 * @author Barb Ericson ericson@cc.gatech.edu
 * 
 * PicturePanel modifies SimplePicture
 * @author John M. Hunt
 * Covenant College
 */
public class PicturePanel extends JPanel {
	  /////////////////////// Fields /////////////////////////
	  
	  /**
	   * the file name associated with the simple picture
	   */
	  private String fileName;
	  
	  /**
	   * buffered image to hold pixels for the simple picture
	   */
	  private BufferedImage bufferedImage;
	  private ImageIcon imageIcon;
	  /** 
	   * extension for this file (jpg or bmp)
	   */
	  private String extension;
/////////////////////// Constructors /////////////////////////
	  
	  /**
	   * A Constructor that takes no arguments.  All fields will be null.
	   * A no-argument constructor must be given in order for a class to
	   * be able to be subclassed.  By default all subclasses will implicitly
	   * call this in their parent's no argument constructor unless a 
	   * different call to super() is explicitly made as the first line 
	   * of code in a constructor.
	   */
	  public PicturePanel() 
	  {this(200,100);}
	  
	  /**
	   * A Constructor that takes a file name and uses the file to create
	   * a picture
	   * @param fileName the file name to use in creating the picture
	   */
	  public PicturePanel(String fileName)
	  {
	    
	    // load the picture into the buffered image 
	    load(fileName);
	    imageIcon = new ImageIcon(bufferedImage);
	    JLabel displayPicture = new JLabel(imageIcon);
	    add(displayPicture);
	    
	  }
	  
	  /**
	   * A constructor that takes the width and height desired for a picture and
	   * creates a buffered image of that size.  This constructor doesn't 
	   * show the picture.
	   * @param width the desired width
	   * @param height the desired height
	   */
	  public  PicturePanel(int width, int height)
	  {
	    bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    imageIcon = new ImageIcon(bufferedImage);
	    JLabel displayPicture = new JLabel(imageIcon);
	    add(displayPicture);
	    fileName = "None";
	    extension = "jpg";
	    setAllPixelsToAColor(255,255,255);
	  }
	  
	  /**
	   * A constructor that takes the width and height desired for a picture and
	   * creates a buffered image of that size.  It also takes the
	   * color to use for the background of the picture.
	   * @param width the desired width
	   * @param height the desired height
	   * @param red, green, blue background color for the picture
	   */
	  public  PicturePanel(int width, int height, int red, int green, int blue)
	  {
	    this(width,height);
	    setAllPixelsToAColor(red,green,blue);
	  }
	  /**
	   * Method to set the color in the picture to the passed color
	   */
	  public void setAllPixelsToAColor(int red, int green, int blue)
	  {
	    // loop through all x
	    for (int x = 0; x < this.getWidth(); x++)
	    {
	      // loop through all y
	      for (int y = 0; y < this.getHeight(); y++)
	      {
	    	Pixel pix = getPixel(x,y);
	        pix.setRed(red);
	        pix.setGreen(green);
	        pix.setBlue(blue);
	      }
	    }
	  }

	  /**
	   * Method to get the buffered image
	   * @return the buffered image 
	   */
	  public BufferedImage getBufferedImage() 
	  {
	     return bufferedImage;
	  }
	  
	  /**
	   * Method to get a graphics object for this picture to use to draw on
	   * @return a graphics object to use for drawing
	   */
	  public Graphics getGraphics()
	  {
	    return bufferedImage.getGraphics();
	  }
	  
	  /**
	   * Method to get a Graphics2D object for this picture which can
	   * be used to do 2D drawing on the picture
	   */
	  public Graphics2D createGraphics()
	  {
	    return bufferedImage.createGraphics();
	  }
	  
	  /**
	   * Method to get the file name associated with the picture
	   * @return  the file name associated with the picture
	   */
	  public String getFileName() { return fileName; }
	  
	  /**
	   * Method to set the file name
	   * @param name the full pathname of the file
	   */
	  public void setFileName(String name)
	  {
	    fileName = name;
	  }
	  /**
	   * Method to get the width of the picture in pixels
	   * @return the width of the picture in pixels
	   */
	  public int getWidth() { return bufferedImage.getWidth(); }
	  
	  /**
	   * Method to get the height of the picture in pixels
	   * @return  the height of the picture in pixels
	   */
	  public int getHeight() { return bufferedImage.getHeight(); }
	  /**
	   * Method to get an image from the picture
	   * @return  the buffered image since it is an image
	   */
	  public Image getImage()
	  {
	    return bufferedImage;
	  }
	  
	  /**
	   * Method to return the pixel value as an int for the given x and y location
	   * @param x the x coordinate of the pixel
	   * @param y the y coordinate of the pixel
	   * @return the pixel value as an integer (alpha, red, green, blue)
	   */
	  public int getBasicPixel(int x, int y)
	  {
	     return bufferedImage.getRGB(x,y);
	  }
	     
	  /** 
	   * Method to set the value of a pixel in the picture from an int
	   * @param x the x coordinate of the pixel
	   * @param y the y coordinate of the pixel
	   * @param rgb the new rgb value of the pixel (alpha, red, green, blue)
	   */     
	  public void setBasicPixel(int x, int y, int rgb)
	  {
	    bufferedImage.setRGB(x,y,rgb);
	  }
	   
	  /**
	   * Method to get a pixel object for the given x and y location
	   * @param x  the x location of the pixel in the picture
	   * @param y  the y location of the pixel in the picture
	   * @return a Pixel object for this location
	   */
	  public Pixel getPixel(int x, int y)
	  {
	    // create the pixel object for this picture and the given x and y location
	    Pixel pixel = new Pixel(this,x,y);
	    return pixel;
	  }
	  
	  /**
	   * Method to get a one-dimensional array of Pixels for this simple picture
	   * @return a one-dimensional array of Pixel objects starting with y=0
	   * to y=height-1 and x=0 to x=width-1.
	   */
	  public Pixel[] getPixels()
	  {
	    int width = getWidth();
	    int height = getHeight();
	    Pixel[] pixelArray = new Pixel[width * height];
	    
	    // loop through height rows from top to bottom
	    for (int row = 0; row < height; row++) 
	      for (int col = 0; col < width; col++) 
	        pixelArray[row * width + col] = new Pixel(this,col,row);
	     
	    return pixelArray;
	  }
	  
	  /**
	   * Method to set a one-dimensional array of Pixels for this simple picture
	   * @return a one-dimensional array of Pixel objects starting with y=0
	   * to y=height-1 and x=0 to x=width-1.
	   * Note the caller is responsible for making sure the array is the right size for the picture
	   */
	  public void setPixels(Pixel[] inPixels)
	  {
	    int width = getWidth();
	    int height = getHeight();
	    
	    // loop through height rows from top to bottom
	    /*
	    for (int row = 0; row < height; row++) 
	      for (int col = 0; col < width; col++) 
	        inPixels[row * width + col];
	     */
	    Pixel[] pixels = getPixels();
	    for(int i = 0; i < inPixels.length; i++){
	 	   pixels[i].setRed(inPixels[i].getRed());
	 	  pixels[i].setGreen(inPixels[i].getGreen());
	 	 pixels[i].setBlue(inPixels[i].getBlue());
	    }
	 	   
	   
	  }
	  
	  /**
	   * Method to load the buffered image with the passed image
	   * @param image  the image to use
	   */
	  public void load(Image image)
	  {
	    // get a graphics context to use to draw on the buffered image
	    Graphics2D graphics2d = bufferedImage.createGraphics();
	    
	    // draw the image on the buffered image starting at 0,0
	    graphics2d.drawImage(image,0,0,null);
	    repaint();
	  }
	  /**
	   * Method to load the picture from the passed file name
	   * @param fileName the file name to use to load the picture from
	   */
	  public void loadOrFail(String fileName) throws IOException
	  {
	     // set the current picture's file name
	    this.fileName = fileName;
	    
	    // set the extension
	    int posDot = fileName.indexOf('.');
	    if (posDot >= 0)
	      this.extension = fileName.substring(posDot + 1);
	
	    File file = new File(this.fileName);

	    if (!file.canRead()) 
	    {
	        throw new IOException(this.fileName +
	                              " could not be opened. Check that you specified the path");
	    }
	    
	    bufferedImage = ImageIO.read(file);
	  }


	  /**
	   * Method to write the contents of the picture to a file with 
	   * the passed name without throwing errors
	   * @param fileName the name of the file to write the picture to
	   * @return true if success else false
	   */
	  public boolean load(String fileName)
	  {
	      try {
	          this.loadOrFail(fileName);
	          return true;

	      } catch (Exception ex) {
	          System.out.println("There was an error trying to open " + fileName);
	          bufferedImage = new BufferedImage(600,200,
	                                            BufferedImage.TYPE_INT_RGB);
	          addMessage("Couldn't load " + fileName,5,100);
	          return false;
	      }
	          
	  }


	  /**
	   * Method to load the picture from the passed file name
	   * this just calls load(fileName) and is for name compatibility
	   * @param fileName the file name to use to load the picture from
	   * @return true if success else false
	   */
	  public boolean loadImage(String fileName)
	  {
	      return load(fileName);
	 }
	  /**
	   * Method to draw a message as a string on the buffered image 
	   * @param message the message to draw on the buffered image
	   * @param xPos  the leftmost point of the string in x 
	   * @param yPos  the bottom of the string in y
	   */
	  public void addMessage(String message, int xPos, int yPos)
	  {
	    // get a graphics context to use to draw on the buffered image
	    Graphics2D graphics2d = bufferedImage.createGraphics();
	    
	    // set the color to white
	    graphics2d.setPaint(Color.white);
	    
	    // set the font to Helvetica bold style and size 16
	    graphics2d.setFont(new Font("Helvetica",Font.BOLD,16));
	    
	    // draw the message
	    graphics2d.drawString(message,xPos,yPos);
	    
	  }
	  
	  /**
	   * Method to draw a string at the given location on the picture
	   * @param text the text to draw
	   * @param xPos the left x for the text 
	   * @param yPos the top y for the text
	   */
	  public void drawString(String text, int xPos, int yPos)
	  {
	    addMessage(text,xPos,yPos);
	  }
	  
	  /**
	    * Method to create a new picture by scaling the current
	    * picture by the given x and y factors
	    * @param xFactor the amount to scale in x
	    * @param yFactor the amount to scale in y
	    * @return the resulting picture
	    */
	  /*
	   public Picture scale(double xFactor, double yFactor)
	   {
	     // set up the scale tranform
	     AffineTransform scaleTransform = new AffineTransform();
	     scaleTransform.scale(xFactor,yFactor);
	     
	     // create a new picture object that is the right size
	     Picture result = new Picture((int) (getWidth() * xFactor),
	                                  (int) (getHeight() * yFactor));
	     
	     // get the graphics 2d object to draw on the result
	     Graphics graphics = result.getGraphics();
	     Graphics2D g2 = (Graphics2D) graphics;
	     
	     // draw the current image onto the result image scaled
	     g2.drawImage(this.getImage(),scaleTransform,null);
	     
	     return result;
	   }
	   */
	   /**
	    * Method to create a new picture of the passed width. 
	    * The aspect ratio of the width and height will stay
	    * the same.
	    * @param width the desired width
	    * @return the resulting picture
	    */
	   /*
	   public Picture getPictureWithWidth(int width)
	   {
	     // set up the scale tranform
	     double xFactor = (double) width / this.getWidth();
	     Picture result = scale(xFactor,xFactor);
	     return result;
	   }
	   */
	   
	   /**
	    * Method to create a new picture of the passed height. 
	    * The aspect ratio of the width and height will stay
	    * the same.
	    * @param height the desired height
	    * @return the resulting picture
	    */
	   /*
	   public Picture getPictureWithHeight(int height)
	   {
	     // set up the scale tranform
	     double yFactor = (double) height / this.getHeight();
	     Picture result = scale(yFactor,yFactor);
	     return result;
	   }
	   */


}
