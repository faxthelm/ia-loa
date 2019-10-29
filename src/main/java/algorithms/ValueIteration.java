package algorithms;

import java.util.HashMap;
import java.util.List;

import bean.State;

public class ValueIteration {
	
	private HashMap<Integer, HashMap<State, Double>> iterations;
	private HashMap<String, Object> problem;
	private List<State> states;

	public ValueIteration(HashMap<String, Object> problem) {
		this.problem = problem;
		this.states = (List<State>) problem.get("states");
		this.iterations = new HashMap<>();
	}
	
	public void initialize() {
		State goal = (State) problem.get("goalState");
		HashMap<State, Double> arbitraryValues = new HashMap<>();
		for(State state : states) {
			Double val = (double) (Math.abs(goal.getX()-state.getX()) + Math.abs(goal.getY()-state.getY()));
			arbitraryValues.put(state,val);
		}
		iterations.put(1, arbitraryValues);
	}
	
	public void calculate() {
		initialize();
		
	}
}
