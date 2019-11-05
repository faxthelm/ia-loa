package algorithms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Action;
import bean.Policy;
import bean.State;
import utils.ProblemManager;

public class ValueIteration {

    private HashMap<Integer, HashMap<String, Double>> iterations;
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
        iterations.put(0, arbitraryValues);
    }

    public void calculate() {
        initialize();
        double residual = Double.MAX_VALUE;
        double sumValue = 0.0;
        double sumValuePrevious = 0.0;
        int iteration = 1;
        HashMap<String, Double> values = new HashMap<>();

        while (residual > 0.001) {
        	System.out.println(residual);
            System.out.print("ITERATION" + iteration + "\n");
            for (State state : states) {
                double value = calculateFunctionValue(state, (iteration - 1));
                values.put(state.toString(), value);
                sumValue += value;
            }
            iterations.put(iteration, values);
            residual = sumValue - sumValuePrevious;
            sumValuePrevious = sumValue;
            sumValue = 0;
            iteration++;
        }

    }

    public double calculateFunctionValue(State state, int previousIteration) {
        double minValue = Double.MAX_VALUE;
        HashMap<String, Double> previousValues = iterations.get(previousIteration);
        HashMap<String, Double> values = new HashMap<>();
        List<Action> applicableActions = ProblemManager.getApplicableActions(state);
        for (Action action : applicableActions) {
            double value = action.getProbability() * (action.getCost() + previousValues.get(action.getToState().toString()));
            values.merge(action.getName(), value, (a, b) -> (a + b));
        }
        for (Map.Entry<String, Double> entry : values.entrySet()) {
            if (minValue > entry.getValue()) {
                minValue = entry.getValue();
                policy.getPolicyStatements().put(state, entry.getKey());
            }
        }
        //System.out.print("V(" + state.toString() + ") = " + minValue + "\n");
        return minValue;
    }

    public Policy getPolicy() {
        return policy;
    }
}
