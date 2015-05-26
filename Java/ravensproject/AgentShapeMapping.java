package ravensproject;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class AgentShapeMapping {

	ArrayList<Map.Entry<String, RavensObject>> figure1Objects = new ArrayList<Map.Entry<String, RavensObject>>();
	ArrayList<Map.Entry<String, RavensObject>> figure2Objects = new ArrayList<Map.Entry<String, RavensObject>>();
	enum mappingTransformations { UNDEFINED, /*NO_CHANGE,*/ SHAPE_CHANGE, SIZE_CHANGE, ABOVE_CHANGE, OVERLAP_CHANGE, ANGLE_CHANGE, FILL_CHANGE, INSIDE_CHANGE, ALIGNMENT_CHANGE, CREATED, DELETED	}
	ArrayList<ArrayList<AgentTransformation>> mapTransformations = new ArrayList<ArrayList<AgentTransformation>>(); 
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
			
			ArrayList<AgentTransformation> transforms = getTransformations(Obj1Key, Obj1, Obj2Key, Obj2);
			mapTransformations.add(transforms);
		}
	}
	
	private ArrayList<AgentTransformation> getTransformations(String Obj1Key, RavensObject Obj1, String Obj2Key, RavensObject Obj2) {
		ArrayList<AgentTransformation> retval = new ArrayList<AgentTransformation>();
		
		
		//IF EITHER OBJECT IS A DUMMY OBJECT, ADD THE CREATED OR DELETED FLAG AND SKIP THE REST
		if(isDummyObject(Obj1Key))
			retval.add(new AgentTransformation(mappingTransformations.CREATED, null));
		else if (isDummyObject(Obj2Key))
			retval.add(new AgentTransformation(mappingTransformations.DELETED, null));
		else {
			//CYCLE THROUGH ATTRIBUTES ON Obj1 AND SEE IF THEY ARE THE SAME ON Obj2
			for(HashMap.Entry<String, String> Obj1Attribute : Obj1.getAttributes().entrySet()){
				
				AgentTransformation theTransformForThisAttribute = getTransformFromAttribute(Obj1Attribute);
	
				if(Obj2.getAttributes().containsKey(Obj1Attribute.getKey())) {
	
					if(!treatAllInstancesOfThisTransformAsEqual(theTransformForThisAttribute)) {
						String value = Obj2.getAttributes().get(Obj1Attribute.getKey());
						if(!value.equals(Obj1Attribute.getValue())) {
							retval.add(AgentTransformation.copy(theTransformForThisAttribute));
						}
					}
				}
				else
					retval.add(AgentTransformation.copy(theTransformForThisAttribute));
				
			}
	
			
			//NOW CYCLE THROUGH ATTRIBUTES ON Obj2 AND SEE IF THEY ARE THE SAME ON Obj1
			//DON'T ADD THE SAME mappingTransformations OBJECT
			for(HashMap.Entry<String, String> Obj2Attribute : Obj2.getAttributes().entrySet()){
				
				AgentTransformation theTransformForThisAttribute = getTransformFromAttribute(Obj2Attribute);
	
//				2015-05-26 - WAS ONLY ADDING THIS TRANSFORMATION IF IT WAS THE FIRST OF ITS KIND. 
//				I THINK THAT'S WRONG AND THAT IT SHOULD BE AN EXHAUSTIVE LIST. WE SHALL SEE....
//				if(getIndexOfFirstBestMatch(theTransformForThisAttribute, retval) == -1) {
					
					if(Obj1.getAttributes().containsKey(Obj2Attribute.getKey())) {
	
						if(!treatAllInstancesOfThisTransformAsEqual(theTransformForThisAttribute)) {
							String value = Obj1.getAttributes().get(Obj2Attribute.getKey());
							if(!value.equals(Obj2Attribute.getValue())) {
								retval.add(AgentTransformation.copy(theTransformForThisAttribute));
							}
						}
					}
					else
						retval.add(AgentTransformation.copy(theTransformForThisAttribute));
	//			}
				
			}		
	
//			if(retval.size() == 0)
//				retval.add(mappingTransformations.NO_CHANGE);
		}
		
		return retval;
	}
	
	private AgentTransformation getTransformFromAttribute(Map.Entry<String, String> attrib) {
		if(attrib.getKey().equals("shape"))
			return new AgentTransformation(mappingTransformations.SHAPE_CHANGE, attrib.getValue());
		if(attrib.getKey().equals("size"))
			return new AgentTransformation(mappingTransformations.SIZE_CHANGE, attrib.getValue());
		if(attrib.getKey().equals("above"))
			return new AgentTransformation(mappingTransformations.ABOVE_CHANGE, attrib.getValue());
		if(attrib.getKey().equals("overlaps"))
			return new AgentTransformation(mappingTransformations.OVERLAP_CHANGE, attrib.getValue());
		if(attrib.getKey().equals("angle"))
			return new AgentTransformation(mappingTransformations.ANGLE_CHANGE, attrib.getValue());
		if(attrib.getKey().equals("fill"))
			return new AgentTransformation(mappingTransformations.FILL_CHANGE, attrib.getValue());
		if(attrib.getKey().equals("inside"))
			return new AgentTransformation(mappingTransformations.INSIDE_CHANGE, attrib.getValue());
		if(attrib.getKey().equals("alignment"))
			return new AgentTransformation(mappingTransformations.ALIGNMENT_CHANGE, attrib.getValue());
		
		return new AgentTransformation(mappingTransformations.UNDEFINED, null);
	}
	
	/*******************************************************************************
	 * SOME TRANSFORMATIONS REFERENCE OTHER OBJECTS IN THEIR VALUES SO THEY WILL 
	 * ALWAYS BE "DIFFERENT" WHEN COMPARING THEIR VALUES
	 * THIS WILL IGNORE SOME OR ALLOF THOSE
	 ****************************************************************************/
	private boolean treatAllInstancesOfThisTransformAsEqual(AgentTransformation trans) {
		if(trans.theTransformation == mappingTransformations.ABOVE_CHANGE)
			return true;
		if(trans.theTransformation == mappingTransformations.INSIDE_CHANGE)
			return true;
		if(trans.theTransformation == mappingTransformations.OVERLAP_CHANGE)
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
	
	private AgentMappingScore GenerateProximityScore(ArrayList<ArrayList<AgentTransformation>> tranListA, ArrayList<ArrayList<AgentTransformation>> tranListB) {
		
		ArrayList<AgentTransformation> totalTransformationDelta = new ArrayList<AgentTransformation>();
		
		ArrayList<ArrayList<AgentTransformation>> newListB = copyListOfLists(tranListB);
		
		
		for(int i = 0; i < tranListA.size(); ++i) {
			
			AgentMappingScore theScore = getClosestMatch(tranListA.get(i), newListB);
			
			if(theScore == null)
				continue;
			
			if(theScore.transformationDelta != null) {
				
				for(int j = 0; j < theScore.transformationDelta.size(); ++j) {
//					if(theScore.transformationDelta.get(j) != mappingTransformations.NO_CHANGE ||
//						(theScore.transformationDelta.get(j) == mappingTransformations.NO_CHANGE && !totalTransformationDelta.contains(mappingTransformations.NO_CHANGE)))
					totalTransformationDelta.add(AgentTransformation.copy(theScore.transformationDelta.get(j)));
				}
				
			}
			
		}

		
		return new AgentMappingScore(-1, totalTransformationDelta, mapTransformations);
	}
	
	private AgentMappingScore getClosestMatch(ArrayList<AgentTransformation> transformList, ArrayList<ArrayList<AgentTransformation>> listOTransformLists) {
		
		int bestMatchIndex = -1;
		ArrayList<AgentTransformation> bestTransformDifferenceTotalTransform = null;
		ArrayList<AgentTransformation> bestTransformDifference = null;
		
		for(int i = 0; i < listOTransformLists.size(); ++i) {
			
			ArrayList<AgentTransformation> transformDifference = getDifferenceInTransformLists(transformList, listOTransformLists.get(i));
			
			if(bestTransformDifference == null || whichTransformListCostsLess("champ", bestTransformDifference, bestTransformDifferenceTotalTransform, "contender", transformDifference, listOTransformLists.get(i)) == "contender") {
				bestTransformDifference = transformDifference;
				bestTransformDifferenceTotalTransform = listOTransformLists.get(i);
				bestMatchIndex = i;
			}
		}
		
		if(bestMatchIndex == -1)
			return null;

		listOTransformLists.remove(bestMatchIndex);
		
		return new AgentMappingScore(bestMatchIndex, bestTransformDifference, mapTransformations);
	}
	
	private ArrayList<AgentTransformation> getDifferenceInTransformLists(ArrayList<AgentTransformation> listA, ArrayList<AgentTransformation> listB) {
		ArrayList<AgentTransformation> transformDifference = new ArrayList<AgentTransformation>();
		
		ArrayList<AgentTransformation> newListA = copyList(listA);
		ArrayList<AgentTransformation> newListB = copyList(listB);
		
		for(int i = 0; i < newListA.size(); ++i) {
			
			int matchingIndex = getIndexOfFirstBestMatch(newListA.get(i), newListB);
			
			if(matchingIndex == -1)// && listA.get(i) != mappingTransformations.NO_CHANGE)
				transformDifference.add(AgentTransformation.copy(newListA.get(i)));
			else
				newListB.remove(matchingIndex);
		}

		newListA = copyList(listA);
		newListB = copyList(listB);
		
		for(int i = 0; i < newListB.size(); ++i) {
			
			int matchingIndex = getIndexOfFirstBestMatch(newListB.get(i),  newListA);
			
			if(matchingIndex == -1)// && listB.get(i) != mappingTransformations.NO_CHANGE)
				transformDifference.add(AgentTransformation.copy(newListB.get(i)));
			else
				newListA.remove(matchingIndex);
		}
		
		
		return transformDifference;
	}
	
	//FINDS THE FIRST BEST MATCH OF A TRANSFORMATION IN A TRANSFORMATION LIST AND RETURNS THE INDEX
	//IF THERE ARE TWO OF THE SAME TRANSFORMATION IN THE LIST IT RETURNS THE FIRST EXACT MATCH INCLUDING 
	//THE ATTRIBUTE VALUE
	private int getIndexOfFirstBestMatch(AgentTransformation A, ArrayList<AgentTransformation> theList) {
		int retval = -1;
		
		for(int i = 0; i < theList.size(); ++i) {
			if(theList.get(i).theTransformation == A.theTransformation) {
				if(theList.get(i).theValue == A.theValue)
					return i;
				
				//NOT A PERFECT MATCH BASED ON THE ATTRIBUTE VALUE, BUT IT WILL WORK
				if(retval == -1)
					retval = i;
			}
		}
		
		return retval;
	}

	private ArrayList<ArrayList<AgentTransformation>> copyListOfLists(ArrayList<ArrayList<AgentTransformation>> list) {
		
		ArrayList<ArrayList<AgentTransformation>> retval = new ArrayList<ArrayList<AgentTransformation>>();
		
		for(int i = 0; i < list.size(); ++i) {
			retval.add(copyList(list.get(i)));
		}
		
		return retval;
	}	
	
	private ArrayList<AgentTransformation> copyList(ArrayList<AgentTransformation> list) {
		
		ArrayList<AgentTransformation> retval = new ArrayList<AgentTransformation>();
		
		for(int i = 0; i < list.size(); ++i) {
			retval.add(AgentTransformation.copy(list.get(i)));
		}
		
		return retval;
	}
	
	private String whichTransformListCostsLess(String AName, ArrayList<AgentTransformation> ADiff, ArrayList<AgentTransformation> ATotal, 
			String BName, ArrayList<AgentTransformation> BDiff, ArrayList<AgentTransformation> BTotal) {
		//*********************************************************
		// THIS AND IN AgentMappingScore.whichScoreIsBetter ARE
		// THE PLACES TO PUT LEARNING LOGIC
		//*********************************************************
		
		int costOfADiff = AgentMappingScore.getTransformationListWeight(ADiff);
		int costOfATotal = AgentMappingScore.getTransformationListWeight(ATotal);
		int costOfBDiff = AgentMappingScore.getTransformationListWeight(BDiff);
		int costOfBTotal = AgentMappingScore.getTransformationListWeight(BTotal);

		
		if(AgentMappingScore.whichScoreIsBetter("A", costOfADiff, costOfATotal, "B", costOfBDiff, costOfBTotal) == "A")
			return AName;
		
		return BName; 
	}
	
}
