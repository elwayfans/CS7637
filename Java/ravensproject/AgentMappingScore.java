package ravensproject;

import java.util.ArrayList;

import ravensproject.AgentShapeMapping.mappingTransformations;

public class AgentMappingScore {

	int transformationScore = -1;
	int transformationCost = -1;
	ArrayList<mappingTransformations> transformationDelta = null;
	int correspondingMapIndex = -1;
	
	public AgentMappingScore(int correspondingMapIndex, ArrayList<mappingTransformations> transformationDelta) {
		this.transformationDelta = transformationDelta;
		this.correspondingMapIndex = correspondingMapIndex;
		
		if(transformationDelta != null) {

			//HERE IS WHERE I WOULD ADD ANY KIND OF WEIGHTING OF TRANSFORMATIONS, LEARNING FROM CASES, ETC
			transformationCost = transformationDelta.size();
			transformationScore = transformationScore * 100;
			
		}
		
	}
	
	public AgentMappingScore whichScoreIsBetter(AgentMappingScore compare) {
		//HERE IS WHERE WE CAN ADD MORE LOGIC FOR MAKING DECISIONS
		
		if(transformationCost < compare.transformationCost)
			return this;
		
		return compare;
	}
}
