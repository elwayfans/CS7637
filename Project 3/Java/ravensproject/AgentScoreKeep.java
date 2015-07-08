package ravensproject;

public class AgentScoreKeep {
	
	public String name = "";
	public enum scoreStatus { CORRECT, INCORRECT, SKIPPED};
	public scoreStatus status;
	
	public AgentScoreKeep(String name) {
		this.name = name;
		this.status = scoreStatus.SKIPPED;
	}
	
	public void Print() {
		System.out.println(name + ": " + getStatusName());
	}
	
	public String getStatusName() {
		if(status == scoreStatus.CORRECT)
			return "Correct";
		if(status == scoreStatus.INCORRECT)
			return "Incorrect";
		return "Skipped";
	}
	

}
