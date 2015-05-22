package ravensproject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AgentDiagramComparison {

	RavensFigure figure1 = null;
	RavensFigure figure2 = null;
	ArrayList<AgentShapeMapping> allPossibleMappings = new ArrayList<AgentShapeMapping>();
	
	public AgentDiagramComparison(RavensFigure fig1, RavensFigure fig2) {
		figure1 = fig1;
		figure2 = fig2;
		
		int numObjects1 = getObjectCount(1);
		int numObjects2 = getObjectCount(2);
		
		for(int i = 0; i < numObjects1; ++i) {
			AgentShapeMapping mapping = new AgentShapeMapping();
			mapping.figure1Objects.add(getFigureObjectByIndex(figure1,  i));
			//NOT SURE HOW TO MAP THESE -- THINKING I NEED RECURSION
		}
	}
	
	public String getFigureName(int figure) {
		if(figure == 1)
			return figure1.getName();
		if(figure == 2)
			return figure2.getName();
		return "Invalid Figure Number";
	}
	
	public int getObjectCount(int figure) {
		if(figure == 1)
			return figure1.getObjects().size();
		if(figure == 2)
			return figure2.getObjects().size();
		return -1;
	}
	
	public RavensObject getFigureObjectByIndex(RavensFigure figure, int objectIndex) {
		
		HashMap<String, RavensObject> objects = figure.getObjects();
		
		int index = 0;
		for(HashMap.Entry<String, RavensObject> entry : objects.entrySet()){
			if(index == objectIndex) {
				return entry.getValue();
			}
			
			index++;
		}

		return null;
	}
	
	public Map.Entry<String, String> getObjectAttributeByIndex(RavensObject object, int attributeIndex) {
		
		int index = 0;
		for(HashMap.Entry<String, String> attribEntry: object.getAttributes().entrySet()) {
			
			if(index == attributeIndex) {
				return attribEntry;
			}
		}
		
	}


	
	
}
