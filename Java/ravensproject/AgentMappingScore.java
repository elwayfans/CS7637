package ravensproject;

import java.util.ArrayList;

import ravensproject.AgentShapeMapping.mappingTransformations;

public class AgentMappingScore {

	int transformationScore = -1;
	//WHAT'S THE COST OF THE DIFFERENCE BETWEEN THESE TRANSFORMATIONS AND THE COMPARED ONE?
	int transformationDeltaCost = -1;
	//WHAT'S THE TOTAL COST OF THE TRANSFORMATIONS FOR THIS MAP?
	int transformationTotalCost = -1;
	ArrayList<mappingTransformations> transformationDelta = null;
	ArrayList<mappingTransformations> totalTransformation = null;
	int correspondingMapIndex = -1;
	
	public AgentMappingScore(int correspondingMapIndex, ArrayList<mappingTransformations> transformationDelta, ArrayList<ArrayList<mappingTransformations>> transformationsForEachObject) {
		this.transformationDelta = transformationDelta;
		this.totalTransformation = getTotalTransformation(transformationsForEachObject);
		this.correspondingMapIndex = correspondingMapIndex;
		
		if(transformationDelta != null) {

			//HERE IS WHERE I WOULD ADD ANY KIND OF WEIGHTING OF TRANSFORMATIONS, LEARNING FROM CASES, ETC
			calculateScore();
			
		}
		
	}
	
	//ACCEPTS AN ARRAY OF TRANSFORMATION ARRAYS FOR EACH OBJECT IN A MAP BETWEEN FIGURES. 
	//RETURNS THE CUMULATIVE LIST OF ALL TRANSFORMATIONS USED IN THE MAP
	private ArrayList<mappingTransformations> getTotalTransformation(ArrayList<ArrayList<mappingTransformations>> list) {
		ArrayList<mappingTransformations> retval = new ArrayList<mappingTransformations>();
		
		for(int i = 0; i < list.size(); ++i) {
			for(int j = 0; j < list.get(i).size(); ++j) {
				if(list.get(i).get(j) != mappingTransformations.NO_CHANGE)
					retval.add(list.get(i).get(j));
			}
		}
		
		if(retval.size() == 0)
			retval.add(mappingTransformations.NO_CHANGE);
		
		return retval;
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
		transformationDeltaCost = deltaCost;

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
		
		if(transformationDeltaCost == compare.transformationDeltaCost) {
			if(transformationTotalCost < compare.transformationTotalCost)
				return this;
			return compare;
		}
		else if(transformationDeltaCost < compare.transformationDeltaCost)
			return this;
		
		return compare;
	}
}
