package ravensproject;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class AgentShapeMapping {

	ArrayList<Map.Entry<String, RavensObject>> figure1Objects = new ArrayList<Map.Entry<String, RavensObject>>();
	ArrayList<Map.Entry<String, RavensObject>> figure2Objects = new ArrayList<Map.Entry<String, RavensObject>>();
	enum mappingTransformations { UNDEFINED, /*NO_CHANGE,*/ SHAPE_CHANGE, SIZE_CHANGE, ABOVE_CHANGE, OVERLAP_CHANGE, ANGLE_CHANGE, FILL_CHANGE, INSIDE_CHANGE, ALIGNMENT_CHANGE, CREATED, DELETED	}
	ArrayList<ArrayList<mappingTransformations>> mapTransformations = new ArrayList<ArrayList<mappingTransformations>>(); 
	AgentMappingScore mapScore = null;
	
	String comparisonName = "";
	String problemName = "";	
	
	public AgentShapeMapping(String problem, String name) {
		problemName = problem;
		comparisonName = name;
	}
	
	public AgentShapeMapping(String problem, String name, AgentShapeMapping map) {
		problemName = problem;
		comparisonName = name;
		
		//COPY CONSTRUCTOR
		for(int i = 0; i < map.figure1Objects.size(); ++i) {
			Map.Entry<String, RavensObject> temp = map.figure1Objects.get(i);
			figure1Objects.add(temp);
		}
		for(int i = 0; i < map.figure2Objects.size(); ++i) {
			Map.Entry<String, RavensObject> temp = map.figure2Objects.get(i);
			figure2Objects.add(temp);
		}	
	}
	
	public void printMapping(String title) {
		System.out.println("Mapping: " + title);
		if(figure1Objects.size() != figure2Objects.size()) {
			System.out.println("Mapped object lists aren't same size. Cannot print map");
			return;
		}
			
		for(int i = 0; i < figure1Objects.size(); ++i) {
			
			String transformation = "";
			for(int j = 0; j < mapTransformations.get(i).size(); ++j) {
				transformation += mapTransformations.get(i).get(j).toString() + " ";
			}				
			
			System.out.println("fig1: " + figure1Objects.get(i).getValue().getName() + " maps to fig2: " + figure2Objects.get(i).getValue().getName() + " transform: " + transformation);
		}
	}
	
	public boolean keyAlreadyUsed(ArrayList<Map.Entry<String, RavensObject>> objectList, String key) {
		for(int i = 0; i < objectList.size(); ++i) {
			if(objectList.get(i).getKey().equals(key)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hasSameMappingsIgnoringDummys(AgentShapeMapping otherMapping) {
		
		for(int i = 0; i < figure1Objects.size(); ++i) {
			String fig1Key = figure1Objects.get(i).getKey();
			String fig2Key = figure2Objects.get(i).getKey();
			
			//IGNORE ANY MAPS TO DUMMY OBJECTS
			if(isDummyObject(fig1Key) || isDummyObject(fig2Key))
				continue;
			
			for(int j = 0; j < otherMapping.figure1Objects.size(); ++j) {
				if(otherMapping.figure1Objects.get(j).getKey().equals(fig1Key) && !otherMapping.figure2Objects.get(j).getKey().equals(fig2Key)) {
					return false;
				}
			}
			
		}
		
		return true;
	}
	
	public boolean isDummyObject(String objectKey) {
		return objectKey.contains(AgentDiagramComparison.dummyRavensObjectString);		
	}
	
	public void identifyTransformationsForMap() {
		mapTransformations.clear();
		
		
		for(int i = 0; i < figure1Objects.size(); ++i) {
			String Obj1Key = figure1Objects.get(i).getKey();
			String Obj2Key = figure2Objects.get(i).getKey();
			RavensObject Obj1 = figure1Objects.get(i).getValue();
			RavensObject Obj2 = figure2Objects.get(i).getValue();
			
			ArrayList<mappingTransformations> transforms = getTransformations(Obj1Key, Obj1, Obj2Key, Obj2);
			mapTransformations.add(transforms);
		}
	}
	
	private ArrayList<mappingTransformations> getTransformations(String Obj1Key, RavensObject Obj1, String Obj2Key, RavensObject Obj2) {
		ArrayList<mappingTransformations> retval = new ArrayList<mappingTransformations>();
		
		
		//IF EITHER OBJECT IS A DUMMY OBJECT, ADD THE CREATED OR DELETED FLAG AND SKIP THE REST
		if(isDummyObject(Obj1Key))
			retval.add(mappingTransformations.CREATED);
		else if (isDummyObject(Obj2Key))
			retval.add(mappingTransformations.DELETED);
		else {
			//CYCLE THROUGH ATTRIBUTES ON Obj1 AND SEE IF THEY ARE THE SAME ON Obj2
			for(HashMap.Entry<String, String> Obj1Attribute : Obj1.getAttributes().entrySet()){
				
				mappingTransformations theTransformForThisAttribute = getTransformFromAttribute(Obj1Attribute);
	
				if(Obj2.getAttributes().containsKey(Obj1Attribute.getKey())) {
	
					if(!treatAllInstancesOfThisTransformAsEqual(theTransformForThisAttribute)) {
						String value = Obj2.getAttributes().get(Obj1Attribute.getKey());
						if(!value.equals(Obj1Attribute.getValue())) {
							retval.add(theTransformForThisAttribute);
						}
					}
				}
				else
					retval.add(theTransformForThisAttribute);
				
			}
	
			
			//NOW CYCLE THROUGH ATTRIBUTES ON Obj2 AND SEE IF THEY ARE THE SAME ON Obj1
			//DON'T ADD THE SAME mappingTransformations OBJECT
			for(HashMap.Entry<String, String> Obj2Attribute : Obj2.getAttributes().entrySet()){
				
				mappingTransformations theTransformForThisAttribute = getTransformFromAttribute(Obj2Attribute);
	
				if(!retval.contains(theTransformForThisAttribute)) {
					
					if(Obj1.getAttributes().containsKey(Obj2Attribute.getKey())) {
	
						if(!treatAllInstancesOfThisTransformAsEqual(theTransformForThisAttribute)) {
							String value = Obj1.getAttributes().get(Obj2Attribute.getKey());
							if(!value.equals(Obj2Attribute.getValue())) {
								retval.add(theTransformForThisAttribute);
							}
						}
					}
					else
						retval.add(theTransformForThisAttribute);
				}
				
			}		
	
//			if(retval.size() == 0)
//				retval.add(mappingTransformations.NO_CHANGE);
		}
		
		return retval;
	}
	
	private mappingTransformations getTransformFromAttribute(Map.Entry<String, String> attrib) {
		if(attrib.getKey().equals("shape"))
			return mappingTransformations.SHAPE_CHANGE;
		if(attrib.getKey().equals("size"))
			return mappingTransformations.SIZE_CHANGE;
		if(attrib.getKey().equals("above"))
			return mappingTransformations.ABOVE_CHANGE;
		if(attrib.getKey().equals("overlaps"))
			return mappingTransformations.OVERLAP_CHANGE;
		if(attrib.getKey().equals("angle"))
			return mappingTransformations.ANGLE_CHANGE;
		if(attrib.getKey().equals("fill"))
			return mappingTransformations.FILL_CHANGE;
		if(attrib.getKey().equals("inside"))
			return mappingTransformations.INSIDE_CHANGE;
		if(attrib.getKey().equals("alignment"))
			return mappingTransformations.ALIGNMENT_CHANGE;
		
		return mappingTransformations.UNDEFINED;
	}
	
	/*******************************************************************************
	 * SOME TRANSFORMATIONS REFERENCE OTHER OBJECTS IN THEIR VALUES SO THEY WILL 
	 * ALWAYS BE "DIFFERENT" WHEN COMPARING THEIR VALUES
	 * THIS WILL IGNORE SOME OR ALLOF THOSE
	 ****************************************************************************/
	private boolean treatAllInstancesOfThisTransformAsEqual(mappingTransformations trans) {
		if(trans == mappingTransformations.ABOVE_CHANGE)
			return true;
		if(trans == mappingTransformations.INSIDE_CHANGE)
			return true;
		if(trans == mappingTransformations.OVERLAP_CHANGE)
			return true;

		return false;
	}

	public AgentMappingScore calculateScore(AgentDiagramComparison compareTo) {
		
		AgentMappingScore bestScore = null;
		
		//LOOP THROUGH ALL THE POSSIBLE MAPPINGS IN "COMPARE TO" AND FIND THE CLOSEST 
		//MATCH TO THIS MAPS TRANSFORMATIONS 
		for(int i = 0; i < compareTo.allPossibleMappings.size(); ++i) {
			AgentShapeMapping compareMap = compareTo.allPossibleMappings.get(i);
			
			AgentMappingScore thisScore = GenerateProximityScore(mapTransformations, compareMap.mapTransformations);
			if(bestScore == null || thisScore.whichScoreIsBetter(bestScore) == thisScore)
				bestScore = thisScore;
		}
		
		mapScore = bestScore;
		return mapScore;
	}
	
	private AgentMappingScore GenerateProximityScore(ArrayList<ArrayList<mappingTransformations>> tranListA, ArrayList<ArrayList<mappingTransformations>> tranListB) {
		
		ArrayList<mappingTransformations> totalTransformationDelta = new ArrayList<mappingTransformations>();
		
		ArrayList<Integer> alreadyUsedIndices = new ArrayList<Integer>();
		for(int i = 0; i < tranListA.size(); ++i) {
			
			AgentMappingScore theScore = getClosestMatch(tranListA.get(i), tranListB, alreadyUsedIndices);
			
			//THIS IS RETURNING NULL ON PROBLEM 10 - NOT SURE WHY BUT SEEING IF THIS FIXES IT AND IT WORKS.
			if(theScore.transformationDelta != null) {
				
				alreadyUsedIndices.add(theScore.correspondingMapIndex);
				for(int j = 0; j < theScore.transformationDelta.size(); ++j) {
//					if(theScore.transformationDelta.get(j) != mappingTransformations.NO_CHANGE ||
//						(theScore.transformationDelta.get(j) == mappingTransformations.NO_CHANGE && !totalTransformationDelta.contains(mappingTransformations.NO_CHANGE)))
					totalTransformationDelta.add(theScore.transformationDelta.get(j));
				}
				
			}
			
		}

		
		return new AgentMappingScore(-1, totalTransformationDelta, mapTransformations);
	}
	
	private AgentMappingScore getClosestMatch(ArrayList<mappingTransformations> transformList, ArrayList<ArrayList<mappingTransformations>> listOTransformLists, ArrayList<Integer> alreadyUsedIndices) {
		
		int bestMatchIndex = -1;
		ArrayList<mappingTransformations> bestTransformDifference = null;
		
		for(int i = 0; i < listOTransformLists.size(); ++i) {
			if(alreadyUsedIndices.contains(i))
				continue;
			
			ArrayList<mappingTransformations> transformDifference = getDifferenceInTransformLists(transformList, listOTransformLists.get(i));
			
			if(bestTransformDifference == null || bestTransformDifference.size() > transformDifference.size()) {
				bestTransformDifference = transformDifference;
				bestMatchIndex = i;
			}
		}
		
		return new AgentMappingScore(bestMatchIndex, bestTransformDifference, mapTransformations);
	}
	
	private ArrayList<mappingTransformations> getDifferenceInTransformLists(ArrayList<mappingTransformations> listA, ArrayList<mappingTransformations> listB) {
		ArrayList<mappingTransformations> transformDifference = new ArrayList<mappingTransformations>();
		
		for(int i = 0; i < listA.size(); ++i) {
			if(!listB.contains(listA.get(i)))// && listA.get(i) != mappingTransformations.NO_CHANGE)
				transformDifference.add(listA.get(i));
		}

		for(int i = 0; i < listB.size(); ++i) {
			if(!listA.contains(listB.get(i)))// && listB.get(i) != mappingTransformations.NO_CHANGE)
				transformDifference.add(listB.get(i));
		}
		
		
		return transformDifference;
	}

}
