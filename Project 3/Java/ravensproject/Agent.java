package ravensproject;

// Uncomment these lines to access image processing.
//import java.awt.Image;
//import java.io.File;
//import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import javax.imageio.ImageIO;

import ravensproject.AgentScoreKeep.scoreStatus;
import ravensproject.AgentTransformation.mappingTransformations;
import ravensproject.AgentSpecialHandling.specialType;

/**
 * Your Agent for solving Raven's Progressive Matrices. You MUST modify this
 * file.
 * 
 * You may also create and submit new files in addition to modifying this file.
 * 
 * Make sure your file retains methods with the signatures:
 * public Agent()
 * public char Solve(RavensProblem problem)
 * 
 * These methods will be necessary for the project's main method to run.
 * 
 */
public class Agent {
	
	enum debugPrintType { NONE, SOME, ALL };
	debugPrintType debugPrinting = debugPrintType.SOME;
	String onlyDoThisProblem = "";//Basic Problem C-07";
	
	ArrayList<AgentScoreKeep> scoreArray = new ArrayList<AgentScoreKeep>();
	
	int problemsCorrect2x2 = 0;
	int problemsIncorrect2x2 = 0;
	int problemsSkipped2x2 = 0;

	int problemsCorrect3x3 = 0;
	int problemsIncorrect3x3 = 0;
	int problemsSkipped3x3 = 0;

	int problemsCorrectVisual = 0;
	int problemsIncorrectVisual = 0;
	int problemsSkippedVisual = 0;

	int maxObjectsAllowed = 7;
	
	int unlikelyAnglePrediction = -9999;
    /**
     * The default constructor for your Agent. Make sure to execute any
     * processing necessary before your Agent starts solving problems here.
     * 
     * Do not add any variables to this signature; they will not be used by
     * main().
     * 
     */
    public Agent() {
        
    }
    /**
     * The primary method for solving incoming Raven's Progressive Matrices.
     * For each problem, your Agent's Solve() method will be called. At the
     * conclusion of Solve(), your Agent should return a String representing its
     * answer to the question: "1", "2", "3", "4", "5", or "6". These Strings
     * are also the Names of the individual RavensFigures, obtained through
     * RavensFigure.getName().
     * 
     * In addition to returning your answer at the end of the method, your Agent
     * may also call problem.checkAnswer(String givenAnswer). The parameter
     * passed to checkAnswer should be your Agent's current guess for the
     * problem; checkAnswer will return the correct answer to the problem. This
     * allows your Agent to check its answer. Note, however, that after your
     * agent has called checkAnswer, it will *not* be able to change its answer.
     * checkAnswer is used to allow your Agent to learn from its incorrect
     * answers; however, your Agent cannot change the answer to a question it
     * has already answered.
     * 
     * If your Agent calls checkAnswer during execution of Solve, the answer it
     * returns will be ignored; otherwise, the answer returned at the end of
     * Solve will be taken as your Agent's answer to this problem.
     * 
     * @param problem the RavensProblem your agent should solve
     * @return your Agent's answer to this problem
     */
    public int Solve(RavensProblem problem) {
        
    	//"2x2" or "3x3":
    	String name = problem.getName();
    	String type = problem.getProblemType();

    	AgentScoreKeep thisScore = new AgentScoreKeep(name);
    	
    	int numAnswers = 6;
    	if(type.equals("3x3"))
    		numAnswers = 8;

    	
    	int answer = -1;
    	
    	//USED ONLY FOR DEBUGGING - SET TO A SPECIFIC PROBLEM TO TEST ONLY THAT PROBLEM
    	

    	if(onlyDoThisProblem.equals("") || name.equals(onlyDoThisProblem)) {

        	if(debugPrinting == debugPrintType.SOME || debugPrinting == debugPrintType.ALL) {
    	    	System.out.println("************************");
    	    	System.out.println("Here we go for " + name);
    	    	System.out.println("************************");
        	}

	    	
	    	if(type.equals("2x2")) {
		    	if(problem.hasVerbal()) {
		    		
		    		answer = do2x2Verbal(problem, name, numAnswers);
		    		if(answer == -1)
		    			++problemsSkipped2x2;
		    	}
		    	else {
		
		    		if(debugPrinting == debugPrintType.SOME || debugPrinting == debugPrintType.ALL) {
		    	    	System.out.println("No verbal data available");
		    	    	++problemsSkipped2x2;
		        	}
		    	}
	    	}
	    	else {//3x3
		    	if(problem.hasVerbal()) {
		    	
		    		//MY MAPPING ROUTE WASN'T WORKING ON CASES WHERE THE RESULT WAS BASED ON THE NUMBER OF 
		    		//SHAPES PREDICTIBLY CHANGING.  tHIS IS MEANT TO CATCH THOSE SITUATIONS
		    		int predictedNumShapes = isShapeNumberPredictable(problem);
		    		
		    		if(predictedNumShapes > -1) {
		    			ArrayList<RavensFigure> matchingFigures = getFiguresWithMatchingObjectCount(predictedNumShapes, problem);
		    			if(matchingFigures.size() == 1) {
		    				answer = Integer.parseInt(matchingFigures.get(0).getName());
		    			}
		    			else if(matchingFigures.size() > 1) {
		    				answer = getBestGuessFromThesePossibleAnswers(matchingFigures, problem);
		    				
		    			}		    			
		    		}

		    		//STILL NO ANSWER - TRY THE ORIGINAL WAY
		    		if(answer == -1){		    			
		    		
			    		answer = do3x3Verbal(problem, name, numAnswers);
		    		}

		    		if(answer == -1)
		    			++problemsSkipped3x3;
		    	}
		    	else {
		
	    			answer = do3x3Visual(problem, name, numAnswers);
	    			
	    			if(answer == -1)
	    				++problemsSkippedVisual;
		        	
		    	}
	    	}
	    	
			if(answer != -1) {

				if(debugPrinting == debugPrintType.SOME || debugPrinting == debugPrintType.ALL) 
					System.out.println("Guess: " + (answer));
				
				int realAnswer = problem.checkAnswer(answer);
				
				if(realAnswer == answer) {

					thisScore.status = scoreStatus.CORRECT;
					
					if(type.equalsIgnoreCase("2x2")) 
						++problemsCorrect2x2;
					else if(problem.hasVerbal())
						++problemsCorrect3x3;
					else
						++problemsCorrectVisual;
				}
				else {
					
					thisScore.status = scoreStatus.INCORRECT;
					
					if(type.equalsIgnoreCase("2x2"))
						++problemsIncorrect2x2;
					else if(problem.hasVerbal())
						++problemsIncorrect3x3;
					else
						++problemsIncorrectVisual;
					
				}

				
				if(debugPrinting == debugPrintType.SOME || debugPrinting == debugPrintType.ALL) {
					System.out.println("Answer: " + realAnswer);
				}
			}
			else {
				if(debugPrinting == debugPrintType.SOME || debugPrinting == debugPrintType.ALL) 
					System.out.println("Skipping");
			}
				
			scoreArray.add(thisScore);

			
			if(debugPrinting == debugPrintType.SOME || debugPrinting == debugPrintType.ALL) {
				//PrintSummary();
				System.out.println("################################################################################################");
				System.out.println("2x2 problems: Correct:" + problemsCorrect2x2 + " Incorrect: " + problemsIncorrect2x2 + " Skipped:" + problemsSkipped2x2);
				System.out.println("3x3 problems: Correct:" + problemsCorrect3x3 + " Incorrect: " + problemsIncorrect3x3 + " Skipped:" + problemsSkipped3x3);
				System.out.println("Vis problems: Correct:" + problemsCorrectVisual + " Incorrect: " + problemsIncorrectVisual + " Skipped:" + problemsSkippedVisual);
				System.out.println("################################################################################################");
			}

    	}
    		

	
		return answer;
    }
    
    public int getBestGuessFromThesePossibleAnswers(ArrayList<RavensFigure> possibleAnswers, RavensProblem problem) {
    	
    	return 4;
    }
    
    public void PrintSummary() {
    
    	System.out.println("**** Summary ****");
    	for(int i = 0; i < scoreArray.size(); ++i) {
    		scoreArray.get(i).Print();
    	}
    }
    
    public int isShapeNumberPredictable(RavensProblem problem) {
    	
    	int prediction = isItASimpleAddition(problem);
    	
    	if(prediction == -1) {
    		//CHECK FOR MORE COMPLICATED ISSUES LIKE BASIC PROBLEM C03
    		prediction = isItAMultiplier(problem);    		
    	}
    	    	
    	return prediction;
    }
    
    public int isItAMultiplier(RavensProblem problem) {
    	int shapeCountPatternModifierABC = getShapeCountPatternModifier(numObjectsInFigure("A", problem), numObjectsInFigure("B", problem), numObjectsInFigure("C", problem));
    	 
    	if(shapeCountPatternModifierABC > 0) {
    		if(!validateShapeCountModifier(shapeCountPatternModifierABC, numObjectsInFigure("A", problem), numObjectsInFigure("D", problem), numObjectsInFigure("G", problem)))
    			return -1;
    		
    		int shapeCountPatternModifierDEF = getShapeCountPatternModifier(numObjectsInFigure("D", problem), numObjectsInFigure("E", problem), numObjectsInFigure("F", problem));
    		if(shapeCountPatternModifierDEF > 0) {
        		if(!validateShapeCountModifier(shapeCountPatternModifierDEF, numObjectsInFigure("B", problem), numObjectsInFigure("E", problem), numObjectsInFigure("H", problem)))
        			return -1;
        		
        		if(numObjectsInFigure("F", problem) - numObjectsInFigure("C", problem) == numObjectsInFigure("H", problem) - numObjectsInFigure("G", problem))
        			return numObjectsInFigure("F", problem) + (numObjectsInFigure("F", problem) - numObjectsInFigure("C", problem));
        			
    		}
    	}
    	
    	
    	
    	return -1;
    	
    }
    
    public int isItASimpleAddition(RavensProblem problem) {
    	int shapeCountPatternModifier = getShapeCountPatternModifier(numObjectsInFigure("A", problem), numObjectsInFigure("B", problem), numObjectsInFigure("C", problem));
    	
    	if(shapeCountPatternModifier > 0) {
    		if(!validateShapeCountModifier(shapeCountPatternModifier, numObjectsInFigure("D", problem), numObjectsInFigure("E", problem), numObjectsInFigure("F", problem)))
    			return -1;
    		if(!validateShapeCountModifier(shapeCountPatternModifier, numObjectsInFigure("A", problem), numObjectsInFigure("D", problem), numObjectsInFigure("G", problem)))
    			return -1;
    		if(!validateShapeCountModifier(shapeCountPatternModifier, numObjectsInFigure("B", problem), numObjectsInFigure("E", problem), numObjectsInFigure("H", problem)))
    			return -1;
    		
    		return problem.getFigures().get("H").getObjects().size() + shapeCountPatternModifier;
    	}
    	
    	return -1;
    }
    
    public int numObjectsInFigure(String figureName, RavensProblem problem) {
    	return problem.getFigures().get(figureName).getObjects().size();
    }
    
    public boolean validateShapeCountModifier(int modifier, int shapes1, int shapes2, int shapes3) {
    	if(shapes1 + modifier == shapes2 && shapes2 + modifier == shapes3)
    		return true;
    	
    	
    	
    	return false;
    }
    
    public int getShapeCountPatternModifier(int shapes1, int shapes2, int shapes3) {
    	    	
    	if(shapes2 - shapes1 == shapes3 - shapes2 && shapes2 - shapes1 > 0)
    		return shapes2 - shapes1;
    	
    	return -1;
    		
    }
    

    public ArrayList<RavensFigure> getFiguresWithMatchingObjectCount(int predictedObjectCount, RavensProblem problem) {

    	ArrayList<RavensFigure> theList = new ArrayList<RavensFigure>();
    	
		for(HashMap.Entry<String, RavensFigure> RFentry : problem.getFigures().entrySet()){

			if(RFentry.getValue().getObjects().size() == predictedObjectCount)
				theList.add(RFentry.getValue());
			
			
		}
    	
    	return theList;
    }
    
    public int getMaxObjectCountInAllFigures(RavensProblem problem, String includeThese, String excludeThese) {
    	int maxObjects = -1;

    	
		for(HashMap.Entry<String, RavensFigure> RFentry : problem.getFigures().entrySet()){

			if((includeThese.length() == 0 || includeThese.contains(RFentry.getValue().getName())) &&
					(excludeThese.length() == 0 || !excludeThese.contains(RFentry.getValue().getName())))
			{
				if(RFentry.getValue().getObjects().size() > maxObjects)
					maxObjects = RFentry.getValue().getObjects().size(); 	
			}
			
		}
    	
    	return maxObjects;
    }

        
    public String ignoreAnswersBasedOnObjectCount(RavensProblem problem) {
    	String ignoreList = "";
    	
    	int maxObjectsInProblem = getMaxObjectCountInAllFigures(problem, "ABCDEFGH", ignoreList);
    	
    	if(problem.getFigures().get("1").getObjects().size() >= maxObjectsInProblem * 2) 
    		ignoreList += "1";
    	if(problem.getFigures().get("2").getObjects().size() >= maxObjectsInProblem * 2) 
    		ignoreList += "2";
    	if(problem.getFigures().get("3").getObjects().size() >= maxObjectsInProblem * 2) 
    		ignoreList += "3";
    	if(problem.getFigures().get("4").getObjects().size() >= maxObjectsInProblem * 2) 
    		ignoreList += "4";
    	if(problem.getFigures().get("5").getObjects().size() >= maxObjectsInProblem * 2) 
    		ignoreList += "5";
    	if(problem.getFigures().get("6").getObjects().size() >= maxObjectsInProblem * 2) 
    		ignoreList += "6";
    	if(problem.getFigures().get("7").getObjects().size() >= maxObjectsInProblem * 2) 
    		ignoreList += "7";
    	if(problem.getFigures().get("8").getObjects().size() >= maxObjectsInProblem * 2) 
    		ignoreList += "8";
    	
    	return ignoreList;
    }
    
    
	public int do3x3Verbal(RavensProblem problem, String name, int numAnswers) {
		
		String ignoreTheseAnswers = ignoreAnswersBasedOnObjectCount(problem);
		
		if(getMaxObjectCountInAllFigures(problem, "", ignoreTheseAnswers) > maxObjectsAllowed) 
			return -1;
		
		int answer;

		//CHECK FOR SPECIAL CASES (LIKE REFLECTION ROTATIONS, ETC)
		ArrayList<AgentSpecialHandling> dailySpecials = checkForSpecialness3x3(problem);
		
		//BUILD COMPARISON MAPPINGS FOR E AND F
		AgentDiagramComparison compEF = new AgentDiagramComparison(name, "EF", problem.getFigures().get("E"), problem.getFigures().get("F"), debugPrinting);
		
		//BUILD COMPARISON MAPPINGS FOR H AND EACH TEST CASE
		ArrayList<AgentDiagramComparison> compHTests = new ArrayList<AgentDiagramComparison>();
		for(int i = 0; i < numAnswers; ++i) {
			if(!ignoreTheseAnswers.contains(Integer.toString(i + 1)))			
				compHTests.add(new AgentDiagramComparison(name, "H" + Integer.toString(i + 1), problem.getFigures().get("H"), problem.getFigures().get(Integer.toString(i + 1)), debugPrinting));    		
		}
		
		//BUILD COMPARISON MAPPINGS FOR E AND H
		AgentDiagramComparison compEH = new AgentDiagramComparison(name, "EH", problem.getFigures().get("E"), problem.getFigures().get("H"), debugPrinting);
		
		//BUILD COMPARISON MAPPINGS FOR F AND EACH TEST CASE
		ArrayList<AgentDiagramComparison> compFTests = new ArrayList<AgentDiagramComparison>();
		for(int i = 0; i < numAnswers; ++i) {
			if(!ignoreTheseAnswers.contains(Integer.toString(i + 1)))
				compFTests.add(new AgentDiagramComparison(name, "F" + Integer.toString(i + 1), problem.getFigures().get("F"), problem.getFigures().get(Integer.toString(i + 1)), debugPrinting));    		
		}
		
		//GO THROUGH EACH MAPPING IN EACH OF THE SOLUTION COMPARISONS IN compHTests.  ASSIGN EACH 
		//MAPPING A SCORE BASED ON HOW CLOSE IT IS TO ANY OF THE MAPPINGS IN compEF.  
		//THEN ASSIGN EACH SOLUTION A SCORE WHICH CORRELATES TO THE LOWEST SCORE OF ITS MAPS
		//IF THERE'S A TIE, GO WITH THE LOWEST SCORE
		
		ArrayList<AgentMappingScore> compHScoreRankings = new ArrayList<AgentMappingScore>();
		for(int i = 0; i < compHTests.size(); ++i) {
			AgentMappingScore temp = compHTests.get(i).calculateScores(compEF, dailySpecials);
			temp.agentIndex = i;
			insertScoreIntoSortedArray(temp, compHScoreRankings);
		}
	
		ArrayList<AgentMappingScore> compFScoreRankings = new ArrayList<AgentMappingScore>();
		for(int i = 0; i < compFTests.size(); ++i) {
			AgentMappingScore temp = compFTests.get(i).calculateScores(compEH, dailySpecials);
			temp.agentIndex = i;
			insertScoreIntoSortedArray(temp, compFScoreRankings);
		}
		
		int index = getBestCombinedRankingIndex(compHScoreRankings, compFScoreRankings); 
		
		answer = index + 1;
		
		return answer;
	}
    
	public int do2x2Verbal(RavensProblem problem, String name, int numAnswers) {
		
		if(getMaxObjectCountInAllFigures(problem, "", "") > maxObjectsAllowed) 
			return -1;
				
		
		int answer;
		//CHECK FOR SPECIAL CASES (LIKE REFLECTION ROTATIONS, ETC)
		ArrayList<AgentSpecialHandling> dailySpecials = checkForSpecialness2x2(problem);
		
		//BUILD COMPARISON MAPPINGS FOR A AND B
		AgentDiagramComparison compAB = new AgentDiagramComparison(name, "AB", problem.getFigures().get("A"), problem.getFigures().get("B"), debugPrinting);
		
		//BUILD COMPARISON MAPPINGS FOR C AND EACH TEST CASE
		ArrayList<AgentDiagramComparison> compCTests = new ArrayList<AgentDiagramComparison>();
		for(int i = 0; i < numAnswers; ++i) {
			compCTests.add(new AgentDiagramComparison(name, "C" + Integer.toString(i + 1), problem.getFigures().get("C"), problem.getFigures().get(Integer.toString(i + 1)), debugPrinting));    		
		}
		
		//BUILD COMPARISON MAPPINGS FOR A AND C
		AgentDiagramComparison compAC = new AgentDiagramComparison(name, "AC", problem.getFigures().get("A"), problem.getFigures().get("C"), debugPrinting);
		
		//BUILD COMPARISON MAPPINGS FOR B AND EACH TEST CASE
		ArrayList<AgentDiagramComparison> compBTests = new ArrayList<AgentDiagramComparison>();
		for(int i = 0; i < numAnswers; ++i) {
			compBTests.add(new AgentDiagramComparison(name, "B" + Integer.toString(i + 1), problem.getFigures().get("B"), problem.getFigures().get(Integer.toString(i + 1)), debugPrinting));    		
		}
		
		//GO THROUGH EACH MAPPING IN EACH OF THE SOLUTION COMPARISONS IN compCTests.  ASSIGN EACH 
		//MAPPING A SCORE BASED ON HOW CLOSE IT IS TO ANY OF THE MAPPINGS IN compAB.  
		//THEN ASSIGN EACH SOLUTION A SCORE WHICH CORRELATES TO THE LOWEST SCORE OF ITS MAPS
		//IF THERE'S A TIE, GO WITH THE LOWEST SCORE
		
		ArrayList<AgentMappingScore> compCScoreRankings = new ArrayList<AgentMappingScore>();
		for(int i = 0; i < compCTests.size(); ++i) {
			AgentMappingScore temp = compCTests.get(i).calculateScores(compAB, dailySpecials);
			temp.agentIndex = i;
			insertScoreIntoSortedArray(temp, compCScoreRankings);
		}

		ArrayList<AgentMappingScore> compBScoreRankings = new ArrayList<AgentMappingScore>();
		for(int i = 0; i < compBTests.size(); ++i) {
			AgentMappingScore temp = compBTests.get(i).calculateScores(compAC, dailySpecials);
			temp.agentIndex = i;
			insertScoreIntoSortedArray(temp, compBScoreRankings);
		}
		
		int index = getBestCombinedRankingIndex(compCScoreRankings, compBScoreRankings); 
		
		answer = index + 1;
		
		return answer;
	}
    
    private ArrayList<AgentSpecialHandling> checkForSpecialness3x3(RavensProblem problem) {

    	ArrayList<AgentSpecialHandling> retval = new ArrayList<AgentSpecialHandling>();
    	
    	//NO KNOWN SPECIALS FOR 3x3 YET
    	
    	return retval;
    }

	private ArrayList<AgentSpecialHandling> checkForSpecialness2x2(RavensProblem problem) {

    	ArrayList<AgentSpecialHandling> retval = new ArrayList<AgentSpecialHandling>();
    	
    	//CHECK FOR REFLECTION ROTATION
    	checkForReflection2x2(problem, retval);    	
    	
    	return retval;
    }

    private void checkForReflection2x2(RavensProblem problem, ArrayList<AgentSpecialHandling> theSpecials) {

    	//CAN ONLY DETECT REFLECTION IF THERE IS ONLY ONE SHAPE IN THE FIGURE (CURRENTLY)
    	//MAKE SURE A, B, AND C EACH ONLY HAVE ONE FIGURE
    	if(problem.getFigures().get("A").getObjects().size() != 1 ||
    			problem.getFigures().get("B").getObjects().size() != 1 ||
    			problem.getFigures().get("C").getObjects().size() != 1) {
    		return;
    	}
    	
    	//SCAN A, B, AND C AND SEE IF ALL THREE HAVE A ROTATION ATTRIBUTE
    	int angleA = 0;
    	int angleB = 0;
    	int angleC = 0;
    	Map.Entry<String, RavensObject> entry = problem.getFigures().get("A").getObjects().entrySet().iterator().next();
    	if(!entry.getValue().getAttributes().containsKey(AgentShapeMapping.attribKey_angle))
    		return;
    	else
    		angleA = Integer.parseInt(entry.getValue().getAttributes().get(AgentShapeMapping.attribKey_angle));
    	
    	entry = problem.getFigures().get("B").getObjects().entrySet().iterator().next();
    	if(!entry.getValue().getAttributes().containsKey(AgentShapeMapping.attribKey_angle))
    		return;
    	else
    		angleB = Integer.parseInt(entry.getValue().getAttributes().get(AgentShapeMapping.attribKey_angle));

    	entry = problem.getFigures().get("C").getObjects().entrySet().iterator().next();
    	if(!entry.getValue().getAttributes().containsKey(AgentShapeMapping.attribKey_angle))
    		return;
    	else
    		angleC = Integer.parseInt(entry.getValue().getAttributes().get(AgentShapeMapping.attribKey_angle));
    	
    	//THERE'S ONLY ONE SHAPE IN A, B, AND C AND EACH SHAPE HAS AN ASSOCIATED ANGLE
    	//NO SEE IF WE CAN PREDICT WHICH ANGLE THE ANSWER SHOULD HAVE
    	int expectedAngle = predictAngleFromThreeOthers(angleA, angleB, angleC);
    	if(expectedAngle != unlikelyAnglePrediction) {
    		theSpecials.add(new AgentSpecialHandling(specialType.REFLECTION_ROTATION, expectedAngle, mappingTransformations.ANGLE_CHANGE, mappingTransformations.EXPECTEDANGLE_CHANGE));
    	}
    		
    	
    	
    }    
    
	private int predictAngleFromThreeOthers(int angleA, int angleB, int angleC) {
		//SOME NUMBER THAT ISN'T A LIKELY ANGLE (-1 COULD BE ONE)
		int retval = unlikelyAnglePrediction;
		
		//IF THEY ARE ALL THE SAME WE SHOULD RETURN THE SAME
		if(angleA == angleB && angleB == angleC) 
			return angleA;


		//A AND C ARE THE SAME BUT B IS DIFFERENT SO YOU WOULD THINK D SHOULD EQUAL B
		if(angleA == angleC && angleA != angleB)
			return angleB;
		
		
		//SEE IF ALL FOUR COMBINED SHOULD ADD TO 720
		if((Math.abs(angleA - angleB) == 90 || (angleA + 90) % 360 == angleB || (angleB + 90) % 360 == angleA) &&
				(Math.abs(angleA - angleC) == 90 || (angleA + 90) % 360 == angleC || (angleC + 90) % 360 == angleA)) {
			retval = 720 - angleA - angleB - angleC;
			return retval;
		}
		
		//SEE IF ALL FOUR COMBINED SHOULD ADD TO 360
		if((Math.abs(angleA - angleB) == 45 || (angleA + 45) % 180 == angleB || (angleB + 45) % 180 == angleA) &&
				(Math.abs(angleA - angleC) == 45 || (angleA + 45) % 180 == angleC || (angleC + 45) % 180 == angleA)) {
			retval = 360 - angleA - angleB - angleC;
			return retval;
		}

		
		
		//ITERATE FROM -360 TO 360 TO SEE IF ONE VALUE IN THERE WILL MAKE EACH OF THE NOW FOUR ANGLES
		//EQUALLY APART FROM EACH OTHER
		for(int i = -360; i <= 360; ++i) {

			
			
		}
		
		return retval; 
	}
    
    
    private int getBestCombinedRankingIndex(ArrayList<AgentMappingScore> compCScoreRankings, ArrayList<AgentMappingScore> compBScoreRankings) {
    	
    	HashMap<Integer, Integer> indexScores = new HashMap<Integer, Integer>();
    	
    	for(int i = 0; i < compCScoreRankings.size(); ++i) {
    		if(indexScores.containsKey(compCScoreRankings.get(i).agentIndex)) {
    			int prevValue = indexScores.get(compCScoreRankings.get(i).agentIndex);
    			indexScores.put(compCScoreRankings.get(i).agentIndex, prevValue + i);
    		}
    		else {
    			indexScores.put(compCScoreRankings.get(i).agentIndex, i);
    		}
    	}

    	for(int i = 0; i < compBScoreRankings.size(); ++i) {
    		if(indexScores.containsKey(compBScoreRankings.get(i).agentIndex)) {
    			int prevValue = indexScores.get(compBScoreRankings.get(i).agentIndex);
    			indexScores.put(compBScoreRankings.get(i).agentIndex, prevValue + i);
    		}
    		else {
    			indexScores.put(compBScoreRankings.get(i).agentIndex, i);
    		}
    	}

    	int bestIndex = -1;
    	int bestScore = -1;
    	
    	for(int i = 0; i < compCScoreRankings.size(); ++i) {
    		if(bestIndex == -1 || indexScores.get(i) < bestScore) {
    			bestIndex = i;
    			bestScore = indexScores.get(i);
    		}
    	}
    	
    	
    	return bestIndex;
    }
    
    private void insertScoreIntoSortedArray(AgentMappingScore theScore, ArrayList<AgentMappingScore> theArray) {
    	boolean added = false;
    	for(int i = 0; i < theArray.size(); ++i) {
    		if(theScore.whichScoreIsBetter(theArray.get(i)) == theScore) {
    			theArray.add(i, theScore);
    			added = true;
    			return;
    		}    			
    	}
    	
    	if(!added)
    		theArray.add(theScore);
    }
    
/****************************************************************************************************************
 * START VISUAL STUFF
****************************************************************************************************************/
 	public int do3x3Visual(RavensProblem problem, String name, int numAnswers) {

 		int answer = -1;
    
 		AgentVisualProblem elProblemo = new AgentVisualProblem(problem);
 		
 		answer = elProblemo.isTheOrComboOfTheFirstAndSecondEquivalentToTheThird(); //SUCH AS BASIC E-03
 		
 		if(answer == -1)
 			answer = elProblemo.isTopOfFirstAndBottomOfSecondEquivalentToThird(); //SUCH AS BASIC E-09

 		if(answer == -1)
 			answer = elProblemo.IsTheOrComboOfTheFirstTwoEquivalentToTheLast(); //SUCH AS BASIC E-10
 		
 		if(answer == -1)
 			answer = elProblemo.areTheNumberOfShapesTheSameDiagonally(); //SUCH AS BASIC D-12
 		
 		if(answer == -1)
 			answer = elProblemo.areShapesEquvalentAlongRows(); //SUCH AS BASIC D-01

 		if(answer == -1)
 			answer = elProblemo.areShapesEquivalentAlongDiagonals(); //SUCH AS BASIC D-02
 		
 		if(answer == -1)
 			answer = elProblemo.isTheChangeInShapeConsitentAcrossRowsColumns(); //SUCH AS BASIC D-04

 		if(answer == -1)
 			answer = elProblemo.isTheXorComboOfTheFirstAndSecondEquivalentToTheThird(); //SUCH AS BASIC D-05
 			
 		if(answer == -1)
 			answer = elProblemo.isItFunkyAndHardToGuessButSimilaritiesInDifferencesMightPredictIt(); //SUCH AS BASIC D-07

 		if(answer == -1)
 			answer = elProblemo.isTheORComboOfRowColumnEndsEquivalentToTheMiddle();  //SUCH AS BASIC E-06

 		return answer;    	
    }
    
    
}

