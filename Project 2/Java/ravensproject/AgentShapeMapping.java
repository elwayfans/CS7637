package ravensproject;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import ravensproject.Agent.debugPrintType;
import ravensproject.AgentSpecialHandling.specialType;
import ravensproject.AgentTransformation.mappingTransformations;
import ravensproject.AgentTransformation.transformationSizes;

public class AgentShapeMapping {

	static String attribKey_shape = "shape";
	static String attribKey_size = "size";
	static String attribKey_width = "width";
	static String attribKey_height = "height";
	static String attribKey_above = "above";
	static String attribKey_leftof = "left-of";
	static String attribKey_overlaps = "overlaps";
	static String attribKey_angle = "angle";
	static String attribKey_fill = "fill";
	static String attribKey_inside = "inside";
	static String attribKey_alignment = "alignment";	
	
	static String sizeKey_verysmall = "very small";
	static String sizeKey_small = "small";
	static String sizeKey_medium = "medium";
	static String sizeKey_large = "large";
	static String sizeKey_verylarge = "very large";
	static String sizeKey_huge = "huge";
	
	ArrayList<Map.Entry<String, RavensObject>> figure1Objects = new ArrayList<Map.Entry<String, RavensObject>>();
	ArrayList<Map.Entry<String, RavensObject>> figure2Objects = new ArrayList<Map.Entry<String, RavensObject>>();
	
	ArrayList<ArrayList<AgentTransformation>> mapTransformations = new ArrayList<ArrayList<AgentTransformation>>();
	int totalTransformationCount = -1;
	AgentMappingScore mapScore = null;
	
	String comparisonName = "";
	String problemName = "";
	String mappingName = "";
	
	debugPrintType debugPrinting;
	
	public AgentShapeMapping(debugPrintType debug, String problem, String name, String mappingName) {
		problemName = problem;
		comparisonName = name;
		this.mappingName = mappingName;
		debugPrinting = debug;
	}
	
	public AgentShapeMapping(String problem, String name, String mappingName, AgentShapeMapping map) {
		problemName = problem;
		comparisonName = name;
		this.mappingName = mappingName;
		debugPrinting = map.debugPrinting;
		
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
	
	public void printMapping() {
		System.out.println("Mapping: " + mappingName);
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
				addTransformationBetweenObjects(Obj2, retval, Obj1Attribute);
			}
	
			
			//NOW CYCLE THROUGH ATTRIBUTES ON Obj2 AND SEE IF THEY ARE THE SAME ON Obj1
			//DON'T ADD THE SAME mappingTransformations OBJECT
			for(HashMap.Entry<String, String> Obj2Attribute : Obj2.getAttributes().entrySet()){
					addTransformationBetweenObjects(Obj1, retval, Obj2Attribute);
			}		
	
		}
		
		return retval;
	}

	public void addTransformationBetweenObjects(RavensObject Obj2,
			ArrayList<AgentTransformation> retval,
			HashMap.Entry<String, String> Obj1Attribute) {
		AgentTransformation theTransformForThisAttribute = getTransformFromAttribute(Obj1Attribute.getKey(), Obj1Attribute.getValue());

		if(Obj2.getAttributes().containsKey(Obj1Attribute.getKey())) {

			if(!treatAllInstancesOfThisTransformAsEqual(theTransformForThisAttribute)) {
				String value = Obj2.getAttributes().get(Obj1Attribute.getKey());
				if(!value.equals(Obj1Attribute.getValue())) {
					
					if(theTransformForThisAttribute.theTransformation == mappingTransformations.SIZE_CHANGE) {
						//CHANGE THE VALUE OF THE SIZE TO AN INTEGER INDICATING THE VALUE OF THE CHANGE
						AgentTransformation theTransformForObj2 = getTransformFromAttribute(Obj1Attribute.getKey(), Obj2.getAttributes().get(Obj1Attribute.getKey()));
						retval.add(new AgentTransformation(theTransformForThisAttribute.theTransformation, 
								AgentTransformation.getSizeTransformModifier((transformationSizes)theTransformForThisAttribute.theValue, (transformationSizes)theTransformForObj2.theValue)));
						
					}
					else 							
						retval.add(AgentTransformation.copy(theTransformForThisAttribute));
				}
			}
		}
		else
			retval.add(AgentTransformation.copy(theTransformForThisAttribute));
	}
	
	private AgentTransformation getTransformFromAttribute(String key, String value) {
		if(key.equals(attribKey_shape))
			return new AgentTransformation(mappingTransformations.SHAPE_CHANGE, value);
		if(key.equals(attribKey_size))
			return new AgentTransformation(mappingTransformations.SIZE_CHANGE, parseSizeKey(value));
		if(key.equals(attribKey_width))
			return new AgentTransformation(mappingTransformations.WIDTH_CHANGE, parseSizeKey(value));		
		if(key.equals(attribKey_height))
			return new AgentTransformation(mappingTransformations.HEIGHT_CHANGE, parseSizeKey(value));
		if(key.equals(attribKey_above))
			return new AgentTransformation(mappingTransformations.ABOVE_CHANGE, value);
		if(key.equals(attribKey_leftof))
			return new AgentTransformation(mappingTransformations.LEFT_OF_CHANGE, value);
		if(key.equals(attribKey_overlaps))
			return new AgentTransformation(mappingTransformations.OVERLAP_CHANGE, value);
		if(key.equals(attribKey_angle))
			return new AgentTransformation(mappingTransformations.ANGLE_CHANGE, value);
		if(key.equals(attribKey_fill))
			return new AgentTransformation(mappingTransformations.FILL_CHANGE, value);
		if(key.equals(attribKey_inside))
			return new AgentTransformation(mappingTransformations.INSIDE_CHANGE, value);
		if(key.equals(attribKey_alignment))
			return new AgentTransformation(mappingTransformations.ALIGNMENT_CHANGE, value);
		
		return new AgentTransformation(mappingTransformations.UNDEFINED, null);
	}
	
	private transformationSizes parseSizeKey(String size) {
		if(size.equals(sizeKey_verysmall))
				return transformationSizes.VERY_SMALL;
		if(size.equals(sizeKey_small))
				return transformationSizes.SMALL;
		if(size.equals(sizeKey_medium))
				return transformationSizes.MEDIUM;
		if(size.equals(sizeKey_large))
				return transformationSizes.LARGE;
		if(size.equals(sizeKey_verylarge))
				return transformationSizes.VERY_LARGE;
		if(size.equals(sizeKey_huge))
				return transformationSizes.HUGE;


		return transformationSizes.UNDEFINED;
		
	}
	

	
	/*******************************************************************************
	 * SOME TRANSFORMATIONS REFERENCE OTHER OBJECTS IN THEIR VALUES SO THEY WILL 
	 * ALWAYS BE "DIFFERENT" WHEN COMPARING THEIR VALUES
	 * THIS WILL IGNORE SOME OR ALLOF THOSE
	 ****************************************************************************/
	private boolean treatAllInstancesOfThisTransformAsEqual(AgentTransformation trans) {
		if(trans.theTransformation == mappingTransformations.ABOVE_CHANGE)
			return true;
		if(trans.theTransformation == mappingTransformations.LEFT_OF_CHANGE)
			return true;
		if(trans.theTransformation == mappingTransformations.INSIDE_CHANGE)
			return true;
		if(trans.theTransformation == mappingTransformations.OVERLAP_CHANGE)
			return true;

		return false;
	}

	public AgentMappingScore calculateScore(AgentDiagramComparison compareTo, ArrayList<AgentSpecialHandling> specials) {
		
		AgentMappingScore bestScore = null;
		
		//LOOP THROUGH ALL THE POSSIBLE MAPPINGS IN "COMPARE TO" AND FIND THE CLOSEST 
		//MATCH TO THIS MAPS TRANSFORMATIONS 
		for(int i = 0; i < compareTo.allPossibleMappings.size(); ++i) {
			AgentShapeMapping compareMap = compareTo.allPossibleMappings.get(i);
			
			AgentMappingScore thisScore = GenerateProximityScore(mapTransformations, compareMap.mapTransformations, specials);
			
			if(debugPrinting == debugPrintType.ALL) {
				System.out.println(comparisonName + "(" + mappingName + ") to " + compareTo.allPossibleMappings.get(i).comparisonName + "(" + compareTo.allPossibleMappings.get(i).mappingName + ") deltaCost:" + thisScore.transformationDeltaCost + " deltaCount:" + thisScore.transformationDelta.size() + " totalCost:" + thisScore.transformationTotalCost + " totalCount:" + thisScore.totalTransformation.size());
			}
			
			if(bestScore == null || thisScore.whichScoreIsBetter(bestScore) == thisScore)
				bestScore = thisScore;
		}
		
		mapScore = bestScore;
		return mapScore;
	}
	
	private AgentMappingScore GenerateProximityScore(ArrayList<ArrayList<AgentTransformation>> thisTranList, ArrayList<ArrayList<AgentTransformation>> compareTranList,
			ArrayList<AgentSpecialHandling> specials) {
		
		ArrayList<AgentTransformation> totalTransformationDelta = new ArrayList<AgentTransformation>();
		ArrayList<AgentTransformation> anticipatedTransformations = new ArrayList<AgentTransformation>();
		ArrayList<ArrayList<AgentTransformation>> totalTransformations = new ArrayList<ArrayList<AgentTransformation>>();
		
		
		ArrayList<ArrayList<AgentTransformation>> newListB = copyListOfLists(compareTranList);
		
		
		for(int i = 0; i < thisTranList.size(); ++i) {
			
			AgentMappingScore theScore = getClosestMatch(thisTranList.get(i), newListB, specials);
			
			//THERE IS NO MATCHING TRANSFORM LIST FOR THIS LIST SO ADD IT ALL TO THE DELTA
			if(theScore == null) {
				totalTransformations.add(thisTranList.get(i));
				
				for(int j = 0; j < thisTranList.get(i).size(); ++j) {
					totalTransformationDelta.add(thisTranList.get(i).get(j));
				}
				
				continue;
			}
			
			if(theScore.transformationDelta != null) {
				
				for(int j = 0; j < theScore.transformationDelta.size(); ++j) {
//					if(theScore.transformationDelta.get(j) != mappingTransformations.NO_CHANGE ||
//						(theScore.transformationDelta.get(j) == mappingTransformations.NO_CHANGE && !totalTransformationDelta.contains(mappingTransformations.NO_CHANGE)))
					totalTransformationDelta.add(AgentTransformation.copy(theScore.transformationDelta.get(j)));
				}
				
				for(int j = 0; j < theScore.anticipatedTransformations.size(); ++j) {
//					if(theScore.transformationDelta.get(j) != mappingTransformations.NO_CHANGE ||
//						(theScore.transformationDelta.get(j) == mappingTransformations.NO_CHANGE && !totalTransformationDelta.contains(mappingTransformations.NO_CHANGE)))
					anticipatedTransformations.add(AgentTransformation.copy(theScore.anticipatedTransformations.get(j)));
				}				
			}
			
			ArrayList<AgentTransformation> thisTotal = copyList(thisTranList.get(i));
			
			
			//SPECIAL CASE SCAN: ROTATION/REFLECTION.  IF ANY EXPECTEDANGLE_CHANGE TRANSFORMATIONS
			//WERE FOUND IN THE DELTA, REPLACE THE TOTAL ANGLE TRANSFORMATIONS WITH THEM AS WELL
			int expectedRotationCount = 0;
			for(int rotIndex = 0; rotIndex < theScore.transformationDelta.size(); ++rotIndex) {
				if(theScore.transformationDelta.get(rotIndex).theTransformation == mappingTransformations.EXPECTEDANGLE_CHANGE)
					++expectedRotationCount;
			}
			for(int rotIndex = 0; rotIndex < expectedRotationCount; ++rotIndex) {
				for(int totalIndex = 0; totalIndex < thisTotal.size(); ++totalIndex) {
					if(thisTotal.get(totalIndex).theTransformation == mappingTransformations.ANGLE_CHANGE) {
						thisTotal.get(totalIndex).theTransformation = mappingTransformations.EXPECTEDANGLE_CHANGE;
					}
				}
			}
			
			totalTransformations.add(thisTotal);
			
			
		}

		//IF THE COMPARE LIST HAS MORE TRANSFORMATION LISTS THAN THIS LIST, ADD THE REST OF THEM TO THE TOTAL
		for(int i =0; i < newListB.size(); ++i) {
			totalTransformations.add(newListB.get(i));
			
			for(int j = 0; j < newListB.get(i).size(); ++j) {
				totalTransformationDelta.add(newListB.get(i).get(j));
			}
		}
		
		
		
		
		return new AgentMappingScore(-1, totalTransformationDelta, anticipatedTransformations, totalTransformations);
	}

	private AgentMappingScore getClosestMatch(ArrayList<AgentTransformation> thisTransformList, ArrayList<ArrayList<AgentTransformation>> compareTransformLists,
			ArrayList<AgentSpecialHandling> specials) {
		
		int bestMatchIndex = -1;
		ArrayList<AgentTransformation> bestTransformDifferenceTotalTransform = null;
		ArrayList<AgentTransformation> bestTransformDifference = null;
		ArrayList<AgentTransformation> bestAnticipatedTransforms = null;
		
		for(int i = 0; i < compareTransformLists.size(); ++i) {
			
			ArrayList<ArrayList<AgentTransformation>> transformResults = getDifferenceInTransformLists(thisTransformList, compareTransformLists.get(i), specials);

			if(transformResults.size() != 2)
				continue;
			
			if(bestTransformDifference == null || whichTransformListCostsLess("champ", bestTransformDifference, bestAnticipatedTransforms, bestTransformDifferenceTotalTransform, 
					"contender", transformResults.get(0), transformResults.get(1), compareTransformLists.get(i)) == "contender") {
				
				bestTransformDifference = transformResults.get(0);
				bestTransformDifferenceTotalTransform = compareTransformLists.get(i);
				bestAnticipatedTransforms = transformResults.get(1);
				bestMatchIndex = i;
			}
		}
		
		if(bestMatchIndex == -1)
			return null;

		compareTransformLists.remove(bestMatchIndex);
		
		return new AgentMappingScore(bestMatchIndex, bestTransformDifference, bestAnticipatedTransforms, mapTransformations);
	}
	
	private ArrayList<ArrayList<AgentTransformation>> getDifferenceInTransformLists(ArrayList<AgentTransformation> thisList, ArrayList<AgentTransformation> compareList,
			ArrayList<AgentSpecialHandling> specials) {
		
		ArrayList<ArrayList<AgentTransformation>> transformResults = new ArrayList<ArrayList<AgentTransformation>>();
		//INDEX 0 IS FOR DIFFERENCES IN THE TRANSFORMS
		transformResults.add(new ArrayList<AgentTransformation>());
		//INDEX 1 IS FOR ANTICIPATED CHANGES
		transformResults.add(new ArrayList<AgentTransformation>());
		
		ArrayList<AgentTransformation> newthisList = copyList(thisList);
		ArrayList<AgentTransformation> newCompareList = copyList(compareList);
		
		for(int i = 0; i < newthisList.size(); ++i) {
			
			int matchingIndex = getIndexOfFirstBestMatch(newthisList.get(i), newCompareList);
			
			if(matchingIndex == -1)// && listA.get(i) != mappingTransformations.NO_CHANGE)
				transformResults.get(0).add(AgentTransformation.copy(newthisList.get(i)));
			else {
				

				//SCAN THROUGH ANY SPECIALS AND LOOK FOR A SPECIAL ROTATION/REFLECTION CASE
				//IF FOUND, SEE IF THIS TRANSFORM MATCHES IT. IF YES, ADD THE SPECIAL TRANSFORMATION
				//TO THE DELTA LIST
				for(int specialIndex = 0; specialIndex < specials.size(); ++specialIndex) {
					if(specials.get(specialIndex).theType == specialType.REFLECTION_ROTATION) {
					
						int expectedAngle = (int)specials.get(specialIndex).theValue;
						
						if(newthisList.get(i).theTransformation == specials.get(specialIndex).convertFromTransform &&
								Integer.parseInt(((String)newthisList.get(i).theValue))== expectedAngle && 
								newCompareList.get(matchingIndex).theTransformation == specials.get(specialIndex).convertFromTransform) {
						
							transformResults.get(0).add(new AgentTransformation(specials.get(specialIndex).convertToTransform, expectedAngle));
						}							
					}
				}
			
				//CHECK TO SEE IF THERE IS AN ANTICIPATED TRANSFORM HERE
				AgentTransformation anticipatedChange = AgentTransformation.isThisAnAnticipatedChange(newthisList.get(i), newCompareList.get(matchingIndex));
				if(anticipatedChange != null)
					transformResults.get(1).add(anticipatedChange);
				
				newCompareList.remove(matchingIndex);
			}
		}

		newthisList = copyList(thisList);
		newCompareList = copyList(compareList);
		
		for(int i = 0; i < newCompareList.size(); ++i) {
			
			int matchingIndex = getIndexOfFirstBestMatch(newCompareList.get(i),  newthisList);
			
			if(matchingIndex == -1)// && listB.get(i) != mappingTransformations.NO_CHANGE)
				transformResults.get(0).add(AgentTransformation.copy(newCompareList.get(i)));
			else
				newthisList.remove(matchingIndex);
		}
		
		
		return transformResults;
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
	
	private String whichTransformListCostsLess(String AName, ArrayList<AgentTransformation> ADiff, ArrayList<AgentTransformation> AAnticipated, ArrayList<AgentTransformation> ATotal, 
			String BName, ArrayList<AgentTransformation> BDiff, ArrayList<AgentTransformation> BAnticipated, ArrayList<AgentTransformation> BTotal) {
		//*********************************************************
		// THIS AND IN AgentMappingScore.whichScoreIsBetter ARE
		// THE PLACES TO PUT LEARNING LOGIC
		//*********************************************************
		
		int costOfADiff = AgentMappingScore.getTransformationListWeight(ADiff);
		int AAnticipatedValue = AgentMappingScore.getAnticipatedTransformationListValue(AAnticipated);
		int costOfATotal = AgentMappingScore.getTransformationListWeight(ATotal);
		int costOfBDiff = AgentMappingScore.getTransformationListWeight(BDiff);
		int costOfBTotal = AgentMappingScore.getTransformationListWeight(BTotal);
		int BAnticipatedValue = AgentMappingScore.getAnticipatedTransformationListValue(BAnticipated);
		

		
		
		if(AgentMappingScore.whichScoreIsBetter("A", costOfADiff, AAnticipatedValue, costOfATotal, "B", costOfBDiff, BAnticipatedValue, costOfBTotal) == "A")
			return AName;
		
		return BName; 
	}
	
	public boolean mappingIsAtLeastSomewhatLikelyToBeUsed() {
		//USED TO DETERMINE WHETHER THIS MAPPING SHOULD BE SAVED OR DELETED WHEN FIRST IDENTIFIED.
		//SOMEHOW WE NEED TO NOT ADD SO MANY OPTIONS (PROBLEM C4 HAS 300,000+ COMBINATIONS)

		int fig1Count = figure1Objects.size();
		int fig2Count = figure2Objects.size();
		
		if(totalTransformationCount == -1) {
			for(int i =0; i < mapTransformations.size(); ++i) {
				totalTransformationCount += mapTransformations.get(i).size();
			}
		}
		
		if(totalTransformationCount > (fig1Count + fig2Count) )
			return false;
		
		return true;
		
	}
	
	public String generateUniqueMappingKey() {
		//USED TO CREATE A UNIQUE MAPPING KEY TO DETERMINE WHETHER WE HAVE A DUPLICATE KEY QUICKLY OR NOT
		//SOMEHOW WE NEED TO NOT ADD SO MANY OPTIONS (PROBLEM C4 HAS 300,000+ COMBINATIONS)
		String key = "";

		for(int i = 0; i < figure1Objects.size(); ++i) {
			key += generateObjectNameForUniqueMappingKey(figure1Objects.get(i).getValue().getName());
			key += "-";
			key += generateObjectNameForUniqueMappingKey(figure2Objects.get(i).getValue().getName());
			key += "|";
		}
	
		return key;
	}

	private String generateObjectNameForUniqueMappingKey(String theName) {
		if(theName.contains(AgentDiagramComparison.dummyRavensObjectString))
			return "DUMMY";
		return theName;
	}
	
}
