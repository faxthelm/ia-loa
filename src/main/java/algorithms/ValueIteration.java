package algorithms;

import bean.Action;
import bean.Policy;
import bean.State;
import utils.ProblemManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValueIteration {

	private HashMap<String, Double> iterations;
	private List<State> states;
	private Policy policy;

	public ValueIteration() {
		this.states = ProblemManager.getStates();
		this.iterations = new HashMap<>();
		this.policy = new Policy(new HashMap<>());
	}

	public void initialize() {
		State goal = ProblemManager.getGoalState();
		HashMap<String, Double> arbitraryValues = new HashMap<>();
		for (State state : states) {
			Double val = (double) (Math.abs(goal.getX() - state.getX()) + Math.abs(goal.getY() - state.getY()));
			arbitraryValues.put(state.toString(), val);
		}
		iterations = arbitraryValues;
	}

	public String calculate() {
		long begin = System.currentTimeMillis();
		initialize();
		double residual = Double.MAX_VALUE;
		double sumValue = 0.0;
		double sumValuePrevious = 0.0;
		while (residual > 0.001) {
			HashMap<String, Double> values = new HashMap<>();
			for (State state : states) {
				double value = calculateFunctionValue(state);
				if(state.equals(ProblemManager.getGoalState())) value = 0;
				values.put(state.toString(), value);
				sumValue += value;
			}
			iterations = new HashMap<>(values);
			residual = sumValue - sumValuePrevious;
			sumValuePrevious = sumValue;
			sumValue = 0;
		}
		Long algorithmTime = System.currentTimeMillis() - begin;
		StringBuilder result = new StringBuilder();
		result.append("\nALGORITHM TIME: ")
			.append(algorithmTime).append(" ms\n\n")
			.append("LAST VALUE ITERATION\n");
		iterations.forEach((k, v) -> result.append(String.format("V(robot-%s) = %f\n", k, v)));
		System.out.println(result);
		System.out.println(policy.toString());
		System.out.println(createMap());
		return "";

	}

	public double calculateFunctionValue(State state) {
		double minValue = Double.MAX_VALUE;
		HashMap<String, Double> values = new HashMap<>();
		List<Action> applicableActions = ProblemManager.getApplicableActions(state);
		for (Action action : applicableActions) {
			double value = action.getProbability()
					* (action.getCost() + iterations.get(action.getToState().toString()));
			values.merge(action.getName(), value, (a, b) -> (a + b));
		}
		for (Map.Entry<String, Double> entry : values.entrySet()) {
			if (minValue > entry.getValue()) {
				minValue = entry.getValue();
				policy.getPolicyStatements().put(state.toString(), entry.getKey());
			}
		}
		return minValue;
	}
	
	public String createMap() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( "GRID WITH OPTIMAL POLICY: \n");
        int gridSize = 20;
        for(int y = gridSize; y>0; y--) {
            for(int x = 1 ; x<=gridSize; x++) {
                String state = "at-x" + x + "y" + y;
                String action = policy.getPolicyStatements().get(state);
                if(action == null) {
                    stringBuilder.append(" P ");
                } else if (action.equals("move-east")) {
                        stringBuilder.append(" > ");
                } else if (action.equals("move-west")) {
                    stringBuilder.append(" < ");
                } else if (action.equals("move-south")) {
                    stringBuilder.append(" V ");
                } else if (action.equals("move-north")){
                    stringBuilder.append(" ^ ");
                }
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
