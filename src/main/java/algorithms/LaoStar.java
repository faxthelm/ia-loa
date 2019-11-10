package algorithms;

import java.util.HashMap;
import java.util.List;

import bean.Action;
import bean.Policy;
import bean.State;
import utils.ProblemManager;

public class LaoStar {
	
	private HashMap<String, List<Action>> possibleActions;
	private List<State> states;
    private Policy policy;
	//TODO definir estrutura dos grafos
    //private graph1;
	//private graph2;
    private HashMap<String, Double> valuesV;
    
	public LaoStar() {
		this.states = ProblemManager.getStates();
		this.policy = new Policy(new HashMap<>());
    	this.possibleActions = ProblemManager.generatePossibleActions();
	}
	
	public String calculateHeuristic() {
		return null;
		
	}

}
