package ravensproject;

import java.util.ArrayList;

import ravensproject.AgentShapeMapping.mappingTransformations;

public class AgentMappingScore {

	int transformationScore = -1;
	//WHAT'S THE COST OF THE DIFFERENCE BETWEEN THESE TRANSFORMATIONS AND THE COMPARED ONE?
	int transformationDeltCost = -1;
	//WHAT'S THE TOTAL COST OF THE TRANSFORMATIONS FOR THIS MAP?
	int transformationTotalCost = -1;
	ArrayList<mappingTransformations> transformationDelta = null;
	ArrayList<mappingTransformations> totalTransformation = null;
	int correspondingMapIndex = -1;
	
	public AgentMappingScore(int correspondingMapIndex, ArrayList<mappingTransformations> transformationDelta, ArrayList<mappingTransformations> totalTransformation) {
		this.transformationDelta = transformationDelta;
		this.totalTransformation = totalTransformation;
		this.correspondingMapIndex = correspondingMapIndex;
		
		if(transformationDelta != null) {

			//HERE IS WHERE I WOULD ADD ANY KIND OF WEIGHTING OF TRANSFORMATIONS, LEARNING FROM CASES, ETC
			calculateScore();
			
		}
		
	}
		
	private void calculateScore() {
		int deltaScore = 0;
		int deltaCost = 0;
		
		for(int i = 0; i < transformationDelta.size(); ++i) {
			if(transformationDelta.get(i) != mappingTransformations.NO_CHANGE) {
				deltaScore += 100;
				deltaCost += 1;
			}
		}
		
		transformationScore = deltaScore;
		transformationDeltCost = deltaCost;

		int totalCost = 0;
		for(int i = 0; i < totalTransformation.size(); ++i) {
			if(totalTransformation.get(i) != mappingTransformations.NO_CHANGE) {
				totalCost += 1;
			}
		}
		
		transformationTotalCost = totalCost;
	
	
	}
	
	public AgentMappingScore whichScoreIsBetter(AgentMappingScore compare) {
		//HERE IS WHERE WE CAN ADD MORE LOGIC FOR MAKING DECISIONS
		
		if(transformationDeltCost == compare.transformationDeltCost) {
			if(transformationTotalCost < compare.transformationTotalCost)
				return this;
			return compare;
		}
		else if(transformationDeltCost < compare.transformationDeltCost)
			return this;
		
		return compare;
	}
}
