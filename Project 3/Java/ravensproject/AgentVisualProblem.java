package ravensproject;

public class AgentVisualProblem {

	AgentVisualFigure figA;    	
	AgentVisualFigure figB;    	
	AgentVisualFigure figC;    	
	AgentVisualFigure figD;    	
	AgentVisualFigure figE;    	
	AgentVisualFigure figF;    	
	AgentVisualFigure figG;    	
	AgentVisualFigure figH;    	
	AgentVisualFigure fig1;    	
	AgentVisualFigure fig2;    	
	AgentVisualFigure fig3;    	
	AgentVisualFigure fig4;    	
	AgentVisualFigure fig5;    	
	AgentVisualFigure fig6;   	
	AgentVisualFigure fig7;  	
	AgentVisualFigure fig8;
	
	int percentageToDeemEquivalent = 3;

	public AgentVisualProblem(RavensProblem problem) {
		
	    	figA = new AgentVisualFigure(problem.getFigures().get("A"));    	
	    	figB = new AgentVisualFigure(problem.getFigures().get("B"));    	
	    	figC = new AgentVisualFigure(problem.getFigures().get("C"));    	
	    	figD = new AgentVisualFigure(problem.getFigures().get("D"));    	
	    	figE = new AgentVisualFigure(problem.getFigures().get("E"));    	
	    	figF = new AgentVisualFigure(problem.getFigures().get("F"));    	
	    	figG = new AgentVisualFigure(problem.getFigures().get("G"));    	
	    	figH = new AgentVisualFigure(problem.getFigures().get("H"));    	
	    	fig1 = new AgentVisualFigure(problem.getFigures().get("1"));    	
	    	fig2 = new AgentVisualFigure(problem.getFigures().get("2"));    	
	    	fig3 = new AgentVisualFigure(problem.getFigures().get("3"));    	
	    	fig4 = new AgentVisualFigure(problem.getFigures().get("4"));    	
	    	fig5 = new AgentVisualFigure(problem.getFigures().get("5"));    	
	    	fig6 = new AgentVisualFigure(problem.getFigures().get("6"));    	
	    	fig7 = new AgentVisualFigure(problem.getFigures().get("7"));    	
	    	fig8 = new AgentVisualFigure(problem.getFigures().get("8"));   
    	}
	
	public int isItLikeBasicD01() {
		int answer = -1;
		
		int similarityAB = figA.percentSimilarToOtherFigure(figB);
		int similarityBC = figB.percentSimilarToOtherFigure(figC);
		int similarityDE = figD.percentSimilarToOtherFigure(figE);
		int similarityEF = figE.percentSimilarToOtherFigure(figF);
		int similarityGH = figG.percentSimilarToOtherFigure(figH);
		
		if(areTheseNumbersEquivalent(similarityAB, 100, percentageToDeemEquivalent) && areTheseNumbersEquivalent(similarityBC, 100, percentageToDeemEquivalent)) { 
			//THE FIRST ROW IS VIRTUALLY THE SAME ACROSS
			if(areTheseNumbersEquivalent(similarityDE, 100, percentageToDeemEquivalent) && areTheseNumbersEquivalent(similarityEF, 100, percentageToDeemEquivalent)) { 
				//THE SECOND ROW IS VIRTUALLY THE SAME ACROSS
				if(areTheseNumbersEquivalent(similarityGH, 100, percentageToDeemEquivalent)) {
					//THE FIRST PART OF THE THIRD ROW IS VIRTUALLY THE SAME, FIND THE ANSWER WHICH WILL BE VIRTUALLY THE SAME AS WELL
					
					return getBestAnswerIndexAboveThreshold(figH,  95);
					
				}
			}
		}
			
		
		return answer;
	}

	public int isItLikeBasicD02() {
		int answer = -1;
		
		int similarityAE = figA.percentSimilarToOtherFigure(figE);
		int similarityDH = figD.percentSimilarToOtherFigure(figH);
		int similarityBF = figB.percentSimilarToOtherFigure(figF);
		
		if(areTheseNumbersEquivalent(similarityAE, 100, percentageToDeemEquivalent)) { 
			//THE DIAGONAL TOP-LEFT/BOTTOM-RIGHT ARE VIRTUALLY THE SAME 
			if(areTheseNumbersEquivalent(similarityDH, 100, percentageToDeemEquivalent) && areTheseNumbersEquivalent(similarityBF, 100, percentageToDeemEquivalent)) { 
				//THE DH AND BF DIAGONALS ARE VIRTUALLY THE SAME.  SEE IF THERE IS A MATCH TO E AMONG THE ANSWERS 
					
				return getBestAnswerIndexAboveThreshold(figE,  95);
			}
		}
			
		
		return answer;
	}
	
	public int isItLikeBasicD04() {
		int answer = -1;
		
		int similarityAB = figA.percentSimilarToOtherFigure(figB);
		int similarityDE = figD.percentSimilarToOtherFigure(figE);
		int similarityGH = figG.percentSimilarToOtherFigure(figH);
		if(areTheseNumbersEquivalent(similarityAB, similarityDE, percentageToDeemEquivalent) && areTheseNumbersEquivalent(similarityAB, similarityGH, percentageToDeemEquivalent)) {
			//DIFFERENCE BETWEEN A AND B IS SAME AS DIFFERENCE BETWEEN D AND E AND THAT OF G AND H

			int similarityBC = figB.percentSimilarToOtherFigure(figC);
			int similarityEF = figE.percentSimilarToOtherFigure(figF);
			if(areTheseNumbersEquivalent(similarityBC, similarityEF, percentageToDeemEquivalent)) {
				//DIFFERENCE BETWEEN B AND C IS SAME AS DIFFERENCE BETWEEN E AND F
				
				if(areTheseNumbersEquivalent(similarityEF, figH.percentSimilarToOtherFigure(fig1), percentageToDeemEquivalent))
					return 1;
				if(areTheseNumbersEquivalent(similarityEF, figH.percentSimilarToOtherFigure(fig2), percentageToDeemEquivalent))
					return 2;
				if(areTheseNumbersEquivalent(similarityEF, figH.percentSimilarToOtherFigure(fig3), percentageToDeemEquivalent))
					return 3;
				if(areTheseNumbersEquivalent(similarityEF, figH.percentSimilarToOtherFigure(fig4), percentageToDeemEquivalent))
					return 4;
				if(areTheseNumbersEquivalent(similarityEF, figH.percentSimilarToOtherFigure(fig5), percentageToDeemEquivalent))
					return 5;
				if(areTheseNumbersEquivalent(similarityEF, figH.percentSimilarToOtherFigure(fig6), percentageToDeemEquivalent))
					return 6;
				if(areTheseNumbersEquivalent(similarityEF, figH.percentSimilarToOtherFigure(fig7), percentageToDeemEquivalent))
					return 7;
				if(areTheseNumbersEquivalent(similarityEF, figH.percentSimilarToOtherFigure(fig8), percentageToDeemEquivalent))
					return 8;
			}			
		}
			
		
		return answer;
	}
	
	public boolean areTheseNumbersEquivalent(int a, int b, int percentage) {
		float percent = (float)percentage / 100;
		
		float min = (float)b * (1 - percent);
		float max = (float)b * (1 + percent);
		
		if(a >= min && a <= max)
			return true;
		
		return false;
	}
	
	public int getBestAnswerIndexAboveThreshold(AgentVisualFigure figure, int threshold) {
		int index = -1;
		int highestPercentage = -1;
		
		
		int temp = fig1.percentSimilarToOtherFigure(figure);
		if(temp >= threshold) {
			index = 1;
			highestPercentage = temp;
		}
			
		temp = fig2.percentSimilarToOtherFigure(figure);
		if(temp >= threshold && temp > highestPercentage) {
			index = 2;
			highestPercentage = temp;
		}
			
		temp = fig3.percentSimilarToOtherFigure(figure);
		if(temp >= threshold && temp > highestPercentage) {
			index = 3;
			highestPercentage = temp;
		}

		temp = fig4.percentSimilarToOtherFigure(figure);
		if(temp >= threshold && temp > highestPercentage) {
			index = 4;
			highestPercentage = temp;
		}

		temp = fig5.percentSimilarToOtherFigure(figure);
		if(temp >= threshold && temp > highestPercentage) {
			index = 5;
			highestPercentage = temp;
		}

		temp = fig6.percentSimilarToOtherFigure(figure);
		if(temp >= threshold && temp > highestPercentage) {
			index = 6;
			highestPercentage = temp;
		}

		temp = fig7.percentSimilarToOtherFigure(figure);
		if(temp >= threshold && temp > highestPercentage) {
			index = 7;
			highestPercentage = temp;
		}

		temp = fig8.percentSimilarToOtherFigure(figure);
		if(temp >= threshold && temp > highestPercentage) {
			index = 8;
			highestPercentage = temp;
		}

		return index;
	}

}
