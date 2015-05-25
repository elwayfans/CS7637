package ravensproject;

// Uncomment these lines to access image processing.
//import java.awt.Image;
//import java.io.File;
//import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.util.HashMap;

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

    	int numAnswers = 6;
    	if(type.equals("3x3"))
    		numAnswers = 8;

    	if(debugPrinting == debugPrintType.SOME || debugPrinting == debugPrintType.ALL) {
	    	System.out.println("************************");
	    	System.out.println("Here we go for " + name);
	    	System.out.println("************************");
    	}
    	
    	int answer = -1;
    	
    	if(problem.hasVerbal()) {
    		
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
    			AgentMappingScore temp = compCTests.get(i).calculateScores(compAB);
    			temp.correspondingMapIndex = i;
    			insertScoreIntoSortedArray(temp, compCScoreRankings);
    		}

    		ArrayList<AgentMappingScore> compBScoreRankings = new ArrayList<AgentMappingScore>();
    		for(int i = 0; i < compBTests.size(); ++i) {
    			AgentMappingScore temp = compBTests.get(i).calculateScores(compAC);
    			temp.correspondingMapIndex = i;
    			insertScoreIntoSortedArray(temp, compBScoreRankings);
    		}
    		
    		int index = getBestCombinedRankingIndex(compCScoreRankings, compBScoreRankings); 
    		
    		answer = index + 1;
    		
        	if(debugPrinting == debugPrintType.SOME || debugPrinting == debugPrintType.ALL) {
        		System.out.println("Guess: " + (answer));
        	}
        	
        	int realAnswer = problem.checkAnswer(answer);
            
        	if(debugPrinting == debugPrintType.SOME || debugPrinting == debugPrintType.ALL) {
        		System.out.println("Answer: " + realAnswer);
        	}
    	}
    	else {

    		if(debugPrinting == debugPrintType.SOME || debugPrinting == debugPrintType.ALL) {
    	    	System.out.println("No verbal data available");
        	}
    	}
    	
        return answer;
    }
    
    private int getBestCombinedRankingIndex(ArrayList<AgentMappingScore> compCScoreRankings, ArrayList<AgentMappingScore> compBScoreRankings) {
    	
    	HashMap<Integer, Integer> indexScores = new HashMap<Integer, Integer>();
    	
    	for(int i = 0; i < compCScoreRankings.size(); ++i) {
    		if(indexScores.containsKey(compCScoreRankings.get(i).correspondingMapIndex)) {
    			int prevValue = indexScores.get(compCScoreRankings.get(i).correspondingMapIndex);
    			indexScores.put(compCScoreRankings.get(i).correspondingMapIndex, prevValue + i);
    		}
    		else {
    			indexScores.put(compCScoreRankings.get(i).correspondingMapIndex, i);
    		}
    	}

    	for(int i = 0; i < compBScoreRankings.size(); ++i) {
    		if(indexScores.containsKey(compBScoreRankings.get(i).correspondingMapIndex)) {
    			int prevValue = indexScores.get(compBScoreRankings.get(i).correspondingMapIndex);
    			indexScores.put(compBScoreRankings.get(i).correspondingMapIndex, prevValue + i);
    		}
    		else {
    			indexScores.put(compBScoreRankings.get(i).correspondingMapIndex, i);
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
}

