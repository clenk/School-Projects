package rayTracePhase3;

//Christopher Lenk for COS 150
//Lets user pick size of image to raytrace

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.*;

public class GUI extends JFrame {
	private int wid;
	private int hi;
	private JTextField widthTxt = new JTextField("800", 5); 
	private JTextField heightTxt = new JTextField("600", 5); 
	private JTextField fileTxt = new JTextField("", 12); 
	private JTextField scenTxt = new JTextField("scene", 12); 
	
	//Getters
	public int getWid() {
		return wid;
	}
	public int getHi() {
		return hi;
	}

	//Listener for the "Okay" button
	private class OKButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				wid = Integer.parseInt(widthTxt.getText());
				hi = Integer.parseInt(heightTxt.getText());
			} catch (NumberFormatException nfe) { //Handle user inputting stuff other than numbers
				JOptionPane.showMessageDialog(null, "Please enter numerals only.");
			}
			
			//Check filename
			String filename = fileTxt.getText();
			String ssf = scenTxt.getText() + ".ssf";
			
			//Make sure a scene file has been provided
			try {
				if (filename == null) {
					new RayTracer(wid, hi);
				} else {
					new RayTracer(wid, hi, filename, ssf);
				}
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(null, "Valid scene file required.");
			} catch (ClassNotFoundException e2) {
				JOptionPane.showMessageDialog(null, "Couldn't find one of the classes in the scene file.");
			}
			
		}
	}
	// action listener builder
	public OKButtonListener buildOKButtonListener() {
		return new OKButtonListener();
	}
	
	//Constructor
	public GUI() {
		setTitle("Ray Tracer - Set Size");
		setSize(300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel pMain = new JPanel();
		JPanel pWidth = new JPanel();
		JPanel pHeight = new JPanel();
		JPanel pFile = new JPanel();
		JPanel pBut = new JPanel();
		JPanel pScen = new JPanel();
		pMain.setLayout(new GridLayout(3, 1));
		
		GridLayout layout = new GridLayout(0,1);
		pMain.setLayout(layout);
		
		JLabel widLabl = new JLabel("Enter Width: ");
		JLabel highLabl = new JLabel("Enter Height: ");
		JLabel fileLabl = new JLabel("Enter Image Filename: ");
		JLabel jpgLabl = new JLabel(".jpg");
		JLabel scenLabl = new JLabel("Enter Scene Filename: ");
		JLabel ssfLabl = new JLabel(".ssf");
		pWidth.add(widLabl);
		pWidth.add(widthTxt);
		pHeight.add(highLabl);
		pHeight.add(heightTxt);
		pFile.add(fileLabl);
		pFile.add(fileTxt);
		pFile.add(jpgLabl);
		pScen.add(scenLabl);
		pScen.add(scenTxt);
		pScen.add(ssfLabl);
		
		
		JButton okBut = new JButton("Okay");
		okBut.addActionListener(buildOKButtonListener());
		pBut.add(okBut);

		pMain.add(pWidth);
		pMain.add(pHeight);
		pMain.add(pFile);
		pMain.add(pScen);
		pMain.add(pBut);
		add(pMain);
		pack();
		setVisible(true);
	}
	
	public static void main(String args[]) {
		new GUI();
	}
}
