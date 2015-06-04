package ravensproject;


public class AgentTransformation {

	//EXPECTED_ANGLE_CHANGE IS FOR SPECIAL CASE ROTATION/REFLECTIONS WHERE CERTAIN ANGLE ROTATIONS ARE EXPECTED
	enum mappingTransformations { UNDEFINED, /*NO_CHANGE,*/ SHAPE_CHANGE, SIZE_CHANGE, ABOVE_CHANGE, OVERLAP_CHANGE, EXPECTEDANGLE_CHANGE, ANGLE_CHANGE, FILL_CHANGE, INSIDE_CHANGE, ALIGNMENT_CHANGE, CREATED, DELETED	}

	enum transformationSizes { UNDEFINED, VERY_SMALL, SMALL, MEDIUM, LARGE, VERY_LARGE, HUGE }
	
	mappingTransformations theTransformation;
	Object theValue;
	
	public AgentTransformation(	mappingTransformations trans, Object val) {
		this.theTransformation = trans;
		this.theValue = val;
	}
	
	public static AgentTransformation copy(AgentTransformation A) {
		return new AgentTransformation(A.theTransformation, A.theValue);
	}
	
	@Override
	public String toString() {
		return theTransformation.name();
	}
	
	public static int getSizeTransformModifier(transformationSizes size1, transformationSizes size2) {
		return size2.ordinal() - size1.ordinal();
	}
	
	public static AgentTransformation isThisAnAnticipatedChange(AgentTransformation uno, AgentTransformation dos) {
		//CHECK FOR ANTICIPATED CHANGES
		
		//ARE THE CHANGES BOTH SIZE CHANGES WITH THE SAME SIZE MODIFIER VALUE?
		if(uno.theTransformation == dos.theTransformation && uno.theTransformation == mappingTransformations.SIZE_CHANGE) {
			if((Integer)uno.theValue == (Integer)dos.theValue)
				return uno;
		}
		
		return null;
	}
}
