package ravensproject;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AgentVisualFigure {

	String filename = "";
	BufferedImage image = null;
	
	float percentBlack = 0f;
	int numberBlackPixels = 0;
	Point centerOfBlack = new Point(0,0);
	int numPixels = 0;
	int numShapes = 0;
	
	enum combinationMethod { OR, AND, XOR }
	
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
	
	/*
	 * COMBINES TWO FIGURES INTO A NEW FIGURE. ALL BLACK PIXELS IN DAD OR MOM ARE COUNTED AS BLACK IN THIS NEW FIGURE
	 */
	public AgentVisualFigure(AgentVisualFigure dad, AgentVisualFigure mom, combinationMethod method) {

		
		image = new BufferedImage(Math.max(dad.image.getWidth(), mom.image.getWidth()), 
				Math.max(dad.image.getHeight(), mom.image.getHeight()), dad.image.getType());
		
		  for(int x = 0; x < image.getWidth(); ++x) {
			  for(int y = 0; y < image.getHeight(); ++y) {
			
				  if(method == combinationMethod.OR) {
					  if(dad.image.getWidth() > x && dad.image.getHeight() > x && !dad.isPixelWhite(x, y))
						  image.setRGB(x, y, new Color(0,0,0).getRGB());
					  else if(mom.image.getWidth() > x && mom.image.getHeight() > x && !mom.isPixelWhite(x, y))
						  image.setRGB(x, y, new Color(0,0,0).getRGB());
					  else
						  image.setRGB(x, y, new Color(255,255,255).getRGB());
				  }
				  else if(method == combinationMethod.XOR) {
					  boolean dadsBlack = false;
					  boolean momsBlack = false;
					  if(dad.image.getWidth() > x && dad.image.getHeight() > x && !dad.isPixelWhite(x, y))
						  dadsBlack = true;
					  if(mom.image.getWidth() > x && mom.image.getHeight() > x && !mom.isPixelWhite(x, y))
						  momsBlack = true;
					  
					  if((dadsBlack && !momsBlack)  || (!dadsBlack && momsBlack))
						  image.setRGB(x, y, new Color(0,0,0).getRGB());
					  else
						  image.setRGB(x, y, new Color(255,255,255).getRGB());
				  }
			  }
		  }
		
		
		getImageData();
	}
	
    private void getImageData() {
    	
    	ensureBlackAndWhiteOnly();
    	
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
		  
		  //countShapes();
		  //ensureBlackAndWhiteOnly();
    }
    
    public void countShapes() {
    	for(int x = 0; x < image.getWidth(); ++x) {
    		for(int y = 0; y < image.getHeight(); ++y) {
    			
    			if(image.getRGB(x,  y) == new Color(0,0,0).getRGB()) {
    				traceNewShape(x, y);
    			}
    			
    		}
    	}
    }
    
    public void traceNewShape(int x, int y) {
    	++numShapes;
    	
    	setPixelAndPeersToNearBlack(x, y);
    }
    
    public void setPixelAndPeersToNearBlack(int x, int y) {
    	image.setRGB(x,  y,  new Color(50,50,50).getRGB());
    	
    	//TOP LEFT PIXEL
    	Point point = new Point(x-1, y-1);
    	if(isPointValidCoordinate(point) && image.getRGB((int)point.getX(), (int)point.getY()) == new Color(0,0,0).getRGB())
    		setPixelAndPeersToNearBlack((int)point.getX(), (int)point.getY());

    	//TOP MIDDLE PIXEL
    	point = new Point(x, y-1);
    	if(isPointValidCoordinate(point) && image.getRGB((int)point.getX(), (int)point.getY()) == new Color(0,0,0).getRGB())
    		setPixelAndPeersToNearBlack((int)point.getX(), (int)point.getY());

    	//TOP RIGHT PIXEL
    	point = new Point(x+1, y-1);
    	if(isPointValidCoordinate(point) && image.getRGB((int)point.getX(), (int)point.getY()) == new Color(0,0,0).getRGB())
    		setPixelAndPeersToNearBlack((int)point.getX(), (int)point.getY());

    	//LEFT PIXEL
    	point = new Point(x-1, y);
    	if(isPointValidCoordinate(point) && image.getRGB((int)point.getX(), (int)point.getY()) == new Color(0,0,0).getRGB())
    		setPixelAndPeersToNearBlack((int)point.getX(), (int)point.getY());

    	//RIGHT PIXEL
    	point = new Point(x+1, y);
    	if(isPointValidCoordinate(point) && image.getRGB((int)point.getX(), (int)point.getY()) == new Color(0,0,0).getRGB())
    		setPixelAndPeersToNearBlack((int)point.getX(), (int)point.getY());

    	//BOTTOM LEFT PIXEL
    	point = new Point(x-1, y+1);
    	if(isPointValidCoordinate(point) && image.getRGB((int)point.getX(), (int)point.getY()) == new Color(0,0,0).getRGB())
    		setPixelAndPeersToNearBlack((int)point.getX(), (int)point.getY());

    	//BOTTOM MIDDLE PIXEL
    	point = new Point(x, y+1);
    	if(isPointValidCoordinate(point) && image.getRGB((int)point.getX(), (int)point.getY()) == new Color(0,0,0).getRGB())
    		setPixelAndPeersToNearBlack((int)point.getX(), (int)point.getY());

    	//BOTTOM RIGHT PIXEL
    	point = new Point(x+1, y+1);
    	if(isPointValidCoordinate(point) && image.getRGB((int)point.getX(), (int)point.getY()) == new Color(0,0,0).getRGB())
    		setPixelAndPeersToNearBlack((int)point.getX(), (int)point.getY());

    }
    
    public boolean isPointValidCoordinate(Point point) {
    	if(point.getX() < 0)
    		return false;
    	if(point.getX() >= image.getWidth())
    		return false;
    	if(point.getY() < 0)
    		return false;
    	if(point.getY() >= image.getHeight())
    		return false;
    	
    	
    	return true;
    }
    
    public void ensureBlackAndWhiteOnly() {
    	for(int x = 0; x < image.getWidth(); ++x) {
    		for(int y = 0; y < image.getHeight(); ++y) {
    			if(isPixelWhite(x, y))
    				image.setRGB(x,  y, new Color(255,255,255).getRGB());
    			else
    				image.setRGB(x,  y, new Color(0,0,0).getRGB());
    		}    		
    	}
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
    
    public float percentSimilarToOtherFigure(AgentVisualFigure compareTo) {
    	float percent = 0;
    	
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
		
		percent = ( (float)(sameCount * 100) / (float)(sameCount + diffCount));
    	
    	
    	return percent;
    }
	
}
