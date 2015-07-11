package ravensproject;

import java.util.ArrayList;

import ravensproject.AgentVisualFigure.combinationMethod;

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
	
	float percentageToDeemEquivalent = 3;
	float percentageToDeemEquivalent_Exactly = 1.5f;
	float percentageToDeemEquivalent_KindOf = 6;
	float percentageToDeemEquivalent_PreciselyExactly = .5f;

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
	
	public int areShapesEquvalentAlongRows() {  //LIKE BASIC D-01
		int answer = -1;
		
		float similarityAB = figA.percentSimilarToOtherFigure(figB);
		float similarityBC = figB.percentSimilarToOtherFigure(figC);
		float similarityDE = figD.percentSimilarToOtherFigure(figE);
		float similarityEF = figE.percentSimilarToOtherFigure(figF);
		float similarityGH = figG.percentSimilarToOtherFigure(figH);
		
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

	public int areTheNumberOfShapesTheSameDiagonally() { //LIKE BASIC D-12
		int answer = -1;
		
		if(figD.numShapes == figH.numShapes && figD.numShapes != figG.numShapes) {
			//MIGHT HAVE A DIAGONAL SHAPE COUNT DEALIO GOING
			if(figA.numShapes == figE.numShapes && figA.numShapes != figD.numShapes) {
				//STILL LOOKS LIKE WE MIGHT HAVE A DIAGONAL SHAPE COUNT DEALIO GOING
				if(figB.numShapes == figF.numShapes && figB.numShapes != figC.numShapes && figB.numShapes != figA.numShapes) {
					//I THINK WE HAVE A DIAGONAL SHAPE COUNT DEALIO GOING
					
					//REMOVE ANY ANSWERS WHICH ARE THE SAME AS A-H
					boolean answer1 = true;
					boolean answer2 = true;
					boolean answer3 = true;
					boolean answer4 = true;
					boolean answer5 = true;
					boolean answer6 = true;
					boolean answer7 = true;
					boolean answer8 = true;
					
					if(isThisAnswerTheSameAsAnyNonAnswer(fig1, percentageToDeemEquivalent_Exactly) > -1)
						answer1 = false;
					if(isThisAnswerTheSameAsAnyNonAnswer(fig2, percentageToDeemEquivalent_Exactly) > -1)
						answer2 = false;
					if(isThisAnswerTheSameAsAnyNonAnswer(fig3, percentageToDeemEquivalent_Exactly) > -1)
						answer3 = false;
					if(isThisAnswerTheSameAsAnyNonAnswer(fig4, percentageToDeemEquivalent_Exactly) > -1)
						answer4 = false;
					if(isThisAnswerTheSameAsAnyNonAnswer(fig5, percentageToDeemEquivalent_Exactly) > -1)
						answer5 = false;
					if(isThisAnswerTheSameAsAnyNonAnswer(fig6, percentageToDeemEquivalent_Exactly) > -1)
						answer6 = false;
					if(isThisAnswerTheSameAsAnyNonAnswer(fig7, percentageToDeemEquivalent_Exactly) > -1)
						answer7 = false;
					if(isThisAnswerTheSameAsAnyNonAnswer(fig8, percentageToDeemEquivalent_Exactly) > -1)
						answer8 = false;

					if(answer1 && fig1.numShapes != figE.numShapes) 
						answer1 = false;
					
					if(answer2 && fig2.numShapes != figE.numShapes) 
						answer2 = false;

					if(answer3 && fig3.numShapes != figE.numShapes) 
						answer3 = false;

					if(answer4 && fig4.numShapes != figE.numShapes) 
						answer4 = false;

					if(answer5 && fig5.numShapes != figE.numShapes) 
						answer5 = false;

					if(answer6 && fig6.numShapes != figE.numShapes) 
						answer6 = false;

					if(answer7 && fig7.numShapes != figE.numShapes) 
						answer7 = false;

					if(answer8 && fig8.numShapes != figE.numShapes) 
						answer8 = false;

					//IF A AND E ARE NOT THE SAME, THE ANSWER SHOULD NOT BE THE SAME EITHER
					if(!areTheseNumbersEquivalent(figA.numberBlackPixels,  figE.numberBlackPixels, percentageToDeemEquivalent_Exactly)) {
						//for any answer left, remove them if they are the same as A or E
						if(answer1) {
							if(areTheseNumbersEquivalent(figA.numberBlackPixels,  fig1.numberBlackPixels, percentageToDeemEquivalent_Exactly) ||
									areTheseNumbersEquivalent(figE.numberBlackPixels,  fig1.numberBlackPixels, percentageToDeemEquivalent_Exactly))
								answer1 = false;
						}
						if(answer2) {
							if(areTheseNumbersEquivalent(figA.numberBlackPixels,  fig2.numberBlackPixels, percentageToDeemEquivalent_Exactly) ||
									areTheseNumbersEquivalent(figE.numberBlackPixels,  fig2.numberBlackPixels, percentageToDeemEquivalent_Exactly))
								answer2 = false;
						}
						if(answer3) {
							if(areTheseNumbersEquivalent(figA.numberBlackPixels,  fig3.numberBlackPixels, percentageToDeemEquivalent_Exactly) ||
									areTheseNumbersEquivalent(figE.numberBlackPixels,  fig3.numberBlackPixels, percentageToDeemEquivalent_Exactly))
								answer3 = false;
						}
						if(answer4) {
							if(areTheseNumbersEquivalent(figA.numberBlackPixels,  fig4.numberBlackPixels, percentageToDeemEquivalent_Exactly) ||
									areTheseNumbersEquivalent(figE.numberBlackPixels,  fig4.numberBlackPixels, percentageToDeemEquivalent_Exactly))
								answer4 = false;
						}
						if(answer5) {
							if(areTheseNumbersEquivalent(figA.numberBlackPixels,  fig5.numberBlackPixels, percentageToDeemEquivalent_Exactly) ||
									areTheseNumbersEquivalent(figE.numberBlackPixels,  fig5.numberBlackPixels, percentageToDeemEquivalent_Exactly))
								answer5 = false;
						}
						if(answer6) {
							if(areTheseNumbersEquivalent(figA.numberBlackPixels,  fig6.numberBlackPixels, percentageToDeemEquivalent_Exactly) ||
									areTheseNumbersEquivalent(figE.numberBlackPixels,  fig6.numberBlackPixels, percentageToDeemEquivalent_Exactly))
								answer6 = false;
						}
						if(answer7) {
							if(areTheseNumbersEquivalent(figA.numberBlackPixels,  fig7.numberBlackPixels, percentageToDeemEquivalent_Exactly) ||
									areTheseNumbersEquivalent(figE.numberBlackPixels,  fig7.numberBlackPixels, percentageToDeemEquivalent_Exactly))
								answer7 = false;
						}
						if(answer8) {
							if(areTheseNumbersEquivalent(figA.numberBlackPixels,  fig8.numberBlackPixels, percentageToDeemEquivalent_Exactly) ||
									areTheseNumbersEquivalent(figE.numberBlackPixels,  fig8.numberBlackPixels, percentageToDeemEquivalent_Exactly))
								answer8 = false;
						}
						
						//if any answer still exists, it's right
						if(answer1)
							return 1;
						if(answer2)
							return 2;
						if(answer3)
							return 3;
						if(answer4)
							return 4;
						if(answer5)
							return 5;
						if(answer6)
							return 6;
						if(answer7)
							return 7;
						if(answer8)
							return 8;
					}


				}
			}
		}
		
		return answer;
	}

	public int isItAnUpsideRightSideTriangleDealio() { //LIKE BASIC E12
		int answer = -1;
		
		if(allFiguresContainTriangles(true)) {
			if(upsideDownRightSideUpTriangleMathWorks(figA, figD, figG)) {
				//A+D=G
				if(upsideDownRightSideUpTriangleMathWorks(figB, figE, figH)) {
					//B+E=H
					if(upsideDownRightSideUpTriangleMathWorks(figA, figB, figC)) {
						//A+B=C
						if(upsideDownRightSideUpTriangleMathWorks(figD, figE, figF)) {
							//D+E=F
							
							//LOOKS LIKE WE GOTS OURSELF A TRIANGLE BONANZA, YA'LL!!! YEEEEHAW!!!
							if(upsideDownRightSideUpTriangleMathWorks(figC, figF, fig1) &&
									upsideDownRightSideUpTriangleMathWorks(figG, figH, fig1) &&
									(fig1.numRightSideUpTriangles == 0 || fig1.numUpsideDownTriangles == 0))
								return 1;
							if(upsideDownRightSideUpTriangleMathWorks(figC, figF, fig2) &&
									upsideDownRightSideUpTriangleMathWorks(figG, figH, fig2) &&
									(fig2.numRightSideUpTriangles == 0 || fig2.numUpsideDownTriangles == 0))
								return 2;
							if(upsideDownRightSideUpTriangleMathWorks(figC, figF, fig3) &&
									upsideDownRightSideUpTriangleMathWorks(figG, figH, fig3) &&
									(fig3.numRightSideUpTriangles == 0 || fig3.numUpsideDownTriangles == 0))
								return 3;
							if(upsideDownRightSideUpTriangleMathWorks(figC, figF, fig4) &&
									upsideDownRightSideUpTriangleMathWorks(figG, figH, fig4) &&
									(fig4.numRightSideUpTriangles == 0 || fig4.numUpsideDownTriangles == 0))
								return 4;
							if(upsideDownRightSideUpTriangleMathWorks(figC, figF, fig5) &&
									upsideDownRightSideUpTriangleMathWorks(figG, figH, fig5) &&
									(fig5.numRightSideUpTriangles == 0 || fig5.numUpsideDownTriangles == 0))
								return 5;
							if(upsideDownRightSideUpTriangleMathWorks(figC, figF, fig6) &&
									upsideDownRightSideUpTriangleMathWorks(figG, figH, fig6) &&
									(fig6.numRightSideUpTriangles == 0 || fig6.numUpsideDownTriangles == 0))
								return 6;
							if(upsideDownRightSideUpTriangleMathWorks(figC, figF, fig7) &&
									upsideDownRightSideUpTriangleMathWorks(figG, figH, fig7) &&
									(fig7.numRightSideUpTriangles == 0 || fig7.numUpsideDownTriangles == 0))
								return 7;
							if(upsideDownRightSideUpTriangleMathWorks(figC, figF, fig8) &&
									upsideDownRightSideUpTriangleMathWorks(figG, figH, fig8) &&
									(fig8.numRightSideUpTriangles == 0 || fig8.numUpsideDownTriangles == 0))
								return 8;
						}
					}
				}
			}
		}
		
		return answer;
	}
	
	public boolean upsideDownRightSideUpTriangleMathWorks(AgentVisualFigure firstFig, AgentVisualFigure secondFig, AgentVisualFigure thirdFig) {
		int A = firstFig.numRightSideUpTriangles - firstFig.numUpsideDownTriangles;
		int B = secondFig.numRightSideUpTriangles - secondFig.numUpsideDownTriangles;
		int C = thirdFig.numRightSideUpTriangles - thirdFig.numUpsideDownTriangles;
		
		if(A + B == C)
			return true;
		
		return false;
	}
	
	public boolean allFiguresContainTriangles(boolean mustContainUpsideDownAndRightSideUp) {
		boolean foundRightSideUp = false;
		boolean foundUpsideDown = false;
		
		if(figA.numRightSideUpTriangles == 0 && figA.numUpsideDownTriangles == 0)
			return false;
		if(figA.numRightSideUpTriangles > 0)
			foundRightSideUp = true;
		if(figA.numUpsideDownTriangles > 0)
			foundUpsideDown = true;
		
		if(figB.numRightSideUpTriangles == 0 && figB.numUpsideDownTriangles == 0)
			return false;
		if(figB.numRightSideUpTriangles > 0)
			foundRightSideUp = true;
		if(figB.numUpsideDownTriangles > 0)
			foundUpsideDown = true;
		
		if(figC.numRightSideUpTriangles == 0 && figC.numUpsideDownTriangles == 0)
			return false;
		if(figC.numRightSideUpTriangles > 0)
			foundRightSideUp = true;
		if(figC.numUpsideDownTriangles > 0)
			foundUpsideDown = true;
		
		if(figD.numRightSideUpTriangles == 0 && figD.numUpsideDownTriangles == 0)
			return false;
		if(figD.numRightSideUpTriangles > 0)
			foundRightSideUp = true;
		if(figD.numUpsideDownTriangles > 0)
			foundUpsideDown = true;
		
		if(figE.numRightSideUpTriangles == 0 && figE.numUpsideDownTriangles == 0)
			return false;
		if(figE.numRightSideUpTriangles > 0)
			foundRightSideUp = true;
		if(figE.numUpsideDownTriangles > 0)
			foundUpsideDown = true;
		
		if(figF.numRightSideUpTriangles == 0 && figF.numUpsideDownTriangles == 0)
			return false;
		if(figF.numRightSideUpTriangles > 0)
			foundRightSideUp = true;
		if(figF.numUpsideDownTriangles > 0)
			foundUpsideDown = true;
		
		if(figG.numRightSideUpTriangles == 0 && figG.numUpsideDownTriangles == 0)
			return false;
		if(figG.numRightSideUpTriangles > 0)
			foundRightSideUp = true;
		if(figG.numUpsideDownTriangles > 0)
			foundUpsideDown = true;
		
		if(figH.numRightSideUpTriangles == 0 && figH.numUpsideDownTriangles == 0)
			return false;
		if(figH.numRightSideUpTriangles > 0)
			foundRightSideUp = true;
		if(figH.numUpsideDownTriangles > 0)
			foundUpsideDown = true;
		
		if(fig1.numRightSideUpTriangles == 0 && fig1.numUpsideDownTriangles == 0)
			return false;
		if(fig1.numRightSideUpTriangles > 0)
			foundRightSideUp = true;
		if(fig1.numUpsideDownTriangles > 0)
			foundUpsideDown = true;
		
		if(fig2.numRightSideUpTriangles == 0 && fig2.numUpsideDownTriangles == 0)
			return false;
		if(fig2.numRightSideUpTriangles > 0)
			foundRightSideUp = true;
		if(fig2.numUpsideDownTriangles > 0)
			foundUpsideDown = true;
		
		if(fig3.numRightSideUpTriangles == 0 && fig3.numUpsideDownTriangles == 0)
			return false;
		if(fig3.numRightSideUpTriangles > 0)
			foundRightSideUp = true;
		if(fig3.numUpsideDownTriangles > 0)
			foundUpsideDown = true;
		
		if(fig4.numRightSideUpTriangles == 0 && fig4.numUpsideDownTriangles == 0)
			return false;
		if(fig4.numRightSideUpTriangles > 0)
			foundRightSideUp = true;
		if(fig4.numUpsideDownTriangles > 0)
			foundUpsideDown = true;
		
		if(fig5.numRightSideUpTriangles == 0 && fig5.numUpsideDownTriangles == 0)
			return false;
		if(fig5.numRightSideUpTriangles > 0)
			foundRightSideUp = true;
		if(fig5.numUpsideDownTriangles > 0)
			foundUpsideDown = true;
		
		if(fig6.numRightSideUpTriangles == 0 && fig6.numUpsideDownTriangles == 0)
			return false;
		if(fig6.numRightSideUpTriangles > 0)
			foundRightSideUp = true;
		if(fig6.numUpsideDownTriangles > 0)
			foundUpsideDown = true;
		
		if(fig7.numRightSideUpTriangles == 0 && fig7.numUpsideDownTriangles == 0)
			return false;
		if(fig7.numRightSideUpTriangles > 0)
			foundRightSideUp = true;
		if(fig7.numUpsideDownTriangles > 0)
			foundUpsideDown = true;
		
		if(fig8.numRightSideUpTriangles == 0 && fig8.numUpsideDownTriangles == 0)
			return false;
		if(fig8.numRightSideUpTriangles > 0)
			foundRightSideUp = true;
		if(fig8.numUpsideDownTriangles > 0)
			foundUpsideDown = true;
		
		if(!mustContainUpsideDownAndRightSideUp || (foundRightSideUp && foundUpsideDown))
			return true;
		
		return false;

	}
	
	
	public int isItAPixelSum() { //LIKE BASIC E-04
		int answer = -1;
		
		if(areTheseNumbersEquivalent(figD.numberBlackPixels + figG.numberBlackPixels,  figA.numberBlackPixels, percentageToDeemEquivalent_Exactly)) {
			//G+D=A
			if(areTheseNumbersEquivalent(figE.numberBlackPixels + figH.numberBlackPixels,  figB.numberBlackPixels, percentageToDeemEquivalent_Exactly)) {
				//E+H=B
				if(areTheseNumbersEquivalent(figB.numberBlackPixels + figC.numberBlackPixels,  figA.numberBlackPixels, percentageToDeemEquivalent_Exactly)) {
					//B+C=A
					if(areTheseNumbersEquivalent(figF.numberBlackPixels + figE.numberBlackPixels,  figD.numberBlackPixels, percentageToDeemEquivalent_Exactly)) {
						//F+E=D

						ArrayList<Integer> answers = new ArrayList<Integer>();

						
						//IF AN ANSWER+F=C AND +H=G, THAT'S IT!
						if(areTheseNumbersEquivalent(fig1.numberBlackPixels + figF.numberBlackPixels,  figC.numberBlackPixels, percentageToDeemEquivalent_Exactly) &&
								areTheseNumbersEquivalent(fig1.numberBlackPixels + figH.numberBlackPixels,  figG.numberBlackPixels, percentageToDeemEquivalent_Exactly))
							answers.add(1);
						if(areTheseNumbersEquivalent(fig2.numberBlackPixels + figF.numberBlackPixels,  figC.numberBlackPixels, percentageToDeemEquivalent_Exactly) &&
								areTheseNumbersEquivalent(fig2.numberBlackPixels + figH.numberBlackPixels,  figG.numberBlackPixels, percentageToDeemEquivalent_Exactly))
							answers.add(2);
						if(areTheseNumbersEquivalent(fig3.numberBlackPixels + figF.numberBlackPixels,  figC.numberBlackPixels, percentageToDeemEquivalent_Exactly) &&
								areTheseNumbersEquivalent(fig3.numberBlackPixels + figH.numberBlackPixels,  figG.numberBlackPixels, percentageToDeemEquivalent_Exactly))
							answers.add(3);
						if(areTheseNumbersEquivalent(fig4.numberBlackPixels + figF.numberBlackPixels,  figC.numberBlackPixels, percentageToDeemEquivalent_Exactly) &&
								areTheseNumbersEquivalent(fig4.numberBlackPixels + figH.numberBlackPixels,  figG.numberBlackPixels, percentageToDeemEquivalent_Exactly))
							answers.add(4);
						if(areTheseNumbersEquivalent(fig5.numberBlackPixels + figF.numberBlackPixels,  figC.numberBlackPixels, percentageToDeemEquivalent_Exactly) &&
								areTheseNumbersEquivalent(fig5.numberBlackPixels + figH.numberBlackPixels,  figG.numberBlackPixels, percentageToDeemEquivalent_Exactly))
							answers.add(5);
						if(areTheseNumbersEquivalent(fig6.numberBlackPixels + figF.numberBlackPixels,  figC.numberBlackPixels, percentageToDeemEquivalent_Exactly) &&
								areTheseNumbersEquivalent(fig6.numberBlackPixels + figH.numberBlackPixels,  figG.numberBlackPixels, percentageToDeemEquivalent_Exactly))
							answers.add(6);
						if(areTheseNumbersEquivalent(fig7.numberBlackPixels + figF.numberBlackPixels,  figC.numberBlackPixels, percentageToDeemEquivalent_Exactly) &&
								areTheseNumbersEquivalent(fig7.numberBlackPixels + figH.numberBlackPixels,  figG.numberBlackPixels, percentageToDeemEquivalent_Exactly))
							answers.add(7);
						if(areTheseNumbersEquivalent(fig8.numberBlackPixels + figF.numberBlackPixels,  figC.numberBlackPixels, percentageToDeemEquivalent_Exactly) &&
								areTheseNumbersEquivalent(fig8.numberBlackPixels + figH.numberBlackPixels,  figG.numberBlackPixels, percentageToDeemEquivalent_Exactly))
							answers.add(8);
						
						
						if(answers.size() == 0)
							return -1;
						if(answers.size() == 1)
							return answers.get(0);
						
						//MORE THAN ONE ANSWER STILL - REMOVE ANY WHICH ARE SAME AS A-H THEN TRY AGAIN
						for(int i = 0; i < answers.size(); ++i) {
							if(isThisAnswerTheSameAsAnyNonAnswer(getFigureByIndex(answers.get(i)), percentageToDeemEquivalent) > -1) {
								answers.remove(i);
								--i;
							}								
						}
						
						if(answers.size() == 1)
							return answers.get(0);
					}
				}
			}
		}
			
		
		return answer;
	}
	
	
	private AgentVisualFigure getFigureByIndex(int index) {
		if(index == 1)
			return fig1;
		if(index == 2)
			return fig2;
		if(index == 3)
			return fig3;
		if(index == 4)
			return fig4;
		if(index == 5)
			return fig5;
		if(index == 6)
			return fig6;
		if(index == 7)
			return fig7;
		if(index == 8)
			return fig8;

		return null;
	}
	
	public int areShapesEquivalentAlongDiagonals() { //LIKE BASIC D-02
		int answer = -1;
		
		float similarityAE = figA.percentSimilarToOtherFigure(figE);
		float similarityDH = figD.percentSimilarToOtherFigure(figH);
		float similarityBF = figB.percentSimilarToOtherFigure(figF);
		
		if(areTheseNumbersEquivalent(similarityAE, 100, percentageToDeemEquivalent)) { 
			//THE DIAGONAL TOP-LEFT/BOTTOM-RIGHT ARE VIRTUALLY THE SAME 
			if(areTheseNumbersEquivalent(similarityDH, 100, percentageToDeemEquivalent) && areTheseNumbersEquivalent(similarityBF, 100, percentageToDeemEquivalent)) { 
				//THE DH AND BF DIAGONALS ARE VIRTUALLY THE SAME.  SEE IF THERE IS A MATCH TO E AMONG THE ANSWERS 
					
				return getBestAnswerIndexAboveThreshold(figE,  95);
			}
		}
			
		
		return answer;
	}
	
	public int isTheChangeInShapeConsitentAcrossRowsColumns() {  //LIKE BASIC D-04
		int answer = -1;
		
		float similarityAB = figA.percentSimilarToOtherFigure(figB);
		float similarityDE = figD.percentSimilarToOtherFigure(figE);
		float similarityGH = figG.percentSimilarToOtherFigure(figH);
		if(areTheseNumbersEquivalent(similarityAB, similarityDE, percentageToDeemEquivalent) && areTheseNumbersEquivalent(similarityAB, similarityGH, percentageToDeemEquivalent)) {
			//DIFFERENCE BETWEEN A AND B IS SAME AS DIFFERENCE BETWEEN D AND E AND THAT OF G AND H

			float similarityBC = figB.percentSimilarToOtherFigure(figC);
			float similarityEF = figE.percentSimilarToOtherFigure(figF);
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
	
	public int isTheORComboOfRowColumnEndsEquivalentToTheMiddle() { //LIKE BASIC E-06
		int answer = -1;
		
		AgentVisualFigure combinationOfAG = new AgentVisualFigure(figA, figG, combinationMethod.OR);
		float similarityD = combinationOfAG.percentSimilarToOtherFigure(figD);
		if(areTheseNumbersEquivalent(similarityD, 100, percentageToDeemEquivalent)) {
			//THE OR OF A AND G EQUALS D
			AgentVisualFigure combinationOfBH = new AgentVisualFigure(figB, figH, combinationMethod.OR);
			float similarityE = combinationOfBH.percentSimilarToOtherFigure(figE);
			if(areTheseNumbersEquivalent(similarityE, 100, percentageToDeemEquivalent)) {
				//THE OR OF B AND H EQUALS E
				
				//REMOVE ANY ANSWERS WHICH ARE THE SAME AS A-H
				boolean answer1 = true;
				boolean answer2 = true;
				boolean answer3 = true;
				boolean answer4 = true;
				boolean answer5 = true;
				boolean answer6 = true;
				boolean answer7 = true;
				boolean answer8 = true;
				
				if(isThisAnswerTheSameAsAnyNonAnswer(fig1, percentageToDeemEquivalent_Exactly) > -1)
					answer1 = false;
				if(isThisAnswerTheSameAsAnyNonAnswer(fig2, percentageToDeemEquivalent_Exactly) > -1)
					answer2 = false;
				if(isThisAnswerTheSameAsAnyNonAnswer(fig3, percentageToDeemEquivalent_Exactly) > -1)
					answer3 = false;
				if(isThisAnswerTheSameAsAnyNonAnswer(fig4, percentageToDeemEquivalent_Exactly) > -1)
					answer4 = false;
				if(isThisAnswerTheSameAsAnyNonAnswer(fig5, percentageToDeemEquivalent_Exactly) > -1)
					answer5 = false;
				if(isThisAnswerTheSameAsAnyNonAnswer(fig6, percentageToDeemEquivalent_Exactly) > -1)
					answer6 = false;
				if(isThisAnswerTheSameAsAnyNonAnswer(fig7, percentageToDeemEquivalent_Exactly) > -1)
					answer7 = false;
				if(isThisAnswerTheSameAsAnyNonAnswer(fig8, percentageToDeemEquivalent_Exactly) > -1)
					answer8 = false;
				

				if(answer1) {
					AgentVisualFigure combinationOf1C = new AgentVisualFigure(fig1, figC, combinationMethod.OR);
					float similarityF = combinationOf1C.percentSimilarToOtherFigure(figF);
					if(areTheseNumbersEquivalent(similarityF, 100, percentageToDeemEquivalent))
						return 1;
				}
				if(answer2) {
					AgentVisualFigure combinationOf2C = new AgentVisualFigure(fig2, figC, combinationMethod.OR);
					float similarityF = combinationOf2C.percentSimilarToOtherFigure(figF);
					if(areTheseNumbersEquivalent(similarityF, 100, percentageToDeemEquivalent))
						return 2;
				}
				if(answer3) {
					AgentVisualFigure combinationOf3C = new AgentVisualFigure(fig3, figC, combinationMethod.OR);
					float similarityF = combinationOf3C.percentSimilarToOtherFigure(figF);
					if(areTheseNumbersEquivalent(similarityF, 100, percentageToDeemEquivalent))
						return 3;
				}
				if(answer4) {
					AgentVisualFigure combinationOf4C = new AgentVisualFigure(fig4, figC, combinationMethod.OR);
					float similarityF = combinationOf4C.percentSimilarToOtherFigure(figF);
					if(areTheseNumbersEquivalent(similarityF, 100, percentageToDeemEquivalent))
						return 4;
				}
				if(answer5) {
					AgentVisualFigure combinationOf5C = new AgentVisualFigure(fig5, figC, combinationMethod.OR);
					float similarityF = combinationOf5C.percentSimilarToOtherFigure(figF);
					if(areTheseNumbersEquivalent(similarityF, 100, percentageToDeemEquivalent))
						return 5;
				}
				if(answer6) {
					AgentVisualFigure combinationOf6C = new AgentVisualFigure(fig6, figC, combinationMethod.OR);
					float similarityF = combinationOf6C.percentSimilarToOtherFigure(figF);
					if(areTheseNumbersEquivalent(similarityF, 100, percentageToDeemEquivalent))
						return 6;
				}
				if(answer7) {
					AgentVisualFigure combinationOf7C = new AgentVisualFigure(fig7, figC, combinationMethod.OR);
					float similarityF = combinationOf7C.percentSimilarToOtherFigure(figF);
					if(areTheseNumbersEquivalent(similarityF, 100, percentageToDeemEquivalent))
						return 7;
				}
				if(answer8) {
					AgentVisualFigure combinationOf8C = new AgentVisualFigure(fig8, figC, combinationMethod.OR);
					float similarityF = combinationOf8C.percentSimilarToOtherFigure(figF);
					if(areTheseNumbersEquivalent(similarityF, 100, percentageToDeemEquivalent))
						return 8;
				}
			}		
		}		
		
		return answer;
	}
		
	
	public int IsTheOrComboOfTheFirstTwoEquivalentToTheLast() { //LIKE BASIC E-10
		int answer = -1;
		
		AgentVisualFigure combinationOfAD = new AgentVisualFigure(figA, figD, combinationMethod.AND);
		float similarityG = combinationOfAD.percentSimilarToOtherFigure(figG);
		if(areTheseNumbersEquivalent(similarityG, 100, percentageToDeemEquivalent_KindOf)) { //HAD TO MAKE THIS "KIND OF" BECAUSE PROBLEM E-07'S AD->G IS NOT A TRUE XOR
			//THE TOP/BOTTOM MERGER OF A AND D EQUALS G
			AgentVisualFigure combinationOfBE = new AgentVisualFigure(figB, figE, combinationMethod.AND);
			float similarityH = combinationOfBE.percentSimilarToOtherFigure(figH);
			if(areTheseNumbersEquivalent(similarityH, 100, percentageToDeemEquivalent)) {
				//THE TOP/BOTTOM MERGER OF B AND E EQUALS H

				AgentVisualFigure combinationOfCF = new AgentVisualFigure(figC, figF, combinationMethod.AND);
				float similarity1 = combinationOfCF.percentSimilarToOtherFigure(fig1);
				if(areTheseNumbersEquivalent(similarity1, 100, percentageToDeemEquivalent_Exactly))
					return 1;
				float similarity2 = combinationOfCF.percentSimilarToOtherFigure(fig2);
				if(areTheseNumbersEquivalent(similarity2, 100, percentageToDeemEquivalent_Exactly))
					return 2;
				float similarity3 = combinationOfCF.percentSimilarToOtherFigure(fig3);
				if(areTheseNumbersEquivalent(similarity3, 100, percentageToDeemEquivalent_Exactly))
					return 3;
				float similarity4 = combinationOfCF.percentSimilarToOtherFigure(fig4);
				if(areTheseNumbersEquivalent(similarity4, 100, percentageToDeemEquivalent_Exactly))
					return 4;
				float similarity5 = combinationOfCF.percentSimilarToOtherFigure(fig5);
				if(areTheseNumbersEquivalent(similarity5, 100, percentageToDeemEquivalent_Exactly))
					return 5;
				float similarity6 = combinationOfCF.percentSimilarToOtherFigure(fig6);
				if(areTheseNumbersEquivalent(similarity6, 100, percentageToDeemEquivalent_Exactly))
					return 6;
				float similarity7 = combinationOfCF.percentSimilarToOtherFigure(fig7);
				if(areTheseNumbersEquivalent(similarity7, 100, percentageToDeemEquivalent_Exactly))
					return 7;
				float similarity8 = combinationOfCF.percentSimilarToOtherFigure(fig8);
				if(areTheseNumbersEquivalent(similarity8, 100, percentageToDeemEquivalent))
					return 8;			
			}
		}
		
		return answer;
	}
	
	public int isTopOfFirstAndBottomOfSecondEquivalentToThird() { //LIKE BASIC E-09
		int answer = -1;
		
		AgentVisualFigure combinationOfAD = new AgentVisualFigure(figA, figD, combinationMethod.TOPBOTTOM);
		float similarityG = combinationOfAD.percentSimilarToOtherFigure(figG);
		if(areTheseNumbersEquivalent(similarityG, 100, percentageToDeemEquivalent_KindOf)) { //HAD TO MAKE THIS "KIND OF" BECAUSE PROBLEM E-07'S AD->G IS NOT A TRUE XOR
			//THE TOP/BOTTOM MERGER OF A AND D EQUALS G
			AgentVisualFigure combinationOfBE = new AgentVisualFigure(figB, figE, combinationMethod.TOPBOTTOM);
			float similarityH = combinationOfBE.percentSimilarToOtherFigure(figH);
			if(areTheseNumbersEquivalent(similarityH, 100, percentageToDeemEquivalent)) {
				//THE TOP/BOTTOM MERGER OF B AND E EQUALS H

				AgentVisualFigure combinationOfCF = new AgentVisualFigure(figC, figF, combinationMethod.TOPBOTTOM);
				float similarity1 = combinationOfCF.percentSimilarToOtherFigure(fig1);
				if(areTheseNumbersEquivalent(similarity1, 100, percentageToDeemEquivalent_Exactly))
					return 1;
				float similarity2 = combinationOfCF.percentSimilarToOtherFigure(fig2);
				if(areTheseNumbersEquivalent(similarity2, 100, percentageToDeemEquivalent_Exactly))
					return 2;
				float similarity3 = combinationOfCF.percentSimilarToOtherFigure(fig3);
				if(areTheseNumbersEquivalent(similarity3, 100, percentageToDeemEquivalent_Exactly))
					return 3;
				float similarity4 = combinationOfCF.percentSimilarToOtherFigure(fig4);
				if(areTheseNumbersEquivalent(similarity4, 100, percentageToDeemEquivalent_Exactly))
					return 4;
				float similarity5 = combinationOfCF.percentSimilarToOtherFigure(fig5);
				if(areTheseNumbersEquivalent(similarity5, 100, percentageToDeemEquivalent_Exactly))
					return 5;
				float similarity6 = combinationOfCF.percentSimilarToOtherFigure(fig6);
				if(areTheseNumbersEquivalent(similarity6, 100, percentageToDeemEquivalent_Exactly))
					return 6;
				float similarity7 = combinationOfCF.percentSimilarToOtherFigure(fig7);
				if(areTheseNumbersEquivalent(similarity7, 100, percentageToDeemEquivalent_Exactly))
					return 7;
				float similarity8 = combinationOfCF.percentSimilarToOtherFigure(fig8);
				if(areTheseNumbersEquivalent(similarity8, 100, percentageToDeemEquivalent))
					return 8;			
			}
		}
		
		return answer;
	}
	
	public int isTheXorComboOfTheFirstAndSecondEquivalentToTheThird() { //LIKE BASIC E-05
		int answer = -1;
		
		AgentVisualFigure combinationOfAD = new AgentVisualFigure(figA, figD, combinationMethod.XOR);
		float similarityG = combinationOfAD.percentSimilarToOtherFigure(figG);
		if(areTheseNumbersEquivalent(similarityG, 100, percentageToDeemEquivalent_KindOf)) { //HAD TO MAKE THIS "KIND OF" BECAUSE PROBLEM E-07'S AD->G IS NOT A TRUE XOR
			//THE XOR OF A AND D EQUALS G
			AgentVisualFigure combinationOfBE = new AgentVisualFigure(figB, figE, combinationMethod.XOR);
			float similarityH = combinationOfBE.percentSimilarToOtherFigure(figH);
			if(areTheseNumbersEquivalent(similarityH, 100, percentageToDeemEquivalent)) {
				//THE XOR OF B AND E EQUALS H

				AgentVisualFigure combinationOfCF = new AgentVisualFigure(figC, figF, combinationMethod.XOR);
				float similarity1 = combinationOfCF.percentSimilarToOtherFigure(fig1);
				if(areTheseNumbersEquivalent(similarity1, 100, percentageToDeemEquivalent))
					return 1;
				float similarity2 = combinationOfCF.percentSimilarToOtherFigure(fig2);
				if(areTheseNumbersEquivalent(similarity2, 100, percentageToDeemEquivalent))
					return 2;
				float similarity3 = combinationOfCF.percentSimilarToOtherFigure(fig3);
				if(areTheseNumbersEquivalent(similarity3, 100, percentageToDeemEquivalent))
					return 3;
				float similarity4 = combinationOfCF.percentSimilarToOtherFigure(fig4);
				if(areTheseNumbersEquivalent(similarity4, 100, percentageToDeemEquivalent))
					return 4;
				float similarity5 = combinationOfCF.percentSimilarToOtherFigure(fig5);
				if(areTheseNumbersEquivalent(similarity5, 100, percentageToDeemEquivalent))
					return 5;
				float similarity6 = combinationOfCF.percentSimilarToOtherFigure(fig6);
				if(areTheseNumbersEquivalent(similarity6, 100, percentageToDeemEquivalent))
					return 6;
				float similarity7 = combinationOfCF.percentSimilarToOtherFigure(fig7);
				if(areTheseNumbersEquivalent(similarity7, 100, percentageToDeemEquivalent))
					return 7;
				float similarity8 = combinationOfCF.percentSimilarToOtherFigure(fig8);
				if(areTheseNumbersEquivalent(similarity8, 100, percentageToDeemEquivalent))
					return 8;
					

			}
		}
		
		return answer;
	}
	
	public int isTheOrComboOfTheFirstAndSecondEquivalentToTheThird() { //LIKE BASIC E-03
		int answer = -1;
		
		AgentVisualFigure combinationOfAB = new AgentVisualFigure(figA, figB, combinationMethod.OR);
		float similarityC = combinationOfAB.percentSimilarToOtherFigure(figC);
		
		if(areTheseNumbersEquivalent(similarityC, 100, percentageToDeemEquivalent)) {
			//COMBO OF A AND B IS EQUIVALENT TO C
			
			AgentVisualFigure combinationOfDE = new AgentVisualFigure(figD, figE, combinationMethod.OR);
			float similarityF = combinationOfDE.percentSimilarToOtherFigure(figF);
			
			if(areTheseNumbersEquivalent(similarityF, 100, percentageToDeemEquivalent)) {
				//COMBO OF D AND E IS EQUIVALENT TO F

				AgentVisualFigure combinationOfAD = new AgentVisualFigure(figA, figD, combinationMethod.OR);
				float similarityG = combinationOfAD.percentSimilarToOtherFigure(figG);
				
				if(areTheseNumbersEquivalent(similarityG, 100, percentageToDeemEquivalent)) {
					//COMBO OF A AND D IS EQUIVALENT TO G

					AgentVisualFigure combinationOfBE = new AgentVisualFigure(figB, figE, combinationMethod.OR);
					float similarityH = combinationOfBE.percentSimilarToOtherFigure(figH);
					
					if(areTheseNumbersEquivalent(similarityH, 100, percentageToDeemEquivalent)) {
						//COMBO OF B AND E IS EQUIVALENT TO H
						
						AgentVisualFigure combinationOfCF = new AgentVisualFigure(figC, figF, combinationMethod.OR);
						AgentVisualFigure combinationOfGH = new AgentVisualFigure(figG, figH, combinationMethod.OR);
						
						float combinedSimilarity1 = combinationOfCF.percentSimilarToOtherFigure(fig1);
						combinedSimilarity1 += combinationOfGH.percentSimilarToOtherFigure(fig1);
						float combinedSimilarity2 = combinationOfCF.percentSimilarToOtherFigure(fig2);
						combinedSimilarity2 += combinationOfGH.percentSimilarToOtherFigure(fig2);
						float combinedSimilarity3 = combinationOfCF.percentSimilarToOtherFigure(fig3);
						combinedSimilarity3 += combinationOfGH.percentSimilarToOtherFigure(fig3);
						float combinedSimilarity4 = combinationOfCF.percentSimilarToOtherFigure(fig4);
						combinedSimilarity4 += combinationOfGH.percentSimilarToOtherFigure(fig4);
						float combinedSimilarity5 = combinationOfCF.percentSimilarToOtherFigure(fig5);
						combinedSimilarity5 += combinationOfGH.percentSimilarToOtherFigure(fig5);
						float combinedSimilarity6 = combinationOfCF.percentSimilarToOtherFigure(fig6);
						combinedSimilarity6 += combinationOfGH.percentSimilarToOtherFigure(fig6);
						float combinedSimilarity7 = combinationOfCF.percentSimilarToOtherFigure(fig7);
						combinedSimilarity7 += combinationOfGH.percentSimilarToOtherFigure(fig7);
						float combinedSimilarity8 = combinationOfCF.percentSimilarToOtherFigure(fig8);
						combinedSimilarity8 += combinationOfGH.percentSimilarToOtherFigure(fig8);

						int bestIndex = 1;
						float bestVal = combinedSimilarity1;
						
						if(combinedSimilarity2 > bestVal) {
							bestVal = combinedSimilarity2;
							bestIndex = 2;
						}
						if(combinedSimilarity3 > bestVal) {
							bestVal = combinedSimilarity3;
							bestIndex = 3;
						}
						if(combinedSimilarity4 > bestVal) {
							bestVal = combinedSimilarity4;
							bestIndex = 4;
						}
						if(combinedSimilarity5 > bestVal) {
							bestVal = combinedSimilarity5;
							bestIndex = 5;
						}
						if(combinedSimilarity6 > bestVal) {
							bestVal = combinedSimilarity6;
							bestIndex = 6;
						}
						if(combinedSimilarity7 > bestVal) {
							bestVal = combinedSimilarity7;
							bestIndex = 7;
						}
						if(combinedSimilarity8 > bestVal) {
							bestVal = combinedSimilarity8;
							bestIndex = 8;
						}
							
						if(areTheseNumbersEquivalent(bestVal, 200, percentageToDeemEquivalent))
							return bestIndex;
						
					}
				}
			}
		}
		
		return answer;
	}
	
	/*
	 * THIS IS A LAST RESORT ONE. I'M NOT SURE WHAT THE ANSWERS ARE REALLY EVEN ABOUT
	 * BUT I'M GOING TO SAY THAT IF A AND E ARE DIFFERENT THEN FIND AN ANSWER WHICH IS 
	 * NOT THE SAME AS A AND NOT THE SAME AS E AND HAS A SIGNIFICANTLY SIMILAR DIFFERENCE 
	 * BETWEEN E AND ITSELF TO THAT OF A AND E
	 */
	public int isItFunkyAndHardToGuessButSimilaritiesInDifferencesMightPredictIt() { //LIKE D-07
		int answer = -1;
		
		float similarityAB = figA.percentSimilarToOtherFigure(figB);
		float similarityBC = figB.percentSimilarToOtherFigure(figC);
		float similarityAD = figA.percentSimilarToOtherFigure(figD);
		float similarityDG = figD.percentSimilarToOtherFigure(figG);
		float similarityAE = figA.percentSimilarToOtherFigure(figE);
		if(!areTheseNumbersEquivalent(similarityAB, 100, percentageToDeemEquivalent) &&
				!areTheseNumbersEquivalent(similarityBC, 100, percentageToDeemEquivalent) &&
				!areTheseNumbersEquivalent(similarityAD, 100, percentageToDeemEquivalent) &&
				!areTheseNumbersEquivalent(similarityDG, 100, percentageToDeemEquivalent) &&
				!areTheseNumbersEquivalent(similarityAE, 100, percentageToDeemEquivalent)) {

			boolean answer1 = true;
			boolean answer2 = true;
			boolean answer3 = true;
			boolean answer4 = true;
			boolean answer5 = true;
			boolean answer6 = true;
			boolean answer7 = true;
			boolean answer8 = true;
			
			//NOT MUCH IS SIMILAR ANYWHERE HERE SO ELIMINATE ANYTHING THAT'S TOO SIMILAR TO E
			float similarityE1 = figE.percentSimilarToOtherFigure(fig1);
			float similarityE2 = figE.percentSimilarToOtherFigure(fig2);
			float similarityE3 = figE.percentSimilarToOtherFigure(fig3);
			float similarityE4 = figE.percentSimilarToOtherFigure(fig4);
			float similarityE5 = figE.percentSimilarToOtherFigure(fig5);
			float similarityE6 = figE.percentSimilarToOtherFigure(fig6);
			float similarityE7 = figE.percentSimilarToOtherFigure(fig7);
			float similarityE8 = figE.percentSimilarToOtherFigure(fig8);

			if(areTheseNumbersEquivalent(similarityE1, 100, percentageToDeemEquivalent))
				answer1 = false;
			if(areTheseNumbersEquivalent(similarityE2, 100, percentageToDeemEquivalent))
				answer2 = false;
			if(areTheseNumbersEquivalent(similarityE3, 100, percentageToDeemEquivalent))
				answer3 = false;
			if(areTheseNumbersEquivalent(similarityE4, 100, percentageToDeemEquivalent))
				answer4 = false;
			if(areTheseNumbersEquivalent(similarityE5, 100, percentageToDeemEquivalent))
				answer5 = false;
			if(areTheseNumbersEquivalent(similarityE6, 100, percentageToDeemEquivalent))
				answer6 = false;
			if(areTheseNumbersEquivalent(similarityE7, 100, percentageToDeemEquivalent))
				answer7 = false;
			if(areTheseNumbersEquivalent(similarityE8, 100, percentageToDeemEquivalent))
				answer8 = false;

			float similarityA1 = -1000;
			float similarityA2 = -1000;
			float similarityA3 = -1000;
			float similarityA4 = -1000;
			float similarityA5 = -1000;
			float similarityA6 = -1000;
			float similarityA7 = -1000;
			float similarityA8 = -1000;
			
			//NOT MUCH IS SIMILAR ANYWHERE HERE SO ELIMINATE ANYTHING THAT'S TOO SIMILAR TO A
			if(answer1) {
				similarityA1 = figA.percentSimilarToOtherFigure(fig1);
				if(areTheseNumbersEquivalent(similarityA1, 100, percentageToDeemEquivalent))
					answer1 = false;
			}
			if(answer2) {
				similarityA2 = figA.percentSimilarToOtherFigure(fig2);
				if(areTheseNumbersEquivalent(similarityA2, 100, percentageToDeemEquivalent))
					answer2 = false;
			}				
			if(answer3) {
				similarityA3 = figA.percentSimilarToOtherFigure(fig3);
				if(areTheseNumbersEquivalent(similarityA3, 100, percentageToDeemEquivalent))
					answer3 = false;
			}				
			if(answer4) {
				similarityA4 = figA.percentSimilarToOtherFigure(fig4);
				if(areTheseNumbersEquivalent(similarityA4, 100, percentageToDeemEquivalent))
					answer4 = false;
			}				
			if(answer5) {
				similarityA5 = figA.percentSimilarToOtherFigure(fig5);
				if(areTheseNumbersEquivalent(similarityA5, 100, percentageToDeemEquivalent))
					answer5 = false;
			}				
			if(answer6) {
				similarityA6 = figA.percentSimilarToOtherFigure(fig6);
				if(areTheseNumbersEquivalent(similarityA6, 100, percentageToDeemEquivalent))
					answer6 = false;
			}				
			if(answer7) {
				similarityA7 = figA.percentSimilarToOtherFigure(fig7);
				if(areTheseNumbersEquivalent(similarityA7, 100, percentageToDeemEquivalent))
					answer7 = false;
			}				
			if(answer8) {
				similarityA8 = figA.percentSimilarToOtherFigure(fig8);
				if(areTheseNumbersEquivalent(similarityA8, 100, percentageToDeemEquivalent))
					answer8 = false;
			}				

			
			//NOW ELIMINATE ANYTHING THAT IS JUST LIKE ANYTHING A-H
			if(answer1 && isThisAnswerTheSameAsAnyNonAnswer(fig1, percentageToDeemEquivalent_Exactly) > -1)
					answer1 = false;
			if(answer2 && isThisAnswerTheSameAsAnyNonAnswer(fig2, percentageToDeemEquivalent_Exactly) > -1)
					answer2 = false;
			if(answer3 && isThisAnswerTheSameAsAnyNonAnswer(fig3, percentageToDeemEquivalent_Exactly) > -1)
					answer3 = false;
			if(answer4 && isThisAnswerTheSameAsAnyNonAnswer(fig4, percentageToDeemEquivalent_Exactly) > -1)
					answer4 = false;
			if(answer5 && isThisAnswerTheSameAsAnyNonAnswer(fig5, percentageToDeemEquivalent_Exactly) > -1)
					answer5 = false;
			if(answer6 && isThisAnswerTheSameAsAnyNonAnswer(fig6, percentageToDeemEquivalent_Exactly) > -1)
					answer6 = false;
			if(answer7 && isThisAnswerTheSameAsAnyNonAnswer(fig7, percentageToDeemEquivalent_Exactly) > -1)
					answer7 = false;
			if(answer8 && isThisAnswerTheSameAsAnyNonAnswer(fig8, percentageToDeemEquivalent_Exactly) > -1)
					answer8 = false;
			

			//NOW FIND THE ANSWER WHICH HAS THE DIFFERENCE TO E WHICH IS MOST CLOSE TO THE DIFFERENCE BETWEEN A AND E 
			int bestIndex = -1;
			float bestVal = -1000;

			if(answer1 && Math.abs((similarityE1 + similarityA1) - (similarityAE * 2)) < Math.abs(bestVal - (similarityAE * 2))) {
				bestIndex = 1;
				bestVal = similarityE1 + similarityA1;
			}
			if(answer2 && Math.abs((similarityE2 + similarityA2) - (similarityAE * 2)) < Math.abs(bestVal - (similarityAE * 2))) {
				bestIndex = 2;
				bestVal = similarityE2 + similarityA2;
			}
			if(answer3 && Math.abs((similarityE3 + similarityA3) - (similarityAE * 2)) < Math.abs(bestVal - (similarityAE * 2))) {
				bestIndex = 3;
				bestVal = similarityE3 + similarityA3;
			}
			if(answer4 && Math.abs((similarityE4 + similarityA4) - (similarityAE * 2)) < Math.abs(bestVal - (similarityAE * 2))) {
				bestIndex = 4;
				bestVal = similarityE4 + similarityA4;
			}
			if(answer5 && Math.abs((similarityE5 + similarityA5) - (similarityAE * 2)) < Math.abs(bestVal - (similarityAE * 2))) {
				bestIndex = 5;
				bestVal = similarityE5 + similarityA5;
			}
			if(answer6 && Math.abs((similarityE6 + similarityA6) - (similarityAE * 2)) < Math.abs(bestVal - (similarityAE * 2))) {
				bestIndex = 6;
				bestVal = similarityE6 + similarityA6;
			}
			if(answer7 && Math.abs((similarityE7 + similarityA7) - (similarityAE * 2)) < Math.abs(bestVal - (similarityAE * 2))) {
				bestIndex = 7;
				bestVal = similarityE7 + similarityA7;
			}
			if(answer8 && Math.abs((similarityE8 + similarityA8) - (similarityAE * 2)) < Math.abs(bestVal - (similarityAE * 2))) {
				bestIndex = 8;
				bestVal = similarityE8 + similarityA8;
			}
				
			
			
			//NOW SEE IF THE NUMBER OF THE BEST ANSWER IS CLOSE ENOUGH TO THE DIFF BETWEEN A AND E TO COUNT
			if(areTheseNumbersEquivalent(bestVal, similarityAE * 2, percentageToDeemEquivalent_KindOf))
				return bestIndex;
			
		}
		
		
		return answer;
	}
	
	public int isThisAnswerTheSameAsAnyNonAnswer(AgentVisualFigure answer, float percentage) {
		
		if(areTheseNumbersEquivalent(answer.percentSimilarToOtherFigure(figA),  100,  percentage))
			return 1; 
		if(areTheseNumbersEquivalent(answer.percentSimilarToOtherFigure(figB),  100,  percentage))
			return 2; 
		if(areTheseNumbersEquivalent(answer.percentSimilarToOtherFigure(figC),  100,  percentage))
			return 3; 
		if(areTheseNumbersEquivalent(answer.percentSimilarToOtherFigure(figD),  100,  percentage))
			return 4; 
		if(areTheseNumbersEquivalent(answer.percentSimilarToOtherFigure(figE),  100,  percentage))
			return 5; 
		if(areTheseNumbersEquivalent(answer.percentSimilarToOtherFigure(figF),  100,  percentage))
			return 6; 
		if(areTheseNumbersEquivalent(answer.percentSimilarToOtherFigure(figG),  100,  percentage))
			return 7; 
		if(areTheseNumbersEquivalent(answer.percentSimilarToOtherFigure(figH),  100,  percentage))
			return 8; 
		
		return -1;
	}
	
	public boolean areTheseNumbersEquivalent(float a, float b, float percentage) {
		float percent = (float)percentage / 100;
		
		float min = (float)b * (1 - percent);
		float max = (float)b * (1 + percent);
		
		if(a >= min && a <= max)
			return true;
		
		return false;
	}
	
	public int getBestAnswerIndexAboveThreshold(AgentVisualFigure figure, int threshold) {
		int index = -1;
		float highestPercentage = -1;
		
		
		float temp = fig1.percentSimilarToOtherFigure(figure);
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
