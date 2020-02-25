package converter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Converter {
	
	public void makeAmbientOcclusionMap(String input, String output) {
	  	BufferedImage originalImage = null;
		BufferedImage img = null;
		
		try {
			if (input.endsWith(".tga")) {
				originalImage = TargaDecoder.getImage(input);
			} else {
				originalImage = ImageIO.read(new File(input) );
			}
			img = new BufferedImage(originalImage.getWidth(),originalImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR); //we need this to support different bit depths
			    
			 int heigth = img.getHeight();
			 int width = img.getWidth();
			 
			 for (int i = 0; i < width; i++) {
			 	for (int j = 0; j < heigth; j++) {
			 		Color originalColor = new Color(originalImage.getRGB(i, j));
			 		Color replacementColor = new Color(255, originalColor.getGreen(), 255);
			    	img.setRGB(i, j, replacementColor.getRGB());
			 	}
			 }
			 
			 //Saves da image
			 File outputfile = new File(output + "as.png");
			 
			 File directory = new File(output);
			 if (!directory.exists()) {
			       directory.mkdir();
			 }
			 
			 ImageIO.write(img, "png", outputfile);
			 
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Can't read ambient occulsion map!", "", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	public void makeSMDIMap(String input, String output, ArrayList<Integer> blueArray) {
		BufferedImage originalImage = null;
		BufferedImage img = null;
		
		try {
			if (input.endsWith(".tga")) {
				originalImage = TargaDecoder.getImage(input);
			} else {
				originalImage = ImageIO.read(new File(input) );
			}
			 img = new BufferedImage(originalImage.getWidth(),originalImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR); //we need this to support different bit depths
			    
			 int heigth = img.getHeight();
			 int width = img.getWidth();

			 int index = 0;
			 
			 for (int i = 0; i < width; i++) {
			 	for (int j = 0; j < heigth; j++) {
			 		Color originalColor = new Color(originalImage.getRGB(i, j));
			 		Color replacementColor = new Color(255, originalColor.getGreen(), blueArray.get(index));
			    	img.setRGB(i, j, replacementColor.getRGB());
			    	index++;
			 	}
			 }
			 
			 //Saves da image
			 File outputfile = new File(output + "smdi.png");
			 
			 File directory = new File(output);
			 if (!directory.exists()) {
			       directory.mkdir();
			 }

			 ImageIO.write(img, "png", outputfile);
			 
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Can't read specular map!", "", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	
	//gets b values from gloss map
	public ArrayList<Integer> getBlueValues(String path) {
		BufferedImage originalImage = null;
		BufferedImage img = null;
		
		ArrayList<Integer> blueArray = new ArrayList<Integer>();
		
		try {
			if (path.endsWith(".tga")) {
				originalImage = TargaDecoder.getImage(path);
			} else {
				originalImage = ImageIO.read(new File(path) );
			}
			img = new BufferedImage(originalImage.getWidth(),originalImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR); //we need this to support different bit depths
		    
		    int heigth = img.getHeight();
		    int width = img.getWidth();
		    
		    for (int i = 0; i < width; i++) {
		        for (int j = 0; j < heigth; j++) {
		            Color c = new Color(originalImage.getRGB(i, j));

		            blueArray.add(c.getBlue());
		        }
		    }
		    
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Can't read gloss map!", "", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return blueArray;
	}
}
