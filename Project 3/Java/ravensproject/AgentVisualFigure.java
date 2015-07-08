package ravensproject;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AgentVisualFigure {

	String filename = "";
	float percentBlack = 0f;
	int numberBlackPixels = 0;
	Point centerOfBlack = new Point(0,0);
	int numPixels = 0;
	BufferedImage image;
	
	public AgentVisualFigure(RavensFigure figure) {
		filename = figure.getVisual();

    	try {
		File file= new File(filename);
		image = ImageIO.read(file);
    	}
    	catch(IOException ex) {
    		
    	}
    	
    	
		getImageData();
	}
	
    private void getImageData() {
    	
    	int numWhite = 0;
    	int numBlack = 0;
    	
    	int blackXSum = 0;
    	int blackYSum = 0;
    	
    	
		  
		  for(int x = 0; x < image.getWidth(); ++x) {
			  for(int y = 0; y < image.getHeight(); ++y) {
				  
				  // Getting pixel color for x,y
				  if(isPixelWhite(x, y))
					  numWhite++;
				  else {
					  numBlack++;
					  
					  blackXSum += x;
					  blackYSum += y;					  
				  }
				  
				  numPixels++;
			  }
		  }
		  
		  
		  
		  numberBlackPixels = numBlack;
		  percentBlack = (float)(numBlack * 100) / (float)(numBlack + numWhite);
		  centerOfBlack = new Point(blackXSum / numBlack, blackYSum / numBlack);
		  
    }

    public boolean isPixelWhite(int x, int y) {

    	int color = image.getRGB(x,y);
    	int red = (color & 0x00ff0000) >> 16;
		int green = (color & 0x0000ff00) >> 8;
		int blue = color & 0x000000ff;
	  
		if(red > 122 && green > 122 && blue > 122)
			return true;
	  
		return false;
    }
    
    public int percentSimilarToOtherFigure(AgentVisualFigure compareTo) {
    	int percent = 0;
    	
    	int sameCount = 0;
    	int diffCount = 0;
    	
		for(int x = 0; x < image.getWidth(); ++x) {
			for(int y = 0; y < image.getHeight(); ++y) {
		
				if(x < compareTo.image.getWidth() && y < compareTo.image.getHeight()) {
					if(isPixelWhite(x, y) == compareTo.isPixelWhite(x, y))
						++sameCount;
					else 
						++diffCount;
				}
				else
					++diffCount;
			}
		}
		
		percent = (int)( (float)(sameCount * 100) / (float)(sameCount + diffCount));
    	
    	
    	return percent;
    }
	
}
