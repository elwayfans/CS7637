package ravensproject;

// Uncomment these lines to access image processing.
//import java.awt.Image;
//import java.io.File;
//import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.ArrayList;

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

    	System.out.println("************************");
    	System.out.println("Here we go for " + name);
    	System.out.println("************************");
    	
    	
    	if(problem.hasVerbal()) {
    		
    		//BUILD COMPARISON MAPPINGS FOR A AND B
    		AgentDiagramComparison compAB = new AgentDiagramComparison(problem.getFigures().get("A"), problem.getFigures().get("B"));
    		
    		//BUILD COMPARISON MAPPINGS FOR C AND EACH TEST CASE
    		ArrayList<AgentDiagramComparison> compCTests = new ArrayList<AgentDiagramComparison>();
    		for(int i = 0; i < numAnswers; ++i) {
    			compCTests.add(new AgentDiagramComparison(problem.getFigures().get("C"), problem.getFigures().get(Integer.toString(i + 1))));    		
    		}
    		

    		//GO THROUGH EACH MAPPING IN EACH OF THE SOLUTION COMPARISONS IN compCTests.  ASSIGN EACH 
    		//MAPPING A SCORE BASED ON HOW CLOSE IT IS TO ANY OF THE MAPPINGS IN compAB.  
    		//THEN ASSIGN EACH SOLUTION A SCORE WHICH CORRELATES TO THE LOWEST SCORE OF ITS MAPS
    		//IF THERE'S A TIE, GO WITH THE LOWEST SCORE
    		for(int i = 0; i < compCTests.size(); ++i) {
    			compCTests.get(i).calculateScores();
    		}
    			
    		
    	}
    	
    	int realAnswer = problem.checkAnswer(1);
        
        System.out.println("Answer: " + realAnswer);
        
        return -1;
    }
}

