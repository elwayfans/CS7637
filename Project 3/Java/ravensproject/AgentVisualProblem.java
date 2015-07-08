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
		
		if(similarityAB >= 95 && similarityBC >= 95) { 
			//THE FIRST ROW IS VIRTUALLY THE SAME ACROSS
			if(similarityDE >= 95 && similarityEF >= 95) { 
				//THE SECOND ROW IS VIRTUALLY THE SAME ACROSS
				if(similarityGH >= 95) {
					//THE FIRST PART OF THE THIRD ROW IS VIRTUALLY THE SAME, FIND THE ANSWER WHICH WILL BE VIRTUALLY THE SAME AS WELL
					
					if(fig1.percentSimilarToOtherFigure(figH) >= 95)
						return 1;
					if(fig2.percentSimilarToOtherFigure(figH) >= 95)
						return 2;
					if(fig3.percentSimilarToOtherFigure(figH) >= 95)
						return 3;
					if(fig4.percentSimilarToOtherFigure(figH) >= 95)
						return 4;
					if(fig5.percentSimilarToOtherFigure(figH) >= 95)
						return 5;
					if(fig6.percentSimilarToOtherFigure(figH) >= 95)
						return 6;
					if(fig7.percentSimilarToOtherFigure(figH) >= 95)
						return 7;
					if(fig8.percentSimilarToOtherFigure(figH) >= 95)
						return 8;
					
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
		
		if(similarityAE >= 95) { 
			//THE DIAGONAL TOP-LEFT/BOTTOM-RIGHT ARE VIRTUALLY THE SAME 
			if(similarityDH >= 95 && similarityBF >= 95) { 
				//THE DH AND BF DIAGONALS ARE VIRTUALLY THE SAME.  SEE IF THERE IS A MATCH TO E AMONG THE ANSWERS 
					
				if(fig1.percentSimilarToOtherFigure(figE) >= 95)
					return 1;
				if(fig2.percentSimilarToOtherFigure(figE) >= 95)
					return 2;
				if(fig3.percentSimilarToOtherFigure(figE) >= 95)
					return 3;
				if(fig4.percentSimilarToOtherFigure(figE) >= 95)
					return 4;
				if(fig5.percentSimilarToOtherFigure(figE) >= 95)
					return 5;
				if(fig6.percentSimilarToOtherFigure(figE) >= 95)
					return 6;
				if(fig7.percentSimilarToOtherFigure(figE) >= 95)
					return 7;
				if(fig8.percentSimilarToOtherFigure(figE) >= 95)
					return 8;
					
				
			}
		}
			
		
		return answer;
	}

}
