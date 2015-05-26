package ravensproject;

import ravensproject.AgentShapeMapping.mappingTransformations;

public class AgentTransformation {

	mappingTransformations transformation;
	Object value;
	
	public AgentTransformation(	mappingTransformations transformation, Object value) {
		this.transformation = transformation;
		this.value = value;
	}
	
}
